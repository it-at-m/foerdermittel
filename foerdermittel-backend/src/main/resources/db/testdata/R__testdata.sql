----------------------------------------
------- MASTER DATA (Stammdaten) -------
----------------------------------------

TRUNCATE TABLE fp_bauleitungen CASCADE;
INSERT INTO fp_bauleitungen (bauleitung, bezeichnung)
VALUES ('A', 'Bauleitung A für Tests'),
       ('B', 'Bauleitung B für Tests'),
       ('C', 'Bauleitung C für Tests');

TRUNCATE TABLE fp_bauprogramme CASCADE;
INSERT INTO fp_bauprogramme (bauprogramm, bezeichnung)
VALUES (1, 'Bauprogramm 1 für Tests'),
       (2, 'Bauprogramm 2 für Tests'),
       (3, 'Bauprogramm 3 für Tests');

TRUNCATE TABLE fp_benutzerhinweise CASCADE;
INSERT INTO fp_benutzerhinweise (for_formsmodul, hinweis1, hinweis2, hinweis3)
VALUES ('FMW_ABLAGEINDEX_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ABRUFE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ABRUFSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ANTRAEGE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ANTRAGSSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ARCHIV_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_BAULEITUNGEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_BAUPROGRAMME_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_BENUTZER_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_BEWILLIGUNGEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_BEWILLSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_CHECKLISTEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DOMAINS_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK1_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK11_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK12_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK13_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK14_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK17_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK18_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK2_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK20_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK21_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK22_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK23_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK25_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK27_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK28_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK3_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK31_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK32_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK33_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK34_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK4_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK5_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK6_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK8_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_DRUCK9_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_EUINFORMATIONEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_FOERDERBEREICHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_GEN_JAHRESSTAT_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_GEPLANTEMASS_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_HHPLAN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_HHSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_HINWEISE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_ISTKOSTEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_KINDERSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_KRANKENHAEUSER_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_KURZBEZEICHNUNGEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_PROFIL_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_PROJEKTE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_PROJEKTSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_PROTOKOLL_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_PUBLIKATIONEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_REFERATE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_SIEDLUNGSGEBIETE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STADTBEZIRKE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STADTBEZIRKSLISTEN_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STAEDTEBAU_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STATFAG_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STATFOB_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STATPROJEKT_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_STICHWORTBEREICHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_TERMINE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_TRAEGER_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_UNTERABSCHNITTE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests'),
       ('FMW_VNSUCHE_F', 'Hinweis1 für Tests', 'Hinweis2 für Tests', 'Hinweis3 für Tests');

