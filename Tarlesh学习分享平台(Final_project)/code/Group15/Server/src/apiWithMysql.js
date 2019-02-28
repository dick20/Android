"use strict";
const connection = require('./mysqlConnection');
const express = require('express');
const multer  = require('multer');
const upload = multer({ dest: '../filestore/' });
const validator = require('./validator');
const shortid = require('shortid');
const router = express.Router();
const jwt = require('jsonwebtoken');
const bodyParser = require('body-parser');
const tokenKEY = 'lt-666';
const fs = require('fs');
const path = require('path');
const uuid = require('uuid/v4');

const filePath = '../filestore/';

const admin = 'admin';//make the account at DB and then change here

router.post('/api/login/trylogin',(req,res) => {
  let paramsErr = validator.checkLogin(req.body);
  if (paramsErr.err) {
    res.send({sign: false, msg: paramsErr});
  } else {
    var sql = 'select * from users where uname = ? and pwd = ?';
    var param = [req.body.username, req.body.password];
    connection.query(sql,param,function (err, result) {
      if(err){
        console.log(err);
        res.send({sign: false, msg: err.sqlMessage});
      } else {
        console.log(result);
        if(result.length == 1){
          var token = jwt.sign(
            {
              uid : result[0].uid,
              username : result[0].uname
            }, 
            tokenKEY, 
            {
              expiresIn : 60*60*24// 授权时效24小时
            });
            res.send({sign: true, msg: 'successed!',
                        token:token});
        }
        else {
          res.send({sign: false, msg: 'the user or password is wrong'});
        }
      }
    });
  }
});

router.post('/api/detail/trylogout',(req,res) => {
  res.send({sign: true});
});

router.post('/api/register/tryregister',(req,res) => {
  let paramsErr = validator.checkRegister(req.body);
  if (paramsErr.err) {
    res.send({sign: false,msg: paramsErr, error: 'Something wrong happened'});
  } else {
    //insert
    var uid = shortid.generate();
    var sql = 'insert into users(uid, uname, pwd, email, phone) values(?,?,?,?,?)';
    var param = [uid ,req.body.username, req.body.password, req.body.email, req.body.phone];
    connection.query(sql,param,function (err, result) {
      if(err){
        res.send({
          sign: false, 
          msg: err.sqlMessage});
      } else {
        var token = jwt.sign(
              {
                uid : uid,
                username : req.body.username
              }, 
              tokenKEY, 
              {
                expiresIn : 60*60*24// 授权时效24小时
              });
        res.send({
          sign: true,
          token: token});
      }
    });
  }
});


router.use(function(req, res, next) {
  // 拿取token 数据 按照自己传递方式写
  var token = req.headers['x-access-token']; //req.body.token || req.query.token || req.headers['x-access-token'];
  if (token) {      
      // 解码 token (验证 secret 和检查有效期（exp）)
      jwt.verify(token, tokenKEY, function(err, decoded) {      
            if (err) {
              return res.send({ 
                sign: false, 
                msg: 'token over time' });    
            } else {
              // 如果验证通过，在req中写入解密结果
              req.decoded = decoded;  
              //console.log(decoded)  ;
              next(); //继续下一步路由
        }
      });
    } else {
      // 没有拿到token 返回错误 
      return res.status(403).send({ 
          sign: false, 
          msg: 'token not found' 
      });
    }
  });


// get the detail for drawer
router.post('/api/detail/getuser', (req, res) => {
  // var result = {};
  var sql = 'select * from users where uname = ?';
  var param = [req.decoded.username];
  connection.query(sql,param,function (err, result) {
    if(err){
      res.send({sign: false, msg: err.sqlMessage});
    } else {
      if(result.length != 1){
        res.send({sign: false, msg: 'you have not logined'});
      } else {
        res.send({sign: true, data: result[0],msg: ''});
      }
    }
  });
});

