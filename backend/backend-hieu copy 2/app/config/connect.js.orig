const mongoose = require('mongoose');
mongoose.connect(
  'mongodb+srv://puenwyn:W1H5UlfgQPZiYrq3@cluster0.i5dplip.mongodb.net/CoffeeApp?retryWrites=true&w=majority',
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