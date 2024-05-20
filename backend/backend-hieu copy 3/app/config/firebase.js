// Import the functions you need from the SDKs you need
const firebase = require('firebase');
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyDUXentN0vzbiV-WnhfYQ37JzIIHAf_rfk",
    authDomain: "coffeeapp-56990.firebaseapp.com",
    projectId: "coffeeapp-56990",
    storageBucket: "coffeeapp-56990.appspot.com",
    messagingSenderId: "931096711999",
    appId: "1:931096711999:web:652612b031d0e12aae398a",
    measurementId: "G-9D1MVNSXG8"
  };
  
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
const db=firebase.firestore();
const User = db.collection("Users");
module.exports = Users;

