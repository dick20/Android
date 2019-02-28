const api = require('./src/apiWithMysql');//use mysql
const errhandler = require('./src/errHandler');
const fs = require('fs');
const path = require('path');
const bodyParser = require('body-parser')
const express = require('express');
const app = express();

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
// app.use(express.bodyParser({uploadDir:'../filestore'}));
// app.use(express.static(path.resolve(__dirname, '../dist')));
app.use(express.static(path.resolve(__dirname, '../filestore')))
app.use(errhandler);

app.get('*', function(req, res) {
    // const html = fs.readFileSync(path.resolve(__dirname, '../dist/index.html'), 'utf-8')
    res.send({sign:true,msg:'welcome to api (✿◕‿◕✿)'});
})

app.use(api);

// 监听8088端口
app.listen(8088);
console.log('success listen in 8088');
