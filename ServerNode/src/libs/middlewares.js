import express from 'express';
const morgan = require('morgan');

module.exports = app => {
    // settings
    app.set('port', process.env.PORT || 3000);
    

    // middleware
    app.use(express.json());
    app.use(morgan('dev'));
    app.use(express.urlencoded({extended: false}));
};