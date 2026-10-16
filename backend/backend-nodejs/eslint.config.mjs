import globals from "globals";
import pluginJs from "@eslint/js";

export default [
    { files: ["**/*.js"], languageOptions: { sourceType: "commonjs" } },
    { languageOptions: { globals: globals.node } },
    pluginJs.configs.recommended,
    {
        rules: {
            "no-unused-vars": [
                "warn",
                {
                    argsIgnorePattern: "^(err|req|res|next)$",
                    caughtErrors: "all",
                    caughtErrorsIgnorePattern: "^err$",
                },
            ],
        },
    },
    // Overrides for browser-specific files
    {
        files: ["public/**/*.js"], // Target files in the /public directory
        languageOptions: {
            sourceType: "script", // classic <script>, not an ES module
            globals: globals.browser, // Apply browser-specific globals
        },
        rules: {
            // These files are loaded via <script src="..."> and expose top-level
            // functions that views/index.html calls from inline scripts, so they
            // look "unused" to the linter but are not.
            "no-unused-vars": "off",
        },
    },
];
