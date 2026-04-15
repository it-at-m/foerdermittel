/*
----------------------------------------
------- MASTER DATA (Stammdaten) -------
----------------------------------------
*/

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
INSERT INTO fp_benutzerhinweise (view_id, funktionsbeschreibung, bedienung, pruefung_vorgaben)
VALUES ('FMW_ABLAGEINDEX_F', 'Funktion (FMW_ABLAGEINDEX_F) für Tests', 'Bedienung (FMW_ABLAGEINDEX_F) für Tests',
        'Vorgaben / Prüfung (FMW_ABLAGEINDEX_F) für Tests'),
       ('FMW_ABRUFE_F', 'Funktion (FMW_ABRUFE_F) für Tests', 'Bedienung (FMW_ABRUFE_F) für Tests',
        'Vorgaben / Prüfung (FMW_ABRUFE_F) für Tests'),
       ('FMW_ABRUFSUCHE_F', 'Funktion (FMW_ABRUFSUCHE_F) für Tests', 'Bedienung (FMW_ABRUFSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_ABRUFSUCHE_F) für Tests'),
       ('FMW_ANTRAEGE_F', 'Funktion (FMW_ANTRAEGE_F) für Tests', 'Bedienung (FMW_ANTRAEGE_F) für Tests',
        'Vorgaben / Prüfung (FMW_ANTRAEGE_F) für Tests'),
       ('FMW_ANTRAGSSUCHE_F', 'Funktion (FMW_ANTRAGSSUCHE_F) für Tests', 'Bedienung (FMW_ANTRAGSSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_ANTRAGSSUCHE_F) für Tests'),
       ('FMW_ARCHIV_F', 'Funktion (FMW_ARCHIV_F) für Tests', 'Bedienung (FMW_ARCHIV_F) für Tests',
        'Vorgaben / Prüfung (FMW_ARCHIV_F) für Tests'),
       ('FMW_BAULEITUNGEN_F', 'Funktion (FMW_BAULEITUNGEN_F) für Tests', 'Bedienung (FMW_BAULEITUNGEN_F) für Tests',
        'Vorgaben / Prüfung (FMW_BAULEITUNGEN_F) für Tests'),
       ('FMW_BAUPROGRAMME_F', 'Funktion (FMW_BAUPROGRAMME_F) für Tests', 'Bedienung (FMW_BAUPROGRAMME_F) für Tests',
        'Vorgaben / Prüfung (FMW_BAUPROGRAMME_F) für Tests'),
       ('FMW_BEWILLIGUNGEN_F', 'Funktion (FMW_BEWILLIGUNGEN_F) für Tests', 'Bedienung (FMW_BEWILLIGUNGEN_F) für Tests',
        'Vorgaben / Prüfung (FMW_BEWILLIGUNGEN_F) für Tests'),
       ('FMW_BEWILLSUCHE_F', 'Funktion (FMW_BEWILLSUCHE_F) für Tests', 'Bedienung (FMW_BEWILLSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_BEWILLSUCHE_F) für Tests'),
       ('FMW_CHECKLISTEN_F', 'Funktion (FMW_CHECKLISTEN_F) für Tests', 'Bedienung (FMW_CHECKLISTEN_F) für Tests',
        'Vorgaben / Prüfung (FMW_CHECKLISTEN_F) für Tests'),
       ('FMW_DOMAINS_F', 'Funktion (FMW_DOMAINS_F) für Tests', 'Bedienung (FMW_DOMAINS_F) für Tests',
        'Vorgaben / Prüfung (FMW_DOMAINS_F) für Tests'),
       ('FMW_DRUCK1_F', 'Funktion (FMW_DRUCK1_F) für Tests', 'Bedienung (FMW_DRUCK1_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK1_F) für Tests'),
       ('FMW_DRUCK11_F', 'Funktion (FMW_DRUCK11_F) für Tests', 'Bedienung (FMW_DRUCK11_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK11_F) für Tests'),
       ('FMW_DRUCK12_F', 'Funktion (FMW_DRUCK12_F) für Tests', 'Bedienung (FMW_DRUCK12_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK12_F) für Tests'),
       ('FMW_DRUCK13_F', 'Funktion (FMW_DRUCK13_F) für Tests', 'Bedienung (FMW_DRUCK13_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK13_F) für Tests'),
       ('FMW_DRUCK14_F', 'Funktion (FMW_DRUCK14_F) für Tests', 'Bedienung (FMW_DRUCK14_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK14_F) für Tests'),
       ('FMW_DRUCK17_F', 'Funktion (FMW_DRUCK17_F) für Tests', 'Bedienung (FMW_DRUCK17_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK17_F) für Tests'),
       ('FMW_DRUCK18_F', 'Funktion (FMW_DRUCK18_F) für Tests', 'Bedienung (FMW_DRUCK18_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK18_F) für Tests'),
       ('FMW_DRUCK2_F', 'Funktion (FMW_DRUCK2_F) für Tests', 'Bedienung (FMW_DRUCK2_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK2_F) für Tests'),
       ('FMW_DRUCK20_F', 'Funktion (FMW_DRUCK20_F) für Tests', 'Bedienung (FMW_DRUCK20_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK20_F) für Tests'),
       ('FMW_DRUCK21_F', 'Funktion (FMW_DRUCK21_F) für Tests', 'Bedienung (FMW_DRUCK21_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK21_F) für Tests'),
       ('FMW_DRUCK22_F', 'Funktion (FMW_DRUCK22_F) für Tests', 'Bedienung (FMW_DRUCK22_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK22_F) für Tests'),
       ('FMW_DRUCK23_F', 'Funktion (FMW_DRUCK23_F) für Tests', 'Bedienung (FMW_DRUCK23_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK23_F) für Tests'),
       ('FMW_DRUCK25_F', 'Funktion (FMW_DRUCK25_F) für Tests', 'Bedienung (FMW_DRUCK25_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK25_F) für Tests'),
       ('FMW_DRUCK27_F', 'Funktion (FMW_DRUCK27_F) für Tests', 'Bedienung (FMW_DRUCK27_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK27_F) für Tests'),
       ('FMW_DRUCK28_F', 'Funktion (FMW_DRUCK28_F) für Tests', 'Bedienung (FMW_DRUCK28_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK28_F) für Tests'),
       ('FMW_DRUCK3_F', 'Funktion (FMW_DRUCK3_F) für Tests', 'Bedienung (FMW_DRUCK3_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK3_F) für Tests'),
       ('FMW_DRUCK31_F', 'Funktion (FMW_DRUCK31_F) für Tests', 'Bedienung (FMW_DRUCK31_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK31_F) für Tests'),
       ('FMW_DRUCK32_F', 'Funktion (FMW_DRUCK32_F) für Tests', 'Bedienung (FMW_DRUCK32_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK32_F) für Tests'),
       ('FMW_DRUCK33_F', 'Funktion (FMW_DRUCK33_F) für Tests', 'Bedienung (FMW_DRUCK33_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK33_F) für Tests'),
       ('FMW_DRUCK34_F', 'Funktion (FMW_DRUCK34_F) für Tests', 'Bedienung (FMW_DRUCK34_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK34_F) für Tests'),
       ('FMW_DRUCK4_F', 'Funktion (FMW_DRUCK4_F) für Tests', 'Bedienung (FMW_DRUCK4_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK4_F) für Tests'),
       ('FMW_DRUCK5_F', 'Funktion (FMW_DRUCK5_F) für Tests', 'Bedienung (FMW_DRUCK5_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK5_F) für Tests'),
       ('FMW_DRUCK6_F', 'Funktion (FMW_DRUCK6_F) für Tests', 'Bedienung (FMW_DRUCK6_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK6_F) für Tests'),
       ('FMW_DRUCK8_F', 'Funktion (FMW_DRUCK8_F) für Tests', 'Bedienung (FMW_DRUCK8_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK8_F) für Tests'),
       ('FMW_DRUCK9_F', 'Funktion (FMW_DRUCK9_F) für Tests', 'Bedienung (FMW_DRUCK9_F) für Tests',
        'Vorgaben / Prüfung (FMW_DRUCK9_F) für Tests'),
       ('FMW_EUINFORMATIONEN_F', 'Funktion (FMW_EUINFORMATIONEN_F) für Tests',
        'Bedienung (FMW_EUINFORMATIONEN_F) für Tests', 'Vorgaben / Prüfung (FMW_EUINFORMATIONEN_F) für Tests'),
       ('FMW_FOERDERBEREICHE_F', 'Funktion (FMW_FOERDERBEREICHE_F) für Tests',
        'Bedienung (FMW_FOERDERBEREICHE_F) für Tests', 'Vorgaben / Prüfung (FMW_FOERDERBEREICHE_F) für Tests'),
       ('FMW_GEN_JAHRESSTAT_F', 'Funktion (FMW_GEN_JAHRESSTAT_F) für Tests',
        'Bedienung (FMW_GEN_JAHRESSTAT_F) für Tests', 'Vorgaben / Prüfung (FMW_GEN_JAHRESSTAT_F) für Tests'),
       ('FMW_GEPLANTEMASS_F', 'Funktion (FMW_GEPLANTEMASS_F) für Tests', 'Bedienung (FMW_GEPLANTEMASS_F) für Tests',
        'Vorgaben / Prüfung (FMW_GEPLANTEMASS_F) für Tests'),
       ('FMW_HHPLAN_F', 'Funktion (FMW_HHPLAN_F) für Tests', 'Bedienung (FMW_HHPLAN_F) für Tests',
        'Vorgaben / Prüfung (FMW_HHPLAN_F) für Tests'),
       ('FMW_HHSUCHE_F', 'Funktion (FMW_HHSUCHE_F) für Tests', 'Bedienung (FMW_HHSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_HHSUCHE_F) für Tests'),
       ('FMW_HINWEISE_F', 'Funktion (FMW_HINWEISE_F) für Tests', 'Bedienung (FMW_HINWEISE_F) für Tests',
        'Vorgaben / Prüfung (FMW_HINWEISE_F) für Tests'),
       ('FMW_ISTKOSTEN_F', 'Funktion (FMW_ISTKOSTEN_F) für Tests', 'Bedienung (FMW_ISTKOSTEN_F) für Tests',
        'Vorgaben / Prüfung (FMW_ISTKOSTEN_F) für Tests'),
       ('FMW_KINDERSUCHE_F', 'Funktion (FMW_KINDERSUCHE_F) für Tests', 'Bedienung (FMW_KINDERSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_KINDERSUCHE_F) für Tests'),
       ('FMW_KRANKENHAEUSER_F', 'Funktion (FMW_KRANKENHAEUSER_F) für Tests',
        'Bedienung (FMW_KRANKENHAEUSER_F) für Tests', 'Vorgaben / Prüfung (FMW_KRANKENHAEUSER_F) für Tests'),
       ('FMW_KURZBEZEICHNUNGEN_F', 'Funktion (FMW_KURZBEZEICHNUNGEN_F) für Tests',
        'Bedienung (FMW_KURZBEZEICHNUNGEN_F) für Tests', 'Vorgaben / Prüfung (FMW_KURZBEZEICHNUNGEN_F) für Tests'),
       ('FMW_PROFIL_F', 'Funktion (FMW_PROFIL_F) für Tests', 'Bedienung (FMW_PROFIL_F) für Tests',
        'Vorgaben / Prüfung (FMW_PROFIL_F) für Tests'),
       ('FMW_PROJEKTE_F', 'Funktion (FMW_PROJEKTE_F) für Tests', 'Bedienung (FMW_PROJEKTE_F) für Tests',
        'Vorgaben / Prüfung (FMW_PROJEKTE_F) für Tests'),
       ('FMW_PROJEKTSUCHE_F', 'Funktion (FMW_PROJEKTSUCHE_F) für Tests', 'Bedienung (FMW_PROJEKTSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_PROJEKTSUCHE_F) für Tests'),
       ('FMW_PROTOKOLL_F', 'Funktion (FMW_PROTOKOLL_F) für Tests', 'Bedienung (FMW_PROTOKOLL_F) für Tests',
        'Vorgaben / Prüfung (FMW_PROTOKOLL_F) für Tests'),
       ('FMW_PUBLIKATIONEN_F', 'Funktion (FMW_PUBLIKATIONEN_F) für Tests', 'Bedienung (FMW_PUBLIKATIONEN_F) für Tests',
        'Vorgaben / Prüfung (FMW_PUBLIKATIONEN_F) für Tests'),
       ('FMW_REFERATE_F', 'Funktion (FMW_REFERATE_F) für Tests', 'Bedienung (FMW_REFERATE_F) für Tests',
        'Vorgaben / Prüfung (FMW_REFERATE_F) für Tests'),
       ('FMW_SIEDLUNGSGEBIETE_F', 'Funktion (FMW_SIEDLUNGSGEBIETE_F) für Tests',
        'Bedienung (FMW_SIEDLUNGSGEBIETE_F) für Tests', 'Vorgaben / Prüfung (FMW_SIEDLUNGSGEBIETE_F) für Tests'),
       ('FMW_STADTBEZIRKE_F', 'Funktion (FMW_STADTBEZIRKE_F) für Tests', 'Bedienung (FMW_STADTBEZIRKE_F) für Tests',
        'Vorgaben / Prüfung (FMW_STADTBEZIRKE_F) für Tests'),
       ('FMW_STADTBEZIRKSLISTEN_F', 'Funktion (FMW_STADTBEZIRKSLISTEN_F) für Tests',
        'Bedienung (FMW_STADTBEZIRKSLISTEN_F) für Tests', 'Vorgaben / Prüfung (FMW_STADTBEZIRKSLISTEN_F) für Tests'),
       ('FMW_STAEDTEBAU_F', 'Funktion (FMW_STAEDTEBAU_F) für Tests', 'Bedienung (FMW_STAEDTEBAU_F) für Tests',
        'Vorgaben / Prüfung (FMW_STAEDTEBAU_F) für Tests'),
       ('FMW_STATFAG_F', 'Funktion (FMW_STATFAG_F) für Tests', 'Bedienung (FMW_STATFAG_F) für Tests',
        'Vorgaben / Prüfung (FMW_STATFAG_F) für Tests'),
       ('FMW_STATFOB_F', 'Funktion (FMW_STATFOB_F) für Tests', 'Bedienung (FMW_STATFOB_F) für Tests',
        'Vorgaben / Prüfung (FMW_STATFOB_F) für Tests'),
       ('FMW_STATPROJEKT_F', 'Funktion (FMW_STATPROJEKT_F) für Tests', 'Bedienung (FMW_STATPROJEKT_F) für Tests',
        'Vorgaben / Prüfung (FMW_STATPROJEKT_F) für Tests'),
       ('FMW_STICHWORTBEREICHE_F', 'Funktion (FMW_STICHWORTBEREICHE_F) für Tests',
        'Bedienung (FMW_STICHWORTBEREICHE_F) für Tests', 'Vorgaben / Prüfung (FMW_STICHWORTBEREICHE_F) für Tests'),
       ('FMW_TERMINE_F', 'Funktion (FMW_TERMINE_F) für Tests', 'Bedienung (FMW_TERMINE_F) für Tests',
        'Vorgaben / Prüfung (FMW_TERMINE_F) für Tests'),
       ('FMW_TRAEGER_F', 'Funktion (FMW_TRAEGER_F) für Tests', 'Bedienung (FMW_TRAEGER_F) für Tests',
        'Vorgaben / Prüfung (FMW_TRAEGER_F) für Tests'),
       ('FMW_UNTERABSCHNITTE_F', 'Funktion (FMW_UNTERABSCHNITTE_F) für Tests',
        'Bedienung (FMW_UNTERABSCHNITTE_F) für Tests', 'Vorgaben / Prüfung (FMW_UNTERABSCHNITTE_F) für Tests'),
       ('FMW_VNSUCHE_F', 'Funktion (FMW_VNSUCHE_F) für Tests', 'Bedienung (FMW_VNSUCHE_F) für Tests',
        'Vorgaben / Prüfung (FMW_VNSUCHE_F) für Tests');

TRUNCATE TABLE fp_foerderbereiche CASCADE;
INSERT INTO fp_foerderbereiche (fb, bezeichnung, finanzausgleich, jahresstatistik, kindergarten, nicht_relevant)
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