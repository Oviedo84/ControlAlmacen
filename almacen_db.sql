

CREATE DATABASE IF NOT EXISTS almacen_db;

USE almacen_db;

CREATE TABLE IF NOT EXISTS productos(
	prod_id INT NOT NULL AUTO_INCREMENT,
	prod_nombre VARCHAR(30) NOT NULL,
    prod_cantidad INT,
    prod_tipo VARCHAR(30) NOT NULL,
    INDEX (prod_nombre),
    INDEX (prod_tipo),
    PRIMARY KEY (prod_id)
);

CREATE TABLE IF NOT EXISTS usuarios(
	usu_id INT NOT NULL AUTO_INCREMENT,
    usu_nombre VARCHAR(30) NOT NULL,
    usu_ape_pat VARCHAR(30) NOT NULL,
    usu_ape_mat VARCHAR(30),
    usu_puesto ENUM('Almacenista','Gerente','Desempleado') NOT NULL,
	usu_password VARCHAR (30) NOT NULL,
    INDEX(usu_nombre,usu_ape_pat),
    PRIMARY KEY (usu_id)
);

CREATE TABLE IF NOT EXISTS registros(
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
INSERT INTO usuarios(usu_nombre, usu_ape_pat, usu_ape_mat, usu_password, usu_puesto) 
	VALUES ('Almacenista01', 'Pedro', 'Ruiz', 'password01', 'Almacenista');
INSERT INTO usuarios(usu_nombre, usu_ape_pat, usu_ape_mat, usu_password, usu_puesto) 
	VALUES ('Desempleado', 'Pedro', 'Ruiz', 'password01', 'Desempleado');
INSERT INTO productos(prod_nombre,prod_cantidad,prod_tipo)
	VALUES ('Allure','50','Esencia'),
			('CK One','30','Esencia'),
            ('212','22','Esencia'),
            ('212 Heroes','33','Esencia'),
            ('CK Everyone','45','Esencia'),
            ('Channel 5','60','Esencia'),
            ('Coco Mademosielle','34','Esencia'),
            ('Thank You Next','46','Esencia'),
            ('Cloud','27','Esencia'),
            ('Ferrari Black','13','Esencia'),
            ('L1212 White','10','Perfume'),
            ('L1212 Red','4','Perfume'),
            ('Desire','6','Perfume'),
            ('Zzegna','7','Perfume'),
            ('Eros','7','Perfume'),
            ('Versace','8','Perfume'),
            ('Toy 2','10','Perfume'),
            ('Light Blue','3','Perfume'),
            ('Halloween','2','Perfume'),
            ('Sauvage Dior','5','Perfume'),
            ('Hollister Jake','13','Loción'),
            ('Tommy Hilfiger','14','Loción'),
            ('360','7','Loción'),
            ('360 Red','8','Loción'),
            ('Polo Blue','18','Loción'),
            ('White Diamond','25','Perfumero'),
            ('Yellow Diamond','23','Perfumero'),
            ('Polo Deep Blue','15','Perfumero'),
            ('Her','17','Perfumero'),
            ('Dylanblue','18','Perfumero');
            
INSERT INTO usuarios(usu_nombre, usu_ape_pat, usu_ape_mat, usu_password, usu_puesto)
		VALUES ('Rigoberto','Martinez','Olmos','Jefaz0','Gerente'),
				('Raúl','López','Doriga','080901','Almacenista'),
                ('Juan','Mendoza','Gonzalez','Juanito1','Almacenista'),
                ('Fernando','Rodríguez','Contreras','Pu55ydest','Almacenista'),
                ('Mauricio','Flores','Castillo','Loquito66','Almacenista'),
                ('Carmelo','Díaz','Benitez','password00','Almacenista'),
                ('Jorge','Oviedo','Guerra','infinite78','Almacenista'),
                ('Jesús','Monzon','Donovan','DukiG0D','Almacenista'),
                ('René','García','Peréz','19962406','Almacenista'),
                ('Luis','Páramo','Chaire','D0nD0n0van','Almacenista');
                
INSERT INTO registros(reg_fecha_ingreso,reg_prod_id,reg_usu_id)
		VALUES ('2022-12-04','1','2'),
				('2019-11-03','2','3'),
                ('2019-10-17','3','4'),
                ('2019-12-15','7','5'),
INSERT INTO registros(reg_fecha_egreso,reg_prod_id,reg_usu_id)                
		VALUES ('2021-06-15','9','6'),
                ('2021-08-23','6','7'),
                ('2022-01-09','4','8'),
INSERT INTO registros(reg_fecha_modificacion,reg_prod_id,reg_usu_id)                
		VALUES ('2022-01-15','2','8'),
                ('2021-06-23','1','7'),
                ('2019-10-26','3','6');