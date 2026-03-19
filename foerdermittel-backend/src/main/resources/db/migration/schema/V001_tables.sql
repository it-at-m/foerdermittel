--------------------------------------------------------
--  DDL for Table FP_ABLAGEINDEXE
--------------------------------------------------------

CREATE TABLE "FP_ABLAGEINDEXE"
(
    "ID"          NUMERIC(38, 0),
    "STB_BEREICH" VARCHAR(30),
    "NR"          NUMERIC(4, 0),
    "WORT"        VARCHAR(80),
    "STICHWORTE"  VARCHAR(400)
);

COMMENT ON COLUMN "FP_ABLAGEINDEXE"."ID" IS 'Primary Key (Sequence)';
COMMENT ON COLUMN "FP_ABLAGEINDEXE"."STB_BEREICH" IS 'Bereich und Bezeichnung des Ablagedeckblattes';
COMMENT ON COLUMN "FP_ABLAGEINDEXE"."NR" IS 'Lfd. Nummer innerhalb des Deckblattes';
COMMENT ON COLUMN "FP_ABLAGEINDEXE"."WORT" IS 'Thema des Schriftgutes';
COMMENT ON COLUMN "FP_ABLAGEINDEXE"."STICHWORTE" IS 'Stichwort (Index) nach dem gesucht werden kann';
COMMENT ON TABLE "FP_ABLAGEINDEXE" IS 'Suchindex zum Auffinden von Ablagen (dbase:hinh)';
--------------------------------------------------------
--  DDL for Table FP_ABRUFE
--------------------------------------------------------

CREATE TABLE "FP_ABRUFE"
(
    "ID"                 NUMERIC(38, 0),
    "PRO_PROJNR"         VARCHAR(7),
    "BWI_ID"             NUMERIC(38, 0),
    "VNABR"              VARCHAR(1),
    "ABRUF_Z"            NUMERIC(10, 0),
    "ABRUF_D"            NUMERIC(10, 0),
    "ABRUF_K"            NUMERIC(10, 0),
    "ABRUF_DATUM"        DATE,
    "ERH_Z"              NUMERIC(10, 0),
    "ERH_D"              NUMERIC(10, 0),
    "ERH_K"              NUMERIC(10, 0),
    "ERH_DATUM"          DATE,
    "REF_REFNR"          NUMERIC(2, 0),
    "SAPABRUFAUFTRAGSNR" NUMERIC(8, 0),
    "SAPFAKTURANR"       NUMERIC(10, 0),
    "FIPO"               VARCHAR(15),
    "BUCHUNGSKREIS"      VARCHAR(4),
    "SACHKONTO"          VARCHAR(6),
    "FIPO_K"             VARCHAR(15),
    "BUCHUNGSKREIS_K"    VARCHAR(4),
    "SACHKONTO_K"        VARCHAR(6),
    "NOTIZEN"            VARCHAR(4000),
    "ANLAGEDATUM"        DATE        DEFAULT CURRENT_DATE,
    "ANLAGEVON"          VARCHAR(30) DEFAULT USER,
    "AENDERUNGSDATUM"    DATE,
    "AENDERUNGVON"       VARCHAR(30)
);

COMMENT ON COLUMN "FP_ABRUFE"."ID" IS 'Primärschlüssel eines Abrufs';
COMMENT ON COLUMN "FP_ABRUFE"."PRO_PROJNR" IS 'Dieser Abruf bezieht sich auf dieses Projekt';
COMMENT ON COLUMN "FP_ABRUFE"."BWI_ID" IS 'Dieser Abruf bezieht sich auf eine Bewilligung';
COMMENT ON COLUMN "FP_ABRUFE"."VNABR" IS 'Ist der Abruf mit dem Verwendungsnachweis erfolgt (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_ABRUFE"."ABRUF_Z" IS 'Höhe der abgerufenen Zuwendung';
COMMENT ON COLUMN "FP_ABRUFE"."ABRUF_D" IS 'Höhe des abgerufenen Darlehens';
COMMENT ON COLUMN "FP_ABRUFE"."ABRUF_K" IS 'Höhe des abgerufenen Konnexitätsbetrags (Kostenerstattung)';
COMMENT ON COLUMN "FP_ABRUFE"."ABRUF_DATUM" IS 'Datum an dem der Abruf erfolgte';
COMMENT ON COLUMN "FP_ABRUFE"."ERH_Z" IS 'Höhe der erhaltenen Zuwendung';
COMMENT ON COLUMN "FP_ABRUFE"."ERH_D" IS 'Höhe des erhaltenen Darlehens';
COMMENT ON COLUMN "FP_ABRUFE"."ERH_K" IS 'Höhe der erhaltenen Konnexität (Kostenerstattung)';
COMMENT ON COLUMN "FP_ABRUFE"."ERH_DATUM" IS 'Datum des Geldeingangs';
COMMENT ON COLUMN "FP_ABRUFE"."REF_REFNR" IS 'Nummer des betroffenen Referats für diesen Abruf';
COMMENT ON COLUMN "FP_ABRUFE"."SAPABRUFAUFTRAGSNR" IS 'SAP Auftragsnummer für diesen Abruf';
COMMENT ON COLUMN "FP_ABRUFE"."SAPFAKTURANR" IS 'SAP Fakturanummer für diesen Abruf';
COMMENT ON COLUMN "FP_ABRUFE"."FIPO" IS 'Haushaltsstelle (FIPO) für Zuwendungen für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."BUCHUNGSKREIS" IS 'SAP Buchungskreis für Zuwendungen  für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."SACHKONTO" IS 'SAP Sachkonto für Zuwendungen  für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."FIPO_K" IS 'Haushaltsstelle (FIPO) für Kostenerstattungen  für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."BUCHUNGSKREIS_K" IS 'SAP Buchungskreis für Kostenerstattungen  für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."SACHKONTO_K" IS 'SAP Sachkonto für Kostenerstattungen  für diesen Abruf (default aus Projekt)';
COMMENT ON COLUMN "FP_ABRUFE"."NOTIZEN" IS 'Notizen zum Abruf als Freitext';
COMMENT ON COLUMN "FP_ABRUFE"."ANLAGEDATUM" IS 'Dieser Satz wurde angelegt am';
COMMENT ON COLUMN "FP_ABRUFE"."ANLAGEVON" IS 'Dieser Satz wurde angelegt von diesem Benutzer';
COMMENT ON COLUMN "FP_ABRUFE"."AENDERUNGSDATUM" IS 'Dieser Satz wurde zuletzt geändert am';
COMMENT ON COLUMN "FP_ABRUFE"."AENDERUNGVON" IS 'Dieser Satz wurde zuletzt geändert von diesem Benutzer';
COMMENT ON TABLE "FP_ABRUFE" IS 'Enthält die Abrufe zu den Bewilligungen eines Projekts (dbase:abrufe)';
--------------------------------------------------------
--  DDL for Table FP_ANTRAEGE
--------------------------------------------------------

CREATE TABLE "FP_ANTRAEGE"
(
    "ID"              NUMERIC(38, 0),
    "PRO_PROJNR"      VARCHAR(7),
    "ANTRAGSDATUM"    DATE,
    "ANTRAGSTYP"      VARCHAR(1),
    "GESKOSTEN"       NUMERIC(10, 0),
    "ZWFKOSTEN"       NUMERIC(10, 0),
    "VORZBEG"         NUMERIC(1, 0) DEFAULT 0,
    "VBDATUM"         DATE,
    "UNBEDDAT"        DATE,
    "UNBEDJA"         DATE,
    "UNBEDBIS"        DATE,
    "A_SU_Z"          NUMERIC(10, 0),
    "A_SU_D"          NUMERIC(10, 0),
    "A_SU_K"          NUMERIC(10, 0),
    "B_VOR_SU_Z"      NUMERIC(10, 0),
    "B_VOR_SU_D"      NUMERIC(10, 0),
    "B_VOR_SU_K"      NUMERIC(10, 0),
    "NOTIZEN"         VARCHAR(4000),
    "ANLAGEDATUM"     DATE DEFAULT CURRENT_DATE,
    "ANLAGEVON"       VARCHAR(30) DEFAULT USER,
    "AENDERUNGSDATUM" DATE,
    "AENDERUNGVON"    VARCHAR(30)
);

COMMENT ON COLUMN "FP_ANTRAEGE"."ID" IS 'Primärschlüssel eines Fördermittelantrags';
COMMENT ON COLUMN "FP_ANTRAEGE"."PRO_PROJNR" IS 'Dieser Antrag bezieht sich auf dieses Projekt';
COMMENT ON COLUMN "FP_ANTRAEGE"."ANTRAGSDATUM" IS 'Datum des Förderantrags';
COMMENT ON COLUMN "FP_ANTRAEGE"."ANTRAGSTYP" IS 'Typ des Förderantrags  (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_ANTRAEGE"."GESKOSTEN" IS 'Beantragte Gesamtkosten des Projekts bei diesem Antrag (besonders beim Erstantrag)';
COMMENT ON COLUMN "FP_ANTRAEGE"."ZWFKOSTEN" IS 'Beantragte zuwendungsfähige Gesamtkosten bei diesem Antrag';
COMMENT ON COLUMN "FP_ANTRAEGE"."VORZBEG" IS 'Wurde Antrag auf vorzeitigen Baubeginn gestellt (0/1)';
COMMENT ON COLUMN "FP_ANTRAEGE"."VBDATUM" IS 'Datum eines vorzeitigen Baubeginns';
COMMENT ON COLUMN "FP_ANTRAEGE"."UNBEDDAT" IS 'Datum des Antrags auf Unbedenklichkeit';
COMMENT ON COLUMN "FP_ANTRAEGE"."UNBEDJA" IS 'Datum der erteilten Unbedenklichkeitsbescheinigung';
COMMENT ON COLUMN "FP_ANTRAEGE"."UNBEDBIS" IS 'Die erteilte Unbedenklichkeitsbescheinigung ist gültig bis zu diesem Datum';
COMMENT ON COLUMN "FP_ANTRAEGE"."A_SU_Z" IS 'Höhe des beantragten Zuschusses (nur Anteil für ein Jahr)';
COMMENT ON COLUMN "FP_ANTRAEGE"."A_SU_D" IS 'Höhe des beantragten Darlehens (nur Anteil für ein Jahr)';
COMMENT ON COLUMN "FP_ANTRAEGE"."A_SU_K" IS 'Höhe der beantragten Kostenerstattung (Konnexität, nur Anteil für ein Jahr)';
COMMENT ON COLUMN "FP_ANTRAEGE"."B_VOR_SU_Z" IS 'voraussichtlich bewilligte Gesamtzuwendung';
COMMENT ON COLUMN "FP_ANTRAEGE"."B_VOR_SU_D" IS 'voraussichtlich bewilligtes Gesamtdarlehen';
COMMENT ON COLUMN "FP_ANTRAEGE"."B_VOR_SU_K" IS ' voraussichtlich bewilligte Kostenerstattung';
COMMENT ON COLUMN "FP_ANTRAEGE"."NOTIZEN" IS 'Notizen zu einem Antrag als Freitext';
COMMENT ON COLUMN "FP_ANTRAEGE"."ANLAGEDATUM" IS 'Dieser Satz wurde angelegt am';
COMMENT ON COLUMN "FP_ANTRAEGE"."ANLAGEVON" IS 'Dieser Satz wurde angelegt von diesem Benutzer';
COMMENT ON COLUMN "FP_ANTRAEGE"."AENDERUNGSDATUM" IS 'Dieser Satz wurde zuletzt geändert am';
COMMENT ON COLUMN "FP_ANTRAEGE"."AENDERUNGVON" IS 'Dieser Satz wurde zuletzt geändert von diesem Benutzer';
COMMENT ON TABLE "FP_ANTRAEGE" IS 'Enthält alle Förderanträge zu Projekten ((dbase:erstantr)';
--------------------------------------------------------
--  DDL for Table FP_ARCHIV
--------------------------------------------------------

