const Joi = require('joi');

const Jusername=Joi.object().keys({
  username: Joi.string().min(5).max(17).alphanum().required()
});

const Jpassword=Joi.object().keys({
  password: Joi.string().min(6).max(12).alphanum().required()
});

const Jemail=Joi.object().keys({
    email: Joi.string().email().required()
});

const Jphone= Joi.object().keys({
  phone: Joi.string().regex(/^[1-9]([0-9]){10}$/).required()
})

var checkRegister = function (msg) {
  let usernameR = Joi.validate(msg, Jusername, {allowUnknown: true}),
      passwordR = Joi.validate(msg, Jpassword, {allowUnknown: true}),
      emailR = Joi.validate(msg, Jemail, {allowUnknown: true}),
      phoneR = Joi.validate(msg, Jphone, {allowUnknown: true});

  return {
    err: usernameR.error!==null || passwordR.error!==null || emailR.error!==null || phoneR.error!==null,
    username: usernameR.error==null?'':usernameR.error.details[0].message,
    password: passwordR.error==null?'':passwordR.error.details[0].message,
    email: emailR.error==null?'':emailR.error.details[0].message,
    phone: phoneR.error==null?'':'phone must be number but not begin with "0" in length 11'
  }
};

var checkLogin = function (msg) {
  let usernameR = Joi.validate(msg, Jusername, {allowUnknown: true}),
      passwordR = Joi.validate(msg, Jpassword, {allowUnknown: true});
  return {
    err: usernameR.error!==null || passwordR.error!==null,
    username: usernameR.error==null?'':usernameR.error.details[0].message,
    password: passwordR.error==null?'':passwordR.error.details[0].message
  }
};

let checkUsername = function (msg) {
  let usernameR = Joi.validate({username: msg}, Jusername);
  return usernameR.error==null?'':usernameR.error.details[0].message;
};

let checkPassword = function (msg) {
  let passwordR = Joi.validate({password: msg}, Jpassword);
  return passwordR.error==null?'':passwordR.error.details[0].message;
};

let checkEmail = function (msg) {
  let emailR = Joi.validate({email: msg}, Jemail);
  return emailR.error==null?'':emailR.error.details[0].message;
};

let checkPhone = function (msg) {
  let phoneR = Joi.validate({phone: msg}, Jphone);
  return phoneR.error==null?'':'phone must be number but not begin with "0" in length 11';
}

const validator = {
  'checkUsername': checkUsername,
  'checkEmail': checkEmail,
  'checkPhone': checkPhone,
  'checkPassword': checkPassword,
  'checkRegister': checkRegister,
  'checkLogin': checkLogin
};

module.exports = validator;
