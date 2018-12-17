import express from 'express';
import consign from 'consign';
import bodyParser from 'body-parser';
const path = require('path');


const app = express();
// Express middleware that allows POSTing data
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

consign({
    cwd: __dirname
})
.include('libs/config.js')
.then('db.js')
.then('libs/middlewares.js')
.then('routes')
.then('libs/boot.js')
.into(app)

// Settings

