/*
----------------------------------------
------- MASTER DATA (Stammdaten) -------
----------------------------------------
*/

TRUNCATE TABLE bauleitungen RESTART IDENTITY CASCADE;
INSERT INTO bauleitungen (bauleitung, bezeichnung)
VALUES ('A', 'Bauleitung A für Tests'),
       ('B', 'Bauleitung B für Tests'),
       ('C', 'Bauleitung C für Tests');

TRUNCATE TABLE bauprogramme RESTART IDENTITY CASCADE;
INSERT INTO bauprogramme (bauprogramm, bezeichnung)
VALUES (1, 'Bauprogramm 1 für Tests'),
       (2, 'Bauprogramm 2 für Tests'),
       (3, 'Bauprogramm 3 für Tests');

TRUNCATE TABLE benutzerhinweise RESTART IDENTITY CASCADE;
INSERT INTO benutzerhinweise (view_id, funktionsbeschreibung, bedienung, pruefung_vorgaben)
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

TRUNCATE TABLE foerderbereiche RESTART IDENTITY CASCADE;
INSERT INTO foerderbereiche (fb, bezeichnung, finanzausgleich, jahresstatistik, kindergarten, nicht_relevant)
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

TRUNCATE TABLE krankenhaeuser RESTART IDENTITY CASCADE;
INSERT INTO krankenhaeuser (krhname, bezeichnung)
VALUES ('A', 'Krankenhaus A für Tests'),
       ('B', 'Krankenhaus B für Tests'),
       ('C', 'Krankenhaus C für Tests');

TRUNCATE TABLE kurzbezeichnungen RESTART IDENTITY CASCADE;
INSERT INTO kurzbezeichnungen (kurzbez, bezeichnung)
VALUES ('KURZBEZ1', 'Kurzbezeichnung KURZBEZ1 für Tests'),
       ('KURZBEZ2', 'Kurzbezeichnung KURZBEZ2 für Tests'),
       ('KURZBEZ3', 'Kurzbezeichnung KURZBEZ3 für Tests');

TRUNCATE TABLE publikationen RESTART IDENTITY CASCADE;
INSERT into publikationen (kurzform, bezeichnung)
VALUES ('A', 'Publikation A für Tests'),
       ('B', 'Publikation B für Tests'),
       ('C', 'Publikation C für Tests');

TRUNCATE TABLE referate RESTART IDENTITY CASCADE;
INSERT INTO referate (refnr, bezeichnung)
VALUES (1, 'Referat 1 für Tests'),
       (2, 'Referat 2 für Tests'),
       (3, 'Referat 3 für Tests');

TRUNCATE TABLE siedlungsgebiete RESTART IDENTITY CASCADE;
INSERT INTO siedlungsgebiete (siedlungsgebiet, bezeichnung)
VALUES (1, 'Siedlungsgebiet 1 für Tests'),
       (2, 'Siedlungsgebiet 2 für Tests'),
       (3, 'Siedlungsgebiet 3 für Tests');

TRUNCATE TABLE stadtbezirke RESTART IDENTITY CASCADE;
INSERT INTO stadtbezirke (stadtbezirk, bezeichnung)
VALUES (1, 'Stadtbezirk 1 für Tests'),
       (2, 'Stadtbezirk 2 für Tests'),
       (3, 'Stadtbezirk 3 für Tests');

TRUNCATE TABLE listennamen RESTART IDENTITY CASCADE;
INSERT INTO listennamen (kurzbez, bezeichnung)
VALUES ('SL1', 'Listenname SL1 für Tests'),
       ('SL2', 'Listenname SL2 für Tests'),
       ('SL3', 'Listenname SL3 für Tests');

TRUNCATE TABLE stadtbezirkslisten RESTART IDENTITY CASCADE;
INSERT INTO stadtbezirkslisten (lna_kurzbez, bez_stadtbezirk, bezeichnung)
VALUES ('SL1', 1, 'Zuordnung von Stadtbezirk 1 zu Listenname SL1 für Tests'),
       ('SL2', 2, 'Zuordnung von Stadtbezirk 2 zu Listenname SL2 für Tests'),
       ('SL2', 3, 'Zuordnung von Stadtbezirk 3 zu Listenname SL2 für Tests');

