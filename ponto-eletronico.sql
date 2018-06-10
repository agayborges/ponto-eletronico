--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.3

-- Started on 2018-06-10 17:10:34

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2831 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16554)
-- Name: batida; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.batida (
    id bigint NOT NULL,
    hora time without time zone NOT NULL,
    jornada_id bigint NOT NULL
);


ALTER TABLE public.batida OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16552)
-- Name: batida_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.batida_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.batida_id_seq OWNER TO postgres;

--
-- TOC entry 2832 (class 0 OID 0)
-- Dependencies: 198
-- Name: batida_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.batida_id_seq OWNED BY public.batida.id;


--
-- TOC entry 196 (class 1259 OID 16402)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16562)
-- Name: jornada; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jornada (
    id bigint NOT NULL,
    dia date NOT NULL,
    intervalo integer NOT NULL,
    intervalo_esta_correto boolean NOT NULL,
    minutos_trabalhados integer NOT NULL,
    minutos_trabalhados_corridos integer NOT NULL,
    usuario_pis character varying(255) NOT NULL
);


ALTER TABLE public.jornada OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16560)
-- Name: jornada_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.jornada_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jornada_id_seq OWNER TO postgres;

--
-- TOC entry 2833 (class 0 OID 0)
-- Dependencies: 200
-- Name: jornada_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.jornada_id_seq OWNED BY public.jornada.id;


--
-- TOC entry 197 (class 1259 OID 16469)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    pis character varying(255) NOT NULL,
    senha character varying(255)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 2683 (class 2604 OID 16557)
-- Name: batida id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batida ALTER COLUMN id SET DEFAULT nextval('public.batida_id_seq'::regclass);


--
-- TOC entry 2684 (class 2604 OID 16565)
-- Name: jornada id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jornada ALTER COLUMN id SET DEFAULT nextval('public.jornada_id_seq'::regclass);


--
-- TOC entry 2821 (class 0 OID 16554)
-- Dependencies: 199
-- Data for Name: batida; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.batida (id, hora, jornada_id) FROM stdin;
\.


--
-- TOC entry 2823 (class 0 OID 16562)
-- Dependencies: 201
-- Data for Name: jornada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.jornada (id, dia, intervalo, intervalo_esta_correto, minutos_trabalhados, minutos_trabalhados_corridos, usuario_pis) FROM stdin;
\.


--
-- TOC entry 2819 (class 0 OID 16469)
-- Dependencies: 197
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (pis, senha) FROM stdin;
83169153803	123
\.


--
-- TOC entry 2834 (class 0 OID 0)
-- Dependencies: 198
-- Name: batida_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.batida_id_seq', 5, true);


--
-- TOC entry 2835 (class 0 OID 0)
-- Dependencies: 196
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 19, true);


--
-- TOC entry 2836 (class 0 OID 0)
-- Dependencies: 200
-- Name: jornada_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.jornada_id_seq', 1, true);


--
-- TOC entry 2688 (class 2606 OID 16559)
-- Name: batida batida_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batida
    ADD CONSTRAINT batida_pkey PRIMARY KEY (id);


--
-- TOC entry 2692 (class 2606 OID 16567)
-- Name: jornada jornada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jornada
    ADD CONSTRAINT jornada_pkey PRIMARY KEY (id);


--
-- TOC entry 2690 (class 2606 OID 16569)
-- Name: batida uk7oec1tl0st90u9neerc7rorq4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batida
    ADD CONSTRAINT uk7oec1tl0st90u9neerc7rorq4 UNIQUE (hora, jornada_id);


--
-- TOC entry 2694 (class 2606 OID 16571)
-- Name: jornada ukekf8l0a3iowbe5ua06q130ubm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jornada
    ADD CONSTRAINT ukekf8l0a3iowbe5ua06q130ubm UNIQUE (dia, usuario_pis);


--
-- TOC entry 2686 (class 2606 OID 16476)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (pis);


--
-- TOC entry 2695 (class 2606 OID 16572)
-- Name: batida fk2e6odpu4sa6uwy892vhjs7cly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batida
    ADD CONSTRAINT fk2e6odpu4sa6uwy892vhjs7cly FOREIGN KEY (jornada_id) REFERENCES public.jornada(id);


--
-- TOC entry 2696 (class 2606 OID 16577)
-- Name: jornada fk3soxilnrtjct6u8rtqtjekjqv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jornada
    ADD CONSTRAINT fk3soxilnrtjct6u8rtqtjekjqv FOREIGN KEY (usuario_pis) REFERENCES public.usuario(pis);


-- Completed on 2018-06-10 17:10:34

--
-- PostgreSQL database dump complete
--

