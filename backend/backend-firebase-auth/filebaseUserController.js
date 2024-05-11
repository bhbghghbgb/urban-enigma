const { StatusCodes } = require("http-status-codes");
const { createFirebaseUserByAdmin } = require("../service/firebaseAuthService");
const {
    s_findUserByFirebaseUid,
    s_findAllStaffs,
    s_findAllCustomers,
    s_createStaff,
    s_createCustomer,
    s_updateUser,
    s_deleteUser,
} = require("../service/firebaseUserService");
async function createUser(req, res) {
    try {
        const newUser = await createFirebaseUserByAdmin(req.body);
        res.json({ message: "Admin created user.", saved: newUser });
    } catch (err) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
}
const findUserByFirebaseUid = async (req, res) => {
    try {
        return res.json(await s_findUserByFirebaseUid(req.params.firebaseUid));
    } catch (err) {
        return res
            .status(StatusCodes.INTERNAL_SERVER_ERROR)
            .json({ message: err.message });
    }
};

const changeUserRole = async (req, res) => {
    try {
        const firebaseUid = req.params.firebaseUid;
        const user = await findUserByFirebaseUid(firebaseUid);
        if (!user) {
            return res
                .status(StatusCodes.NOT_FOUND)
                .json({ message: "FirebaseUid is not exist" });
        }
        user.role = req.body.role;
        await user.save();
        return res.json({ message: "Changed role successfully", saved: user });
    } catch (err) {
        return res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
};

const getAllStaffs = async (req, res) => {
    try {
        return res.json(await s_findAllStaffs());
    } catch (err) {
        return res
            .status(StatusCodes.INTERNAL_SERVER_ERROR)
            .json({ message: err.message });
    }
};

const getAllCustomers = async (req, res) => {
    try {
        return res.json(await s_findAllCustomers());
    } catch (err) {
        return res
            .status(StatusCodes.INTERNAL_SERVER_ERROR)
            .json({ message: err.message });
    }
};
async function createStaff(req, res) {
    try {
        const newStaff = await s_createStaff(req.body);
        res.json({ message: "Admin created staff.", saved: newStaff });
    } catch (err) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
}
async function createCustomer(req, res) {
    try {
        const newCustomer = await s_createCustomer(req.body);
        res.json({ message: "Admin created customer.", saved: newCustomer });
    } catch (err) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
}
async function updateUser(req, res) {
    try {
        const user = await s_updateUser(req.body);
        res.json({ message: "Admin updated User.", saved: user });
    } catch (err) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
}
async function deleteUser(req, res) {
    try {
        const user = await s_deleteUser(req.params.firebaseUid);
        res.json({ message: "Admin deleted User.", removed: user });
    } catch (err) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).json({
            message: err.message,
        });
    }
}
module.exports = {
    findUserByFirebaseUid,
    changeUserRole,
    createUser,
    getAllStaffs,
    getAllCustomers,
    createStaff,
    createCustomer,
    updateUser,
    deleteUser,
};
