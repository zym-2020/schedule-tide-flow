--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6
-- Dumped by pg_dump version 13.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: flow; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flow (
    stcd text NOT NULL,
    tm text NOT NULL,
    z real,
    q real
);


ALTER TABLE public.flow OWNER TO postgres;

--
-- Name: tide; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tide (
    stcd text NOT NULL,
    tm text NOT NULL,
    tdz real
);


ALTER TABLE public.tide OWNER TO postgres;

--
-- Data for Name: flow; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flow (stcd, tm, z, q) FROM stdin;
\.


--
-- Data for Name: tide; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tide (stcd, tm, tdz) FROM stdin;
\.


--
-- Name: flow flow_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flow
    ADD CONSTRAINT flow_pkey PRIMARY KEY (stcd, tm);


--
-- Name: tide tide_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tide
    ADD CONSTRAINT tide_pkey PRIMARY KEY (stcd, tm);


--
-- PostgreSQL database dump complete
--

