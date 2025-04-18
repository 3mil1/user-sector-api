--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.user_sectors DROP CONSTRAINT fk_user_sector_user;
ALTER TABLE ONLY public.user_sectors DROP CONSTRAINT fk_user_sector_sector;
ALTER TABLE ONLY public.user_consents DROP CONSTRAINT fk_user_consent_user;
ALTER TABLE ONLY public.sectors DROP CONSTRAINT fk_sector_parent;
DROP INDEX public.idx_user_consents_user_id;
DROP INDEX public.idx_user_consents_type_version;
DROP INDEX public.idx_sectors_parent_id;
DROP INDEX public.flyway_schema_history_s_idx;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.user_sectors DROP CONSTRAINT user_sectors_pkey;
ALTER TABLE ONLY public.user_consents DROP CONSTRAINT user_consents_pkey;
ALTER TABLE ONLY public.user_consents DROP CONSTRAINT uk_user_consent_type_version;
ALTER TABLE ONLY public.sectors DROP CONSTRAINT sectors_pkey;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
DROP TABLE public.users;
DROP TABLE public.user_sectors;
DROP TABLE public.user_consents;
DROP TABLE public.sectors;
DROP TABLE public.flyway_schema_history;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


--
-- Name: sectors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sectors (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    parent_id bigint
);


--
-- Name: user_consents; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_consents (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    consent_type character varying(100) NOT NULL,
    terms_version character varying(50) NOT NULL,
    agreed_at timestamp without time zone NOT NULL
);


--
-- Name: user_sectors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.user_sectors (
    user_id uuid NOT NULL,
    sector_id bigint NOT NULL
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(100) NOT NULL
);


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create schema	SQL	V1__create_schema.sql	-1117258070	myuser	2025-04-18 17:16:45.401666	9	t
2	2	insert sectors	SQL	V2__insert_sectors.sql	1046817079	myuser	2025-04-18 17:16:45.423479	7	t
\.


--
-- Data for Name: sectors; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.sectors (id, name, parent_id) FROM stdin;
1	Manufacturing	\N
2	Service	\N
3	Other	\N
19	Construction materials	1
18	Electronics and Optics	1
6	Food and Beverage	1
13	Furniture	1
12	Machinery	1
11	Metalworking	1
9	Plastic and Rubber	1
5	Printing	1
7	Textile and Clothing	1
8	Wood	1
342	Bakery & confectionery products	6
43	Beverages	6
42	Fish & fish products	6
40	Meat & meat products	6
39	Milk & dairy products	6
437	Other	6
378	Sweets & snack food	6
389	Bathroom/sauna	13
385	Bedroom	13
390	Children's room	13
98	Kitchen	13
101	Living room	13
392	Office	13
394	Other (Furniture)	13
341	Outdoor	13
99	Project furniture	13
94	Machinery components	12
91	Machinery equipment/tools	12
224	Manufacture of machinery	12
97	Maritime	12
93	Metal structures	12
508	Other	12
227	Repair and maintenance service	12
271	Aluminium and steel workboats	97
269	Boat/Yacht building	97
230	Ship repair and conversion	97
67	Construction of metal structures	11
263	Houses and buildings	11
267	Metal products	11
542	Metal works	11
75	CNC-machining	542
62	Forgings, Fasteners	542
69	Gas, Plasma, Laser cutting	542
66	MIG, TIG, Aluminum welding	542
54	Packaging	9
556	Plastic goods	9
559	Plastic processing technology	9
560	Plastic profiles	9
55	Blowing	559
57	Moulding	559
53	Plastics welding and processing	559
148	Advertising	5
150	Book/Periodicals printing	5
145	Labelling and packaging printing	5
44	Clothing	7
45	Textile	7
337	Other (Wood)	8
51	Wooden building materials	8
47	Wooden houses	8
25	Business services	2
35	Engineering	2
28	Information Technology and Telecommunications	2
22	Tourism	2
141	Translation services	2
21	Transport and Logistics	2
581	Data processing, Web portals, E-marketing	28
576	Programming, Consultancy	28
121	Software, Hardware	28
122	Telecommunications	28
111	Air	21
114	Rail	21
112	Road	21
113	Water	21
37	Creative industries	3
29	Energy technology	3
33	Environment	3
\.


