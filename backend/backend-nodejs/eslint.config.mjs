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
        files: ["public/**/*.js"], // Target files in the /views directory
        languageOptions: {
            globals: globals.browser, // Apply browser-specific globals
        },
        rules: {
            // You can add or override rules specific to browser environment here
        },
    },
];
