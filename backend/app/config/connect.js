// var mysql = require('mysql');
// var sqlConnection = mysql.createConnection({
//     host: 'localhost',
//     user: 'root',
//     password: '',
//     database: 'database',
// })

// sqlConnection.connect(function (err, connection) {
//     if(err) {
//         console.log('Kết nối CSDL không thành công') 
//     } else {
//         console.log('Kết nối CSDL thành công') 
//     }
// })

// module.exports = sqlConnection;
const mongoose = require('mongoose');
mongoose.connect(
  process.env.DB_CONNECT_STRING,
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