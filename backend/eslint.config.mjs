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
];
