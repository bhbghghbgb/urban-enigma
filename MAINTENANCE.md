# MAINTENANCE.md

Engineering reference for `bhbghghbgb/urban-enigma`. **Assume you have zero context about
this repo.** Last updated by the 2026-10 dependency-remediation pass.

---

## 1. What this repo is

A Vietnamese university project ("do an nang cao") for an Android food-delivery app.
It is **not** a typical JS monorepo despite the folder names.

### 1.1 Layout

```
/
├── .github/                       ISSUE_TEMPLATE only → no CI workflows exist
├── .idea/                         committed JetBrains project files
├── README.md                      setup guide (Firebase, MongoDB, ngrok, IP whitelist)
├── SECURITY.md                    unmodified GitHub template
├── LICENSE
├── cant-push-secrets-error.txt    committed artifact, unrelated to the build
├── MAINTENANCE.md                 this file
├── backend/
│   └── backend-nodejs/            ← THE ONLY JS/PACKAGE-MANAGED PROJECT IN THE REPO
│       ├── package.json           npm manifest (name: "nodejs")
│       ├── yarn.lock              Yarn Berry lockfile (__metadata.version: 10)
│       ├── .yarnrc.yml            Yarn Berry config (nodeLinker: node-modules)
│       ├── .node-version          "24"
│       ├── eslint.config.mjs      ESLint flat config (CommonJS sourceType)
│       ├── index.js               Express entrypoint, port 3001
│       ├── app/{config,controllers,middleware,models,routes,service,utils}/
│       ├── public/{favicon.ico,main.js,firebase-auth-test.html}
│       └── views/index.html       89 KB single-page frontend (jQuery + Firebase compat SDK)
└── frontend/
    ├── cart-app/                  Android app (Kotlin + Compose), com.example.cart
    ├── customer-app/              Android app (Kotlin + Compose), com.doansgu.cafectm
    ├── QrCodeScanner/             Android app (Kotlin + Compose), com.example.qrcodescanner
    └── shipper-app/               Android app (Kotlin + Compose), com.example.delivery_app
```

### 1.2 Dead / absent directories

| Path | Status |
| --- | --- |
| `backend/backend-fastapi/` | **Deleted** in `837fa8c`. Reachable only via `git log --all`. Contained `pyproject.toml`, `uv.lock`, `Pipfile` — no Python survives in the tree. |
| `backend/backend-hieu copy 2/`, `backend/backend-firebase-auth/`, `backend/backend-firebase-storage/`, `backend/app/` | Deleted in `837fa8c`. |
| `frontend/*/` | **NOT web apps.** They are Android Studio projects. There is no `package.json`, `yarn.lock`, `pnpm-lock.yaml` or `package-lock.json` anywhere under `frontend/`. Any tooling that assumes "frontend = npm" is wrong. |
| `.github/workflows/` | Does not exist. There is no CI to run and none to break. |
| `Dockerfile`, `docker-compose.yml`, `Pipfile`, `pyproject.toml`, `requirements*.txt` | None anywhere in the repo. |
| `cant-push-secrets-error.txt` | Committed artifact, unrelated to the build. Leave it alone. |

`backend/backend-nodejs/package.json` and `backend/backend-nodejs/yarn.lock` are the only
manifest and lockfile in the entire tracked tree.

---

## 2. Toolchains

| Tool | Version | Status |
| --- | --- | --- |
| Node.js | `>= 22.13.0` (`engines`), `.node-version` pins `24` | required |
| Yarn | `4.18.1` Berry, pinned by `packageManager` — use Corepack, not Yarn Classic | required |
| ESLint | 10.12.0 (flat config, `eslint.config.mjs`) | required |
| Prettier | 3.9.10 | required |
| Java | 17+ | required by the four Android projects — **not available at time of maintenance** |
| Gradle | via the committed wrapper in each Android project | **not available at time of maintenance** |
| Android SDK | compileSdk 34 | **not available at time of maintenance** |

The Android gaps blocked only `frontend/*` — see section 4.

**Not required:** protoc / Rust. There is no gRPC or protobuf codegen in this repo.
`protobufjs@7.x` (transitive from `firebase-admin`) ships prebuilt JS and builds fine with
`enableScripts: true` in `.yarnrc.yml`.

---

## 3. Upgrade and verification — `backend/backend-nodejs`

