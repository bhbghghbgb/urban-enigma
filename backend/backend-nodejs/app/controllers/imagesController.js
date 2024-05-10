const { dirname } = require("path");
const imagesPath = dirname(require.main.filename) + "/public/images";
exports.uploadImage = async (req, res) => {
    if (!req.files || req.files.length <= 0) {
        return res.status(400).json({ message: "No files were uploaded." });
    }
    const imageFile = req.files.file;
    const fileName = req.body.name;
    const uploadPath = `${imagesPath}/${fileName}`;

    imageFile.mv(uploadPath, (err) => {
        if (err) return res.status(500).send(err);

        res.json({ message: "Image uploaded." });
    });
};