TRUNCATE TABLE stichwortbereiche RESTART IDENTITY CASCADE;
INSERT INTO stichwortbereiche (bereich, bezeichnung)
VALUES ('STICHWORTBEREICH1', 'Stichwortbereich STICHWORTBEREICH1 für Tests'),
       ('STICHWORTBEREICH2', 'Stichwortbereich STICHWORTBEREICH2 für Tests'),
       ('STICHWORTBEREICH3', 'Stichwortbereich STICHWORTBEREICH3 für Tests');

TRUNCATE TABLE traeger RESTART IDENTITY CASCADE;
INSERT INTO traeger (kurzform, bezeichnung)
VALUES (1, 'Träger 1 für Tests'),
       (2, 'Träger 2 für Tests'),
       (3, 'Träger 3 für Tests');

TRUNCATE TABLE hauptabschnitte RESTART IDENTITY CASCADE;
INSERT INTO hauptabschnitte (ha, bezeichnung)
VALUES ('01', 'Hauptabschnitt 01 für Tests'),
       ('02', 'Hauptabschnitt 02 für Tests'),
       ('03', 'Hauptabschnitt 03 für Tests');

TRUNCATE TABLE unterabschnitte RESTART IDENTITY CASCADE;
INSERT INTO unterabschnitte (ua, bezeichnung, has_ha)
VALUES ('11', 'Unterabschnitt 11 für Tests', '01'),
       ('21', 'Unterabschnitt 21 für Tests', '02'),
       ('22', 'Unterabschnitt 22 für Tests', '02'),
       ('31', 'Unterabschnitt 31 für Tests', '03'),
       ('32', 'Unterabschnitt 32 für Tests', '03'),
       ('33', 'Unterabschnitt 33 für Tests', '03');

/*
----------------------------------------
------- TRANSACTIONAL DATA (Bewegungsdaten) -------
----------------------------------------
*/

TRUNCATE TABLE projekte RESTART IDENTITY CASCADE;
INSERT INTO projekte (projnr, fob_fb, kur_kurzbez, uas_ua, jahr, lfdnr1, lfdnr2, pname, pstrasse, kauf, projart,
                      refinanzierbar, ble_bauleitung, bauleitungkontakt, bez_stadtbezirk, krn_krhname, krhzweck,
                      krisofp, kripplatz, kigaplatz, hortplatz, vndat, vnkosten, vnzwfkosten, vnpruefdat, vnrueck_z,
                      vnnachfoerderung, vnpruefzwf, vnschlusszwf, vnschlussbew, sap, sapstatauf, sapmatnr, sapwertnr,
                      sapjahrwert, sapanlagennr, zinsen, zinsdatum, fipo, buchungskreis, sachkonto, fipo_k,
                      buchungskreis_k, sachkonto_k, psbaubuch, psbauref, psbaunr, notizen, altdaten, anlagedatum,
                      anlagevon, aenderungsdatum, aenderungvon, vngesamtzuwendung, sapinnenauftrag, bpg_bauprogramm,
                      sgt_siedlungsgebiet, bauende, baubeendet, bauvergabe1, baubeginn, baumitteilung, kreditnummer,
                      stadtanleihe, anleihenennwert, anleihejahrvon, anleihejahrbis)
