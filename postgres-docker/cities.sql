--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: cities; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE cities (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    lat double precision,
    lng double precision,
    state_id integer,
    slug character varying(255) NOT NULL
);


ALTER TABLE public.cities OWNER TO admin;

--
-- Name: cities_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE cities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cities_id_seq OWNER TO admin;

--
-- Name: cities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE cities_id_seq OWNED BY cities.id;


--
-- Name: cities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('cities_id_seq', 5736, true);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY cities ALTER COLUMN id SET DEFAULT nextval('cities_id_seq'::regclass);


--
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: admin
--


INSERT INTO public.cities
("name", lat, lng, state_id, slug)
values
	('Atlântida', -29.7861450000000012, -50.0328829999999982, 1, 'atlantida'),
	('Santa Cruz do Sul', -29.7197684337466015, -52.429928587370398, 1, 'santa-cruz-do-sul'),
	('Lajeado', -29.4654544024709999, -51.9678277005001021, 1, 'lajeado'),
	('Crato', 7.23175278993045012, -39.4083902977885998, 12, 'crato'),
	('Brasília', -15.794087361891, -47.8879054780312998, 13, 'brasilia'),
	('Marília', -22.2149329999999985, -49.9516456431032978, 3, 'marilia');



--
-- Name: cities_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY cities
    ADD CONSTRAINT cities_pkey PRIMARY KEY (id);


--
-- Name: index_cities_on_slug; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE UNIQUE INDEX index_cities_on_slug ON cities USING btree (slug);


--
-- PostgreSQL database dump complete
--
