const mongoose = require('mongoose');
mongoose.connect(
  'mongodb+srv://phuochieudev:Bow9TTo7qqwTAwty@cluster0-phuochieudev.soyvoeu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0-PhuocHieuDev',
  {
    dbName: 'CoffeeApp',
    useNewUrlParser: true,
    useUnifiedTopology: true
  }
);

const db = mongoose.connection;

db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {
  console.log('Connected to MongoDB');
});

module.exports = db;
//Bow9TTo7qqwTAwty