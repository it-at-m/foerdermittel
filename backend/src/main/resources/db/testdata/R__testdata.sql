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
VALUES ('bauleitungen', 'Funktionsbeschreibung für Bauleitungen', 'Bedienungshínweis für Bauleitungen',
        'Prüfungen und Vorgaben für Bauleitungen'),
       ('bauprogramme', 'Funktionsbeschreibung für Bauprogramme', 'Bedienungshínweis für Bauprogramme',
        'Prüfungen und Vorgaben für Bauprogramme'),
       ('krankenhaeuser', 'Funktionsbeschreibung für Krankenhäuser', 'Bedienungshínweis für Krankenhäuser',
        'Prüfungen und Vorgaben für Krankenhäuser'),
       ('kurzbezeichnungen', 'Funktionsbeschreibung für Kurzbezeichnungen', 'Bedienungshínweis für Kurzbezeichnungen',
        'Prüfungen und Vorgaben für Kurzbezeichnungen'),
       ('publikationen', 'Funktionsbeschreibung für Publikationen', 'Bedienungshínweis für Publikationen',
        'Prüfungen und Vorgaben für Publikationen'),
       ('referate', 'Funktionsbeschreibung für Referate', 'Bedienungshínweis für Referate',
        'Prüfungen und Vorgaben für Referate'),
       ('siedlungsgebiete', 'Funktionsbeschreibung für Siedlungsgebiete', 'Bedienungshínweis für Siedlungsgebiete',
        'Prüfungen und Vorgaben für Siedlungsgebiete'),
       ('stadtbezirke', 'Funktionsbeschreibung für Stadtbezirke', 'Bedienungshínweis für Stadtbezirke',
        'Prüfungen und Vorgaben für Stadtbezirke'),
       ('traeger', 'Funktionsbeschreibung für Träger', 'Bedienungshínweis für Träger',
        'Prüfungen und Vorgaben für Träger'),
       ('foerderbereiche', 'Funktionsbeschreibung für Förderbereiche', 'Bedienungshínweis für Förderbereiche',
        'Prüfungen und Vorgaben für Förderbereiche');

TRUNCATE TABLE foerderbereiche RESTART IDENTITY CASCADE;
INSERT INTO foerderbereiche (fb, bezeichnung, finanzausgleich, jahresstatistik, kindergarten, nicht_relevant)
VALUES (1, 'Förderbereich 1 für Tests', FALSE, FALSE, FALSE, FALSE),
       (2, 'Förderbereich 2 für Tests', TRUE, FALSE, FALSE, FALSE),
       (3, 'Förderbereich 3 für Tests', FALSE, TRUE, FALSE, FALSE),
       (4, 'Förderbereich 4 für Tests', FALSE, FALSE, TRUE, FALSE),
       (5, 'Förderbereich 5 für Tests', FALSE, FALSE, FALSE, TRUE),
       (6, 'Förderbereich 6 für Tests', TRUE, TRUE, FALSE, FALSE),
       (7, 'Förderbereich 7 für Tests', TRUE, FALSE, TRUE, FALSE),
       (8, 'Förderbereich 8 für Tests', TRUE, FALSE, FALSE, TRUE),
       (9, 'Förderbereich 9 für Tests', FALSE, TRUE, TRUE, FALSE),
       (10, 'Förderbereich 10 für Tests', FALSE, TRUE, FALSE, TRUE),
       (11, 'Förderbereich 11 für Tests', FALSE, FALSE, TRUE, TRUE),
       (12, 'Förderbereich 12 für Tests', TRUE, TRUE, TRUE, FALSE),
       (13, 'Förderbereich 13 für Tests', TRUE, TRUE, FALSE, TRUE),
       (14, 'Förderbereich 14 für Tests', TRUE, FALSE, TRUE, TRUE),
       (15, 'Förderbereich 15 für Tests', FALSE, TRUE, TRUE, TRUE),
       (16, 'Förderbereich 16 für Tests', TRUE, TRUE, TRUE, TRUE);

