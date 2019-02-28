var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '123456',
  database : 'filesystem',
  charset:'UTF8_GENERAL_CI',
  dateStrings: true
});

connection.connect();

connection.query("select * from users where 1 != 1", function (error, results) {
  if (error){
    console.log(error);
    console.log('Error to connect mysql, please retry');
  }
  else {
    console.log('successed to connect mysql');
  }
});

module.exports = connection;