CREATE TABLE "FP_ARCHIV"
(
    "ID"                 NUMERIC(38, 0),
    "PRO_PROJNR"         VARCHAR(7),
    "SPEICHERDATUM"      DATE,
    "SPEICHERAKT"        NUMERIC(1, 0) DEFAULT 0,
    "SPEICHERRECHNUNGEN" NUMERIC(1, 0) DEFAULT 0,
    "MIKRODATPLAN"       DATE,
    "MIKRODAT"           DATE,
    "NOTIZEN"            VARCHAR(4000)
);

COMMENT ON COLUMN "FP_ARCHIV"."ID" IS 'Eindeutiger Schlüssel in der Tabelle';
COMMENT ON COLUMN "FP_ARCHIV"."PRO_PROJNR" IS 'Die Ablage kann eine Projektablage sein';
COMMENT ON COLUMN "FP_ARCHIV"."SPEICHERDATUM" IS 'Datum der Ablage im Speicher';
COMMENT ON COLUMN "FP_ARCHIV"."SPEICHERAKT" IS 'Befindet sich auch der Akt zu einem Projekt im Speicher (0,1)';
COMMENT ON COLUMN "FP_ARCHIV"."SPEICHERRECHNUNGEN" IS 'Befinden sich auch die Rechnungen zu einem Projekt im Speicher (0,1)';
COMMENT ON COLUMN "FP_ARCHIV"."MIKRODATPLAN" IS 'Bestelldatum der Microfiche (neu: Feb 2013)';
COMMENT ON COLUMN "FP_ARCHIV"."MIKRODAT" IS 'Datum an dem die Microfiche erhalten und eingelagert wurden (neu: Feb 2013)';
COMMENT ON COLUMN "FP_ARCHIV"."NOTIZEN" IS 'Notizen als Freitext zu einer Ablage';
COMMENT ON TABLE "FP_ARCHIV" IS 'Beschreibt welche allgemeinen Dokumente und Projektakten und Mikrofiche im Archiv liegen. Diese Ablagen können projektbezogen sein, aber auch Dokumente allgemeiner Natur. Das Archiv selbst befindet sich im Speicher des Rathauses (dbase: speicher und mik)';
--------------------------------------------------------
--  DDL for Table FP_BAULEITUNGEN
--------------------------------------------------------

