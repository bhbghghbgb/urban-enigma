/* eslint-env browser */

function img2base64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
    });
}
function getUniqueFileName(inputTypeFileElement) {
    const name = crypto.randomUUID();
    const ext = inputTypeFileElement.files[0].name
        .split(".")
        .pop();
    return `${name}.${ext}`;
}
async function uploadImage(inputTypeFileElement, fileName) {
    const formData = new FormData();
    formData.append("file", inputTypeFileElement.files[0]);
    formData.append("name", fileName);

    // Create an alert element
    const alertElement = document.createElement('div');
    alertElement.textContent = 'Uploading image...';
    alertElement.style.position = 'fixed';
    alertElement.style.left = '50%';
    alertElement.style.top = '50%';
    alertElement.style.transform = 'translate(-50%, -50%)';
    alertElement.style.backgroundColor = 'lightgrey';
    alertElement.style.padding = '20px';
    alertElement.style.borderRadius = '5px';
    alertElement.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.2)';
    alertElement.style.zIndex = '1000';

    document.body.appendChild(alertElement);
    // Perform the fetch request
    try {
        await fetch("/images", { method: "POST", body: formData });
        alert('Image uploaded successfully!');
    } catch (error) {
        alert('Failed to upload image.');
        console.error('Error:', error);
    } finally {
        // Remove the alert element
        if (document.body.contains(alertElement)) {
            document.body.removeChild(alertElement);
        }
    }
}
