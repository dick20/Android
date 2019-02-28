function errHandler(err, req, res, next) {
  console.error(err);
  res.status(404).send({
    error:err,
    msg: "something no found,please retry or call lt for help"
  });
}

module.exports = errHandler;