All Dependabot alerts in this repo point at this project's `yarn.lock`.

```sh
cd backend/backend-nodejs

# 1. bump every direct + dev dependency to the latest STABLE tag
npx --yes npm-check-updates@latest --target latest -u

# 2. regenerate the lockfile: delete yarn.lock, then install.
#    Deleting it is essential — a stale Berry lockfile is why the earlier
#    "fix Dependabot alerts" commits (df56151, 5bb6ad1) cleared nothing.
corepack yarn install --no-immutable

# 3. verify
corepack yarn lint                          # must be silent, exit 0
corepack yarn npm audit --all --recursive   # must report 0 vulnerabilities
```

`--no-immutable` is only needed right after deleting the lockfile; a plain
`corepack yarn install` enforces `--immutable` and is the right check that the committed
lockfile matches `package.json`.

> **Never hand-edit `yarn.lock`.** Fixes come from changing `package.json` ranges or
> dropping packages and re-resolving.

### 3.1 Run the app

```sh
cd backend/backend-nodejs
corepack yarn start          # node index.js, listens on 3001
```

Needs a live MongoDB Atlas instance and a Firebase project. It will not start unless the
Firebase JSON that `app/config/connect.js` requires by a fixed filename has been placed
in `app/config/` locally (see README for the download step); it is not committed.
`initFirebase()` recovers from that and continues, but `initMongodb()` calls
`process.exit(1)` when the database is unreachable.

### 3.2 Offline Express 5 route check

Express 5 validates path patterns (path-to-regexp v8) at *registration* time, so an
incompatible route only fails at boot. This checks that without a server or network
access. Save as `_route-smoke.tmp.js` inside `backend/backend-nodejs`, run
`node _route-smoke.tmp.js`, then delete it — it must live there because
`app/middleware/favicon.js` resolves `favicon.ico` from `require.main.filename`.

```js
const path = require("path"), ROOT = __dirname, r = (p) => require(path.join(ROOT, p));
const express = require("express"), cors = require("cors");
const bodyParser = require("body-parser"), logger = require("morgan");
const { ipAccessControl } = r("./app/middleware/ipAccessControl");
const { error2jsonHandler, error2jsonHandler404 } = r("./app/middleware/error2jsonHandler");
const { servePublic } = r("./app/middleware/servePublic");
const { favicon } = r("./app/middleware/favicon");
const { firebaseAuthBearer } = r("./app/middleware/firebaseAuthPassport");
const { userRole } = r("./app/middleware/connectRoles");

const app = express();
app.use(logger("combined"), ipAccessControl, servePublic, favicon, userRole.middleware());
app.use(bodyParser.urlencoded({ extended: true }), bodyParser.json(), cors(), express.json());
for (const [m, p] of [
  ["/products", "productRouter"], ["/category", "categoryRouter"], ["/account", "accountRouter"],
  ["/orders", "orderRouter"], ["/cart", "cartRouter"], ["/rating", "ratingRouter"],
  ["/user", "userRouter"], ["/delivery", "deliveryRouter"], ["/accountinfo", "accountInfoRouter"],
  ["/oders2", "order.route"], ["/auth", "authRouter"], ["/notification", "notificationRouter"],
  ["/test", "testRouter"],
]) app.use(m, firebaseAuthBearer, userRole.is("customer"), r(`./app/routes/${p}`));
app.use("/", r("./app/routes/publicFilesRouter"));
app.use(error2jsonHandler, error2jsonHandler404);
console.log(`OK: express ${require("express/package.json").version} mounted ${app.router.stack.length} layers`);
```

Expected on the current tree: `OK: express 5.3.0 mounted 51 top-level layers`.

---

## 4. Android projects — untouched, and why

All four are real Android Studio projects (Gradle wrapper jar + `gradlew` are committed).
They were skipped because JDK 17+, Gradle and the Android SDK were not available, so they
could not be compiled or dependency-locked, and because they produce **zero Dependabot
alerts** (the Dependabot config tracks only `backend/backend-nodejs/yarn.lock`).