TRUNCATE TABLE fp_domains CASCADE;
INSERT INTO fp_domains (tabelle, spalte, wert, beschreibung)
VALUES ('fp_abrufe', 'vnabr', 'A', 'Wert A (fp_abrufe -> vnabr) für Tests'),
       ('fp_abrufe', 'vnabr', 'B', 'Wert B (fp_abrufe -> vnabr) für Tests'),
       ('fp_abrufe', 'vnabr', 'C', 'Wert C (fp_abrufe -> vnabr) für Tests'),
       ('fp_antraege', 'antragstyp', 'A', 'Wert A (fp_antrage -> antragstyp) für Tests'),
       ('fp_antraege', 'antragstyp', 'B', 'Wert B (fp_antrage -> antragstyp) für Tests'),
       ('fp_antraege', 'antragstyp', 'C', 'Wert C (fp_antrage -> antragstyp) für Tests'),
       ('fp_bewilligungen', 'bzuwart', 'A', 'Wert A (fp_bewilligungen -> bzuwart) für Tests'),
       ('fp_bewilligungen', 'bzuwart', 'B', 'Wert B (fp_bewilligungen -> bzuwart) für Tests'),
       ('fp_bewilligungen', 'bzuwart', 'C', 'Wert C (fp_bewilligungen -> bzuwart) für Tests'),
       ('fp_bewilligungen', 'krw', 'A', 'Wert A (fp_bewilligungen -> krw) für Tests'),
       ('fp_bewilligungen', 'krw', 'B', 'Wert B (fp_bewilligungen -> krw) für Tests'),
       ('fp_bewilligungen', 'krw', 'C', 'Wert C (fp_bewilligungen -> krw) für Tests'),
       ('fp_projekte', 'baubeendet', 'A', 'Wert A (fp_projekte -> baubeendet) für Tests'),
       ('fp_projekte', 'baubeendet', 'B', 'Wert B (fp_projekte -> baubeendet) für Tests'),
       ('fp_projekte', 'baubeendet', 'C', 'Wert C (fp_projekte -> baubeendet) für Tests'),
       ('fp_projekte', 'krhzweck', 'A', 'Wert A (fp_projekte -> krhzweck) für Tests'),
       ('fp_projekte', 'krhzweck', 'B', 'Wert B (fp_projekte -> krhzweck) für Tests'),
       ('fp_projekte', 'krhzweck', 'C', 'Wert C (fp_projekte -> krhzweck) für Tests'),
       ('fp_projekte', 'krisofp', 'A', 'Wert A (fp_projekte -> krisofp) für Tests'),
       ('fp_projekte', 'krisofp', 'B', 'Wert B (fp_projekte -> krisofp) für Tests'),
       ('fp_projekte', 'krisofp', 'C', 'Wert C (fp_projekte -> krisofp) für Tests'),
       ('fp_projekte', 'psbauref', 'A', 'Wert A (fp_projekte -> psbauref) für Tests'),
       ('fp_projekte', 'psbauref', 'B', 'Wert B (fp_projekte -> psbauref) für Tests'),
       ('fp_projekte', 'psbauref', 'C', 'Wert C (fp_projekte -> psbauref) für Tests');

TRUNCATE TABLE fp_foerderbereiche CASCADE;
INSERT INTO fp_foerderbereiche (fb, bezeichnung, funktion1, funktion2, funktion3, funktion4)
VALUES (1, 'Förderbereich 1 für Tests', false, false, false, false),
       (2, 'Förderbereich 2 für Tests', true, false, false, false),
       (3, 'Förderbereich 3 für Tests', false, true, false, false),
       (4, 'Förderbereich 4 für Tests', false, false, true, false),
       (5, 'Förderbereich 5 für Tests', false, false, false, true),
       (6, 'Förderbereich 6 für Tests', true, true, false, false),
       (7, 'Förderbereich 7 für Tests', true, false, true, false),
       (8, 'Förderbereich 8 für Tests', true, false, false, true),
       (9, 'Förderbereich 9 für Tests', false, true, true, false),
       (10, 'Förderbereich 10 für Tests', false, true, false, true),
       (11, 'Förderbereich 11 für Tests', false, false, true, true),
       (12, 'Förderbereich 12 für Tests', true, true, true, false),
       (13, 'Förderbereich 13 für Tests', true, true, false, true),
       (14, 'Förderbereich 14 für Tests', true, false, true, true),
       (15, 'Förderbereich 15 für Tests', false, true, true, true),
       (16, 'Förderbereich 16 für Tests', true, true, true, true);

TRUNCATE TABLE fp_krankenhaeuser CASCADE;
INSERT INTO fp_krankenhaeuser (krhname, bezeichnung)
VALUES ('A', 'Krankenhaus A für Tests'),
       ('B', 'Krankenhaus B für Tests'),
       ('C', 'Krankenhaus C für Tests');

TRUNCATE TABLE fp_kurzbezeichnungen CASCADE;
INSERT INTO fp_kurzbezeichnungen (kurzbez, bezeichnung)
VALUES ('KURZBEZ1', 'Kurzbezeichnung KURZBEZ1 für Tests'),
       ('KURZBEZ2', 'Kurzbezeichnung KURZBEZ2 für Tests'),
       ('KURZBEZ3', 'Kurzbezeichnung KURZBEZ3 für Tests');