VALUES ('1124101', '11', 'KURZBEZ1', '11', '24', '1', '01', 'Projektname 1', 'Müllerstraße 12', 'false', 'Projektart 1',
        'false', 'A', 'Klaus-Jürgen Meier, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL),
       ('2124101', '12', 'KURZBEZ1', '21', '24', '1', '01', 'Projektname 2', 'Schönhauser Allee 42', 'false',
        'Projektart 2', 'true', 'B', 'Anna-Lena Schmidt, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       ('2224103', '14', 'KURZBEZ2', '22', '24', '1', '01', 'Projektname 3', 'Bäckerstraße 7', 'false', 'Projektart 3', 'true',
        'A', 'Max Müller, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL),
       ('3325101', '11', 'KURZBEZ1', '33', '25', '1', '01', 'Projektname 4', 'Am Waldweg 3', 'false', 'Projektart 1', 'false',
        'C', 'Sophie von Hohenberg, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('3225101', '11', 'KURZBEZ3', '32', '25', '1', '01', 'Projektname 5', 'Hauptstraße 99', 'false', 'Projektart 3', 'true',
        'C', 'Lavinia Traumschreiber, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('1125101', '11', 'KURZBEZ1', '11', '25', '1', '01', 'Projektname 6', 'Müllerstraße 12', 'false', 'Projektart 3',
        'true', 'A', 'Aurelius Federwolke, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL),
       ('1126101', '11', 'KURZBEZ1', '11', '26', '1', '01', 'Projektname 7', 'Müllerstraße 12', 'false', 'Projektart 4',
        'false', 'B', 'Verena Lichtspiel, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('2126101', '11', 'KURZBEZ3', '21', '26', '1', '01', 'Projektname 8', 'Mühlenweg 8', 'false', 'Projektart 1', 'true',
        'A', 'Alaric Nebelgeist, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('1126103', '11', 'KURZBEZ1', '11', '26', '1', '01', 'Projektname 9', 'Alte-Lindenstraße 45', 'false',
        'Projektart 3', 'true', 'B', 'Celestine Glimmerstein, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       ('1126104', '11', 'KURZBEZ2', '11', '26', '1', '01', 'Projektname 10', 'Schillerstraße-Ost 7', 'false',
        'Projektart 2', 'true', 'C', 'Max Mustermann, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, 'true', 'false', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, 'false', '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

TRUNCATE TABLE antraege RESTART IDENTITY CASCADE;
INSERT INTO antraege (id, pro_projnr, antragsdatum, antragstyp, geskosten, zwfkosten, vorzbeg, vbdatum, unbeddat,
                      unbedja, unbedbis, a_su_z, a_su_d, a_su_k, b_vor_su_z, b_vor_su_d, b_vor_su_k, notizen,
                      anlagedatum, anlagevon, aenderungsdatum, aenderungvon)
VALUES ('1', '1124101', '2024-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('2', '2124101', '2024-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('3', '2224103', '2024-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('4', '3325101', '2025-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('5', '3225101', '2025-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('6', '1125101', '2025-01-22', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('7', '1126101', '2026-01-02', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('8', '2126101', '2026-01-05', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('9', '1126103', '2026-01-08', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('10', '1126104', '2026-01-10', 'E', '500000', '250000', 'true', NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('11', '1126101', '2026-01-15', 'E', '500000', '250000', 'false', NULL, NULL, NULL, NULL, '125000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('12', '1126101', '2026-01-18', 'A', '500000', '260000', 'false', NULL, NULL, NULL, NULL, '25000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ('13', '1126101', '2026-01-22', 'F', '500000', '260000', 'false', NULL, NULL, NULL, NULL, '25000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL);

TRUNCATE TABLE bewilligungen RESTART IDENTITY CASCADE;
INSERT INTO bewilligungen (id, pro_projnr, ant_id, bdatum, afsatz, bfsatz, bzwfkosten, bzuwendung_z, bzuwendung_d,
                           bzuwendung_k, bzuwart, baktenzeichen, geszuwendungen, geskonnex, krw, notizen, anlagedatum,
                           anlagevon, aenderungsdatum, aenderungvon)
VALUES ('1', '1126101', '7', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER'),
       ('2', '1124101', '1', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER'),
       ('3', '3225101', '5', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER');

TRUNCATE TABLE abrufe RESTART IDENTITY CASCADE;
INSERT INTO abrufe (id, pro_projnr, bwi_id, vnabr, abruf_z, abruf_d, abruf_k, abruf_datum, erh_z, erh_d, erh_k,
                    erh_datum, ref_refnr, sapabrufauftragsnr, sapfakturanr, fipo, buchungskreis, sachkonto, fipo_k,
                    buchungskreis_k, sachkonto_k, notizen, anlagedatum, anlagevon, aenderungsdatum, aenderungvon)
VALUES ('1', '1126101', '1', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2026-04-23', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User'),
       ('2', '1124101', '2', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2024-05-10', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User'),
       ('3', '3225101', '3', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2025-07-01', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User');

TRUNCATE TABLE projektistkosten RESTART IDENTITY CASCADE;
INSERT INTO projektistkosten (pro_projnr, jahr, monat, istkosten)
VALUES ('1126101', '2026', '3', '25000'),
       ('1126101', '2026', '4', '30000');

TRUNCATE TABLE projekttermine RESTART IDENTITY CASCADE;
INSERT INTO projekttermine (id, pro_projnr, termin, zustaendig, telefon, notizen, ueberwachung)
VALUES ('1', '1126101', '2026-07-22', 'Frau. Test', '00000000',
        'Fusce tincidunt, nisl quis bibendum fermentum, libero justo tincidunt eros (at gravida ex). Nisi ut nibh. Curabitur blandit leo a dictum euismod. Phasellus non lacus ut ligula gravida dapibus (Nam tincidunt, justo non aliquam ultrices).',
        'false');

TRUNCATE TABLE archiv RESTART IDENTITY CASCADE;
INSERT INTO archiv (id, pro_projnr, speicherdatum, speicherakt, speicherrechnungen, mikrodatplan, mikrodat, notizen)
VALUES ('1', '1124101', '2024-09-14', 'false', 'true', '2024-09-16', '2024-09-18',
        'Fusce tincidunt, nisl quis bibendum fermentum'),
       ('2', '3325101', '2025-09-14', 'false', 'false', '2025-09-16', '2025-09-18', NULL),
       ('3', '1126104', '2026-04-14', 'false', 'false', '2026-04-16', '2026-04-18',
        'Fusce tincidunt, nisl quis bibendum fermentum');

TRUNCATE TABLE ablageindexe RESTART IDENTITY CASCADE;
INSERT INTO ablageindexe (id, stb_bereich, nr, wort, stichworte)
values ('1', 'STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2024 Beispieltext', 'Beispiel Stichwort'),
       ('2', 'STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2025 Beispieltext', 'Beispiel Stichwort'),
       ('3', 'STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2026 Beispieltext', 'Beispiel Stichwort');

TRUNCATE TABLE euinformationen RESTART IDENTITY CASCADE;
INSERT INTO euinformationen (id, jahr, pub_kurzform, hefta, nummer, wichtig, seitennr, stichwort, rawi, schulref,
                             sozref_r_5, rgu_11, rgu_cs, krh, afa, swm, kulturref, bauref, planref, direktorium, por,
                             kvr, kommref, sew, stk, inhalt, infodat)
VALUES ('1', '2001', 'A', NULL, '88', 'true', '1', NULL, 'true', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false',
        'false', 'false', 'false', 'false', 'Anonymisierte Beschreibung 1', NULL),
       ('2', '2002', 'B', NULL, '89', 'true', '4', NULL, 'true', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false',
        'false', 'false', 'false', 'false', 'Anonymisierte Beschreibung 2', NULL),
       ('3', '2003', 'C', NULL, '90', 'true', '2', NULL, 'true', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false',
        'false', 'false', 'false', 'false', 'Anonymisierte Beschreibung 3', NULL);

TRUNCATE TABLE geplantemassnahmen RESTART IDENTITY CASCADE;
INSERT INTO geplantemassnahmen (id, kur_kurzbez, bez_stadtbezirk, strasse, projekt, baubeginn, angelegt, plannr)
VALUES ('1', 'KURZBEZ1', '3', 'Anonyme Straße 1', 'Anonymes Projekt Beschreibung', NULL, '2015', NULL),
       ('2', 'KURZBEZ2', '2', 'Anonyme Straße 2', 'Anonymes Projekt Beschreibung 2', NULL, '2016', NULL),
       ('3', 'KURZBEZ3', '1', 'Anonyme Straße 3', 'Anonymes Projekt Beschreibung 3', NULL, '2017', NULL);

TRUNCATE TABLE hhjahre RESTART IDENTITY CASCADE;
INSERT INTO hhjahre (hhjahr, hhbezeichnung, notizen)
VALUES ('2024', 'Haushalt 2024', NULL),
       ('2025', 'Haushalt 2025', NULL),
       ('2026', 'Haushalt 2026', NULL);

TRUNCATE TABLE hhplan RESTART IDENTITY CASCADE;
INSERT INTO hhplan (hhj_jahr, fipo, pro_projnr, p_pstrasse, p_pname, p_fob_fb, p_bez_stadtbezirk, p_lna_kurzbez,
                    p_vndat, mitteleinplanung, bewilligung_voj, bewilligung_hhj, erhalten_voj, erhalten_hhj,
                    baustandaktuell, an, n1, n2, notizen, anlagedatum, anlagevon, aenderungsdatum, aenderungvon)
VALUES ('2026', '0000.000.0000.1', '2126101', 'Anonyme Straße 1', 'Anonymes Projekt 1', '14', '23', 'A', NULL, '338000',
        '270000', '0', '270000', '0', '100', '0', '0', '0', NULL, '2026-06-15', 'Anonym-User', '2024-04-22',
        'Anonym-User'),
       ('2025', '0000.000.0000.3', '3225101', 'Anonyme Straße 3', 'Anonymes Projekt 3', '14', '23', 'H', NULL, '338000',
        '90000', '0', '90000', '0', '95.1', '0', '0', '0', NULL, '2026-08-17', 'Anonym-User', '2022-05-13',
        'Anonym-User'),
       ('2025', '0000.000.0000.4', '3325101', 'Anonyme Straße 4', 'Anonymes Projekt 4', '14', '23', 'F', NULL, '338000',
        '90000', '0', '90000', '0', '58.91', '0', '0', '0', NULL, '2026-03-20', 'Anonym-User', '2026-06-10',
        'Anonym-User'),
       ('2024', '0000.000.0000.5', '2124101', 'Anonyme Straße 5', 'Anonymes Projekt 5', '14', '23', 'F', NULL, '338000',
        '0', '0', '0', '0', '13.02', '0', '0', '0', NULL, '2026-08-27', 'Anonym-User', '2026-06-15', 'Anonym-User'),
       ('2024', '0000.000.0000.6', '2224103', 'Anonyme Straße 6', 'Anonymes Projekt 6', '14', '23', 'A', NULL, '338000',
        '0', '0', '0', '0', '0', '0', '0', '0', NULL, '2026-06-14', 'Anonym-User', '2026-06-14', 'Anonym-User');

TRUNCATE TABLE jahresstatistik RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik (jahr, jahresstatistik1_am, jahresstatistik1_von, jahresstatistik2_am, jahresstatistik2_von,
                             jahresstatistik3_am, jahresstatistik3_von, notizen)
VALUES ('2024', '2024-03-09', 'Anonym-User', '2024-03-09', 'Anonym-User', '2024-03-09', 'Anonym-User', NULL),
       ('2025', '2025-03-09', 'Anonym-User', '2025-03-09', 'Anonym-User', '2025-03-09', 'Anonym-User', NULL),
       ('2026', '2026-03-09', 'Anonym-User', '2026-03-09', 'Anonym-User', '2026-03-09', 'Anonym-User', NULL);

TRUNCATE TABLE jahresstatistik1 RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik1 (id, jss_jahr, foerderbereich, fb, gruppe, gesamtkosten, zuwendungsfaehig, b_zuschuss,
                              b_darlehen, b_konnexitaet, e_zuschuss, e_darlehen, e_konnexitaet)
VALUES ('1', '2026', 'Anonym', '11', '1', '50000000', '25000000', '8000000', '0', '0', '5000000', '1500000', '0');

TRUNCATE TABLE jahresstatistik2 RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik2 (id, jss_jahr, foerderbereich, fb, gruppe, anzahl_abrufe, anzahl_vn, anzahl_bewilligungen,
                              vngeskosten, anzahl_erst, a_su_z_erst, a_su_k_erst, anzahl_folge, a_su_z_folge,
                              a_su_k_folge, a_vor_su_z_gesamt, a_vor_su_k_gesamt, anzahl_unbed)
VALUES ('1', '2026', 'Bereich 11', '11', '1', '100', '10', '80', '50000000', '15', '1000000', '0', '25', '15000000',
        '0', '8000000', '0', '0');

TRUNCATE TABLE jahresstatistik3 RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik3 (id, jss_jahr, foerderbereich, fb, gruppe, bzuwendung_z_plus, bzuwendung_z_minus,
                              bzuwendung_d_plus, bzuwendung_d_minus, bzuwendung_k_plus, bzuwendung_k_minus, erh_z,
                              erh_d, erh_k)
VALUES ('1', '2026', 'Bereich 11', '11', '1', '8000000', '0', '0', '0', '0', '0', '5000000', '1500000', '0');

TRUNCATE TABLE staedtebaufoerderungen RESTART IDENTITY CASCADE;
INSERT INTO staedtebaufoerderungen (id, bdat, bnr, bjahr, betrag, soz, azbank, antrnr, antrjahr, azstk, ausbet, erhdat,
                                    tra_kurzform, hst, hulnr, huljahr, notizen, projektname, restlos, zahlanz,
                                    schuldurk, alt_lfdnr)
VALUES ('1', '2003-09-03', '001', '2004', '300000', 'false', 'BANK', '001', '2004', '007', '0', NULL, NULL, NULL, NULL,
        NULL, 'Anonymisierte Wohnraumförderung, Anonymes Projektname', 'Darlehensaufnahme, Anonyme Straße', NULL, NULL,
        'false', '1091');