| Project | Gradle wrapper | AGP | Kotlin | compileSdk / targetSdk | Compose BOM |
| --- | --- | --- | --- | --- | --- |
| `frontend/cart-app` | 8.4 | 8.3.2 (`gradle/libs.versions.toml`) | 1.9.0 | 34 / 34 | 2024.04.01 |
| `frontend/customer-app` | 8.6 | 8.4.0 | 1.8.10 (android), 1.9.22 (jvm/serialization) | 34 / 34 | 2024.05.00 |
| `frontend/QrCodeScanner` | 8.0 | 8.1.1 | 1.8.10 | 34 / 33 | 2024.04.01 |
| `frontend/shipper-app` | 8.6 | 8.4.0 | 1.8.10 (android), 1.9.22 (jvm/serialization) | 34 / 33 | 2024.05.00 |

To work on them, from within the project directory:

```sh
./gradlew wrapper --gradle-version <target>
./gradlew dependencyUpdates     # com.autonomousapps.dependency-analysis, 2 of the 4 projects
./gradlew :app:assembleDebug    # the only real verification
```

AGP 8.x → 9.x and Kotlin 1.8/1.9 → 2.x are source-level migrations
(`kotlinOptions { compose = true }`, deprecated Compose APIs, and
`kotlinCompilerExtensionVersion` coupling to the Kotlin compiler version). They cannot be
done blind. Treat it as its own task.

---

## 5. Gotchas

1. **`.orig` leftovers from unresolved merge conflicts are committed.** ESLint ignores
   them (extension is `.orig`, not `.js`): `backend/backend-nodejs/.gitignore.orig`
   (byte-identical to `.gitignore`), `index.js.orig`, `app/config/_APP.js.orig`,
   `app/config/connect.js.orig`, `views/index.html.orig`, and
   `frontend/customer-app/.idea/deploymentTargetDropDown.xml.orig`.
2. **`.idea/` is committed** at the repo root and under each `frontend/*` project.
3. **The root `.gitignore` does NOT ignore `node_modules`.** Only
   `backend/backend-nodejs/.gitignore` does. Never `git add -A` from the repo root
   without checking `git status` first — you will commit ~100 MB of `node_modules`.
4. **`index.js` has two lazy-load layers.** `initConnection(main)` runs at line 3 and
   `main()` re-requires every module itself. The requires at the top of the file look
   duplicated but are intentional — do not "tidy" them.
5. **`app/middleware/favicon.js` resolves `public/favicon.ico` from
   `require.main.filename`**, not `__dirname`. It only works when `index.js` is the main
   module; any other entrypoint crashes with `ENOENT ... public/favicon.ico`.
6. **`public/main.js` must stay a classic browser script.** `views/index.html` calls
   `getUniqueFileName()` and `uploadImage()` from an inline `<script>`, so those
   top-level declarations are intentional globals. `eslint.config.mjs` disables
   `no-unused-vars` for `public/**/*.js` for exactly this reason.
7. **`app/config/_APP.js` reads `DATABASE_CONNECTION_URL` from the environment**, with a
   non-functional placeholder URI as the fallback. Anything not listed in `WHITELIST_IPS`
   is rejected with `403 Forbidden, this client IP ... is not whitelisted.`
8. **`frontend/cart-app` cannot build anywhere except on the original author's machine.**
   `app/build.gradle.kts` pulls the ZaloPay AAR in through a hard-coded absolute path; the
   three sibling apps use a relative path instead. Fix the path before trying to build it.