router.post('/api/file/fileList', (req, res) => {
    console.log(req.body);
    
    var sql = '';
    if(req.body.rankType == undefined || req.body.rankType == 0){
        sql = 'call getFileList(?)'
    }else if(req.body.rankType == 1){
        sql = 'call fileRank(?)'
    }else if(req.body.rankType == 2){
        sql = 'call fileRank2(?)'
    }else{
        res.send({
            sign:false,
            msg:'type error'
        });
        return;
    }

    var param = [];
    if(req.body.filename == undefined){
        param[0] ="%%";
    }else{
        param[0]="%"+req.body.filename+"%";
    }

    connection.query(sql,param,function (err, results) {
      if(err){
        res.send({sign: false, msg: err.sqlMessage});
      } else {
          res.send({sign: true, data: results[0],msg: ''});
      }
    });
});

router.post('/api/file/fileList', (req, res) => {
    var sql = '';
    if(req.body.rankType == undefined || req.body.rankType == 0){
        sql = 'call getFileList()'
    }else if(req.body.rankType == 1){
        sql = 'call fileRank()'
    }else if(req.body.rankType == 2){
        sql = 'call fileRank2()'
    }else{
        res.send({
            sign:false,
            msg:'type error'
        });
        return;
    }

    connection.query(sql/*,param*/,function (err, results) {
      if(err){
        res.send({sign: false, msg: err.sqlMessage});
      } else {
          res.send({sign: true, data: results[0],msg: ''});
      }
    });
});

router.use('/api/file/*', function(req, res, next) {
    var contype = req.headers['content-type'];
    if (contype === 'multipart/form-data') {
        return next();
    } else {
        router.use(bodyParser.json({ type: '*/*', limit: '10mb'}));
        router.use(bodyParser.urlencoded({extended: true}));
        next();
    }
  });

router.post('/api/file/upload', upload.single('file'), function (req, res, next) {
  // req.file is the `avatar` file
  // req.body will hold the text fields, if there were any
  // 看起来还是把上传下载这一类搞个过程会好点，方便0.0
  var sql = 'call upload(?,?,?,?)';
  var sqlParams = [req.file.filename, req.file.originalname, req.decoded['uid'], req.body['description']];
  connection.query(sql, sqlParams, function (error, results) {
    if (error){
      console.log(error.sqlMessage);
      res.send({
        sign: false,
        msg:  error.sqlMessage
      });
    }
    else {
      res.send({
        sign: true,
        msg: results[0]
      });
    }
  });
});

router.post('/api/file/download', (req, res) => {

    // 看起来还是把上传下载这一类搞个过程会好点，方便0.0
    var sql = 'call download(?,?,?)';
    var sqlParams = [shortid.generate(), req.body['fid'], req.decoded['uid']];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error)
        res.send({
          sign: false,
          msg:  error.sqlMessage
        });
      else if(results[0].length<=0){
        res.send({
          sign: false,
          msg:  'no such file'
        });
      }else {
        console.log(results);
        
          const file = fs.readFileSync('../filestore/'+req.body['fid']);
          res.send({sign: true ,
                    fname:results[0][0].fname, 
                    file:file});
      }
    });
});


router.post('/api/file/upload2', function (req, res) {
  // 备用api
  // 看起来还是把上传下载这一类搞个过程会好点，方便0.0

    var filename = uuid();
    var sql = 'call upload(?,?,?,?)';
    console.log(req.body);
    
    if(req.body.file == undefined || req.body.filename == undefined 
            || req.body.description == undefined){
        res.send({sign: false,msg:"require file"})
    }else{
        fs.writeFile(filePath + filename, req.body.file, function(err) {
            if (err) {
                console.log('error in save.');
                res.send({sign: false,msg:"save file error,please retry"})
            }else{
                var sqlParams = [filename, req.body.filename, req.decoded['uid'], req.body['description']];

                connection.query(sql, sqlParams, function (error, results) {
                console.log(sqlParams);
                if (error)
                    res.send({
                    sign: false,
                    msg:  error.sqlMessage
                    });
                else {
                    res.send({
                    sign: true,
                    msg: results[0]
                    });
                }
                });
                console.log('Saved.');
            }
        });
    }


});

