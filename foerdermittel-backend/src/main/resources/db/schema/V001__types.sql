CREATE TYPE abrufe_vnabr AS ENUM ('V', 'I');
COMMENT ON TYPE abrufe_vnabr IS 'Enum für Spalte vnabr in Tabelle abrufe (V = Abruf mit VN, I = Abruf mit Index)';

CREATE TYPE antraege_antragstyp AS ENUM ('E', 'V', 'A', 'F', 'U');
COMMENT ON TYPE antraege_antragstyp IS 'Enum für Spalte antragstyp in Tabelle antraege (E = Erstantrag, V = Schlussantrag (VN), A = Änderungsantrag, F = Fortsetzungsantrag, U = Unbedenklichkeitsantrag)';

CREATE TYPE bewilligungen_bzuwart AS ENUM ('R', 'V', 'T', 'G');
COMMENT ON TYPE bewilligungen_bzuwart IS 'Enum für Spalte bzuwart in Tabelle bewilligungen (R = Restzuwendung, V = Mittelverfall, T = Teilzuwendung, G = Gesamtzuwendung)';

CREATE TYPE bewilligungen_krw AS ENUM ('Z', 'H', 'P');
COMMENT ON TYPE bewilligungen_krw IS 'Enum für Spalte krw in Tabelle bewilligungen (Z = ZTK, H = Höchstwert, P = Pauschale oder Festbetrag)';

CREATE TYPE projekte_baubeendet AS ENUM ('V', 'E');
COMMENT ON TYPE projekte_baubeendet IS 'Enum für Spalte baubeendet in Tabelle projekte (V = vorläufig, E = endgültig)';

CREATE TYPE projekte_krhzweck AS ENUM ('J', 'S', 'R');
COMMENT ON TYPE projekte_krhzweck IS 'Enum für Spalte krhzweck in Tabelle projekte (J = Jahreskrankenhausprogramm: J, S = Jahreskrankenhausprogramm: S, R = Jahreskrankenhausprogramm: R)';

CREATE TYPE projekte_krisofp AS ENUM ('Z', 'W', 'V', 'K', 'X', 'F', 'Y');
COMMENT ON TYPE projekte_krisofp IS 'Enum für Spalte krisofp in Tabelle projekte (Z = 4. SIP – Kinderbetreuung 2017-2020/2021, W = 5. SIP – Schulkindbetreuung Land (Hortförd. GS), V = Schulkindbetreuung – Bund (GaFöG), K = KJP II, X = 1.+2. SIP - KriSoFö 2008-2014, F = FAGplus15, Y = 3. SIP - Kinderbetreuung 2015-2019';

CREATE TYPE projekte_psbauref AS ENUM ('GN', 'GI', 'HB', 'HI', 'HM');
COMMENT ON TYPE projekte_psbauref IS 'Enum für Spalte psbauref in Tabelle projekte (GN = Abteilung GN im Baureferat, GI = Abteilung GI im Baureferat, HB = Abteilung HB im Baureferat, HI = Abteilung HI im Baureferat, HM = Abteilung HM im Baureferat)';