9. **Duplicate, still-wired-up code.** `app/routes/order.route.js` +
   `app/controllers/oder.controller.js` + `app/service/oder.service.js` ("oder", not
   "order") duplicate `orderRouter.js` / `orderController.js`; both are mounted
   (`/orders` and `/oders2`). `product.service.js` still carries a
   `getProductsWithRating_Hung` (the original author's name).
10. **There are no tests.** `yarn test` is the stock `exit 1` stub.
11. **`public/firebase-auth-test.html` ships placeholder Firebase web config values.**
    Paste your own project's web config into `firebaseConfig` to use that sign-in test page.

---

## 6. What the 2026-10 pass changed

### 6.1 Direct / dev dependencies

| Package | Before | After |
| --- | --- | --- |
| express | `^5.2.1` | `^5.3.0` |
| firebase-admin | `^14.1.0` | `^14.5.0` |
| mongoose | `^9.7.3` | `^9.11.2` |
| morgan | `^1.11.0` | `^1.12.1` |
| eslint | `^10.6.0` | `^10.12.0` |
| globals | `^17.7.0` | `^17.13.0` |
| prettier | *(script existed, no binary)* | `^3.9.10` (new devDependency) |
| mysql | `^2.18.1` | **removed — never required from source** |
| jsonwebtoken | `^9.0.3` | **removed — never required from source** |
| ngrok | `^5.0.0-beta.2` | **removed — never required from source; also the sole owner of `extract-zip`, `uuid@8`, `yaml` and `websocket-driver`** |

Also: added `engines.node ">=22.13.0"`, added `.node-version`, bumped `packageManager`
`yarn@4.17.0` → `yarn@4.18.1`, added `lint` / `lint:fix` / `format` / `format:check` scripts.

### 6.2 Transitive dependencies (lockfile regenerated from scratch)

| Package | Was | Now |
| --- | --- | --- |
| proxy-addr | 2.0.7 | **2.0.8** |
| brace-expansion | 2.1.1 / 5.0.6 | **2.1.7 / 5.0.12** |
| @fastify/busboy | 3.1.1 | **3.2.3** |
| @grpc/grpc-js | 1.14.4 | **1.14.6** |
| morgan | 1.11.0 | **1.12.1** |
| body-parser | 2.2.2 / 2.3.0 | **2.3.0** (single entry) |
| qs | 6.15.2 / 6.15.3 | **6.16.1** |
| @humanfs/node | 0.16.6 | **0.16.8** |
| websocket-driver | 0.7.4 | **0.7.5** |
| flatted | 3.3.3 | **3.4.4** |
| jws | 3.2.2 + 4.0.0 + 4.0.1 | **4.0.1 only** |
| extract-zip, ngrok, form-data, uuid, yaml, @tootallnate/once | present | **gone from the tree** |

`corepack yarn npm audit --all --recursive` → **0 vulnerabilities**. Two *deprecation*
notices remain and are not advisories: `glob@10.5.0` (via `rimraf@5.0.10`) and
`node-domexception@1.0.0` (via `fetch-blob@3.2.0`).

### 6.3 Source changes

| File | Change | Why |
| --- | --- | --- |
| `public/main.js` | removed `/* eslint-env browser */` | ESLint flat config rejects `eslint-env` comments |
| `eslint.config.mjs` | `public/**/*.js` sets `sourceType: "script"` + `no-unused-vars: "off"` | gotcha 6 |
| `app/config/connect.js` | dropped `useNewUrlParser` / `useUnifiedTopology` + a stale `eslint-disable` | removed in Mongoose 6, no-ops on Mongoose 9 |
| `app/service/product.service.js` | removed 7 no-op `try { … } catch (e) { throw e }` wrappers; restored the missing `Rating` import and missing `return` in `getProductsWithRating` | `no-undef` crash plus dead code |
| `index.js` | removed duplicated top-level `firebaseAuthBearer` / `userRole` requires | gotcha 4 |
| `app/service/cartService.js` | removed unused `Customer` / `Product` imports | `no-unused-vars` |
| `app/controllers/oder.controller.js` | `const order = await …` → `await …` | `no-unused-vars` |
| `app/config/_APP.js` | `DATABASE_CONNECTION_URL` reads the environment, placeholder fallback | configurable without editing the file |
| `public/firebase-auth-test.html` | placeholder web config values | gotcha 11 |
| `README.md` | ngrok step no longer claims ngrok is an npm dependency | ngrok left `dependencies` |

`yarn lint` is clean (0 errors, 0 warnings) and all 14 routers register on Express 5.3.0.

---

## 7. Left unfixed, and why

| Item | Reason |
| --- | --- |
| All four `frontend/*` Android projects (AGP, Kotlin, Compose BOM, Gradle wrapper, AndroidX / Firebase / Retrofit pins) | JDK, Gradle and Android SDK were unavailable; the AGP 8→9 and Kotlin 1.8→2 bumps are source-level migrations that cannot be verified. They generate zero Dependabot alerts. See section 4. |
| `backend/backend-fastapi` and the other Python projects | Deleted in `837fa8c`. Nothing to upgrade. |
| `.orig` merge leftovers, committed `.idea/` trees, duplicated `oder` code | Cleanup that could destroy something the author still needs; listed as gotchas instead. |
| `glob@10.5.0` / `node-domexception@1.0.0` deprecation notices | Transitive, no advisory, no fixed-version path available from this project. |
| Dependabot alert closure | GitHub closes alerts when the commit reaches the remote and the manifest is re-scanned; nothing more to do locally. |
