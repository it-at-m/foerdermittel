DROP TABLE fp_domains;

-- fp_abrufe
CREATE TYPE vnabr AS ENUM ('V', 'I');

ALTER TABLE fp_abrufe
    ALTER COLUMN vnabr TYPE vnabr USING vnabr::vnabr;

COMMENT ON COLUMN fp_abrufe.vnabr IS 'Ist der Abruf mit dem Verwendungsnachweis erfolgt';

-- fp_antraege
CREATE TYPE antragstyp AS ENUM ('E', 'V', 'A', 'F', 'U');

ALTER TABLE fp_antraege
    ALTER COLUMN antragstyp TYPE antragstyp USING antragstyp::antragstyp;

COMMENT ON COLUMN fp_antraege.antragstyp IS 'Typ des Förderantrags';

-- fp_bewilligungen
CREATE TYPE bzuwart AS ENUM ('R', 'V', 'T', 'G');
CREATE TYPE krw AS ENUM ('Z', 'H', 'P');

ALTER TABLE fp_bewilligungen
    ALTER COLUMN bzuwart TYPE bzuwart USING bzuwart::bzuwart,
    ALTER COLUMN krw TYPE krw USING krw::krw;

COMMENT ON COLUMN fp_bewilligungen.bzuwart IS 'Bewilligte Zuwendungsart';
COMMENT ON COLUMN fp_bewilligungen.krw IS 'Typ des Kostenrichtwerts';

-- fp_projekte
CREATE TYPE baubeendet AS ENUM ('V', 'E');
CREATE TYPE krhzweck AS ENUM ('J', 'S', 'R');
CREATE TYPE krisofp AS ENUM ('Z', 'W', 'V', 'K', 'X', 'F', 'Y');
CREATE TYPE psbauref AS ENUM ('GN', 'GI', 'HB', 'HI', 'HM');

ALTER TABLE fp_projekte
    ALTER COLUMN baubeendet TYPE baubeendet USING baubeendet::baubeendet,
    ALTER COLUMN krhzweck TYPE krhzweck USING krhzweck::krhzweck,
    ALTER COLUMN krisofp TYPE krisofp USING krisofp::krisofp,
    ALTER COLUMN psbauref TYPE psbauref USING psbauref::psbauref;

COMMENT ON COLUMN fp_projekte.baubeendet IS 'Status zum Datum des Bauendes';
COMMENT ON COLUMN fp_projekte.krhzweck IS 'Förderung nach Jahreskrankenhausbauprogramm';
COMMENT ON COLUMN fp_projekte.krisofp IS 'Art des Krippensonderförderprogramms';
COMMENT ON COLUMN fp_projekte.psbauref IS 'Projektstruktur Baubuchnummer Teil2';