CREATE TABLE "FP_BAULEITUNGEN"
(
    "BAULEITUNG"  VARCHAR(1),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_BAULEITUNGEN"."BAULEITUNG" IS 'Abkürzung der Bauleitung wie H,1,S';
COMMENT ON COLUMN "FP_BAULEITUNGEN"."BEZEICHNUNG" IS 'Beschreibung der Bauleitung';
COMMENT ON TABLE "FP_BAULEITUNGEN" IS 'Aufstellung aller Bauleitungen als Stammdaten';
--------------------------------------------------------
--  DDL for Table FP_BAUPROGRAMME
--------------------------------------------------------

CREATE TABLE "FP_BAUPROGRAMME"
(
    "BAUPROGRAMM" NUMERIC(2, 0),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_BAUPROGRAMME"."BAUPROGRAMM" IS 'Nummer des Bauprogramms';
COMMENT ON COLUMN "FP_BAUPROGRAMME"."BEZEICHNUNG" IS 'Beschreibung des Bauprogramms';
COMMENT ON TABLE "FP_BAUPROGRAMME" IS 'Aufstellung aller Bauprogramme';
--------------------------------------------------------
--  DDL for Table FP_BENUTZER
--------------------------------------------------------

CREATE TABLE "FP_BENUTZER"
(
    "ORAUSER"   VARCHAR(10)  DEFAULT NULL,
    "VORNAME"   VARCHAR(30),
    "NACHNAME"  VARCHAR(30),
    "EMAIL"     VARCHAR(40),
    "ABTEILUNG" VARCHAR(30),
    "FUNKTION1" NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION2" NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION3" NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION4" NUMERIC(1, 0) DEFAULT 0,
    "PASSWORT"  VARCHAR(10)  DEFAULT NULL,
    "ANREDE"    VARCHAR(10)  DEFAULT NULL,
    "TELEFON"   VARCHAR(30),
    "LASTLOGIN" DATE,
    "FUNKTION5" NUMERIC(1, 0) DEFAULT 0
);

COMMENT ON COLUMN "FP_BENUTZER"."ORAUSER" IS 'Benutzername des Anwenders zur Anmeldung';
COMMENT ON COLUMN "FP_BENUTZER"."VORNAME" IS 'Vorname des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."NACHNAME" IS 'Nachname des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."EMAIL" IS 'Email des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."ABTEILUNG" IS 'Abteilung des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."FUNKTION1" IS 'Besitzt dieser Benutzer ADMIN Rechte (0/1)';
COMMENT ON COLUMN "FP_BENUTZER"."FUNKTION2" IS 'Besitzt dieser User das Recht für die Haushaltsplanung (0/1)';
COMMENT ON COLUMN "FP_BENUTZER"."FUNKTION3" IS 'Funktion noch offen (0/1)';
COMMENT ON COLUMN "FP_BENUTZER"."FUNKTION4" IS 'Funktion noch offen (0/1)';
COMMENT ON COLUMN "FP_BENUTZER"."PASSWORT" IS 'Passwort des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."ANREDE" IS 'Anrede (Hr., Fr.) des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."TELEFON" IS 'Telefon des Anwenders';
COMMENT ON COLUMN "FP_BENUTZER"."LASTLOGIN" IS 'Datum des letzten Logins dieses Benutzers';
COMMENT ON COLUMN "FP_BENUTZER"."FUNKTION5" IS 'Muss der Benutzer sein Passwort beim nächsten Login ändern (0/1)';
COMMENT ON TABLE "FP_BENUTZER" IS 'Diese Tabelle beinhaltet die Anwender des Systems Fördermittelverwaltung';
--------------------------------------------------------
--  DDL for Table FP_BENUTZERHINWEISE
--------------------------------------------------------

CREATE TABLE "FP_BENUTZERHINWEISE"
(
    "FOR_FORMSMODUL" VARCHAR(30),
    "HINWEIS1"       VARCHAR(4000),
    "HINWEIS2"       VARCHAR(4000),
    "HINWEIS3"       VARCHAR(4000),
    "MENUETOP"       VARCHAR(40),
    "MENUEZEILE"     VARCHAR(40)
);

COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."FOR_FORMSMODUL" IS 'Name des Formsmoduls für den Bedienerhinweis';
COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."HINWEIS1" IS 'Texte für Benutzerhilfen';
COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."HINWEIS2" IS 'Texte für Benutzerhilfen';
COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."HINWEIS3" IS 'Texte für Benutzerhilfen';
COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."MENUETOP" IS 'Menütext im Topmenü (etwa Projekte)';
COMMENT ON COLUMN "FP_BENUTZERHINWEISE"."MENUEZEILE" IS 'Menütext im aufrufenden Menü (etwa Bericht Zinsen)';
COMMENT ON TABLE "FP_BENUTZERHINWEISE" IS 'Enthält Benutzerhinweise im Kontext eines Forms Moduls und dient damit als online Benutzerhilfe';
--------------------------------------------------------
--  DDL for Table FP_BEWILLIGUNGEN
--------------------------------------------------------

CREATE TABLE "FP_BEWILLIGUNGEN"
(
    "ID"              NUMERIC(38, 0),
    "PRO_PROJNR"      VARCHAR(7),
    "ANT_ID"          NUMERIC(38, 0),
    "BDATUM"          DATE,
    "AFSATZ"          NUMERIC(8, 2),
    "BFSATZ"          NUMERIC(8, 2),
    "BZWFKOSTEN"      NUMERIC(10, 0),
    "BZUWENDUNG_Z"    NUMERIC(10, 0),
    "BZUWENDUNG_D"    NUMERIC(10, 0),
    "BZUWENDUNG_K"    NUMERIC(10, 0),
    "BZUWART"         VARCHAR(1),
    "BAKTENZEICHEN"   VARCHAR(20),
    "GESZUWENDUNGEN"  NUMERIC(10, 0),
    "GESKONNEX"       NUMERIC(10, 0),
    "KRW"             VARCHAR(1),
    "NOTIZEN"         VARCHAR(4000),
    "ANLAGEDATUM"     DATE        DEFAULT CURRENT_DATE,
    "ANLAGEVON"       VARCHAR(30) DEFAULT USER,
    "AENDERUNGSDATUM" DATE,
    "AENDERUNGVON"    VARCHAR(30)
);

COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."ID" IS 'Primärschlüssel einer Bewilligung';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."PRO_PROJNR" IS 'Diese Bewilligung bezieht sich auf dieses Projekt';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."ANT_ID" IS 'Diese Bewilligung bezieht sich auf einen Antrag';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BDATUM" IS 'Datum der Bewilligung';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."AFSATZ" IS 'Beantrager Fördersatz';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BFSATZ" IS 'Bewilligter Fördersatz';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BZWFKOSTEN" IS 'bewilligte zuwendungsfähige Kosten Gesamtbetrag';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BZUWENDUNG_Z" IS 'Bewilligte Zuwendung auf Jahresbasis als Zuschuss';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BZUWENDUNG_D" IS 'Bewilligte Zuwendung auf Jahresbasis als Darlehen';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BZUWENDUNG_K" IS 'Bewilligte Zuwendung auf Jahresbasis als Kostenerstattung';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BZUWART" IS 'Bewilligte Zuwendungsart (Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."BAKTENZEICHEN" IS 'Aktenzeichen des Bewilligers';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."GESZUWENDUNGEN" IS 'Höhe der zu erwartenden Gesamtzuwendungen für das Förderprojekt zum Zeitpunkt dieser Bewilligung';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."GESKONNEX" IS 'Höhe der zu erwartenden Gesamtkonnexitätszahlungen für das Förderprojekt zum Zeitpunkt dieser Bewilligung';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."KRW" IS 'Typ des Kostenrichtwerts (Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."NOTIZEN" IS 'Notizen zu einer Bewilligung als Freitext';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."ANLAGEDATUM" IS 'Dieser Satz wurde angelegt am';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."ANLAGEVON" IS 'Dieser Satz wurde angelegt von diesem Benutzer';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."AENDERUNGSDATUM" IS 'Dieser Satz wurde zuletzt geändert am';
COMMENT ON COLUMN "FP_BEWILLIGUNGEN"."AENDERUNGVON" IS 'Dieser Satz wurde zuletzt geändert von diesem Benutzer';
COMMENT ON TABLE "FP_BEWILLIGUNGEN" IS 'Enthält alle Bewilligungen zu Förderanträgen. (dbase:bewill)';
--------------------------------------------------------
--  DDL for Table FP_DOMAINS
--------------------------------------------------------

CREATE TABLE "FP_DOMAINS"
(
    "TABELLE"      VARCHAR(30),
    "SPALTE"       VARCHAR(30),
    "WERT"         VARCHAR(3),
    "BESCHREIBUNG" VARCHAR(50)
);

COMMENT ON COLUMN "FP_DOMAINS"."TABELLE" IS 'Tabelle auf welche sich die Domäne bezieht';
COMMENT ON COLUMN "FP_DOMAINS"."SPALTE" IS 'Spalte auf welche sich die Domäne bezieht';
COMMENT ON COLUMN "FP_DOMAINS"."WERT" IS 'Wert der Domäne';
COMMENT ON COLUMN "FP_DOMAINS"."BESCHREIBUNG" IS 'Beschreibung der Domäne';
COMMENT ON TABLE "FP_DOMAINS" IS 'Aufstellung aller Domains und ihrer Werte';
--------------------------------------------------------
--  DDL for Table FP_EUINFORMATIONEN
--------------------------------------------------------

CREATE TABLE "FP_EUINFORMATIONEN"
(
    "ID"           NUMERIC(38, 0),
    "JAHR"         NUMERIC(4, 0),
    "PUB_KURZFORM" VARCHAR(1),
    "HEFTA"        VARCHAR(1),
    "NUMMER"       NUMERIC(3, 0),
    "WICHTIG"      NUMERIC(1, 0),
    "SEITENNR"     VARCHAR(3),
    "STICHWORT"    VARCHAR(50),
    "RAWI"         NUMERIC(1, 0),
    "SCHULREF"     NUMERIC(1, 0),
    "SOZREF_R_5"   NUMERIC(1, 0),
    "RGU_11"       NUMERIC(1, 0),
    "RGU_CS"       NUMERIC(1, 0),
    "KRH"          NUMERIC(1, 0),
    "AFA"          NUMERIC(1, 0),
    "SWM"          NUMERIC(1, 0),
    "KULTURREF"    NUMERIC(1, 0),
    "BAUREF"       NUMERIC(1, 0),
    "PLANREF"      NUMERIC(1, 0),
    "DIREKTORIUM"  NUMERIC(1, 0),
    "POR"          NUMERIC(1, 0),
    "KVR"          NUMERIC(1, 0),
    "KOMMREF"      NUMERIC(1, 0),
    "SEW"          NUMERIC(1, 0),
    "STK"          NUMERIC(1, 0),
    "INHALT"       VARCHAR(200),
    "INFODAT"      DATE
);

COMMENT ON COLUMN "FP_EUINFORMATIONEN"."ID" IS 'Primary Key';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."JAHR" IS 'Jahr der Erscheinung';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."PUB_KURZFORM" IS 'Publikation';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."HEFTA" IS 'Zusatzbezeichnung zur Publikation';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."NUMMER" IS 'Nummer des Heftes';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."WICHTIG" IS 'Information weitergeben (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."SEITENNR" IS 'Seitennummer inerhalb des Heftes';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."STICHWORT" IS 'Stichwort zu dieser Infomation';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."RAWI" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."SCHULREF" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."SOZREF_R_5" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."RGU_11" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."RGU_CS" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."KRH" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."AFA" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."SWM" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."KULTURREF" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."BAUREF" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."PLANREF" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."DIREKTORIUM" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."POR" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."KVR" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."KOMMREF" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."SEW" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."STK" IS 'Soll dieses Referat informiert werden (0/1)';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."INHALT" IS 'Kurzbeschreibung der Information';
COMMENT ON COLUMN "FP_EUINFORMATIONEN"."INFODAT" IS 'Datum der Information';
COMMENT ON TABLE "FP_EUINFORMATIONEN" IS 'Enthält wesentliche Informationen diverses EU-naher Publikationen und ist Basis einer eigenständigen Applikation (dbase:eu)';
--------------------------------------------------------
--  DDL for Table FP_FOERDERBEREICHE
--------------------------------------------------------

CREATE TABLE "FP_FOERDERBEREICHE"
(
    "FB"          NUMERIC(2, 0),
    "BEZEICHNUNG" VARCHAR(200),
    "FUNKTION1"   NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION2"   NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION3"   NUMERIC(1, 0) DEFAULT 0,
    "FUNKTION4"   NUMERIC(1, 0) DEFAULT 0
);

COMMENT ON COLUMN "FP_FOERDERBEREICHE"."FB" IS 'Abkürzung des Förderbereichs';
COMMENT ON COLUMN "FP_FOERDERBEREICHE"."BEZEICHNUNG" IS 'Bezeichnung des Förderbereichs';
COMMENT ON COLUMN "FP_FOERDERBEREICHE"."FUNKTION1" IS 'Soll der Förderbereich in der FAG-Statistik verwendet werden (0/1)';
COMMENT ON COLUMN "FP_FOERDERBEREICHE"."FUNKTION2" IS 'Soll der Förderbereich in der Jahresstatistik verwendet werden (0/1)';
COMMENT ON COLUMN "FP_FOERDERBEREICHE"."FUNKTION3" IS 'Ist der Förderbereich vom Typ Kindergarten (0/1). Wird verwendet in Jahresstatistik.';
COMMENT ON COLUMN "FP_FOERDERBEREICHE"."FUNKTION4" IS 'Der Förderbereich kann als nicht relevant gekennzeichnet werden (0/1). Damit Aussteuerung in manchen Berichten';
COMMENT ON TABLE "FP_FOERDERBEREICHE" IS 'Austellung aller Förderbereiche (dbase:fb)';
--------------------------------------------------------
--  DDL for Table FP_GEPLANTEMASSNAHMEN
--------------------------------------------------------

CREATE TABLE "FP_GEPLANTEMASSNAHMEN"
(
    "ID"              NUMERIC(38, 0),
    "KUR_KURZBEZ"     VARCHAR(8),
    "BEZ_STADTBEZIRK" NUMERIC(2, 0),
    "STRASSE"         VARCHAR(50),
    "PROJEKT"         VARCHAR(70),
    "BAUBEGINN"       NUMERIC(4, 0),
    "ANGELEGT"        NUMERIC(4, 0),
    "PLANNR"          VARCHAR(10)
);

COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."ID" IS 'Primary Key';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."KUR_KURZBEZ" IS 'Kurzform der Projektkategorie für das Vorhaben';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."BEZ_STADTBEZIRK" IS 'Stadtbezirk der geplanten Maßnahme';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."STRASSE" IS 'Strasse des Vorhabens';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."PROJEKT" IS 'Projektbezeichnung';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."BAUBEGINN" IS 'Geplanter Baubeginn (Jahr)';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."ANGELEGT" IS 'Vorgang angelegt (Jahr)';
COMMENT ON COLUMN "FP_GEPLANTEMASSNAHMEN"."PLANNR" IS 'Nummer des Bebauungsplans';
COMMENT ON TABLE "FP_GEPLANTEMASSNAHMEN" IS 'Enthält Infomationen über zukünftige, geplanten Maßnahmen die mal in Projekte münden sollen (dbase:geplmasn)';
--------------------------------------------------------
--  DDL for Table FP_HAUPTABSCHNITTE
--------------------------------------------------------

CREATE TABLE "FP_HAUPTABSCHNITTE"
(
    "HA"          VARCHAR(2),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_HAUPTABSCHNITTE"."HA" IS 'Hauptabschnitt';
COMMENT ON COLUMN "FP_HAUPTABSCHNITTE"."BEZEICHNUNG" IS 'Bezeichnung des Hauptabschnitts';
COMMENT ON TABLE "FP_HAUPTABSCHNITTE" IS 'Hauptabschnitte zur Klassifizierung der Unterabschnitte (dbase: keine Datei vorhanden)';
--------------------------------------------------------
--  DDL for Table FP_HHJAHRE
--------------------------------------------------------

CREATE TABLE "FP_HHJAHRE"
(
    "HHJAHR"        NUMERIC(4, 0),
    "HHBEZEICHNUNG" VARCHAR(200),
    "NOTIZEN"       TEXT
);

COMMENT ON COLUMN "FP_HHJAHRE"."HHJAHR" IS 'Haushaltsjahr';
COMMENT ON COLUMN "FP_HHJAHRE"."HHBEZEICHNUNG" IS 'Bezeichnung des Haushaltsjahres';
COMMENT ON COLUMN "FP_HHJAHRE"."NOTIZEN" IS 'Notizen und Protokoll zu einem Haushaltsjahr';
COMMENT ON TABLE "FP_HHJAHRE" IS 'Aufstellung aller Haushaltsjahre';
--------------------------------------------------------
--  DDL for Table FP_HHPLAN
--------------------------------------------------------

CREATE TABLE "FP_HHPLAN"
(
    "HHJ_JAHR"          NUMERIC(4, 0),
    "FIPO"              VARCHAR(15) DEFAULT '000000000000000',
    "PRO_PROJNR"        VARCHAR(7),
    "P_PSTRASSE"        VARCHAR(100),
    "P_PNAME"           VARCHAR(100),
    "P_FOB_FB"          NUMERIC(2, 0),
    "P_BEZ_STADTBEZIRK" NUMERIC(2, 0),
    "P_LNA_KURZBEZ"     VARCHAR(3),
    "P_VNDAT"           DATE,
    "MITTELEINPLANUNG"  NUMERIC(12, 0),
    "BEWILLIGUNG_VOJ"   NUMERIC(12, 0),
    "BEWILLIGUNG_HHJ"   NUMERIC(12, 0),
    "ERHALTEN_VOJ"      NUMERIC(12, 0),
    "ERHALTEN_HHJ"      NUMERIC(12, 0),
    "BAUSTANDAKTUELL"   NUMERIC(12, 2),
    "AN"                NUMERIC(12, 0),
    "N1"                NUMERIC(12, 0),
    "N2"                NUMERIC(12, 0),
    "NOTIZEN"           TEXT,
    "ANLAGEDATUM"       DATE        DEFAULT CURRENT_DATE,
    "ANLAGEVON"         VARCHAR(30) DEFAULT USER,
    "AENDERUNGSDATUM"   DATE,
    "AENDERUNGVON"      VARCHAR(30)
);

COMMENT ON COLUMN "FP_HHPLAN"."HHJ_JAHR" IS 'Planung für ein Haushaltsjahr';
COMMENT ON COLUMN "FP_HHPLAN"."FIPO" IS 'Planung für eine FIPO (Haushaltsstelle) des Projekts (Zuwendungen oder Konnexität)';
COMMENT ON COLUMN "FP_HHPLAN"."PRO_PROJNR" IS 'Planung für ein Projekt innerhalb eines Haushaltsjahres';
COMMENT ON COLUMN "FP_HHPLAN"."P_PSTRASSE" IS 'Strasse aus Projekt übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."P_PNAME" IS 'Projektname aus Projekt übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."P_FOB_FB" IS 'Förderbereich aus Projekt übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."P_BEZ_STADTBEZIRK" IS 'Stadtbezirk aus Projekt übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."P_LNA_KURZBEZ" IS 'Bezirksliste übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."P_VNDAT" IS 'VN Datum aus Projekt übernommen';
COMMENT ON COLUMN "FP_HHPLAN"."MITTELEINPLANUNG" IS 'Mitteleinplanung zum Zeitpunkt der Erstellung der Planung.';
COMMENT ON COLUMN "FP_HHPLAN"."BEWILLIGUNG_VOJ" IS 'Bewilligte Zuwendungen in den Vorjahren.';
COMMENT ON COLUMN "FP_HHPLAN"."BEWILLIGUNG_HHJ" IS 'Bewilligte Zuwendungen im Haushaltsjahr.';
COMMENT ON COLUMN "FP_HHPLAN"."ERHALTEN_VOJ" IS 'Erhaltene Zuwendungen in den Vorjahren.';
COMMENT ON COLUMN "FP_HHPLAN"."ERHALTEN_HHJ" IS 'Erhaltene Zuwendungen im Haushaltsjahr.';
COMMENT ON COLUMN "FP_HHPLAN"."BAUSTANDAKTUELL" IS 'Baustand zum Zeitpunkt der Erstellung der Planung.';
COMMENT ON COLUMN "FP_HHPLAN"."AN" IS 'Ansatz im Nov des Vorjahres. Wird manuell erfasst.';
COMMENT ON COLUMN "FP_HHPLAN"."N1" IS '1. Nachtrag im April des Haushaltsjahres. Wird manuell erfasst.';
COMMENT ON COLUMN "FP_HHPLAN"."N2" IS '2. Nachtrag im August des Haushaltsjahres. Wird manuell erfasst.';
COMMENT ON COLUMN "FP_HHPLAN"."NOTIZEN" IS 'Notizen und Anmerkungen bezüglich einer Position im Haushaltsplan';
COMMENT ON COLUMN "FP_HHPLAN"."ANLAGEDATUM" IS 'Dieser Satz wurde angelegt am';
COMMENT ON COLUMN "FP_HHPLAN"."ANLAGEVON" IS 'Dieser Satz wurde angelegt von diesem Benutzer';
COMMENT ON COLUMN "FP_HHPLAN"."AENDERUNGSDATUM" IS 'Dieser Satz wurde zuletzt geändert am';
COMMENT ON COLUMN "FP_HHPLAN"."AENDERUNGVON" IS 'Dieser Satz wurde zuletzt geändert von diesem Benutzer';
COMMENT ON TABLE "FP_HHPLAN" IS 'Enthält Einnahmen (Zuwendungen und Kostenerstattungen) je Projekt und Jahr und FIPO. Ist Basis für die jährliche Aufstellung der Haushaltsplanung (dbase:haush)';
--------------------------------------------------------
--  DDL for Table FP_JAHRESSTATISTIK
--------------------------------------------------------

CREATE TABLE "FP_JAHRESSTATISTIK"
(
    "JAHR"                 NUMERIC(4, 0),
    "JAHRESSTATISTIK1_AM"  DATE,
    "JAHRESSTATISTIK1_VON" VARCHAR(30),
    "JAHRESSTATISTIK2_AM"  DATE,
    "JAHRESSTATISTIK2_VON" VARCHAR(30),
    "JAHRESSTATISTIK3_AM"  DATE,
    "JAHRESSTATISTIK3_VON" VARCHAR(30),
    "NOTIZEN"              TEXT
);

COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHR" IS 'Jahr der Statistik';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK1_AM" IS 'Die Jahresstatistik I wurde zuletzt zu diesem Zeitpunkt erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK1_VON" IS 'Die Jahresstatistik I wurde zuletzt von diesem Benutzer erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK2_AM" IS 'Die Jahresstatistik II wurde zuletzt zu diesem Zeitpunkt erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK2_VON" IS 'Die Jahresstatistik II wurde zuletzt von diesem Benutzer erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK3_AM" IS 'Die Jahresstatistik III wurde zuletzt zu diesem Zeitpunkt erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."JAHRESSTATISTIK3_VON" IS 'Die Jahresstatistik III wurde zuletzt von diesem Benutzer erstellt';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK"."NOTIZEN" IS 'Allgemeine Notizen';
COMMENT ON TABLE "FP_JAHRESSTATISTIK" IS 'Diese Tabelle protokolliert die Erstellung der drei Jahresstatistiken';
--------------------------------------------------------
--  DDL for Table FP_JAHRESSTATISTIK1
--------------------------------------------------------

CREATE TABLE "FP_JAHRESSTATISTIK1"
(
    "ID"               NUMERIC(38, 0),
    "JSS_JAHR"         NUMERIC(4, 0),
    "FOERDERBEREICH"   VARCHAR(60),
    "FB"               NUMERIC(2, 0),
    "GRUPPE"           NUMERIC(1, 0),
    "GESAMTKOSTEN"     NUMERIC(12, 2),
    "ZUWENDUNGSFAEHIG" NUMERIC(12, 2),
    "B_ZUSCHUSS"       NUMERIC(12, 2),
    "B_DARLEHEN"       NUMERIC(12, 2),
    "B_KONNEXITAET"    NUMERIC(12, 2),
    "E_ZUSCHUSS"       NUMERIC(12, 2),
    "E_DARLEHEN"       NUMERIC(12, 2),
    "E_KONNEXITAET"    NUMERIC(12, 2)
);

COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."ID" IS 'ID als Sortierungskriterium';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."JSS_JAHR" IS 'Jahr der Statistik';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."FOERDERBEREICH" IS 'Beschreibung des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."FB" IS 'Nummer des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."GRUPPE" IS 'Gruppenkennzeichen (1) FAG ohne KIGA  (2) FAG KIGA  (3) alle anderen FB  für Zwischensummen';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."GESAMTKOSTEN" IS 'Gesamtkosten im Erstantrag';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."ZUWENDUNGSFAEHIG" IS 'Zuwendungsfähige Kosten im Erstantrag';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."B_ZUSCHUSS" IS 'Bewilligter Zuschuss  im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."B_DARLEHEN" IS 'Bewilligtes Darlehen  im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."B_KONNEXITAET" IS 'Bewilligte Konnexität im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."E_ZUSCHUSS" IS 'Erhaltener Zuschuss  im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."E_DARLEHEN" IS 'Erhaltenes Darlehen  im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK1"."E_KONNEXITAET" IS 'Erhaltene Konnexität im Jahr';
COMMENT ON TABLE "FP_JAHRESSTATISTIK1" IS 'Diese Tabelle enthält die generierte Jahresstatistik für Seite I';
--------------------------------------------------------
--  DDL for Table FP_JAHRESSTATISTIK2
--------------------------------------------------------

CREATE TABLE "FP_JAHRESSTATISTIK2"
(
    "ID"                   NUMERIC(38, 0),
    "JSS_JAHR"             NUMERIC(4, 0),
    "FOERDERBEREICH"       VARCHAR(60),
    "FB"                   NUMERIC(2, 0),
    "GRUPPE"               NUMERIC(1, 0),
    "ANZAHL_ABRUFE"        NUMERIC(10, 0),
    "ANZAHL_VN"            NUMERIC(10, 0),
    "ANZAHL_BEWILLIGUNGEN" NUMERIC(10, 0),
    "VNGESKOSTEN"          NUMERIC(12, 2),
    "ANZAHL_ERST"          NUMERIC(10, 0),
    "A_SU_Z_ERST"          NUMERIC(12, 2),
    "A_SU_K_ERST"          NUMERIC(12, 2),
    "ANZAHL_FOLGE"         NUMERIC(10, 0),
    "A_SU_Z_FOLGE"         NUMERIC(12, 2),
    "A_SU_K_FOLGE"         NUMERIC(12, 2),
    "A_VOR_SU_Z_GESAMT"    NUMERIC(12, 2),
    "A_VOR_SU_K_GESAMT"    NUMERIC(12, 2),
    "ANZAHL_UNBED"         NUMERIC(10, 0)
);

COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ID" IS 'ID als Sortierkriterium';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."JSS_JAHR" IS 'Jahr der Statistik';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."FOERDERBEREICH" IS 'Beschreibung des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."FB" IS 'Nummer des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."GRUPPE" IS 'Gruppenkennzeichen für Zwischensummen';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_ABRUFE" IS 'Anzahl der Abrufe eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_VN" IS 'Anzahl der Verwendungsnachweise eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_BEWILLIGUNGEN" IS 'Anzahl der Bewilligungen eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."VNGESKOSTEN" IS 'Endkosten im Verwendungsnachweis';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_ERST" IS 'Anzahl der Erstanträge eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_SU_Z_ERST" IS 'Zuwendungen bei Erstanträgen in einem Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_SU_K_ERST" IS 'Konnexität bei Erstanträgen in einem Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_FOLGE" IS 'Anzahl der Folgeanträge eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_SU_Z_FOLGE" IS 'Zuwendungen bei Folgeanträgen in einem Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_SU_K_FOLGE" IS 'Konnexität bei Folgeanträgen in einem Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_VOR_SU_Z_GESAMT" IS 'Voraussichtliche Gesamtzuwendung über alle Anträge eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."A_VOR_SU_K_GESAMT" IS 'Voraussichtliche Gesamtkonnexität über alle Anträge eines Jahres';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK2"."ANZAHL_UNBED" IS 'Anzahl der Unbedenklichkeitsanträge eines Jahres';
COMMENT ON TABLE "FP_JAHRESSTATISTIK2" IS 'Diese Tabelle enthält die generierte Jahresstatistik für Seite II';
--------------------------------------------------------
--  DDL for Table FP_JAHRESSTATISTIK3
--------------------------------------------------------

CREATE TABLE "FP_JAHRESSTATISTIK3"
(
    "ID"                 NUMERIC(38, 0),
    "JSS_JAHR"           NUMERIC(4, 0),
    "FOERDERBEREICH"     VARCHAR(60),
    "FB"                 NUMERIC(2, 0),
    "GRUPPE"             NUMERIC(1, 0),
    "BZUWENDUNG_Z_PLUS"  NUMERIC(12, 2),
    "BZUWENDUNG_Z_MINUS" NUMERIC(12, 2),
    "BZUWENDUNG_D_PLUS"  NUMERIC(12, 2),
    "BZUWENDUNG_D_MINUS" NUMERIC(12, 2),
    "BZUWENDUNG_K_PLUS"  NUMERIC(12, 2),
    "BZUWENDUNG_K_MINUS" NUMERIC(12, 2),
    "ERH_Z"              NUMERIC(12, 2),
    "ERH_D"              NUMERIC(12, 2),
    "ERH_K"              NUMERIC(12, 2)
);

COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."ID" IS 'ID als Sortierkriterium';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."JSS_JAHR" IS 'Jahr der Statistik';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."FOERDERBEREICH" IS 'Beschreibung des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."FB" IS 'Nummer des Förderbereichs';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."GRUPPE" IS 'Gruppenkennzeichen für Zwischensummen';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_Z_PLUS" IS 'Bewilligter Zuschuss im Jahr (positiver Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_Z_MINUS" IS 'Bewilligter Zuschuss im Jahr (negativer Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_D_PLUS" IS 'Bewilligtes Darlehen im Jahr (positiver Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_D_MINUS" IS 'Bewilligtes Darlehen im Jahr (negativer Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_K_PLUS" IS 'Bewilligte Konnexität im Jahr (positiver Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."BZUWENDUNG_K_MINUS" IS 'Bewilligte Konnexität im Jahr (negativer Wert)';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."ERH_Z" IS 'Erhaltener Zuschuss im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."ERH_D" IS 'Erhaltenes Darlehen im Jahr';
COMMENT ON COLUMN "FP_JAHRESSTATISTIK3"."ERH_K" IS 'Erhaltene Konnexität im Jahr';
COMMENT ON TABLE "FP_JAHRESSTATISTIK3" IS 'Diese Tabelle enthält die generierte Jahresstatistik für Seite II';
--------------------------------------------------------
--  DDL for Table FP_KRANKENHAEUSER
--------------------------------------------------------

CREATE TABLE "FP_KRANKENHAEUSER"
(
    "KRHNAME"     VARCHAR(1),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_KRANKENHAEUSER"."KRHNAME" IS 'Abkürzung des Krankenhauses wie B, R';
COMMENT ON COLUMN "FP_KRANKENHAEUSER"."BEZEICHNUNG" IS 'Beschreibung des Krankenhauses';
COMMENT ON TABLE "FP_KRANKENHAEUSER" IS 'Aufstellung aller Krankenhäuser als Stammdaten';
--------------------------------------------------------
--  DDL for Table FP_KURZBEZEICHNUNGEN
--------------------------------------------------------

CREATE TABLE "FP_KURZBEZEICHNUNGEN"
(
    "KURZBEZ"     VARCHAR(8),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_KURZBEZEICHNUNGEN"."KURZBEZ" IS 'Kurzbezeichnung für eine Projekt';
COMMENT ON COLUMN "FP_KURZBEZEICHNUNGEN"."BEZEICHNUNG" IS 'Beschreibung zu einer Kurzbezeichnung';
COMMENT ON TABLE "FP_KURZBEZEICHNUNGEN" IS 'Alle Kurzbezeichnungen zur Klassiifzierung von Projekten  (dbase:kurzbez)';
--------------------------------------------------------
--  DDL for Table FP_LISTENNAMEN
--------------------------------------------------------

CREATE TABLE "FP_LISTENNAMEN"
(
    "KURZBEZ"     VARCHAR(3),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_LISTENNAMEN"."KURZBEZ" IS 'Kurzbezeichnung für eine Liste von Stadtbezirken';
COMMENT ON COLUMN "FP_LISTENNAMEN"."BEZEICHNUNG" IS 'Beschreibung zu einem Listennamen';
COMMENT ON TABLE "FP_LISTENNAMEN" IS 'Alle Listennamen für die Zusammenstellung mehrerer Stadtbezirke';
--------------------------------------------------------
--  DDL for Table FP_PROJEKTE
--------------------------------------------------------

CREATE TABLE "FP_PROJEKTE"
(
    "PROJNR"              VARCHAR(7),
    "FOB_FB"              NUMERIC(2, 0),
    "KUR_KURZBEZ"         VARCHAR(8),
    "UAS_UA"              VARCHAR(2),
    "JAHR"                VARCHAR(2),
    "LFDNR1"              VARCHAR(1),
    "LFDNR2"              VARCHAR(2),
    "PNAME"               VARCHAR(100),
    "PSTRASSE"            VARCHAR(100),
    "KAUF"                NUMERIC(1, 0),
    "PROJART"             VARCHAR(50),
    "REFINANZIERBAR"      NUMERIC(1, 0) DEFAULT 1,
    "BLE_BAULEITUNG"      VARCHAR(1),
    "BAULEITUNGKONTAKT"   VARCHAR(30),
    "BEZ_STADTBEZIRK"     NUMERIC(2, 0),
    "KRN_KRHNAME"         VARCHAR(1),
    "KRHZWECK"            VARCHAR(1),
    "KRISOFP"             VARCHAR(1),
    "KRIPPLATZ"           NUMERIC(10, 0),
    "KIGAPLATZ"           NUMERIC(10, 0),
    "HORTPLATZ"           NUMERIC(10, 0),
    "VNDAT"               DATE,
    "VNKOSTEN"            NUMERIC(12, 2),
    "VNZWFKOSTEN"         NUMERIC(12, 2),
    "VNPRUEFDAT"          DATE,
    "VNRUECK_Z"           NUMERIC(12, 2),
    "VNNACHFOERDERUNG"    NUMERIC(12, 2),
    "VNPRUEFZWF"          NUMERIC(12, 2),
    "VNSCHLUSSZWF"        NUMERIC(12, 2),
    "VNSCHLUSSBEW"        DATE,
    "SAP"                 NUMERIC(1, 0),
    "SAPSTATAUF"          NUMERIC(1, 0),
    "SAPMATNR"            NUMERIC(8, 0),
    "SAPWERTNR"           NUMERIC(8, 0),
    "SAPJAHRWERT"         DATE,
    "SAPANLAGENNR"        NUMERIC(8, 0),
    "ZINSEN"              NUMERIC(10, 0),
    "ZINSDATUM"           DATE,
    "FIPO"                VARCHAR(15),
    "BUCHUNGSKREIS"       VARCHAR(4),
    "SACHKONTO"           VARCHAR(6),
    "FIPO_K"              VARCHAR(15),
    "BUCHUNGSKREIS_K"     VARCHAR(4),
    "SACHKONTO_K"         VARCHAR(6),
    "PSBAUBUCH"           VARCHAR(2),
    "PSBAUREF"            VARCHAR(2),
    "PSBAUNR"             VARCHAR(6),
    "NOTIZEN"             TEXT,
    "ALTDATEN"            NUMERIC(1, 0) DEFAULT 0,
    "ANLAGEDATUM"         DATE         DEFAULT CURRENT_DATE,
    "ANLAGEVON"           VARCHAR(30)  DEFAULT USER,
    "AENDERUNGSDATUM"     DATE,
    "AENDERUNGVON"        VARCHAR(30),
    "VNGESAMTZUWENDUNG"   NUMERIC(12, 2),
    "SAPINNENAUFTRAG"     VARCHAR(12),
    "BPG_BAUPROGRAMM"     NUMERIC(2, 0),
    "SGT_SIEDLUNGSGEBIET" NUMERIC(2, 0),
    "BAUENDE"             DATE,
    "BAUBEENDET"          VARCHAR(1),
    "BAUVERGABE1"         DATE,
    "BAUBEGINN"           DATE,
    "BAUMITTEILUNG"       DATE,
    "KREDITNUMMER"        NUMERIC(8, 0),
    "STADTANLEIHE"        VARCHAR(50),
    "ANLEIHENENNWERT"     NUMERIC(9, 0),
    "ANLEIHEJAHRVON"      NUMERIC(4, 0),
    "ANLEIHEJAHRBIS"      NUMERIC(4, 0)
);

COMMENT ON COLUMN "FP_PROJEKTE"."PROJNR" IS 'Primary Key Projektnummer zusammengesetzt';
COMMENT ON COLUMN "FP_PROJEKTE"."FOB_FB" IS 'Förderbereich Kurzform';
COMMENT ON COLUMN "FP_PROJEKTE"."KUR_KURZBEZ" IS 'Kurzbezeichnung zur Klassifizierung eines Projekts';
COMMENT ON COLUMN "FP_PROJEKTE"."UAS_UA" IS 'Unterabschnitt als Teil der Projektnummer (Stelle 1-2)';
COMMENT ON COLUMN "FP_PROJEKTE"."JAHR" IS 'Jahreszahl zweistellig als Teil der Projektnummer (Stelle 3-4)';
COMMENT ON COLUMN "FP_PROJEKTE"."LFDNR1" IS 'Laufende Nummer einstellig als Teil der Projektnummer (Stelle 5)';
COMMENT ON COLUMN "FP_PROJEKTE"."LFDNR2" IS 'Laufende Nummer zweistellig als Teil der Projektnummer (Stelle 6-7)';
COMMENT ON COLUMN "FP_PROJEKTE"."PNAME" IS 'Name des Projekts (altes Verfahren: beginnend mit der Straße)';
COMMENT ON COLUMN "FP_PROJEKTE"."PSTRASSE" IS 'Straße des Projekts allein';
COMMENT ON COLUMN "FP_PROJEKTE"."KAUF" IS 'Liegt ein Ankauf oder Teilerwerb vor (0/1)';
COMMENT ON COLUMN "FP_PROJEKTE"."PROJART" IS 'Beschreibung des Projekts etwa Umbau, Neubau (Freitext)';
COMMENT ON COLUMN "FP_PROJEKTE"."REFINANZIERBAR" IS 'Ist dieses Förderprojekt refinanzierbar (0/1)';
COMMENT ON COLUMN "FP_PROJEKTE"."BLE_BAULEITUNG" IS 'Zuständige Abteilung im Baureferat (FK)';
COMMENT ON COLUMN "FP_PROJEKTE"."BAULEITUNGKONTAKT" IS 'Baustand: Name des Projektleiters sowie Telefon';
COMMENT ON COLUMN "FP_PROJEKTE"."BEZ_STADTBEZIRK" IS 'Nummer des Stadtbezirks (FK)';
COMMENT ON COLUMN "FP_PROJEKTE"."KRN_KRHNAME" IS 'Kurzbezeichnung Krankenhausname (FK)';
COMMENT ON COLUMN "FP_PROJEKTE"."KRHZWECK" IS 'Förderung nach Jahreskrankenhausbauprogramm (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_PROJEKTE"."KRISOFP" IS 'Art des Krippensonderförderprogramms (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_PROJEKTE"."KRIPPLATZ" IS 'Anzahl der Krippenplätze';
COMMENT ON COLUMN "FP_PROJEKTE"."KIGAPLATZ" IS 'Anzahl der Kindergartenplätze';
COMMENT ON COLUMN "FP_PROJEKTE"."HORTPLATZ" IS 'Anzahl der Hortplätze';
COMMENT ON COLUMN "FP_PROJEKTE"."VNDAT" IS 'Datum des abgeschlossenen Verwendungsnachweises.';
COMMENT ON COLUMN "FP_PROJEKTE"."VNKOSTEN" IS 'Endkosten lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNZWFKOSTEN" IS 'Zuwendungsfähige Gesamtkosten lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNPRUEFDAT" IS 'Datum der Prüfung des Verwendungsnachweises lt. Zuwendungsgeber';
COMMENT ON COLUMN "FP_PROJEKTE"."VNRUECK_Z" IS 'Höhe des zurückgeforderten Zuschussbetrags  lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNNACHFOERDERUNG" IS 'Höhe der Nachförderung (Zuschuss)  lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNPRUEFZWF" IS 'Höhe der geprüften zuwendungsfähigen Kosten  lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNSCHLUSSZWF" IS 'Höhe der endgültigen zuwendungsfähigen Kosten  lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."VNSCHLUSSBEW" IS 'Datum des letzten Bewilligungsbescheids (Schlussbescheid)  lt. Verwendungsnachweis';
COMMENT ON COLUMN "FP_PROJEKTE"."SAP" IS 'Wird dieses Projekt in SAP geführt (0/1)';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPSTATAUF" IS 'Statistischer Innenauftrag (0/1)';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPMATNR" IS 'SAP Materialnummer';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPWERTNR" IS 'SAP Wertkontraktnummer';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPJAHRWERT" IS 'Datum in SAP (Jahr Wertkontrakt)';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPANLAGENNR" IS 'SAP Anlagennummer';
COMMENT ON COLUMN "FP_PROJEKTE"."ZINSEN" IS 'Höhe der Zinszahlungen';
COMMENT ON COLUMN "FP_PROJEKTE"."ZINSDATUM" IS 'Datum der Zinszahlungen';
COMMENT ON COLUMN "FP_PROJEKTE"."FIPO" IS 'Finanzposition im Haushalt (Haushaltsstelle) für Zuwendungen';
COMMENT ON COLUMN "FP_PROJEKTE"."BUCHUNGSKREIS" IS 'SAP Buchungskreis für Zuwendungen';
COMMENT ON COLUMN "FP_PROJEKTE"."SACHKONTO" IS 'SAP Sachkonto für Zuwendungen';
COMMENT ON COLUMN "FP_PROJEKTE"."FIPO_K" IS 'Finanzposition im Haushalt (Haushaltsstelle) für Erstattungen';
COMMENT ON COLUMN "FP_PROJEKTE"."BUCHUNGSKREIS_K" IS 'SAP Buchungskreis für Erstattungen';
COMMENT ON COLUMN "FP_PROJEKTE"."SACHKONTO_K" IS 'SAP Sachkonto für Erstattungen';
COMMENT ON COLUMN "FP_PROJEKTE"."PSBAUBUCH" IS 'Projektstruktur Baubuchnummer Teil1';
COMMENT ON COLUMN "FP_PROJEKTE"."PSBAUREF" IS 'Projektstruktur Baubuchnummer Teil2  (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_PROJEKTE"."PSBAUNR" IS 'Projektstruktur Baubuchnummer Teil3';
COMMENT ON COLUMN "FP_PROJEKTE"."NOTIZEN" IS 'Notizen zum Projekt, Baustand und VN (Freitext)';
COMMENT ON COLUMN "FP_PROJEKTE"."ALTDATEN" IS 'Kennzeichen ob dieses Projekt aus der Altdatenübernahme stammt 0=nein, 1=ja';
COMMENT ON COLUMN "FP_PROJEKTE"."ANLAGEDATUM" IS 'Dieser Satz wurde angelegt am';
COMMENT ON COLUMN "FP_PROJEKTE"."ANLAGEVON" IS 'Dieser Satz wurde angelegt von diesem Benutzer';
COMMENT ON COLUMN "FP_PROJEKTE"."AENDERUNGSDATUM" IS 'Dieser Satz wurde zuletzt geändert am';
COMMENT ON COLUMN "FP_PROJEKTE"."AENDERUNGVON" IS 'Dieser Satz wurde zuletzt geändert von diesem Benutzer';
COMMENT ON COLUMN "FP_PROJEKTE"."VNGESAMTZUWENDUNG" IS 'Im Schlussbescheid festgesetzte Gesamtzuwendung seitens der Fördermittelgebers';
COMMENT ON COLUMN "FP_PROJEKTE"."SAPINNENAUFTRAG" IS 'SAP Innenauftrag';
COMMENT ON COLUMN "FP_PROJEKTE"."BPG_BAUPROGRAMM" IS 'Nummer des zugehörigen Bauprogramms (FK)';
COMMENT ON COLUMN "FP_PROJEKTE"."SGT_SIEDLUNGSGEBIET" IS 'Nummer des zugehörigen Siedlungsgebietes (FK)';
COMMENT ON COLUMN "FP_PROJEKTE"."BAUENDE" IS 'Datum des voraussichtlichen oder endgültigen Bauendes';
COMMENT ON COLUMN "FP_PROJEKTE"."BAUBEENDET" IS 'Status zum Datum des Bauendes (Gültige Werte siehe FP_DOMAINS)';
COMMENT ON COLUMN "FP_PROJEKTE"."BAUVERGABE1" IS 'Wann erfolgte die erste Vergabe zum Bau';
COMMENT ON COLUMN "FP_PROJEKTE"."BAUBEGINN" IS 'Wann erfolgte der offizielle Baubeginn';
COMMENT ON COLUMN "FP_PROJEKTE"."BAUMITTEILUNG" IS 'Wann erfolgte die letzte Mitteilung zum Bau';
COMMENT ON COLUMN "FP_PROJEKTE"."KREDITNUMMER" IS 'Kreditnummer von der Kämmerei vergeben';
COMMENT ON COLUMN "FP_PROJEKTE"."STADTANLEIHE" IS 'Falls Stadtanleihe dann Beschreibung oder Titel der Stadtanleihe';
COMMENT ON COLUMN "FP_PROJEKTE"."ANLEIHENENNWERT" IS 'Höhe der Anleihe (Nominalwert)';
COMMENT ON COLUMN "FP_PROJEKTE"."ANLEIHEJAHRVON" IS 'Laufzeit der Anleihe (Jahr von)';
COMMENT ON COLUMN "FP_PROJEKTE"."ANLEIHEJAHRBIS" IS 'Laufzeit der Anleihe (Jahr bis)';
COMMENT ON TABLE "FP_PROJEKTE" IS 'Alle Projekte der Fördermittelverwaltung (dbase:projekte,kindbetr,vn)';
--------------------------------------------------------
--  DDL for Table FP_PROJEKTE_BAUSTAND_SAVE
--------------------------------------------------------

CREATE TABLE "FP_PROJEKTE_BAUSTAND_SAVE"
(
    "PROJNR"              VARCHAR(7),
    "FOB_FB"              NUMERIC(2, 0),
    "KUR_KURZBEZ"         VARCHAR(8),
    "UAS_UA"              VARCHAR(2),
    "JAHR"                VARCHAR(2),
    "LFDNR1"              VARCHAR(1),
    "LFDNR2"              VARCHAR(2),
    "PNAME"               VARCHAR(100),
    "PSTRASSE"            VARCHAR(100),
    "KAUF"                NUMERIC(1, 0),
    "PROJART"             VARCHAR(50),
    "REFINANZIERBAR"      NUMERIC(1, 0),
    "KFWP88"              VARCHAR(15),
    "KFWP89"              VARCHAR(15),
    "KFWP90"              VARCHAR(15),
    "KFWH88"              VARCHAR(15),
    "KFWH89"              VARCHAR(15),
    "KFWH90"              VARCHAR(15),
    "BLE_BAULEITUNG"      VARCHAR(1),
    "BAULEITUNGKONTAKT"   VARCHAR(30),
    "BEZ_STADTBEZIRK"     NUMERIC(2, 0),
    "KRN_KRHNAME"         VARCHAR(1),
    "KRHZWECK"            VARCHAR(1),
    "KRISOFP"             VARCHAR(1),
    "KRIPPLATZ"           NUMERIC(10, 0),
    "KIGAPLATZ"           NUMERIC(10, 0),
    "HORTPLATZ"           NUMERIC(10, 0),
    "VNDAT"               DATE,
    "VNKOSTEN"            NUMERIC(12, 2),
    "VNZWFKOSTEN"         NUMERIC(12, 2),
    "VNPRUEFDAT"          DATE,
    "VNRUECK_Z"           NUMERIC(12, 2),
    "VNNACHFOERDERUNG"    NUMERIC(12, 2),
    "VNPRUEFZWF"          NUMERIC(12, 2),
    "VNSCHLUSSZWF"        NUMERIC(12, 2),
    "VNSCHLUSSBEW"        DATE,
    "SAP"                 NUMERIC(1, 0),
    "SAPSTATAUF"          NUMERIC(1, 0),
    "SAPMATNR"            NUMERIC(8, 0),
    "SAPWERTNR"           NUMERIC(8, 0),
    "SAPJAHRWERT"         DATE,
    "SAPANLAGENNR"        NUMERIC(8, 0),
    "ZINSEN"              NUMERIC(10, 0),
    "ZINSDATUM"           DATE,
    "ZINSBELEG"           NUMERIC(15, 0),
    "FIPO"                VARCHAR(15),
    "BUCHUNGSKREIS"       VARCHAR(4),
    "SACHKONTO"           VARCHAR(6),
    "FIPO_K"              VARCHAR(15),
    "BUCHUNGSKREIS_K"     VARCHAR(4),
    "SACHKONTO_K"         VARCHAR(6),
    "FIPO_Z"              VARCHAR(15),
    "BUCHUNGSKREIS_Z"     VARCHAR(4),
    "SACHKONTO_Z"         VARCHAR(6),
    "PSBAUBUCH"           VARCHAR(2),
    "PSBAUREF"            VARCHAR(2),
    "PSBAUNR"             VARCHAR(6),
    "NOTIZEN"             TEXT,
    "ALTDATEN"            NUMERIC(1, 0),
    "ANLAGEDATUM"         DATE,
    "ANLAGEVON"           VARCHAR(30),
    "AENDERUNGSDATUM"     DATE,
    "AENDERUNGVON"        VARCHAR(30),
    "VNGESAMTZUWENDUNG"   NUMERIC(12, 2),
    "SAPINNENAUFTRAG"     VARCHAR(12),
    "BPG_BAUPROGRAMM"     NUMERIC(2, 0),
    "SGT_SIEDLUNGSGEBIET" NUMERIC(2, 0),
    "BAUISTAUSGABEN"      NUMERIC(10, 0),
    "BAUPROGNOSE"         NUMERIC(10, 0),
    "BAUSTAND"            NUMERIC(3, 0),
    "BAUSTANDDATUM"       DATE
);

COMMENT ON TABLE "FP_PROJEKTE_BAUSTAND_SAVE" IS 'Backup der Tabelle FP_PROJEKTE (10/2024)';
--------------------------------------------------------
--  DDL for Table FP_PROJEKTE_ZINSEN_SAVE
--------------------------------------------------------

CREATE TABLE "FP_PROJEKTE_ZINSEN_SAVE"
(
    "PROJNR"              VARCHAR(7),
    "FOB_FB"              NUMERIC(2, 0),
    "KUR_KURZBEZ"         VARCHAR(8),
    "UAS_UA"              VARCHAR(2),
    "JAHR"                VARCHAR(2),
    "LFDNR1"              VARCHAR(1),
    "LFDNR2"              VARCHAR(2),
    "PNAME"               VARCHAR(100),
    "PSTRASSE"            VARCHAR(100),
    "KAUF"                NUMERIC(1, 0),
    "PROJART"             VARCHAR(50),
    "REFINANZIERBAR"      NUMERIC(1, 0),
    "BLE_BAULEITUNG"      VARCHAR(1),
    "BAULEITUNGKONTAKT"   VARCHAR(30),
    "BEZ_STADTBEZIRK"     NUMERIC(2, 0),
    "KRN_KRHNAME"         VARCHAR(1),
    "KRHZWECK"            VARCHAR(1),
    "KRISOFP"             VARCHAR(1),
    "KRIPPLATZ"           NUMERIC(10, 0),
    "KIGAPLATZ"           NUMERIC(10, 0),
    "HORTPLATZ"           NUMERIC(10, 0),
    "VNDAT"               DATE,
    "VNKOSTEN"            NUMERIC(12, 2),
    "VNZWFKOSTEN"         NUMERIC(12, 2),
    "VNPRUEFDAT"          DATE,
    "VNRUECK_Z"           NUMERIC(12, 2),
    "VNNACHFOERDERUNG"    NUMERIC(12, 2),
    "VNPRUEFZWF"          NUMERIC(12, 2),
    "VNSCHLUSSZWF"        NUMERIC(12, 2),
    "VNSCHLUSSBEW"        DATE,
    "SAP"                 NUMERIC(1, 0),
    "SAPSTATAUF"          NUMERIC(1, 0),
    "SAPMATNR"            NUMERIC(8, 0),
    "SAPWERTNR"           NUMERIC(8, 0),
    "SAPJAHRWERT"         DATE,
    "SAPANLAGENNR"        NUMERIC(8, 0),
    "ZINSEN"              NUMERIC(10, 0),
    "ZINSDATUM"           DATE,
    "ZINSBELEG"           NUMERIC(15, 0),
    "FIPO"                VARCHAR(15),
    "BUCHUNGSKREIS"       VARCHAR(4),
    "SACHKONTO"           VARCHAR(6),
    "FIPO_K"              VARCHAR(15),
    "BUCHUNGSKREIS_K"     VARCHAR(4),
    "SACHKONTO_K"         VARCHAR(6),
    "FIPO_Z"              VARCHAR(15),
    "BUCHUNGSKREIS_Z"     VARCHAR(4),
    "SACHKONTO_Z"         VARCHAR(6),
    "PSBAUBUCH"           VARCHAR(2),
    "PSBAUREF"            VARCHAR(2),
    "PSBAUNR"             VARCHAR(6),
    "NOTIZEN"             TEXT,
    "ALTDATEN"            NUMERIC(1, 0),
    "ANLAGEDATUM"         DATE,
    "ANLAGEVON"           VARCHAR(30),
    "AENDERUNGSDATUM"     DATE,
    "AENDERUNGVON"        VARCHAR(30),
    "VNGESAMTZUWENDUNG"   NUMERIC(12, 2),
    "SAPINNENAUFTRAG"     VARCHAR(12),
    "BPG_BAUPROGRAMM"     NUMERIC(2, 0),
    "SGT_SIEDLUNGSGEBIET" NUMERIC(2, 0),
    "BAUENDE"             DATE,
    "BAUBEENDET"          VARCHAR(1),
    "BAUVERGABE1"         DATE,
    "BAUBEGINN"           DATE,
    "BAUMITTEILUNG"       DATE
);

COMMENT ON TABLE "FP_PROJEKTE_ZINSEN_SAVE" IS 'Backup der Tabelle FP_PROJEKTE (05/2025)';
--------------------------------------------------------
--  DDL for Table FP_PROJEKTISTKOSTEN
--------------------------------------------------------

CREATE TABLE "FP_PROJEKTISTKOSTEN"
(
    "PRO_PROJNR" VARCHAR(7),
    "JAHR"       NUMERIC(4, 0),
    "MONAT"      NUMERIC(2, 0),
    "ISTKOSTEN"  NUMERIC(12, 0)
);

COMMENT ON COLUMN "FP_PROJEKTISTKOSTEN"."PRO_PROJNR" IS 'IstKosten bezogen auf ein Förderprojekt';
COMMENT ON COLUMN "FP_PROJEKTISTKOSTEN"."JAHR" IS 'IstKosten bezogen auf ein Jahr';
COMMENT ON COLUMN "FP_PROJEKTISTKOSTEN"."MONAT" IS 'IstKosten bezogen auf einen Monat (im Jahr)';
COMMENT ON COLUMN "FP_PROJEKTISTKOSTEN"."ISTKOSTEN" IS 'Istkosten in Euro aktueller Gesamtstand';
COMMENT ON TABLE "FP_PROJEKTISTKOSTEN" IS 'Enthält die Istkosten der Projekte in Form des monatlich aktuellen Gesamtwerts (dbase:pist, palt*). Diese Tabelle zeigt somit die Entwickung der IST-Kosten im Zeitverlauf jeweils summiert an.';
--------------------------------------------------------
--  DDL for Table FP_PROJEKTTERMINE
--------------------------------------------------------

CREATE TABLE "FP_PROJEKTTERMINE"
(
    "ID"           NUMERIC(38, 0),
    "PRO_PROJNR"   VARCHAR(7),
    "TERMIN"       DATE,
    "ZUSTAENDIG"   VARCHAR(60),
    "TELEFON"      VARCHAR(30),
    "NOTIZEN"      TEXT,
    "UEBERWACHUNG" NUMERIC(1, 0) DEFAULT 0
);

COMMENT ON COLUMN "FP_PROJEKTTERMINE"."ID" IS 'Primary Key als ID';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."PRO_PROJNR" IS 'Projektbezug eines Termins';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."TERMIN" IS 'Termin';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."ZUSTAENDIG" IS 'Zuständigkeit im Baureferet';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."TELEFON" IS 'Telefon des Bauleiters bzw. der Kontaktperson';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."NOTIZEN" IS 'Notizen (Freitext)';
COMMENT ON COLUMN "FP_PROJEKTTERMINE"."UEBERWACHUNG" IS 'Kennzeichen für Terminüberwachung 0/1';
COMMENT ON TABLE "FP_PROJEKTTERMINE" IS 'Enthält eine Terminverwaltung bezüglich Projekten (dbase:vnuanf)';
--------------------------------------------------------
--  DDL for Table FP_PROTOKOLL
--------------------------------------------------------

CREATE TABLE "FP_PROTOKOLL"
(
    "LOG_ID"            NUMERIC(38, 0),
    "LOG_DATE"          DATE,
    "LOG_MODULE"        VARCHAR(100),
    "LOG_GUI_USER"      VARCHAR(30),
    "LOG_MESSAGE"       TEXT,
    "LOG_ERROR_MESSAGE" TEXT
);

COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_ID" IS 'Eindeutiger Schluessel für einen Record';
COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_DATE" IS 'Datum des Ereignisses';
COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_MODULE" IS 'Module des Ereignisses';
COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_GUI_USER" IS 'Beutzername des Ereignisses';
COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_MESSAGE" IS 'Log Message';
COMMENT ON COLUMN "FP_PROTOKOLL"."LOG_ERROR_MESSAGE" IS 'Log Error';
COMMENT ON TABLE "FP_PROTOKOLL" IS 'Diese Tabelle beinhaltet die systemtechnischen Protokolle im Falle von Systemfehlern oder Prozeduren';
--------------------------------------------------------
--  DDL for Table FP_PUBLIKATIONEN
--------------------------------------------------------

CREATE TABLE "FP_PUBLIKATIONEN"
(
    "KURZFORM"    VARCHAR(1),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_PUBLIKATIONEN"."KURZFORM" IS 'Kurzform der EU nahen Publikation';
COMMENT ON COLUMN "FP_PUBLIKATIONEN"."BEZEICHNUNG" IS 'Beschreibung der EU nahen Publikation';
COMMENT ON TABLE "FP_PUBLIKATIONEN" IS 'Enthält Kurzformen EU naher Publikationen';
--------------------------------------------------------
--  DDL for Table FP_REFERATE
--------------------------------------------------------

CREATE TABLE "FP_REFERATE"
(
    "REFNR"       NUMERIC(2, 0),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_REFERATE"."REFNR" IS 'Nummer des Referats';
COMMENT ON COLUMN "FP_REFERATE"."BEZEICHNUNG" IS 'Beschreibung des Referats';
COMMENT ON TABLE "FP_REFERATE" IS 'Aufstellung aller Referate  (dbase:referate)';
--------------------------------------------------------
--  DDL for Table FP_SIEDLUNGSGEBIETE
--------------------------------------------------------

CREATE TABLE "FP_SIEDLUNGSGEBIETE"
(
    "SIEDLUNGSGEBIET" NUMERIC(2, 0),
    "BEZEICHNUNG"     VARCHAR(200)
);

COMMENT ON COLUMN "FP_SIEDLUNGSGEBIETE"."SIEDLUNGSGEBIET" IS 'Nummer des Siedlungsgebiets';
COMMENT ON COLUMN "FP_SIEDLUNGSGEBIETE"."BEZEICHNUNG" IS 'Beschreibung des Siedlungsgebiets';
COMMENT ON TABLE "FP_SIEDLUNGSGEBIETE" IS 'Aufstellung aller Siedlungsgebiete';
--------------------------------------------------------
--  DDL for Table FP_STADTBEZIRKE
--------------------------------------------------------

CREATE TABLE "FP_STADTBEZIRKE"
(
    "STADTBEZIRK" NUMERIC(2, 0),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_STADTBEZIRKE"."STADTBEZIRK" IS 'Nummer des Stadtbezirks';
COMMENT ON COLUMN "FP_STADTBEZIRKE"."BEZEICHNUNG" IS 'Beschreibung des Bezirks';
COMMENT ON TABLE "FP_STADTBEZIRKE" IS 'Aufstellung aller Stadtbezirke';
--------------------------------------------------------
--  DDL for Table FP_STADTBEZIRKSLISTEN
--------------------------------------------------------

CREATE TABLE "FP_STADTBEZIRKSLISTEN"
(
    "LNA_KURZBEZ"     VARCHAR(3),
    "BEZ_STADTBEZIRK" NUMERIC(2, 0),
    "BEZEICHNUNG"     VARCHAR(200)
);

COMMENT ON COLUMN "FP_STADTBEZIRKSLISTEN"."LNA_KURZBEZ" IS 'Kurzbezeichnung der Stadtbezirksliste';
COMMENT ON COLUMN "FP_STADTBEZIRKSLISTEN"."BEZ_STADTBEZIRK" IS 'Nummer des Stadtbezirks';
COMMENT ON COLUMN "FP_STADTBEZIRKSLISTEN"."BEZEICHNUNG" IS 'Notizen zu diesem Eintrag';
COMMENT ON TABLE "FP_STADTBEZIRKSLISTEN" IS 'Zuordnung von Stadtbezirken zu einer Stadtbezirksliste';
--------------------------------------------------------
--  DDL for Table FP_STAEDTEBAUFOERDERUNGEN
--------------------------------------------------------

CREATE TABLE "FP_STAEDTEBAUFOERDERUNGEN"
(
    "ID"           NUMERIC(38, 0),
    "BDAT"         DATE,
    "BNR"          NUMERIC(4, 0),
    "BJAHR"        NUMERIC(4, 0),
    "BETRAG"       NUMERIC(10, 0),
    "SOZ"          NUMERIC(1, 0) DEFAULT 0,
    "AZBANK"       VARCHAR(40),
    "ANTRNR"       NUMERIC(3, 0),
    "ANTRJAHR"     NUMERIC(4, 0),
    "AZSTK"        VARCHAR(6),
    "AUSBET"       NUMERIC(10, 0),
    "ERHDAT"       DATE,
    "TRA_KURZFORM" NUMERIC(1, 0),
    "HST"          VARCHAR(15),
    "HULNR"        VARCHAR(5),
    "HULJAHR"      NUMERIC(4, 0),
    "NOTIZEN"      TEXT,
    "PROJEKTNAME"  VARCHAR(60),
    "RESTLOS"      DATE,
    "ZAHLANZ"      DATE,
    "SCHULDURK"    NUMERIC(1, 0) DEFAULT 0,
    "ALT_LFDNR"    NUMERIC(10, 0)
);

COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ID" IS 'Eindeutiger Schluessel für einen Datensatz';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."BDAT" IS 'Datum der Bewilligung';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."BNR" IS 'Bescheidnummer';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."BJAHR" IS 'Jahr der Bewilligung';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."BETRAG" IS 'Bewilligter Betrag';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."SOZ" IS 'Kennzeichen Soz Stadt (0/1)';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."AZBANK" IS 'Aktenzeichen der Bank';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ANTRNR" IS 'Antragsnummer';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ANTRJAHR" IS 'Antragsjahr';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."AZSTK" IS 'Aktenzeichen der Stadtkämmerei';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."AUSBET" IS 'Ausbezahlter Betrag';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ERHDAT" IS 'Datum des Geldeingangs';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."TRA_KURZFORM" IS 'Träger des Städtebauförderprogramms';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."HST" IS 'Haushaltsstelle der Stadt als Freitext';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."HULNR" IS 'Hül Nummer der Stadt';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."HULJAHR" IS 'Hül Jahr';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."NOTIZEN" IS 'Notizen';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."PROJEKTNAME" IS 'Name des Projekts';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."RESTLOS" IS 'Datum der Restlosanzeige an Dienststelle';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ZAHLANZ" IS 'Zahlungsanzeige erhalten am';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."SCHULDURK" IS 'Schuldurkunde ausgestellt (0/1)';
COMMENT ON COLUMN "FP_STAEDTEBAUFOERDERUNGEN"."ALT_LFDNR" IS 'Lfdnr zur Altdatenübernahme der Memos';
COMMENT ON TABLE "FP_STAEDTEBAUFOERDERUNGEN" IS 'Aufstellung aller Programme zur Städtebauförderung als eingeständige Anwendung (dbase:stadt)';
--------------------------------------------------------
--  DDL for Table FP_STICHWORTBEREICHE
--------------------------------------------------------

CREATE TABLE "FP_STICHWORTBEREICHE"
(
    "BEREICH"     VARCHAR(30),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_STICHWORTBEREICHE"."BEREICH" IS 'Definierter Bereich für die Stichwortvergabe';
COMMENT ON COLUMN "FP_STICHWORTBEREICHE"."BEZEICHNUNG" IS 'Bezeichnung dieses Stichwortbereichs';
COMMENT ON TABLE "FP_STICHWORTBEREICHE" IS 'Verzeichnis aller Stichwortbereiche (Nutzung in Ablageindexe)';
--------------------------------------------------------
--  DDL for Table FP_TRAEGER
--------------------------------------------------------

CREATE TABLE "FP_TRAEGER"
(
    "KURZFORM"    NUMERIC(1, 0),
    "BEZEICHNUNG" VARCHAR(200)
);

COMMENT ON COLUMN "FP_TRAEGER"."KURZFORM" IS 'Kurzform für den Träger des Städtebauförderprogramms';
COMMENT ON COLUMN "FP_TRAEGER"."BEZEICHNUNG" IS 'Beschreibung des Trägers des Städebauförderprogramms';
COMMENT ON TABLE "FP_TRAEGER" IS 'Enthält Träger der Städtebauförderprogramme';
--------------------------------------------------------
--  DDL for Table FP_UNTERABSCHNITTE
--------------------------------------------------------

CREATE TABLE "FP_UNTERABSCHNITTE"
(
    "UA"          VARCHAR(2),
    "BEZEICHNUNG" VARCHAR(200),
    "HAS_HA"      VARCHAR(2)
);

COMMENT ON COLUMN "FP_UNTERABSCHNITTE"."UA" IS 'Nummer des Unterabschnitts';
COMMENT ON COLUMN "FP_UNTERABSCHNITTE"."BEZEICHNUNG" IS 'Bezeichnung des Unterabschnitts';
COMMENT ON COLUMN "FP_UNTERABSCHNITTE"."HAS_HA" IS 'Nummer des Hauptabschnitt des UAs (FK)';
COMMENT ON TABLE "FP_UNTERABSCHNITTE" IS 'Unterabschnitte als Teil1 der Projektnummer (dbase: keine Datei vorhanden)';