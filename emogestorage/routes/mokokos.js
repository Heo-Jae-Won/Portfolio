var express = require('express');
var router = express.Router();
const db = require('../db');


//upload
const multer = require('multer');
const upload = multer({
  storage: multer.diskStorage({
    destination: (req, file, done) => {
      done(null, "./public/images") 
    },
    filename: (req, file, done) => {
      done(null, Date.now() + '_' + file.originalname)
    }
  })
})


//delete task
router.delete('/:code', (req, res) => {
  const code = req.params.code; 
  const sql = 'delete from mokoko where code=?';
  db.get().query(sql, [code], (err, rows) => {
    !err ? res.sendStatus(200) : res.sendStatus(400);
  })
});

//list page
router.get('/list', function (req, res, next) {
  res.render('index', {
    title: '목록출력',
    pageName: 'mokoko/list.ejs'
  })
});


//list task
router.get('/', function (req, res, next) { 
  let sort = '';
  switch (req.query.order) {
    case "recent":
      sort = 'code desc'
      break;
    case "name":
      sort = 'name'
      break;
    case "high":
      sort = 'price desc'
      break;
    case "low":
      sort = 'price'
      break;

  }

  let word = '%' + req.query.word + '%'; 
  const sql = 'select * from mokoko where name like ? or price like ? or code like ? order by ' + sort;
  db.get().query(sql, [word, word, word], (err, rows) => {
    res.send(rows);
  })
});



//Android 마지막번호얻기
router.get('/code', (req, res) => {
  const sql = 'select concat("P",max(substring(code,2))+1) code from mokoko';
  db.get().query(sql, (err, rows) => {
    res.send(rows); 
  })
});

//insert page
router.get('/insert', function (req, res, next) {
  const sql = 'select concat("P",max(substring(code,2))+1) code from mokoko'; 
  db.get().query(sql, (err, rows) => {
    res.render('index', {
      title: '',
      pageName: 'mokoko/insert.ejs',
      newCode: rows[0] 
    })
  })
});



//insert task
router.post('/', upload.single('image'), function (req, res, next) {
  let image = ''; 
  if (req.file != null) {
    image = req.file.filename;
  }
  const code = req.body.code; 
  const name = req.body.name;
  const price = req.body.price;

  const sql = 'insert into mokoko(code,name,price,image) values(?,?,?,?)';
  db.get().query(sql, [code, name, price, image], (err, rows) => {
    res.redirect('/mokokos/list');
  })
});


//read page
router.get('/read/:code', (req, res) => {
  const code = req.params.code;
  const sql = 'select * from mokoko where code=?';
  db.get().query(sql, [code], (err, rows) => {
    res.render('index', {
      title: '읽기',
      pageName: 'mokoko/read.ejs',
      row: rows[0]
    })
  })
})



//read task
router.get('/:code', (req, res) => {
  const code = req.params.code;   
  const sql = 'select * from mokoko where code=?';
  db.get().query(sql, [code], (err, rows) => {
    res.send({rows});
  })
})

//update task
router.post('/:code', upload.single('image'), function (req, res, next) {
  const code = req.params.code;
  const name = req.body.name;
  const price = req.body.price;
  let image=req.files[0].filename;
  const sql = 'update mokoko set name=?,price=?,image=? where code=?';
  db.get().query(sql, [name, price, image, code], (err, rows) => {
   !err ? res.sendStatus(200) : alert(err);
  })
});




module.exports = router;
