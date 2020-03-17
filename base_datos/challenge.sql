#Se crea la base de datos con el nombre challenge
CREATE DATABASE challenge;

#Se usa la base de datos creada previamente
USE challenge;

#----------------------------------------------------------------------------------------------

#Se crean las tablas

create table correos(
	id_correo INT UNSIGNED AUTO_INCREMENT NOT NULL ,
	fecha DATE NOT NULL,		
	from_ VARCHAR(120) NOT NULL,
	subject VARCHAR(120) NOT NULL,	
	PRIMARY KEY (id_correo)
	
 ) ENGINE=InnoDB;


#----------------------------------------------------------------------------------------------
#Se crean los usuarios y se le otorgan los privelegios

#Administrador con todos los privelegios.
DELIMITER ; 
GRANT ALL PRIVILEGES ON challenge.* TO admin@localhost 
    IDENTIFIED BY 'admin' WITH GRANT OPTION;