router.post('/api/file/download2', (req, res) => {
    var sql = 'call download(?,?,?)';
    var sqlParams = [shortid.generate(), req.body['fid'], req.decoded['uid']];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error)
        res.send({
          sign: false,
          msg:  error.sqlMessage
        });
      else {
        fs.readFile(filePath + req.body.fid, function(err, data) {
            if (err) {
                res.send({
                    sign: false,
                    msg:  'error in get file,please retry'
                  });
            }else{
                res.send({sign: true ,fname:results[0].fname, file:data});
            }
        });
          
      }
    });
});


router.post('/api/file/detail', (req, res) => {
  var sql = 'call getFileDetail(?)';
  var sqlParams = [req.body['fid']];

  connection.query(sql, sqlParams, function (error, results) {
    console.log(sqlParams);
    if (error)
      res.send({
        sign: false,
        msg:  "Failed to return books,please try again."
      });
    else {
      if(results.length != 0){
        res.send({sign: true ,
                  file:results[0],
                  comments: results[1],
                  msg:''});
      } else {
        res.send({sign: false,msg:'error to find the file'});
      }
    }
  });
});

router.post('/api/file/comment', (req, res) => {
    console.log(req.body);
    
      var sql = 'call commentOnFile(?,?,?,?,?)';
      var sqlParams = [shortid.generate(), req.body['fid'], req.decoded['uid'], req.body['msg'],req.body['points']];
    
      connection.query(sql, sqlParams, function (error, results) {
        console.log(sqlParams);
        if (error)
          res.send({
            sign: false,
            msg:  error.sqlMessage
          });
        else {
            console.log(results);
            
          if(results[0].length != 0){
            res.send({sign: true ,msg:'',comments:results[0]});
          } else {
            res.send({sign: false,msg:'could not comment,some error happened'});
          }
        }
      });
});

router.post('/api/qa/getQuestionList', (req, res) => {
    var sql = 'call getQuestionList()';
    var sqlParams = [];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error)
        res.send({
          sign: false,
          msg:  "Failed to get list,please try again."
        });
      else {
          res.send({sign: true ,msg:'',
                    question:results[0],
                    answers:results[1]});
      }
    });
});


router.post('/api/qa/getQuestion', (req, res) => {
    var sql = 'call getQuestion(?)';
    var sqlParams = [req.body['qid']];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error)
        res.send({
          sign: false,
          msg:  error.sqlMessage
        });
      else {
          res.send({sign: true ,data:results});
      }
    });
});

router.post('/api/qa/createQuestion', (req, res) => {
    var sql = 'call createQuestion(?,?,?,?)';
    var sqlParams = [shortid.generate(), req.decoded['uid'],req.body['title'], req.body['content']];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error ){
        res.send({
          sign: false,
          msg:  error.sqlMessage
        });
      }
      else if(results.length == 0){
        res.send({
          sign: false,
          msg:  "Failed to create question,please try again."
        });
      }else{
          res.send({sign: true ,msg:'',data:results[0][0]});
      }
    });
});

router.post('/api/qa/answerQuestion', (req, res) => {
    var sql = 'call answerQuestion(?,?,?,?)';
    var sqlParams = [shortid.generate(), req.body['qid'],req.decoded['uid'],req.body['content']];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error)
        res.send({
          sign: false,
          msg:  error.sqlMessage
        });
      else {
          results.pop();
          res.send({sign: true ,msg:'',answers:results[0]});
      }
    });
});


router.post('/api/rank/userRank', (req, res) => {
    var sql = 'call userRank()';
    var sqlParams = [];
  
    connection.query(sql, sqlParams, function (error, results) {
      console.log(sqlParams);
      if (error || results.length == 0)
        res.send({
          sign: false,
          msg:  "Failed to create question,please try again."
        });
      else {
          res.send({sign: true ,data:results[0]});
      }
    });
});

module.exports = router;
