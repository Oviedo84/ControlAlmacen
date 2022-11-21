DROP DATABASE almacen_db;
CREATE DATABASE almacen_db;

USE almacen_db;

CREATE TABLE productos(
	prod_id INT NOT NULL AUTO_INCREMENT,
	prod_nombre VARCHAR(30) NOT NULL,
    prod_cantidad INT,
    prod_tipo VARCHAR(30) NOT NULL,
    INDEX (prod_nombre),
    INDEX (prod_tipo),
    PRIMARY KEY (prod_id)
);

CREATE TABLE usuarios(
	usu_id INT NOT NULL AUTO_INCREMENT,
    usu_nombre VARCHAR(30) NOT NULL,
    usu_ape_pat VARCHAR(30) NOT NULL,
    usu_ape_mat VARCHAR(30),
    usu_puesto ENUM('Almacenista','Gerente','Desempleado') NOT NULL,
	usu_password VARCHAR (30) NOT NULL,
    INDEX(usu_nombre,usu_ape_pat),
    PRIMARY KEY (usu_id)
);

CREATE TABLE registros(
	reg_id INT NOT NULL AUTO_INCREMENT,
	reg_fecha_ingreso DATE,
    reg_fecha_egreso DATE,
    reg_fecha_modificacion DATE,
    reg_prod_id INT NOT NULL,
    reg_usu_id INT NOT NULL,
	INDEX (reg_fecha_ingreso),
    INDEX (reg_fecha_egreso),
    INDEX (reg_fecha_modificacion),
    PRIMARY KEY (reg_id),
    CONSTRAINT fk_reg_prod_id
    FOREIGN KEY (reg_usu_id)
		REFERENCES usuarios (usu_id)
        ON DELETE NO ACTION,
    CONSTRAINT fk_reg_usu_id
    FOREIGN KEY (reg_prod_id)
		REFERENCES productos (prod_id)
        ON DELETE NO ACTION
);

#----------------------INSERT-----------------------------------------------	
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 01', '123', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 02', '321', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 03', '1543', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 04', '7132', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 05', '643', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 06', '1436', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 07', '481', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 08', '1348', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 09', '2029', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 10', '1886', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 11', '1236', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 12', '219', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 13', '1439', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 14', '1156', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 15', '505', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 16', '2026', 'Esencia ');
INSERT INTO productos(prod_nombre, prod_cantidad, prod_tipo) 
	VALUES ('Perfume 17', '101', 'Esencia ');
INSERT INTO usuarios(usu_nombre, usu_ape_pat, usu_ape_mat, usu_password, usu_puesto) 
	VALUES ('Gerente01', 'Gonzalez', 'Perez', 'password', 'Gerente');
INSERT INTO privilegios()
