CREATE TYPE fp_abrufe_vnabr AS ENUM ('V', 'I');
COMMENT ON TYPE fp_abrufe_vnabr IS 'Enum für Spalte vnabr in Tabelle fp_abrufe (V = Abruf mit VN, I = Abruf mit Index)';

CREATE TYPE fp_antraege_antragstyp AS ENUM ('E', 'V', 'A', 'F', 'U');
COMMENT ON TYPE fp_antraege_antragstyp IS 'Enum für Spalte antragstyp in Tabelle fp_antraege (E = Erstantrag, V = Schlussantrag (VN), A = Änderungsantrag, F = Fortsetzungsantrag, U = Unbedenklichkeitsantrag)';

CREATE TYPE fp_bewilligungen_bzuwart AS ENUM ('R', 'V', 'T', 'G');
COMMENT ON TYPE fp_bewilligungen_bzuwart IS 'Enum für Spalte bzuwart in Tabelle fp_bewilligungen (R = Restzuwendung, V = Mittelverfall, T = Teilzuwendung, G = Gesamtzuwendung)';

CREATE TYPE fp_bewilligungen_krw AS ENUM ('Z', 'H', 'P');
COMMENT ON TYPE fp_bewilligungen_krw IS 'Enum für Spalte krw in Tabelle fp_bewilligungen (Z = ZTK, H = Höchstwert, P = Pauschale oder Festbetrag)';

CREATE TYPE fp_projekte_baubeendet AS ENUM ('V', 'E');
COMMENT ON TYPE fp_projekte_baubeendet IS 'Enum für Spalte baubeendet in Tabelle fp_projekte (V = vorläufig, E = endgültig)';

CREATE TYPE fp_projekte_krhzweck AS ENUM ('J', 'S', 'R');
COMMENT ON TYPE fp_projekte_krhzweck IS 'Enum für Spalte krhzweck in Tabelle fp_projekte (J = Jahreskrankenhausprogramm: J, S = Jahreskrankenhausprogramm: S, R = Jahreskrankenhausprogramm: R)';

CREATE TYPE fp_projekte_krisofp AS ENUM ('Z', 'W', 'V', 'K', 'X', 'F', 'Y');
COMMENT ON TYPE fp_projekte_krisofp IS 'Enum für Spalte krisofp in Tabelle fp_projekte (Z = 4. SIP – Kinderbetreuung 2017-2020/2021, W = 5. SIP – Schulkindbetreuung Land (Hortförd. GS), V = Schulkindbetreuung – Bund (GaFöG), K = KJP II, X = 1.+2. SIP - KriSoFö 2008-2014, F = FAGplus15, Y = 3. SIP - Kinderbetreuung 2015-2019';

CREATE TYPE fp_projekte_psbauref AS ENUM ('GN', 'GI', 'HB', 'HI', 'HM');
COMMENT ON TYPE fp_projekte_psbauref IS 'Enum für Spalte psbauref in Tabelle fp_projekte (GN = Abteilung GN im Baureferat, GI = Abteilung GI im Baureferat, HB = Abteilung HB im Baureferat, HI = Abteilung HI im Baureferat, HM = Abteilung HM im Baureferat)';