--
-- Data for Name: user_consents; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_consents (id, user_id, consent_type, terms_version, agreed_at) FROM stdin;
df0dd6b8-8641-42dc-bc20-5a9b377445c6	6bfebf54-e6a2-4a02-86cc-1a212b25e01d	TERMS_AND_CONDITIONS	v1.1	2025-04-18 17:21:37.142148
347c78b4-b640-496b-acd1-effeba6bcd42	46d77701-6347-4466-8382-0dcb4e92d97c	TERMS_AND_CONDITIONS	v1.1	2025-04-18 17:47:41.755402
80e34695-3a49-4d5c-a2f6-96618836ce29	07430683-9795-459f-8651-32c2f3077ffa	TERMS_AND_CONDITIONS	v1.1	2025-04-18 17:49:55.173963
d878919f-9dc6-4247-abfc-87ee6d790a82	130ec71b-9d48-429d-b9e1-bcbd451893cc	TERMS_AND_CONDITIONS	v1.1	2025-04-18 17:52:55.066794
0204507d-3ca9-49ab-ac0c-33207af2c1d8	19de205e-4230-431a-970b-c764bda1a8e6	TERMS_AND_CONDITIONS	v1.1	2025-04-18 17:53:38.459422
\.


--
-- Data for Name: user_sectors; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.user_sectors (user_id, sector_id) FROM stdin;
6bfebf54-e6a2-4a02-86cc-1a212b25e01d	7
6bfebf54-e6a2-4a02-86cc-1a212b25e01d	8
46d77701-6347-4466-8382-0dcb4e92d97c	5
46d77701-6347-4466-8382-0dcb4e92d97c	150
46d77701-6347-4466-8382-0dcb4e92d97c	1
07430683-9795-459f-8651-32c2f3077ffa	1
130ec71b-9d48-429d-b9e1-bcbd451893cc	6
130ec71b-9d48-429d-b9e1-bcbd451893cc	1
19de205e-4230-431a-970b-c764bda1a8e6	150
19de205e-4230-431a-970b-c764bda1a8e6	145
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.users (id, name) FROM stdin;
6bfebf54-e6a2-4a02-86cc-1a212b25e01d	Emil
46d77701-6347-4466-8382-0dcb4e92d97c	Emil
07430683-9795-459f-8651-32c2f3077ffa	emi3
130ec71b-9d48-429d-b9e1-bcbd451893cc	emil
19de205e-4230-431a-970b-c764bda1a8e6	ffaf
\.


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: sectors sectors_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sectors
    ADD CONSTRAINT sectors_pkey PRIMARY KEY (id);


--
-- Name: user_consents uk_user_consent_type_version; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_consents
    ADD CONSTRAINT uk_user_consent_type_version UNIQUE (user_id, consent_type, terms_version);


--
-- Name: user_consents user_consents_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_consents
    ADD CONSTRAINT user_consents_pkey PRIMARY KEY (id);


--
-- Name: user_sectors user_sectors_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_sectors
    ADD CONSTRAINT user_sectors_pkey PRIMARY KEY (user_id, sector_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: idx_sectors_parent_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_sectors_parent_id ON public.sectors USING btree (parent_id);


--
-- Name: idx_user_consents_type_version; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_consents_type_version ON public.user_consents USING btree (consent_type, terms_version);


--
-- Name: idx_user_consents_user_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_consents_user_id ON public.user_consents USING btree (user_id);


--
-- Name: sectors fk_sector_parent; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sectors
    ADD CONSTRAINT fk_sector_parent FOREIGN KEY (parent_id) REFERENCES public.sectors(id);


--
-- Name: user_consents fk_user_consent_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_consents
    ADD CONSTRAINT fk_user_consent_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: user_sectors fk_user_sector_sector; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_sectors
    ADD CONSTRAINT fk_user_sector_sector FOREIGN KEY (sector_id) REFERENCES public.sectors(id);


--
-- Name: user_sectors fk_user_sector_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.user_sectors
    ADD CONSTRAINT fk_user_sector_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

