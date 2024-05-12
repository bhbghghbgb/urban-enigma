exports.favicon = require("serve-favicon")(
    require("path").dirname(require.main.filename) + "/public/favicon.ico",
);
