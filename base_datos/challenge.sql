#Se crea la base de datos con el nombre challenge
CREATE DATABASE challenge;

#Se usa la base de datos creada previamente
USE challenge;

#----------------------------------------------------------------------------------------------

#Se crean las tablas

create table correos(
	fecha DATE NOT NULL,
	id_correo INT UNSIGNED AUTO_INCREMENT NOT NULL ,	
	from VARCHAR(40) NOT NULL,
	subject VARCHAR(40) NOT NULL,	
	PRIMARY KEY (id_correo)
	
 ) ENGINE=InnoDB;


#----------------------------------------------------------------------------------------------
#Se crean los usuarios y se le otorgan los privelegios

#Administrador con todos los privelegios.
DELIMITER ; 
GRANT ALL PRIVILEGES ON Parquimetros.* TO admin@localhost 
    IDENTIFIED BY 'admin' WITH GRANT OPTION;