TRUNCATE TABLE krankenhaeuser RESTART IDENTITY CASCADE;
INSERT INTO krankenhaeuser (krhname, bezeichnung)
VALUES ('A', 'Krankenhaus A für Tests'),
       ('B', 'Krankenhaus B für Tests'),
       ('C', 'Krankenhaus C für Tests');

TRUNCATE TABLE kurzbezeichnungen RESTART IDENTITY CASCADE;
INSERT INTO kurzbezeichnungen (kurzbez, bezeichnung)
VALUES ('KB1', 'Kurzbezeichnung KB1 für Tests'),
       ('KB2', 'Kurzbezeichnung KB2 für Tests'),
       ('KB3', 'Kurzbezeichnung KB3 für Tests');

TRUNCATE TABLE publikationen RESTART IDENTITY CASCADE;
INSERT INTO publikationen (kurzform, bezeichnung)
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
VALUES ('1124101', '11', 'KB1', '11', '24', '1', '01', 'Projektname 1', 'Müllerstraße 12', FALSE, 'Projektart 1',
        FALSE, 'A', 'Klaus-Jürgen Meier, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL),
       ('2124101', '12', 'KB1', '21', '24', '1', '01', 'Projektname 2', 'Schönhauser Allee 42', FALSE,
        'Projektart 2', TRUE, 'B', 'Anna-Lena Schmidt, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       ('2224103', '14', 'KB2', '22', '24', '1', '01', 'Projektname 3', 'Bäckerstraße 7', FALSE, 'Projektart 3', TRUE,
        'A', 'Max Müller, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL),
       ('3325101', '11', 'KB1', '33', '25', '1', '01', 'Projektname 4', 'Am Waldweg 3', FALSE, 'Projektart 1', FALSE,
        'C', 'Sophie von Hohenberg, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('3225101', '11', 'KB3', '32', '25', '1', '01', 'Projektname 5', 'Hauptstraße 99', FALSE, 'Projektart 3', TRUE,
        'C', 'Lavinia Traumschreiber, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('1125101', '11', 'KB1', '11', '25', '1', '01', 'Projektname 6', 'Müllerstraße 12', FALSE, 'Projektart 3',
        TRUE, 'A', 'Aurelius Federwolke, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL),
       ('1126101', '11', 'KB1', '11', '26', '1', '01', 'Projektname 7', 'Müllerstraße 12', FALSE, 'Projektart 4',
        FALSE, 'B', 'Verena Lichtspiel, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('2126101', '11', 'KB3', '21', '26', '1', '01', 'Projektname 8', 'Mühlenweg 8', FALSE, 'Projektart 1', TRUE,
        'A', 'Alaric Nebelgeist, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('1126103', '11', 'KB1', '11', '26', '1', '01', 'Projektname 9', 'Alte-Lindenstraße 45', FALSE,
        'Projektart 3', TRUE, 'B', 'Celestine Glimmerstein, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       ('1126104', '11', 'KB2', '11', '26', '1', '01', 'Projektname 10', 'Schillerstraße-Ost 7', FALSE,
        'Projektart 2', TRUE, 'C', 'Max Mustermann, HO3', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, TRUE, FALSE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, FALSE, '2026-04-22', 'Test-User', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

TRUNCATE TABLE antraege RESTART IDENTITY CASCADE;
INSERT INTO antraege (pro_projnr, antragsdatum, antragstyp, geskosten, zwfkosten, vorzbeg, vbdatum, unbeddat,
                      unbedja, unbedbis, a_su_z, a_su_d, a_su_k, b_vor_su_z, b_vor_su_d, b_vor_su_k, notizen,
                      anlagedatum, anlagevon, aenderungsdatum, aenderungvon)
VALUES ( '1124101', '2024-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '2124101', '2024-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '2224103', '2024-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '3325101', '2025-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '3225101', '2025-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1125101', '2025-01-22', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126101', '2026-01-02', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '2126101', '2026-01-05', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126103', '2026-01-08', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126104', '2026-01-10', 'E', '500000', '250000', TRUE, NULL, NULL, NULL, NULL, '250000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126101', '2026-01-15', 'E', '500000', '250000', FALSE, NULL, NULL, NULL, NULL, '125000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126101', '2026-01-18', 'A', '500000', '260000', FALSE, NULL, NULL, NULL, NULL, '25000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL),
       ( '1126101', '2026-01-22', 'F', '500000', '260000', FALSE, NULL, NULL, NULL, NULL, '25000', '0', '0', '0',
        '0', '0', NULL, '2026-04-22', 'Test-User', NULL, NULL);

TRUNCATE TABLE bewilligungen RESTART IDENTITY CASCADE;
INSERT INTO bewilligungen (pro_projnr, ant_id, bdatum, afsatz, bfsatz, bzwfkosten, bzuwendung_z, bzuwendung_d,
                           bzuwendung_k, bzuwart, baktenzeichen, geszuwendungen, geskonnex, krw, notizen, anlagedatum,
                           anlagevon, aenderungsdatum, aenderungvon)
VALUES ('1126101', '7', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER'),
       ( '1124101', '1', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER'),
       ('3225101', '5', '2026-04-22', '0', '50', '250000', '125000', '0', '0', 'T', '000000000', '125000', '0',
        NULL, NULL, '2026-04-22', 'TEST-USER', '2026-04-22', 'TEST-USER');

TRUNCATE TABLE abrufe RESTART IDENTITY CASCADE;
INSERT INTO abrufe (pro_projnr, bwi_id, vnabr, abruf_z, abruf_d, abruf_k, abruf_datum, erh_z, erh_d, erh_k,
                    erh_datum, ref_refnr, sapabrufauftragsnr, sapfakturanr, fipo, buchungskreis, sachkonto, fipo_k,
                    buchungskreis_k, sachkonto_k, notizen, anlagedatum, anlagevon, aenderungsdatum, aenderungvon)
VALUES ( '1126101', '1', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2026-04-23', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User'),
       ('1124101', '2', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2024-05-10', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User'),
       ('3225101', '3', NULL, '125000', '0', '0', '2026-04-23', '125000', '0', '0', '2025-07-01', '1', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-22', 'Test-User', '2026-04-22', 'Test-User');

TRUNCATE TABLE projektistkosten RESTART IDENTITY CASCADE;
INSERT INTO projektistkosten (pro_projnr, jahr, monat, istkosten)
VALUES ('1126101', '2026', '3', '25000'),
       ('1126101', '2026', '4', '30000');

TRUNCATE TABLE projekttermine RESTART IDENTITY CASCADE;
INSERT INTO projekttermine (pro_projnr, termin, zustaendig, telefon, notizen, ueberwachung)
VALUES ('1126101', '2026-07-22', 'Frau. Test', '00000000',
        'Fusce tincidunt, nisl quis bibendum fermentum, libero justo tincidunt eros (at gravida ex). Nisi ut nibh. Curabitur blandit leo a dictum euismod. Phasellus non lacus ut ligula gravida dapibus (Nam tincidunt, justo non aliquam ultrices).',
        FALSE);

TRUNCATE TABLE archiv RESTART IDENTITY CASCADE;
INSERT INTO archiv (pro_projnr, speicherdatum, speicherakt, speicherrechnungen, mikrodatplan, mikrodat, notizen)
VALUES ('1124101', '2024-09-14', FALSE, TRUE, '2024-09-16', '2024-09-18',
        'Fusce tincidunt, nisl quis bibendum fermentum'),
       ('3325101', '2025-09-14', FALSE, FALSE, '2025-09-16', '2025-09-18', NULL),
       ('1126104', '2026-04-14', FALSE, FALSE, '2026-04-16', '2026-04-18',
        'Fusce tincidunt, nisl quis bibendum fermentum');

TRUNCATE TABLE ablageindexe RESTART IDENTITY CASCADE;
INSERT INTO ablageindexe (stb_bereich, nr, wort, stichworte)
values ('STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2024 Beispieltext', 'Beispiel Stichwort'),
       ('STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2025 Beispieltext', 'Beispiel Stichwort'),
       ('STICHWORTBEREICH1', '1', 'Anonymisierte Mitteilung v. 01.01.2026 Beispieltext', 'Beispiel Stichwort');

TRUNCATE TABLE euinformationen RESTART IDENTITY CASCADE;
INSERT INTO euinformationen (jahr, pub_kurzform, hefta, nummer, wichtig, seitennr, stichwort, rawi, schulref,
                             sozref_r_5, rgu_11, rgu_cs, krh, afa, swm, kulturref, bauref, planref, direktorium, por,
                             kvr, kommref, sew, stk, inhalt, infodat)
VALUES ('2001', 'A', NULL, '88', TRUE, '1', NULL, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE,
        FALSE, FALSE, FALSE, FALSE, 'Anonymisierte Beschreibung 1', NULL),
       ('2002', 'B', NULL, '89', TRUE, '4', NULL, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE,
        FALSE, FALSE, FALSE, FALSE, 'Anonymisierte Beschreibung 2', NULL),
       ('2003', 'C', NULL, '90', TRUE, '2', NULL, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE,
        FALSE, FALSE, FALSE, FALSE, 'Anonymisierte Beschreibung 3', NULL);

TRUNCATE TABLE geplantemassnahmen RESTART IDENTITY CASCADE;
INSERT INTO geplantemassnahmen (kur_kurzbez, bez_stadtbezirk, strasse, projekt, baubeginn, angelegt, plannr)
VALUES ( 'KB1', '3', 'Anonyme Straße 1', 'Anonymes Projekt Beschreibung', NULL, '2015', NULL),
       ('KB2', '2', 'Anonyme Straße 2', 'Anonymes Projekt Beschreibung 2', NULL, '2016', NULL),
       ('KB3', '1', 'Anonyme Straße 3', 'Anonymes Projekt Beschreibung 3', NULL, '2017', NULL);

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
INSERT INTO jahresstatistik1 (jss_jahr, foerderbereich, fb, gruppe, gesamtkosten, zuwendungsfaehig, b_zuschuss,
                              b_darlehen, b_konnexitaet, e_zuschuss, e_darlehen, e_konnexitaet)
VALUES ('2026', 'Anonym', '11', '1', '50000000', '25000000', '8000000', '0', '0', '5000000', '1500000', '0');

TRUNCATE TABLE jahresstatistik2 RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik2 (jss_jahr, foerderbereich, fb, gruppe, anzahl_abrufe, anzahl_vn, anzahl_bewilligungen,
                              vngeskosten, anzahl_erst, a_su_z_erst, a_su_k_erst, anzahl_folge, a_su_z_folge,
                              a_su_k_folge, a_vor_su_z_gesamt, a_vor_su_k_gesamt, anzahl_unbed)
VALUES ( '2026', 'Bereich 11', '11', '1', '100', '10', '80', '50000000', '15', '1000000', '0', '25', '15000000',
        '0', '8000000', '0', '0');

TRUNCATE TABLE jahresstatistik3 RESTART IDENTITY CASCADE;
INSERT INTO jahresstatistik3 (jss_jahr, foerderbereich, fb, gruppe, bzuwendung_z_plus, bzuwendung_z_minus,
                              bzuwendung_d_plus, bzuwendung_d_minus, bzuwendung_k_plus, bzuwendung_k_minus, erh_z,
                              erh_d, erh_k)
VALUES ('2026', 'Bereich 11', '11', '1', '8000000', '0', '0', '0', '0', '0', '5000000', '1500000', '0');

TRUNCATE TABLE staedtebaufoerderungen RESTART IDENTITY CASCADE;
INSERT INTO staedtebaufoerderungen (bdat, bnr, bjahr, betrag, soz, azbank, antrnr, antrjahr, azstk, ausbet, erhdat,
                                    tra_kurzform, hst, hulnr, huljahr, notizen, projektname, restlos, zahlanz,
                                    schuldurk, alt_lfdnr)
VALUES ('2003-09-03', '001', '2004', '300000', FALSE, 'BANK', '001', '2004', '007', '0', NULL, NULL, NULL, NULL,
        NULL, 'Anonymisierte Wohnraumförderung, Anonymes Projektname', 'Darlehensaufnahme, Anonyme Straße', NULL, NULL,
        FALSE, '1091');