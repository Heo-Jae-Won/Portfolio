var mysql=require('mysql');
var con;

exports.connect=function(){ //DB를 구동할 때 딱 한번만 실행하면 되기 때문에 connect는 딱 한번만 app.js에 씀.
    con=mysql.createPool({
        connectionLimit:100,
        host:'localhost',
        user:'test',
        password:'pass',
        database:'testdb',
        port:'3306'
    })
}

exports.get=function(){
    return con;
}