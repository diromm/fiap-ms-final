SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE tb_serial(
    id SERIAL
);

CREATE SEQUENCE drones_id_seq;

CREATE TABLE tb_drones (
    id_drone integer NOT NULL DEFAULT nextval('drones_id_seq') ,
    nome_drone character varying(255) NOT NULL, 
    lat_drone double precision , 
    lng_drone double precision ,
    temperatura double precision , 
    umidade double precision ,
    rastreando BOOLEAN NOT NULL ,
	CONSTRAINT "tb_drones_pkey" PRIMARY KEY (id_drone)
);


ALTER SEQUENCE drones_id_seq
OWNED BY tb_drones.id_drone;