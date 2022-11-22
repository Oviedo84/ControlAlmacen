const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const session = require("express-session");

const app = express();

const db = mysql.createConnection({
    port: '3306',
    host: '127.0.0.1',
    user: 'root',
    password: 'password',
    database: 'almacen_db'
});

db.connect(function(err){
    if (err){
        console.log(err.code);
    }
    else {
        console.log("Conectado a base de datos");
    }
});

app.use(session({
    secret: 'secret',
    resave: true,
    saveUninitialized:true
}));

app.use(express.urlencoded({extended: true}));
app.use(express.json());

app.post('/auth', function(req, res) {
    var username = req.body.username;
    var password = req.body.password;
    if(username && password){
        db.query('SELECT usu_puesto FROM usuarios WHERE usu_nombre = ? AND usu_password = ?', [username, password], function(error, result, fields) {
            if (error){
                res.write(JSON.stringify({
                    error: true,
                    error_object: error
                }));
                res.end();
            }
            if (result.length > 0){
                req.session.loggedin = true;
                req.session.username = username;
                res.write(JSON.stringify(result));
            }
            else {
                res.send('Incorrect Username and/or password');
            }
            res.end();
        });
    }
    else {
        res.send('Please enter the username and the password');
        res.end();
    }
});

app.get('/listProd', function(req, res){
    var sql = "SELECT * FROM productos";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/listProdbyQuantity', function(req, res){
    var sql = "SELECT * FROM productos ORDER BY prod_cantidad DESC";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/listProdbyNameDesc', function(req, res){
    var sql = "SELECT * FROM productos ORDER BY prod_nombre DESC";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/listProdbyNameAsc', function(req, res){
    var sql = "SELECT * FROM productos ORDER BY prod_nombre ASC";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/cat', function(req, res){
    var sql = "SELECT * FROM categorias";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.post('/insertProd', function(req, res, next){
    var nombre = req.body.nombre;
    var descripcion = req.body.descripcion;
    var p_venta = req.body.p_venta;
    var p_compra = req.body.p_compra;
    var fecha = req.body.fecha;
    var activo = req.body.activo;
    var cantidad = req.body.cantidad;
    
    var sql = `INSERT INTO productos(nombre, descripcion, p_venta, p_compra, fecha, activo, cantidad) VALUES ("${nombre}", "${descripcion}", ${p_venta}, ${p_compra}, "${fecha}", "${activo}", ${cantidad})`;
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.delete('/deleteProduct/:id', function(req, res){
    var sql = "DELETE FROM productos WHERE prod_id = " + 
        db.escape(req.params.id);
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.put('/editProd/:id', function(req, res, next){
    var id = req.params.id;
    var nombre = req.body.nombre;
    var tipo = req.body.tipo;
    var cantidad = req.body.cantidad;
    
    var sql = `UPDATE productos SET prod_nombre=?, prod_tipo=?, prod_cantidad=? WHERE prod_id = ?`;
    db.query(sql, [nombre, tipo, cantidad, id],function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/listComp', function(req, res){
    var sql = "SELECT * FROM compras";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.post('/insertComp', function(req, res, next){
    var producto_id = req.body.producto_id;
    var usuario_id = req.body.usuario_id;
    var fecha = req.body.fecha;
    var cantidad = req.body.cantidad;
    var proveedor = req.body.proveedor;
    
    var sql = `INSERT INTO compras(producto_id, usuario_id, fecha, cantidad, proveedor) VALUES ("${producto_id}", "${usuario_id}", "${fecha}", "${cantidad}", "${proveedor}")`;
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});

app.get('/listUsers', function(req, res){
    var sql = "SELECT * FROM usuarios";
    db.query(sql, function(error, result){
        if (error){
            res.write(JSON.stringify({
                error: true,
                error_object: error
            }));
            res.end();
        }
        else {
            res.write(JSON.stringify(result));
            res.end();
        }
    });
});


app.listen(8080, () => {
    console.log("Servidor NodeJs @ 8080");
});