TRUNCATE TABLE fp_publikationen CASCADE;
INSERT into fp_publikationen (kurzform, bezeichnung)
VALUES ('A', 'Publikation A für Tests'),
       ('B', 'Publikation B für Tests'),
       ('C', 'Publikation C für Tests');

TRUNCATE TABLE fp_referate CASCADE;
INSERT INTO fp_referate (refnr, bezeichnung)
VALUES (1, 'Referat 1 für Tests'),
       (2, 'Referat 2 für Tests'),
       (3, 'Referat 3 für Tests');

TRUNCATE TABLE fp_siedlungsgebiete CASCADE;
INSERT INTO fp_siedlungsgebiete (siedlungsgebiet, bezeichnung)
VALUES (1, 'Siedlungsgebiet 1 für Tests'),
       (2, 'Siedlungsgebiet 2 für Tests'),
       (3, 'Siedlungsgebiet 3 für Tests');

TRUNCATE TABLE fp_stadtbezirke CASCADE;
INSERT INTO fp_stadtbezirke (stadtbezirk, bezeichnung)
VALUES (1, 'Stadtbezirk 1 für Tests'),
       (2, 'Stadtbezirk 2 für Tests'),
       (3, 'Stadtbezirk 3 für Tests');

TRUNCATE TABLE fp_listennamen CASCADE;
INSERT INTO fp_listennamen (kurzbez, bezeichnung)
VALUES ('SL1', 'Listenname SL1 für Tests'),
       ('SL2', 'Listenname SL2 für Tests'),
       ('SL3', 'Listenname SL3 für Tests');

TRUNCATE TABLE fp_stadtbezirkslisten CASCADE;
INSERT INTO fp_stadtbezirkslisten (lna_kurzbez, bez_stadtbezirk, bezeichnung)
VALUES ('SL1', 1, 'Zuordnung von Stadtbezirk 1 zu Listenname SL1 für Tests'),
       ('SL2', 2, 'Zuordnung von Stadtbezirk 2 zu Listenname SL2 für Tests'),
       ('SL2', 3, 'Zuordnung von Stadtbezirk 3 zu Listenname SL2 für Tests');

TRUNCATE TABLE fp_stichwortbereiche CASCADE;
INSERT INTO fp_stichwortbereiche (bereich, bezeichnung)
VALUES ('STICHWORTBEREICH1', 'Stichwortbereich STICHWORTBEREICH1 für Tests'),
       ('STICHWORTBEREICH2', 'Stichwortbereich STICHWORTBEREICH2 für Tests'),
       ('STICHWORTBEREICH3', 'Stichwortbereich STICHWORTBEREICH3 für Tests');

TRUNCATE TABLE fp_traeger CASCADE;
INSERT INTO fp_traeger (kurzform, bezeichnung)
VALUES (1, 'Träger 1 für Tests'),
       (2, 'Träger 2 für Tests'),
       (3, 'Träger 3 für Tests');

TRUNCATE TABLE fp_hauptabschnitte CASCADE;
INSERT INTO fp_hauptabschnitte (ha, bezeichnung)
VALUES ('01', 'Hauptabschnitt 01 für Tests'),
       ('02', 'Hauptabschnitt 02 für Tests'),
       ('03', 'Hauptabschnitt 03 für Tests');

TRUNCATE TABLE fp_unterabschnitte CASCADE;
INSERT INTO fp_unterabschnitte (ua, bezeichnung, has_ha)
VALUES ('11', 'Unterabschnitt 11 für Tests', '01'),
       ('21', 'Unterabschnitt 21 für Tests', '02'),
       ('22', 'Unterabschnitt 22 für Tests', '02'),
       ('31', 'Unterabschnitt 31 für Tests', '03'),
       ('32', 'Unterabschnitt 32 für Tests', '03'),
       ('33', 'Unterabschnitt 33 für Tests', '03');