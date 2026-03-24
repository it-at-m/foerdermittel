--------------------------------------------------------
--  DDL for View FP_V_ABRUFSUCHE
--------------------------------------------------------

CREATE VIEW "FP_V_ABRUFSUCHE" AS
SELECT
    -- Projekt
    P."PROJNR"              AS "V_PROJNR",
    P."FOB_FB"              AS "V_FOB_FB",
    F."BEZEICHNUNG"         AS "V_FOB_BEZEICHNUNG",
    P."PNAME"               AS "V_PNAME",
    P."PSTRASSE"            AS "V_PSTRASSE",
    P."KUR_KURZBEZ"         AS "V_KUR_KURZBEZ",
    Z."BEZEICHNUNG"         AS "V_KUR_BEZEICHNUNG",
    P."UAS_UA"              AS "V_UAS_UA",
    U."BEZEICHNUNG"         AS "V_UAS_BEZEICHNUNG",
    P."BEZ_STADTBEZIRK"     AS "V_BEZ_STADTBEZIRK",
    S."BEZEICHNUNG"         AS "V_BEZ_BEZEICHNUNG",
    P."KRISOFP"             AS "V_KRISOFP",
    P."VNDAT"               AS "V_VNDAT",
    P."KRHZWECK"            AS "V_KRHZWECK",
    P."KRN_KRHNAME"         AS "V_KRN_KRHNAME",
    K."BEZEICHNUNG"         AS "V_KRN_BEZEICHNUNG",
    P."SAPSTATAUF"          AS "V_SAPSTATAUF",
    P."SAPANLAGENNR"        AS "V_SAPANLAGENNR",
    -- Abruf
    A."VNABR"               AS "V_VNABR",
    A."ABRUF_Z"             AS "V_ABRUF_Z",
    A."ABRUF_D"             AS "V_ABRUF_D",
    A."ABRUF_K"             AS "V_ABRUF_K",
    A."ABRUF_DATUM"         AS "V_ABRUF_DATUM",
    A."ERH_Z"               AS "V_ERH_Z",
    A."ERH_D"               AS "V_ERH_D",
    A."ERH_K"               AS "V_ERH_K",
    A."ERH_DATUM"           AS "V_ERH_DATUM",
    A."REF_REFNR"           AS "V_REF_REFNR",
    R."BEZEICHNUNG"         AS "V_REF_BEZEICHNUNG",
    A."SAPABRUFAUFTRAGSNR"  AS "V_SAPABRUFAUFTRAGSNR",
    A."SAPFAKTURANR"        AS "V_SAPFAKTURANR",
    A."FIPO"                AS "V_FIPO",
    A."BUCHUNGSKREIS"       AS "V_BUCHUNGSKREIS",
    A."SACHKONTO"           AS "V_SACHKONTO",
    A."FIPO_K"              AS "V_FIPO_K",
    A."BUCHUNGSKREIS_K"     AS "V_BUCHUNGSKREIS_K",
    A."SACHKONTO_K"         AS "V_SACHKONTO_K",
    -- Bewilligung
    B."BDATUM"              AS "V_BDATUM",
    B."BFSATZ"              AS "V_BFSATZ",
    B."BZWFKOSTEN"          AS "V_BZWFKOSTEN",
    -- SIEDLUNGSGEBIET BAUPROGRAMM
    P."SGT_SIEDLUNGSGEBIET" AS "V_SGT_SIEDLUNGSGEBIET",
    I."BEZEICHNUNG"         AS "V_SGT_BEZEICHNUNG",
    P."BPG_BAUPROGRAMM"     AS "V_BPG_BAUPROGRAMM",
    O."BEZEICHNUNG"         AS "V_BPG_BEZEICHNUNG"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_BEWILLIGUNGEN" B ON A."BWI_ID" = B."ID"
         LEFT JOIN "FP_KRANKENHAEUSER" K ON P."KRN_KRHNAME" = K."KRHNAME"
         LEFT JOIN "FP_REFERATE" R ON A."REF_REFNR" = R."REFNR"
         LEFT JOIN "FP_BAUPROGRAMME" O ON P."BPG_BAUPROGRAMM" = O."BAUPROGRAMM"
         LEFT JOIN "FP_SIEDLUNGSGEBIETE" I ON P."SGT_SIEDLUNGSGEBIET" = I."SIEDLUNGSGEBIET"
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_BEWILL
--------------------------------------------------------

CREATE VIEW "FP_V_ANTRAG_BEWILL" AS
SELECT A."ID"              AS "V_ID",
       A."PRO_PROJNR"      AS "V_PRO_PROJNR",
       P."VNDAT"           AS "V_VNDAT",
       P."FOB_FB"          AS "V_FOB_FB",
       F."BEZEICHNUNG"     AS "V_FOB_BEZEICHNUNG",
       P."PNAME"           AS "V_PNAME",
       P."PSTRASSE"        AS "V_PSTRASSE",
       P."KAUF"            AS "V_KAUF",
       P."KUR_KURZBEZ"     AS "V_KUR_KURZBEZ",
       Z."BEZEICHNUNG"     AS "V_KUR_BEZEICHNUNG",
       P."UAS_UA"          AS "V_UAS_UA",
       U."BEZEICHNUNG"     AS "V_UAS_BEZEICHNUNG",
       P."BEZ_STADTBEZIRK" AS "V_BEZ_STADTBEZIRK",
       S."BEZEICHNUNG"     AS "V_BEZ_BEZEICHNUNG",
       P."KRISOFP"         AS "V_KRISOFP",
       A."ANTRAGSDATUM"    AS "V_ANTRAGSDATUM",
       A."ANTRAGSTYP"      AS "V_ANTRAGSTYP",
       A."GESKOSTEN"       AS "V_GESKOSTEN",
       A."ZWFKOSTEN"       AS "V_ZWFKOSTEN",
       A."A_SU_Z"          AS "V_A_SU_Z",
       A."A_SU_D"          AS "V_A_SU_D",
       A."A_SU_K"          AS "V_A_SU_K",
       B."NOTIZEN"         AS "V_NOTIZEN",
       B."ANT_ID"          AS "V_ANT_ID",
       B."BDATUM"          AS "V_BDATUM",
       B."BFSATZ"          AS "V_BFSATZ",
       B."BZWFKOSTEN"      AS "V_BZWFKOSTEN",
       B."BZUWENDUNG_Z"    AS "V_BZUWENDUNG_Z",
       B."BZUWENDUNG_D"    AS "V_BZUWENDUNG_D",
       B."BZUWENDUNG_K"    AS "V_BZUWENDUNG_K",
       B."BZUWART"         AS "V_BZUWART"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_ANTRAEGE" A ON B."ANT_ID" = A."ID"
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_BEWILL_ABRUF
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_ANTRAG_BEWILL_ABRUF" ("V_P_TYP", "V_P_PROJNR", "V_P_VNDAT", "V_P_FOB_FB", "V_P_PNAME", "V_P_PSTRASSE", "V_A_ID", "V_A_ANTRAGSTYP", "V_A_ANTRAGSDATUM", "V_A_GESKOSTEN", "V_A_ZWFKOSTEN", "V_A_SU_Z", "V_A_SU_D", "V_A_SU_K", "V_B_ID", "V_B_ANT_ID", "V_B_BZUWART", "V_B_BDATUM", "V_B_BZUWENDUNG_Z", "V_B_BZUWENDUNG_D", "V_B_ZUWENDUNG_K", "V_R_BWI_ID", "V_R_ABRUF_DATUM", "V_R_ABRUF_Z", "V_R_ABRUF_D", "V_R_ABRUF_K", "V_R_ERH_DATUM", "V_R_ERH_Z", "V_R_ERH_D", "V_R_ERH_K") AS
SELECT X0,
       X1,
       X2,
       X3,
       X4,
       X5,
       X_A_ID,
       X6,
       X7,
       X8,
       X9,
       X10,
       X11,
       X12,
       X_B_ID,
       X13,
       X14,
       X15,
       X16,
       X17,
       X18,
       X19,
       X20,
       X21,
       X22,
       X23,
       X24,
       X25,
       X26,
       X27
FROM (SELECT null           X0, -- Typ1
             P.PROJNR       X1,
             P.VNDAT        X2,
             P.FOB_FB       X3,
             P.PNAME        X4,
             P.PSTRASSE     X5,
             A.ID           X_A_ID,
             A.ANTRAGSTYP   X6,
             A.ANTRAGSDATUM X7,
             A.GESKOSTEN    X8,
             A.ZWFKOSTEN    X9,
             A.A_SU_Z       X10,
             A.A_SU_D       X11,
             A.A_SU_K       X12,
             B.ID           X_B_ID,
             B.ANT_ID       X13,
             B.BZUWART      X14,
             B.BDATUM       X15,
             B.BZUWENDUNG_Z X16,
             B.BZUWENDUNG_D X17,
             B.BZUWENDUNG_K X18,
             R.BWI_ID       X19,
             R.ABRUF_DATUM  X20,
             R.ABRUF_Z      X21,
             R.ABRUF_D      X22,
             R.ABRUF_K      X23,
             R.ERH_DATUM    X24,
             R.ERH_Z        X25,
             R.ERH_D        X26,
             R.ERH_K        X27
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE A,
           FP_BEWILLIGUNGEN B,
           FP_ABRUFE R
      WHERE -- Typ 1: vollst�ndige Verkn�pfungen selektieren mit outerjoin
          P.PROJNR = A.PRO_PROJNR
        AND A.ID = B.ANT_ID(+)
        AND B.ID = R.BWI_ID(+)
      UNION
      SELECT 'Bewilligung ohne Antrag' X0, -- Typ2
             P.PROJNR                  X1,
             P.VNDAT                   X2,
             P.FOB_FB                  X3,
             P.PNAME                   X4,
             P.PSTRASSE                X5,
             null                      X_A_ID,
             null                      X6,
             null                      X7,
             null                      X8,
             null                      X9,
             null                      X10,
             null                      X11,
             null                      X12,
             B.ID                      X_B_ID,
             B.ANT_ID                  X13,
             B.BZUWART                 X14,
             B.BDATUM                  X15,
             B.BZUWENDUNG_Z            X16,
             B.BZUWENDUNG_D            X17,
             B.BZUWENDUNG_K            X18,
             R.BWI_ID                  X19,
             R.ABRUF_DATUM             X20,
             R.ABRUF_Z                 X21,
             R.ABRUF_D                 X22,
             R.ABRUF_K                 X23,
             R.ERH_DATUM               X24,
             R.ERH_Z                   X25,
             R.ERH_D                   X26,
             R.ERH_K                   X27
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_ABRUFE R
      WHERE -- Typ 2: Antrag fehlt, Bewill+Abrufe vorhanden mit outerjoin
          P.PROJNR = B.PRO_PROJNR
        AND B.ANT_ID is null
        AND B.ID = R.BWI_ID(+)
      UNION
      SELECT 'Abruf ohne Bewilligung' X0, -- Typ3
             P.PROJNR                 X1,
             P.VNDAT                  X2,
             P.FOB_FB                 X3,
             P.PNAME                  X4,
             P.PSTRASSE               X5,
             null                     X_A_ID,
             null                     X6,
             null                     X7,
             null                     X8,
             null                     X9,
             null                     X10,
             null                     X11,
             null                     X12,
             null                     X_B_ID,
             null                     X13,
             null                     X14,
             null                     X15,
             null                     X15,
             null                     X17,
             null                     X18,
             R.BWI_ID                 X19,
             R.ABRUF_DATUM            X20,
             R.ABRUF_Z                X21,
             R.ABRUF_D                X22,
             R.ABRUF_K                X23,
             R.ERH_DATUM              X24,
             R.ERH_Z                  X25,
             R.ERH_D                  X26,
             R.ERH_K                  X27
      FROM FP_PROJEKTE P,
           FP_ABRUFE R
      WHERE -- Typ 3: Antrag fehlt, Bewill fehlt nur Abrufe ohne Verbindung
          P.PROJNR = R.PRO_PROJNR
        AND R.BWI_ID is null)
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAGSUCHE
--------------------------------------------------------

CREATE VIEW "FP_V_ANTRAGSUCHE" AS
SELECT
    --  geändert Sep 2024:
    --  Sicherstellen, dass je Antrag nur ein Record geliefert wird.
    --  unabhängig wieviele Bewilligungen vorliegen (0 .. viele)
    --  deshalb zwei Select mit UNION.
    --
    --  1. Select: Antrag besitzt min. 1 Bewilligung --> liefert nur die jüngste Bewilligung
    A."ID"                  AS "V_ID",
    A."PRO_PROJNR"          AS "V_PRO_PROJNR",
    P."VNDAT"               AS "V_VNDAT",
    P."FOB_FB"              AS "V_FOB_FB",
    F."BEZEICHNUNG"         AS "V_FOB_BEZEICHNUNG",
    P."PNAME"               AS "V_PNAME",
    P."PSTRASSE"            AS "V_PSTRASSE",
    P."KAUF"                AS "V_KAUF",
    P."KUR_KURZBEZ"         AS "V_KUR_KURZBEZ",
    Z."BEZEICHNUNG"         AS "V_KUR_BEZEICHNUNG",
    P."UAS_UA"              AS "V_UAS_UA",
    U."BEZEICHNUNG"         AS "V_UAS_BEZEICHNUNG",
    P."BEZ_STADTBEZIRK"     AS "V_BEZ_STADTBEZIRK",
    S."BEZEICHNUNG"         AS "V_BEZ_BEZEICHNUNG",
    P."KRISOFP"             AS "V_KRISOFP",
    A."ANTRAGSDATUM"        AS "V_ANTRAGSDATUM",
    A."ANTRAGSTYP"          AS "V_ANTRAGSTYP",
    A."GESKOSTEN"           AS "V_GESKOSTEN",
    A."ZWFKOSTEN"           AS "V_ZWFKOSTEN",
    A."VORZBEG"             AS "V_VORZBEG",
    A."VBDATUM"             AS "V_VBDATUM",
    A."UNBEDDAT"            AS "V_UNBEDDAT",
    A."UNBEDJA"             AS "V_UNBEDJA",
    A."UNBEDBIS"            AS "V_UNBEDBIS",
    A."A_SU_Z"              AS "V_A_SU_Z",
    A."A_SU_D"              AS "V_A_SU_D",
    A."A_SU_K"              AS "V_A_SU_K",
    A."B_VOR_SU_Z"          AS "V_B_VOR_SU_Z",
    A."B_VOR_SU_D"          AS "V_B_VOR_SU_D",
    A."B_VOR_SU_K"          AS "V_B_VOR_SU_K",
    A."NOTIZEN"             AS "V_NOTIZEN",
    P."SGT_SIEDLUNGSGEBIET" AS "V_SGT_SIEDLUNGSGEBIET",
    I."BEZEICHNUNG"         AS "V_SGT_BEZEICHNUNG",
    P."BPG_BAUPROGRAMM"     AS "V_BPG_BAUPROGRAMM",
    O."BEZEICHNUNG"         AS "V_BPG_BEZEICHNUNG",
    B."ID"                  AS "V_BID",
    B."GESZUWENDUNGEN"      AS "V_BGESZUWENDUNGEN",
    B."BDATUM"              AS "V_BDATUM"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
         JOIN "FP_BEWILLIGUNGEN" B ON A."ID" = B."ANT_ID"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_BAUPROGRAMME" O ON P."BPG_BAUPROGRAMM" = O."BAUPROGRAMM"
         LEFT JOIN "FP_SIEDLUNGSGEBIETE" I ON P."SGT_SIEDLUNGSGEBIET" = I."SIEDLUNGSGEBIET"
WHERE B."ID" = (SELECT MAX(B1."ID") FROM "FP_BEWILLIGUNGEN" B1 WHERE B1."ANT_ID" = A."ID")

UNION ALL

SELECT
--  2. Select: Antrag noch ohne Bewilligung --> liefert null für BEW Columns
A."ID",
A."PRO_PROJNR",
P."VNDAT",
P."FOB_FB",
F."BEZEICHNUNG",
P."PNAME",
P."PSTRASSE",
P."KAUF",
P."KUR_KURZBEZ",
Z."BEZEICHNUNG",
P."UAS_UA",
U."BEZEICHNUNG",
P."BEZ_STADTBEZIRK",
S."BEZEICHNUNG",
P."KRISOFP",
A."ANTRAGSDATUM",
A."ANTRAGSTYP",
A."GESKOSTEN",
A."ZWFKOSTEN",
A."VORZBEG",
A."VBDATUM",
A."UNBEDDAT",
A."UNBEDJA",
A."UNBEDBIS",
A."A_SU_Z",
A."A_SU_D",
A."A_SU_K",
A."B_VOR_SU_Z",
A."B_VOR_SU_D",
A."B_VOR_SU_K",
A."NOTIZEN",
P."SGT_SIEDLUNGSGEBIET",
I."BEZEICHNUNG",
P."BPG_BAUPROGRAMM",
O."BEZEICHNUNG",
NULL, --B.ID,
NULL, --B.GESZUWENDUNGEN,
NULL  --B.BDATUM
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_BAUPROGRAMME" O ON P."BPG_BAUPROGRAMM" = O."BAUPROGRAMM"
         LEFT JOIN "FP_SIEDLUNGSGEBIETE" I ON P."SGT_SIEDLUNGSGEBIET" = I."SIEDLUNGSGEBIET"
WHERE NOT EXISTS (SELECT 1 FROM "FP_BEWILLIGUNGEN" B WHERE B."ANT_ID" = A."ID")
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_UNBED
--------------------------------------------------------

CREATE VIEW "FP_V_ANTRAG_UNBED" AS
SELECT "PRO_PROJNR"    AS "V_PRO_PROJNR",
       MIN("UNBEDDAT") AS "V_UNBEDDAT",
       MIN("UNBEDJA")  AS "V_UNBEDJA",
       MIN("UNBEDBIS") AS "V_UNBEDBIS",
       MIN("VBDATUM")  AS "V_VBDATUM"
FROM "FP_ANTRAEGE"
GROUP BY "PRO_PROJNR"
;
--------------------------------------------------------
--  DDL for View FP_V_BEWILL_ABRUFE
--------------------------------------------------------

CREATE VIEW "FP_V_BEWILL_ABRUFE" AS
SELECT
    -- Projekt
    P."PROJNR"                    AS "V_PROJNR",
    P."FOB_FB"                    AS "V_FOB_FB",
    F."BEZEICHNUNG"               AS "V_FOB_BEZEICHNUNG",
    P."PNAME"                     AS "V_PNAME",
    P."PSTRASSE"                  AS "V_PSTRASSE",
    P."KUR_KURZBEZ"               AS "V_KUR_KURZBEZ",
    Z."BEZEICHNUNG"               AS "V_KUR_BEZEICHNUNG",
    P."UAS_UA"                    AS "V_UAS_UA",
    U."BEZEICHNUNG"               AS "V_UAS_BEZEICHNUNG",
    P."BEZ_STADTBEZIRK"           AS "V_BEZ_STADTBEZIRK",
    S."BEZEICHNUNG"               AS "V_BEZ_BEZEICHNUNG",
    P."KRISOFP"                   AS "V_KRISOFP",
    P."VNDAT"                     AS "V_VNDAT",
    -- Abruf
    SUM(COALESCE(A."ABRUF_Z", 0)) AS "V_SUM_ABRUF_Z",
    SUM(COALESCE(A."ABRUF_D", 0)) AS "V_SUM_ABRUF_D",
    SUM(COALESCE(A."ABRUF_K", 0)) AS "V_SUM_ABRUF_K",
    MIN(A."ABRUF_DATUM")          AS "V_MIN_ABRUF_DATUM",
    SUM(COALESCE(A."ERH_Z", 0))   AS "V_SUM_ERH_Z",
    SUM(COALESCE(A."ERH_D", 0))   AS "V_SUM_ERH_D",
    SUM(COALESCE(A."ERH_K", 0))   AS "V_SUM_ERH_K",
    MIN(A."ERH_DATUM")            AS "V_MIN_ERH_DATUM",
    MIN(A."FIPO")                 AS "V_MIN_FIPO",
    -- Bewilligung
    B."ID"                        AS "V_ID",
    B."ANT_ID"                    AS "V_ANT_ID",
    B."BDATUM"                    AS "V_BDATUM",
    B."AFSATZ"                    AS "V_AFSATZ",
    B."BFSATZ"                    AS "V_BFSATZ",
    B."BZWFKOSTEN"                AS "V_BZWFKOSTEN",
    B."BZUWENDUNG_Z"              AS "V_BZUWENDUNG_Z",
    B."BZUWENDUNG_D"              AS "V_BZUWENDUNG_D",
    B."BZUWENDUNG_K"              AS "V_BZUWENDUNG_K",
    B."BZUWART"                   AS "V_BZUWART",
    B."BAKTENZEICHEN"             AS "V_BAKTENZEICHEN",
    B."GESZUWENDUNGEN"            AS "V_GESZUWENDUNGEN",
    B."GESKONNEX"                 AS "V_GESKONNEX",
    B."KRW"                       AS "V_KRW"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_ABRUFE" A ON B."ID" = A."BWI_ID"
GROUP BY P."PROJNR",
         P."FOB_FB",
         F."BEZEICHNUNG",
         P."PNAME",
         P."PSTRASSE",
         P."KUR_KURZBEZ",
         Z."BEZEICHNUNG",
         P."UAS_UA",
         U."BEZEICHNUNG",
         P."BEZ_STADTBEZIRK",
         S."BEZEICHNUNG",
         P."KRISOFP",
         P."VNDAT",
         B."ID",
         B."ANT_ID",
         B."BDATUM",
         B."AFSATZ",
         B."BFSATZ",
         B."BZWFKOSTEN",
         B."BZUWENDUNG_Z",
         B."BZUWENDUNG_D",
         B."BZUWENDUNG_K",
         B."BZUWART",
         B."BAKTENZEICHEN",
         B."GESZUWENDUNGEN",
         B."GESKONNEX",
         B."KRW"
;
--------------------------------------------------------
--  DDL for View FP_V_BEWILLSUCHE
--------------------------------------------------------

CREATE VIEW "FP_V_BEWILLSUCHE" AS
SELECT P."PROJNR"              AS "V_PROJNR",
       P."FOB_FB"              AS "V_FOB_FB",
       F."BEZEICHNUNG"         AS "V_FOB_BEZEICHNUNG",
       P."PNAME"               AS "V_PNAME",
       P."PSTRASSE"            AS "V_PSTRASSE",
       P."KUR_KURZBEZ"         AS "V_KUR_KURZBEZ",
       Z."BEZEICHNUNG"         AS "V_KUR_BEZEICHNUNG",
       P."UAS_UA"              AS "V_UAS_UA",
       U."BEZEICHNUNG"         AS "V_UAS_BEZEICHNUNG",
       P."BEZ_STADTBEZIRK"     AS "V_BEZ_STADTBEZIRK",
       S."BEZEICHNUNG"         AS "V_BEZ_BEZEICHNUNG",
       P."KRISOFP"             AS "V_KRISOFP",
       P."VNDAT"               AS "V_VNDAT",
       -- Antrag
       A."ANTRAGSDATUM"        AS "V_ANTRAGSDATUM",
       A."ANTRAGSTYP"          AS "V_ANTRAGSTYP",
       A."GESKOSTEN"           AS "V_GESKOSTEN",
       A."ZWFKOSTEN"           AS "V_ZWFKOSTEN",
       A."A_SU_Z"              AS "V_A_SU_Z",
       A."A_SU_D"              AS "V_A_SU_D",
       A."A_SU_K"              AS "V_A_SU_K",
       A."B_VOR_SU_Z"          AS "V_B_VOR_SU_Z",
       A."B_VOR_SU_D"          AS "V_B_VOR_SU_D",
       A."B_VOR_SU_K"          AS "V_B_VOR_SU_K",
       -- Bewilligung
       B."ID"                  AS "V_ID",
       B."ANT_ID"              AS "V_ANT_ID",
       B."BDATUM"              AS "V_BDATUM",
       B."AFSATZ"              AS "V_AFSATZ",
       B."BFSATZ"              AS "V_BFSATZ",
       B."BZWFKOSTEN"          AS "V_BZWFKOSTEN",
       B."BZUWENDUNG_Z"        AS "V_BZUWENDUNG_Z",
       B."BZUWENDUNG_D"        AS "V_BZUWENDUNG_D",
       B."BZUWENDUNG_K"        AS "V_BZUWENDUNG_K",
       B."BZUWART"             AS "V_BZUWART",
       B."BAKTENZEICHEN"       AS "V_BAKTENZEICHEN",
       B."GESZUWENDUNGEN"      AS "V_GESZUWENDUNGEN",
       B."GESKONNEX"           AS "V_GESKONNEX",
       B."KRW"                 AS "V_KRW",
       B."NOTIZEN"             AS "V_NOTIZEN",
       -- SIEDLUNGSGEBIET BAUPROGRAMM
       P."SGT_SIEDLUNGSGEBIET" AS "V_SGT_SIEDLUNGSGEBIET",
       I."BEZEICHNUNG"         AS "V_SGT_BEZEICHNUNG",
       P."BPG_BAUPROGRAMM"     AS "V_BPG_BAUPROGRAMM",
       O."BEZEICHNUNG"         AS "V_BPG_BEZEICHNUNG"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_ANTRAEGE" A ON B."ANT_ID" = A."ID"
         LEFT JOIN "FP_BAUPROGRAMME" O ON P."BPG_BAUPROGRAMM" = O."BAUPROGRAMM"
         LEFT JOIN "FP_SIEDLUNGSGEBIETE" I ON P."SGT_SIEDLUNGSGEBIET" = I."SIEDLUNGSGEBIET"
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN1
--------------------------------------------------------

CREATE VIEW "FP_V_CHECKLISTEN1" AS
SELECT '1 Offene Projekte ohne Förderantrag' AS "V_HINWEIS",
       P."PROJNR"                            AS "V_PROJNR",
       P."PSTRASSE"                          AS "V_PSTRASSE",
       NULL                                  AS "V_INFO"
FROM "FP_PROJEKTE" P
WHERE P."VNDAT" IS NULL
  AND NOT EXISTS (SELECT 1 FROM "FP_ANTRAEGE" A WHERE P."PROJNR" = A."PRO_PROJNR")

UNION ALL

SELECT '2 Offene Projekte mit Anträgen auf Unbedenklichkeit ohne Förderantrag',
       P."PROJNR",
       P."PSTRASSE",
       'Unbedenk. am: ' || TO_CHAR(A."UNBEDDAT", 'DD.MM.YYYY')
FROM "FP_ANTRAEGE" A
         JOIN "FP_PROJEKTE" P ON A."PRO_PROJNR" = P."PROJNR"
WHERE A."UNBEDDAT" IS NOT NULL
  AND A."ANTRAGSDATUM" IS NULL
  AND P."VNDAT" IS NULL

UNION ALL

SELECT '3 Offene Projekte und ausstehende Genehmigungen zum vorzeitigen Baubeginn',
       P."PROJNR",
       P."PSTRASSE",
       'Vorz. Baubeginn: ' || A."VORZBEG"
FROM "FP_ANTRAEGE" A
         JOIN "FP_PROJEKTE" P ON P."PROJNR" = A."PRO_PROJNR"
WHERE P."VNDAT" IS NULL
  AND A."VORZBEG" iS TRUE
  AND A."VBDATUM" IS NULL

UNION ALL

SELECT '4 Offene Projekte und ausstehende Bewilligungen (ohne Datum)',
       P."PROJNR",
       P."PSTRASSE",
       'Bewilligt am: ' || TO_CHAR(B."BDATUM", 'DD.MM.YYYY')
FROM "FP_PROJEKTE" P
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
WHERE P."VNDAT" IS NULL
  AND B."BDATUM" IS NULL

UNION ALL

SELECT '5 Offene Projekte und Bewilligungen ohne Verknüpfung zu einem Antrag',
       P."PROJNR",
       P."PSTRASSE",
       NULL
FROM "FP_PROJEKTE" P
         JOIN "FP_BEWILLIGUNGEN" B ON B."PRO_PROJNR" = P."PROJNR"
WHERE P."VNDAT" IS NULL
  AND B."ANT_ID" IS NULL

UNION ALL

SELECT '6 Offene Projekte und Abrufe ohne Verknüpfung zu einer Bewilligung',
       P."PROJNR",
       P."PSTRASSE",
       NULL
FROM "FP_PROJEKTE" P
         JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
WHERE A."BWI_ID" IS NULL
  AND P."VNDAT" IS NULL

UNION ALL

SELECT '7 Offene Projekte und leere Bewilligungen',
       P."PROJNR",
       P."PSTRASSE",
       'Leer angelegt am: ' || TO_CHAR(B."ANLAGEDATUM", 'DD.MM.YYYY')
FROM "FP_PROJEKTE" P
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
WHERE P."VNDAT" IS NULL
  AND B."BDATUM" IS NULL
  AND COALESCE(B."AFSATZ", 0) = 0
  AND COALESCE(B."BFSATZ", 0) = 0
  AND COALESCE(B."BZWFKOSTEN", 0) = 0
  AND COALESCE(B."BZUWENDUNG_Z", 0) = 0
  AND COALESCE(B."BZUWENDUNG_D", 0) = 0
  AND COALESCE(B."BZUWENDUNG_K", 0) = 0
  AND B."BZUWART" IS NULL
  AND B."BAKTENZEICHEN" IS NULL
  AND COALESCE(B."GESZUWENDUNGEN", 0) = 0
  AND COALESCE(B."GESKONNEX", 0) = 0
  AND B."KRW" IS NULL
  AND B."AENDERUNGSDATUM" IS NULL
  AND B."AENDERUNGVON" IS NULL

UNION ALL

SELECT '8 Projekte mit VN-Datum ab 1.1.2000 und leere Bewilligungen',
       P."PROJNR",
       P."PSTRASSE",
       'Leer angelegt am: ' || TO_CHAR(B."ANLAGEDATUM", 'DD.MM.YYYY')
FROM "FP_PROJEKTE" P
         JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
WHERE P."VNDAT" > TO_DATE('01.01.2000', 'dd.mm.yyyy')
  AND B."BDATUM" IS NULL
  AND COALESCE(B."AFSATZ", 0) = 0
  AND COALESCE(B."BFSATZ", 0) = 0
  AND COALESCE(B."BZWFKOSTEN", 0) = 0
  AND COALESCE(B."BZUWENDUNG_Z", 0) = 0
  AND COALESCE(B."BZUWENDUNG_D", 0) = 0
  AND COALESCE(B."BZUWENDUNG_K", 0) = 0
  AND B."BZUWART" IS NULL
  AND B."BAKTENZEICHEN" IS NULL
  AND COALESCE(B."GESZUWENDUNGEN", 0) = 0
  AND COALESCE(B."GESKONNEX", 0) = 0
  AND B."KRW" IS NULL
  AND B."AENDERUNGSDATUM" IS NULL
  AND B."AENDERUNGVON" IS NULL

UNION ALL

SELECT '9 Projekte mit VN-Datum ab 1.1.2000 aber ohne Schlußbescheid',
       P."PROJNR",
       P."PSTRASSE",
       'VN: ' || TO_CHAR(P."VNDAT", 'DD.MM.YYYY')
FROM "FP_PROJEKTE" P
WHERE P."VNDAT" > TO_DATE('01.01.2000', 'DD.MM.YYYY')
  AND P."VNSCHLUSSBEW" IS NULL

UNION ALL

SELECT '10 Projekte mit VN-Datum ab 1.1.2000 aber ohne Endkosten',
       P."PROJNR",
       P."PSTRASSE",
       'VN: ' || TO_CHAR(P."VNDAT", 'DD.MM.YYYY')
FROM "FP_PROJEKTE" P
WHERE P."VNDAT" > TO_DATE('01.01.2000', 'DD.MM.YYYY')
  AND P."VNKOSTEN" IS NULL

ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN2
--------------------------------------------------------

CREATE VIEW "FP_V_CHECKLISTEN2" AS
SELECT '1 Verwendungsnachweise ohne VN Datum'                                        AS "V_FEHLER",
       P."PROJNR"                                                                    AS "V_PROJNR",
       P."PSTRASSE"                                                                  AS "V_PSTRASSE",
       'VN ZWF KOSTEN: ' || P."VNZWFKOSTEN" || '  VN Gesamtkosten: ' || P."VNKOSTEN" AS "V_INFO"
FROM "FP_PROJEKTE" P
WHERE P."VNDAT" IS NULL
  AND (P."VNZWFKOSTEN" > 0 OR P."VNKOSTEN" > 0)

UNION ALL

SELECT '2 Erfolgte Bewilligungen ohne Bewilligungsdatum',
       P."PROJNR",
       P."PSTRASSE",
       'Summe Zuwendungen: ' || TO_CHAR(B."BZUWENDUNG_Z" + B."BZUWENDUNG_D" + B."BZUWENDUNG_K")
FROM "FP_BEWILLIGUNGEN" B
         JOIN "FP_PROJEKTE" P ON B."PRO_PROJNR" = P."PROJNR"
WHERE B."BDATUM" IS NULL
  AND (B."BZUWENDUNG_Z" > 0 OR B."BZUWENDUNG_D" > 0 OR B."BZUWENDUNG_K" > 0)

UNION ALL

SELECT '3 Erfolgte Abrufe ohne Abrufdatum',
       P."PROJNR",
       P."PSTRASSE",
       'Summe Abrufe: ' || TO_CHAR(A."ABRUF_Z" + A."ABRUF_D" + A."ABRUF_K")
FROM "FP_ABRUFE" A
         JOIN "FP_PROJEKTE" P ON A."PRO_PROJNR" = P."PROJNR"
WHERE A."ABRUF_DATUM" IS NULL
  AND (A."ABRUF_Z" > 0 OR A."ABRUF_D" > 0 OR A."ABRUF_K" > 0)

UNION ALL

SELECT '4 Erhaltene Abrufe ohne Erhaltdatum',
       P."PROJNR",
       P."PSTRASSE",
       'Summe erhalten: ' || TO_CHAR(A."ERH_Z" + A."ERH_D" + A."ERH_K")
FROM "FP_ABRUFE" A
         JOIN "FP_PROJEKTE" P ON A."PRO_PROJNR" = P."PROJNR"
WHERE A."ERH_DATUM" IS NULL
  AND (A."ERH_Z" > 0 OR A."ERH_D" > 0 OR A."ERH_K" > 0)

UNION ALL

SELECT '5 Anträge ohne Antragsdatum und ohne Unbedenklichkeitsantrag',
       P."PROJNR",
       P."PSTRASSE",
       NULL
FROM "FP_ANTRAEGE" A
         JOIN "FP_PROJEKTE" P ON A."PRO_PROJNR" = P."PROJNR"
WHERE A."UNBEDDAT" IS NULL
  AND A."ANTRAGSDATUM" IS NULL
  AND P."VNDAT" IS NULL

ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_EUINFORMATIONEN
--------------------------------------------------------

CREATE VIEW "FP_V_EUINFORMATIONEN" AS
SELECT E."JAHR"         AS "V_JAHR",
       E."HEFTA"        AS "V_HEFTA",
       E."NUMMER"       AS "V_NUMMER",
       E."SEITENNR"     AS "V_SEITENNR",
       E."PUB_KURZFORM" AS "V_PUB_KURZFORM",
       P."BEZEICHNUNG"  AS "V_BEZEICHNUNG",
       E."INHALT"       AS "V_INHALT",
       E."INFODAT"      AS "V_INFODAT",
       1                AS "V_REFID",
       'RAW'            AS "V_REFBEZ"
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."RAWI" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       2,
       'RBS'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."SCHULREF" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       3,
       'Sozref R 5'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."SOZREF_R_5" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       4,
       'RGU 11'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."RGU_11" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       5,
       'RGU Cs'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."RGU_CS" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       6,
       'Krh'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."KRH" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       7,
       'AWB'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."AFA" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       8,
       'SWM'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."SWM" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       9,
       'Kulturreferat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."KULTURREF" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       10,
       'Baureferat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."BAUREF" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       11,
       'Planungsreferat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."PLANREF" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       12,
       'Direktorium'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."DIREKTORIUM" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       13,
       'Personal- und Org.referat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."POR" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       14,
       'Kreisverwaltungsreferat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."KVR" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       15,
       'Kommunalreferat'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."KOMMREF" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       16,
       'MSE'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."SEW" IS TRUE

UNION ALL

SELECT E."JAHR",
       E."HEFTA",
       E."NUMMER",
       E."SEITENNR",
       E."PUB_KURZFORM",
       P."BEZEICHNUNG",
       E."INHALT",
       E."INFODAT",
       17,
       'Stk'
FROM "FP_EUINFORMATIONEN" E
         JOIN "FP_PUBLIKATIONEN" P ON E."PUB_KURZFORM" = P."KURZFORM"
WHERE E."STK" IS TRUE
;
--------------------------------------------------------
--  DDL for View FP_V_FAGSTAT
--------------------------------------------------------

CREATE VIEW "FP_V_FAGSTAT" AS
SELECT "X1"                   AS "F_FB",
       "X2"                   AS "F_BEZEICHNUNG",
       "X3"                   AS "F_JAHR",
       SUM(COALESCE("X4", 0)) AS "B_BEWILL",
       SUM(COALESCE("X5", 0)) AS "B_RUECK",
       SUM(COALESCE("X6", 0)) AS "E_ERHALTEN",
       SUM(COALESCE("X7", 0)) AS "E_RUECK"
FROM
    -- bewilligter Zuschuss ohne Rückzahlungen
    (SELECT F."FB"                                 AS "X1",
            F."BEZEICHNUNG"                        AS "X2",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')) AS "X3",
            SUM(COALESCE(B."BZUWENDUNG_Z", 0))     AS "X4",
            0                                      AS "X5",
            0                                      AS "X6",
            0                                      AS "X7"
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_Z" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     -- bewilligtes Darlehen ohne Rückzahlungen
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
            SUM(COALESCE(B."BZUWENDUNG_D", 0)),
            0,
            0,
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_D" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     -- bewilligte Kostenerstattung ohne Rückzahlungen
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
            SUM(COALESCE(B."BZUWENDUNG_K", 0)),
            0,
            0,
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_K" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     --  Bewilligter Zuschuss und rückgefordert
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
            0,
            SUM(COALESCE(B."BZUWENDUNG_Z", 0)),
            0,
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_Z" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     --  Bewilligtes Darlehen und rückgefordert
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
            0,
            SUM(COALESCE(B."BZUWENDUNG_D", 0)),
            0,
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_D" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     --  Bewilligte Kostenerstattung und rückgefordert
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
            0,
            SUM(COALESCE(B."BZUWENDUNG_K", 0)),
            0,
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
     WHERE B."BDATUM" IS NOT NULL
       AND B."BZUWENDUNG_K" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

     UNION

     -- Erhalten Zuschuss ohne Rückzahlung
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            SUM(A."ERH_Z"),
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"

     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_Z" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

     UNION

     -- erhalten Darlehen ohne Rückzahlung
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            SUM(A."ERH_D"),
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_D" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

     UNION

     -- erhalten Kostenerstattung ohne Rückzahlung
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            SUM(A."ERH_K"),
            0
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_K" >= 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

     UNION

     -- Erhalten Rückzahlung von Zuschüssen
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            0,
            SUM(A."ERH_Z")
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_Z" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

     UNION

     -- erhalten Rückzahlung von Darlehen
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            0,
            SUM(A."ERH_D")
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_D" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

     UNION

     -- erhalten Rückzahlung von Kostenerstattungen
     SELECT F."FB",
            F."BEZEICHNUNG",
            TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
            0,
            0,
            0,
            SUM(A."ERH_K")
     FROM "FP_PROJEKTE" P
              JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
              JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
     WHERE A."ERH_DATUM" IS NOT NULL
       AND A."ERH_K" < 0
     GROUP BY F."FB",
              F."BEZEICHNUNG",
              TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))) AS X

GROUP BY "X1",
         "X2",
         "X3"
;
--------------------------------------------------------
--  DDL for View FP_V_FBSTAT
--------------------------------------------------------

CREATE VIEW "FP_V_FBSTAT" AS
SELECT "X1"                   AS "F_FB",
       "X2"                   AS "F_BEZEICHNUNG",
       "X3"                   AS "F_JAHR",
       SUM(COALESCE("X4", 0)) AS "B_BZUWENDUNG_Z",
       SUM(COALESCE("X5", 0)) AS "B_BZUWENDUNG_D",
       SUM(COALESCE("X6", 0)) AS "B_BZUWENDUNG_K",
       SUM(COALESCE("X7", 0)) AS "A_ERH_Z",
       SUM(COALESCE("X8", 0)) AS "A_ERH_D",
       SUM(COALESCE("X9", 0)) AS "A_ERH_K"
FROM (SELECT F."FB"                                 AS "X1",
             F."BEZEICHNUNG"                        AS "X2",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')) AS "X3",
             SUM(COALESCE(B."BZUWENDUNG_Z", 0))     AS "X4",
             SUM(COALESCE(B."BZUWENDUNG_D", 0))     AS "X5",
             SUM(COALESCE(B."BZUWENDUNG_K", 0))     AS "X6",
             NULL                                   AS "X7",
             NULL                                   AS "X8",
             NULL                                   AS "X9"
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(A."ERH_Z", 0)),
             SUM(COALESCE(A."ERH_D", 0)),
             SUM(COALESCE(A."ERH_K", 0))
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ERH_DATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))) as X
GROUP BY "X1",
         "X2",
         "X3"
;
--------------------------------------------------------
--  DDL for View FP_V_ISTKOSTENMAX
--------------------------------------------------------

CREATE VIEW "FP_V_ISTKOSTENMAX" AS
SELECT "X1"               AS "P_PROJNR",
       "X2"               AS "P_MAX_ISTDATUM",
       SUBSTR("X2", 1, 4) AS "P_JAHR",
       SUBSTR("X2", 5, 2) AS "P_MONAT",
       "X3"               AS "P_ISTKOSTEN"
FROM (SELECT I1."PRO_PROJNR"                              AS "X1",
             TO_CHAR(I1."JAHR" || LPAD(I1."MONAT", 2, 0)) AS "X2",
             I1."ISTKOSTEN"                               AS "X3"
      FROM "FP_PROJEKTISTKOSTEN" I1
      WHERE TO_CHAR(I1."JAHR" || LPAD(I1."MONAT", 2, 0)) =
            (SELECT MAX(TO_CHAR(I2."JAHR" || LPAD(I2."MONAT", 2, 0)))
             FROM "FP_PROJEKTISTKOSTEN" I2
             WHERE I2."PRO_PROJNR" = I1."PRO_PROJNR")) AS X
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK1
--------------------------------------------------------

CREATE VIEW "FP_V_JAHRESSTATISTIK1" AS
SELECT "X1"                    AS "F_FB",
       "X2"                    AS "F_BEZEICHNUNG",
       "X3"                    AS "F_JAHR",
       SUM(COALESCE("X4", 0))  AS "B_BZUWENDUNG_Z",
       SUM(COALESCE("X5", 0))  AS "B_BZUWENDUNG_D",
       SUM(COALESCE("X6", 0))  AS "B_BZUWENDUNG_K",
       SUM(COALESCE("X7", 0))  AS "A_ERH_Z",
       SUM(COALESCE("X8", 0))  AS "A_ERH_D",
       SUM(COALESCE("X9", 0))  AS "A_ERH_K",
       SUM(COALESCE("X10", 0)) AS "E_GESKOSTEN",
       SUM(COALESCE("X11", 0)) AS "E_ZWFKOSTEN"
FROM (SELECT F."FB"                                 AS "X1",
             F."BEZEICHNUNG"                        AS "X2",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')) AS "X3",
             SUM(COALESCE(B."BZUWENDUNG_Z", 0))     AS "X4",
             SUM(COALESCE(b."BZUWENDUNG_D", 0))     AS "X5",
             SUM(COALESCE(b."BZUWENDUNG_K", 0))     AS "X6",
             NULL                                   AS "X7",
             NULL                                   AS "X8",
             NULL                                   AS "X9",
             NULL                                   AS "X10",
             NULL                                   AS "X11"
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(A."ERH_Z", 0)),
             SUM(COALESCE(A."ERH_D", 0)),
             SUM(COALESCE(A."ERH_K", 0)),
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ERH_DATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(A."GESKOSTEN", 0)),
             SUM(COALESCE(A."ZWFKOSTEN", 0))
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ANTRAGSDATUM" =
            (SELECT MIN(Y."ANTRAGSDATUM")
             FROM "FP_ANTRAEGE" Y
             WHERE Y."PRO_PROJNR" = P."PROJNR"
               AND Y."ANTRAGSTYP" = 'E')
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY'))) as X
GROUP BY "X1",
         "X2",
         "X3"
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK2
--------------------------------------------------------

CREATE VIEW "FP_V_JAHRESSTATISTIK2" AS
SELECT "X1"                    AS "F_FB",
       "X2"                    AS "F_BEZEICHNUNG",
       "X3"                    AS "F_JAHR",
       SUM(COALESCE("X4", 0))  AS "V_ANZAHL_ABRUFE",
       SUM(COALESCE("X5", 0))  AS "V_ANZAHL_VN",
       SUM(COALESCE("X6", 0))  AS "V_ANZAHL_BEWILLIGUNGEN",
       SUM(COALESCE("X7", 0))  AS "V_VNGESKOSTEN",
       SUM(COALESCE("X8", 0))  AS "V_ANZAHL_ERST",
       SUM(COALESCE("X9", 0))  AS "V_A_SU_Z_ERST",
       SUM(COALESCE("X10", 0)) AS "V_A_SU_K_ERST",
       SUM(COALESCE("X11", 0)) AS "V_ANZAHL_FOLGE",
       SUM(COALESCE("X12", 0)) AS "V_A_SU_Z_FOLGE",
       SUM(COALESCE("X13", 0)) AS "V_A_SU_K_FOLGE",
       SUM(COALESCE("X14", 0)) AS "V_A_VOR_SU_Z_GESAMT",
       SUM(COALESCE("X15", 0)) AS "V_A_VOR_SU_K_GESAMT",
       SUM(COALESCE("X16", 0)) AS "V_ANZAHL_UNBED"
FROM (SELECT F."FB"                                      AS "X1",
             F."BEZEICHNUNG"                             AS "X2",
             TO_NUMBER(TO_CHAR(A."ABRUF_DATUM", 'YYYY')) AS "X3",
             COUNT(*)                                    AS "X4",
             NULL                                        AS "X5",
             NULL                                        AS "X6",
             NULL                                        AS "X7",
             NULL                                        AS "X8",
             NULL                                        AS "X9",
             NULL                                        AS "X10",
             NULL                                        AS "X11",
             NULL                                        AS "X12",
             NULL                                        AS "X13",
             NULL                                        AS "X14",
             NULL                                        AS "X15",
             NULL                                        AS "X16"
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ABRUF_DATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(P."VNDAT", 'YYYY')),
             NULL,
             COUNT(*),
             NULL,
             SUM(P."VNKOSTEN"),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
      WHERE P."VNDAT" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(P."VNDAT", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             NULL,
             COUNT(*),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             COUNT(*),
             SUM(A."A_SU_Z"),
             SUM(A."A_SU_K"),
             NULL,
             NULL,
             NULL,
             SUM(A."B_VOR_SU_Z"),
             SUM(A."B_VOR_SU_K"),
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ANTRAGSDATUM" =
            (SELECT MIN(Y."ANTRAGSDATUM")
             FROM "FP_ANTRAEGE" Y
             WHERE Y."PRO_PROJNR" = P."PROJNR"
               AND Y."ANTRAGSTYP" = 'E')
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             COUNT(*),
             SUM(A."A_SU_Z"),
             SUM(A."A_SU_K"),
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ANTRAGSTYP" = 'F' -- nicht A und nicht V
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ANTRAGSDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."UNBEDDAT", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             COUNT(*)
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."UNBEDDAT" IS NOT NULL
        AND A."ANTRAGSTYP" = 'E'
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."UNBEDDAT", 'YYYY'))) as X

GROUP BY "X1",
         "X2",
         "X3"
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK3
--------------------------------------------------------

CREATE VIEW "FP_V_JAHRESSTATISTIK3" AS
SELECT "X1"                    AS "F_FB",
       "X2"                    AS "F_BEZEICHNUNG",
       "X3"                    AS "F_JAHR",
       SUM(COALESCE("X4", 0))  AS "V_BZUWENDUNG_Z_PLUS",
       SUM(COALESCE("X5", 0))  AS "V_BZUWENDUNG_D_PLUS",
       SUM(COALESCE("X6", 0))  AS "V_BZUWENDUNG_K_PLUS",
       SUM(COALESCE("X7", 0))  AS "V_BZUWENDUNG_Z_MINUS",
       SUM(COALESCE("X8", 0))  AS "V_BZUWENDUNG_D_MINUS",
       SUM(COALESCE("X9", 0))  AS "V_BZUWENDUNG_K_MINUS",
       SUM(COALESCE("X10", 0)) AS "V_ERH_Z",
       SUM(COALESCE("X11", 0)) AS "V_ERH_D",
       SUM(COALESCE("X12", 0)) AS "V_ERH_K"
FROM (SELECT F."FB"                                 AS "X1",
             F."BEZEICHNUNG"                        AS "X2",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')) AS "X3",
             SUM(COALESCE(B."BZUWENDUNG_Z", 0))     AS "X4",
             NULL                                   AS "X5",
             NULL                                   AS "X6",
             NULL                                   AS "X7",
             NULL                                   AS "X8",
             NULL                                   AS "X9",
             NULL                                   AS "X10",
             NULL                                   AS "X11",
             NULL                                   AS "X12"
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_Z" >= 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             SUM(COALESCE(B."BZUWENDUNG_D", 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_D" >= 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             NULL,
             SUM(COALESCE(B."BZUWENDUNG_K", 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_K" >= 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(B."BZUWENDUNG_Z", 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_Z" < 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(B."BZUWENDUNG_D", 0)),
             NULL,
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_D" < 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(B."BZUWENDUNG_K", 0)),
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR"
      WHERE B."BDATUM" IS NOT NULL
        AND B."BZUWENDUNG_K" < 0
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(B."BDATUM", 'YYYY'))

      UNION

      SELECT F."FB",
             F."BEZEICHNUNG",
             TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY')),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(A."ERH_Z", 0)),
             SUM(COALESCE(A."ERH_D", 0)),
             SUM(COALESCE(A."ERH_K", 0))
      FROM "FP_PROJEKTE" P
               JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
               JOIN "FP_ABRUFE" A ON P."PROJNR" = A."PRO_PROJNR"
      WHERE A."ERH_DATUM" IS NOT NULL
      GROUP BY F."FB",
               F."BEZEICHNUNG",
               TO_NUMBER(TO_CHAR(A."ERH_DATUM", 'YYYY'))) as X

GROUP BY "X1",
         "X2",
         "X3"
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTERSTANTRAG
--------------------------------------------------------

CREATE VIEW "FP_V_PROJEKTERSTANTRAG" AS
SELECT P."PROJNR"        AS "P_PROJNR",
       P."FOB_FB"        AS "P_FOB_FB",
       P."PNAME"         AS "P_PNAME",
       P."PSTRASSE"      AS "P_PSTRASSE",
       A1."ANTRAGSTYP"   AS "A1_ANTRAGSTYP",
       A1."VBDATUM"      AS "A1_VBDATUM",
       A1."VORZBEG"      AS "A1_VORZBEG",
       P."VNDAT"         AS "P_VNDAT",
       A1."GESKOSTEN"    AS "A1_GESKOSTEN",
       P."VNKOSTEN"      AS "P_VNGESKOSTEN",
       P."VNPRUEFZWF"    AS "P_VNPRUEFZWF",
       P."VNPRUEFDAT"    AS "P_VNPRUEFDAT",
       P."ZINSEN"        AS "P_ZINSEN",
       A1."ANTRAGSDATUM" AS "A1_ANTRAGSDATUM",
       A1."ZWFKOSTEN"    AS "A1_ZWFKOSTEN",
       A1."A_SU_Z"       AS "A1_A_SU_Z",
       A1."A_SU_D"       AS "A1_A_SU_D",
       A1."A_SU_K"       AS "A1_A_SU_K",
       A1."B_VOR_SU_Z"   AS "A1_B_VOR_SU_Z",
       A1."B_VOR_SU_D"   AS "A1_B_VOR_SU_D",
       A1."B_VOR_SU_K"   AS "A1_B_VOR_SU_K",
       A1."NOTIZEN"      AS "A1_NOTIZEN"
FROM "FP_PROJEKTE" P
         JOIN "FP_ANTRAEGE" A1 ON P."PROJNR" = A1."PRO_PROJNR"
WHERE A1."ANTRAGSTYP" IN ('E', 'A')
  AND A1."ID" = (SELECT MAX("ID")
                 FROM "FP_ANTRAEGE" Y
                 WHERE Y."PRO_PROJNR" = P."PROJNR"
                   AND Y."ANTRAGSTYP" IN ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTFLUESSE
--------------------------------------------------------

CREATE VIEW "FP_V_PROJEKTFLUESSE" AS
SELECT "X1"                    AS "P_PROJNR",
       SUM(COALESCE("X2", 0))  AS "AX_A_SU_Z",
       SUM(COALESCE("X3", 0))  AS "AX_A_SU_D",
       SUM(COALESCE("X4", 0))  AS "AX_A_SU_K",
       SUM(COALESCE("X5", 0))  AS "R_ERH_Z",
       SUM(COALESCE("X6", 0))  AS "R_ERH_D",
       SUM(COALESCE("X7", 0))  AS "R_ERH_K",
       SUM(COALESCE("X8", 0))  AS "B_BEWILL_Z",
       SUM(COALESCE("X9", 0))  AS "B_BEWILL_D",
       SUM(COALESCE("X10", 0)) AS "B_BEWILL_K"
FROM (SELECT P."PROJNR"              AS "X1",
             COALESCE(A."A_SU_Z", 0) AS "X2",
             COALESCE(A."A_SU_D", 0) AS "X3",
             COALESCE(A."A_SU_K", 0) AS "X4",
             NULL                    AS "X5",
             NULL                    AS "X6",
             NULL                    AS "X7",
             NULL                    AS "X8",
             NULL                    AS "X9",
             NULL                    AS "X10"
      FROM "FP_PROJEKTE" P
               JOIN "FP_ANTRAEGE" A ON P."PROJNR" = A."PRO_PROJNR"

      UNION ALL

      SELECT P."PROJNR",
             NULL,
             NULL,
             NULL,
             COALESCE(R."ERH_Z", 0),
             COALESCE(R."ERH_D", 0),
             COALESCE(R."ERH_K", 0),
             NULL,
             NULL,
             NULL
      FROM "FP_PROJEKTE" P
               JOIN "FP_ABRUFE" R ON P."PROJNR" = R."PRO_PROJNR"

      UNION ALL

      SELECT P."PROJNR",
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             COALESCE(B."BZUWENDUNG_Z", 0),
             COALESCE(B."BZUWENDUNG_D", 0),
             COALESCE(B."BZUWENDUNG_K", 0)
      FROM "FP_PROJEKTE" P
               JOIN "FP_BEWILLIGUNGEN" B ON P."PROJNR" = B."PRO_PROJNR") as X
GROUP BY "X1"
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSTAT
--------------------------------------------------------

CREATE VIEW "FP_V_PROJEKTSTAT" AS
SELECT A."P_PROJNR"        AS "P_PROJNR",
       A."P_FOB_FB"        AS "P_FOB_FB",
       A."P_PNAME"         AS "P_PNAME",
       A."P_PSTRASSE"      AS "P_PSTRASSE",
       A."A1_ANTRAGSTYP"   AS "A1_ANTRAGSTYP",
       A."A1_VBDATUM"      AS "A1_VBDATUM",
       A."A1_VORZBEG"      AS "A1_VORZBEG",
       A."P_VNDAT"         AS "P_VNDAT",
       A."A1_GESKOSTEN"    AS "A1_GESKOSTEN",
       A."P_VNGESKOSTEN"   AS "P_VNGESKOSTEN",
       A."P_VNPRUEFZWF"    AS "P_VNPRUEFZWF",
       A."P_VNPRUEFDAT"    AS "P_VNPRUEFDAT",
       A."P_ZINSEN"        AS "P_ZINSEN",
       A."A1_ANTRAGSDATUM" AS "A1_ANTRAGSDATUM",
       A."A1_ZWFKOSTEN"    AS "A1_ZWFKOSTEN",
       A."A1_A_SU_Z"       AS "A1_A_SU_Z",
       A."A1_A_SU_D"       AS "A1_A_SU_D",
       A."A1_A_SU_K"       AS "A1_A_SU_K",
       A."A1_B_VOR_SU_Z"   AS "A1_B_VOR_SU_Z",
       A."A1_B_VOR_SU_D"   AS "A1_B_VOR_SU_D",
       A."A1_B_VOR_SU_K"   AS "A1_B_VOR_SU_K",
       A."A1_NOTIZEN"      AS "A1_NOTIZEN",
       F."AX_A_SU_Z"       AS "AX_A_SU_Z",
       F."AX_A_SU_D"       AS "AX_A_SU_D",
       F."AX_A_SU_K"       AS "AX_A_SU_K",
       F."R_ERH_Z"         AS "R_ERH_Z",
       F."R_ERH_D"         AS "R_ERH_D",
       F."R_ERH_K"         AS "R_ERH_K",
       F."B_BEWILL_Z"      AS "B_BEWILL_Z",
       F."B_BEWILL_D"      AS "B_BEWILL_D",
       F."B_BEWILL_K"      AS "B_BEWILL_K"
FROM "FP_V_PROJEKTERSTANTRAG" A
         JOIN "FP_V_PROJEKTFLUESSE" F ON A."P_PROJNR" = F."P_PROJNR"
;
--------------------------------------------------------
--  DDL for View FP_V_KINDER
--------------------------------------------------------

CREATE VIEW "FP_V_KINDER" AS
SELECT P."PROJNR"          AS "P_PROJNR",
       P."FOB_FB"          AS "P_FOB_FB",
       F."BEZEICHNUNG"     AS "P_FOB_BEZEICHNUNG",
       P."BEZ_STADTBEZIRK" AS "P_BEZ_STADTBEZIRK",
       Z."BEZEICHNUNG"     AS "P_STADTBEZIRK",
       P."UAS_UA"          AS "P_UAS_UA",
       P."KUR_KURZBEZ"     AS "P_KUR_KURZBEZ",
       P."PNAME"           AS "P_PNAME",
       P."PSTRASSE"        AS "P_PSTRASSE",
       P."VNDAT"           AS "P_VNDAT",
       P."VNKOSTEN"        AS "P_VNKOSTEN",
       P."KRISOFP"         AS "P_KRISOFP",
       P."KRIPPLATZ"       AS "P_KRIPPLATZ",
       P."KIGAPLATZ"       AS "P_KIGAPLATZ",
       P."HORTPLATZ"       AS "P_HORTPLATZ",
       S."A1_ANTRAGSTYP"   AS "A1_ANTRAGSTYP",
       S."A1_ANTRAGSDATUM" AS "A1_ANTRAGSDATUM",
       S."A1_GESKOSTEN"    AS "A1_GESKOSTEN",
       S."R_ERH_Z"         AS "P_ERH_Z",
       S."B_BEWILL_Z"      AS "P_BZUWENDUNG_Z"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         LEFT JOIN "FP_V_PROJEKTSTAT" S ON P."PROJNR" = S."P_PROJNR"
         LEFT JOIN "FP_STADTBEZIRKE" Z ON P."BEZ_STADTBEZIRK" = Z."STADTBEZIRK"
;
--------------------------------------------------------
--  DDL for View FP_V_MITTELEINPLANUNG
--------------------------------------------------------

CREATE VIEW "FP_V_MITTELEINPLANUNG" AS
SELECT B1."ID"             AS "B_ID",
       B1."PRO_PROJNR"     AS "B_PROJNR",
       B1."ANT_ID"         AS "B_ANT_ID",
       B1."BDATUM"         AS "B_BDATUM",
       B1."AFSATZ"         AS "B_AFSATZ",
       B1."BFSATZ"         AS "B_BFSATZ",
       B1."BZUWENDUNG_Z"   AS "B_BZUWENDUNG_Z",
       B1."BZWFKOSTEN"     AS "B_BZWFKOSTEN",
       B1."KRW"            AS "B_KRW",
       B1."BZUWART"        AS "B_ZUWART",
       B1."BAKTENZEICHEN"  AS "B_BAKTENZEICHEN",
       B1."GESZUWENDUNGEN" AS "B_GESZUWENDUNGEN",
       B1."GESKONNEX"      AS "B_GESKONNEX",
       B1."NOTIZEN"        AS "B_NOTIZEN",
       A1."ANTRAGSTYP"     AS "A_ANTRAGSTYP",
       A1."ANTRAGSDATUM"   AS "A_ANTRAGSDATUM",
       A1."GESKOSTEN"      AS "A_GESKOSTEN"
FROM "FP_BEWILLIGUNGEN" B1
         JOIN "FP_ANTRAEGE" A1 ON A1."ID" = B1."ANT_ID"
WHERE A1."ANTRAGSTYP" IN ('E', 'A')
  AND B1."ID" = (SELECT MAX(B2."ID")
                 FROM "FP_BEWILLIGUNGEN" B2
                          JOIN "FP_ANTRAEGE" A2 ON A2."ID" = B2."ANT_ID"
                 WHERE B2."PRO_PROJNR" = B1."PRO_PROJNR"
                   AND A2."ANTRAGSTYP" IN ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_NOTIZEN
--------------------------------------------------------

CREATE VIEW "FP_V_NOTIZEN" AS
SELECT X0 AS "V_TYP",
       X1 AS "V_PROJNR",
       X2 AS "V_DATUM",
       X3 AS "V_NOTIZEN"
FROM (
         -- Typ Projekt
         SELECT 'Projekt'   AS X0,
                P."PROJNR"  AS X1,
                P."VNDAT"   AS X2,
                P."NOTIZEN" AS X3
         FROM "FP_PROJEKTE" P
         WHERE P."NOTIZEN" IS NOT NULL

         UNION ALL

         -- Typ Antrag
         SELECT 'Antrag'         AS X0,
                A."PRO_PROJNR"   AS X1,
                A."ANTRAGSDATUM" AS X2,
                A."NOTIZEN"      AS X3
         FROM "FP_ANTRAEGE" A
         WHERE A."NOTIZEN" IS NOT NULL

         UNION ALL

         -- Typ Bewilligungen
         SELECT 'Bewilligung'  AS X0,
                B."PRO_PROJNR" AS X1,
                B."BDATUM"     AS X2,
                B."NOTIZEN"    AS X3
         FROM "FP_BEWILLIGUNGEN" B
         WHERE B."NOTIZEN" IS NOT NULL

         UNION

         -- Typ Abruf
         SELECT 'Abruf'         AS X0,
                R."PRO_PROJNR"  AS X1,
                R."ABRUF_DATUM" AS X2,
                R."NOTIZEN"     AS X3
         FROM "FP_ABRUFE" R
         WHERE R."NOTIZEN" IS NOT NULL

         UNION

         -- Typ Termin
         SELECT 'Termin'                                                                           AS X0,
                T."PRO_PROJNR"                                                                     AS X1,
                T."TERMIN"                                                                         AS X2,
                T."NOTIZEN" || E'\r\nZuständig: ' || T."ZUSTAENDIG" || ' Telefon: ' || T."TELEFON" AS X3
         FROM "FP_PROJEKTTERMINE" T
         WHERE T."NOTIZEN" IS NOT NULL) as subquery;
;

--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSUCHE
--------------------------------------------------------

CREATE VIEW "FP_V_PROJEKTSUCHE" AS
SELECT P."PROJNR"                               AS "V_PROJNR",
       P."VNDAT"                                AS "V_VNDAT",
       P."FOB_FB"                               AS "V_FOB_FB",
       TO_CHAR(P."VNDAT", 'YYYY')               AS "V_VNDAT_JAHR",
       TO_CHAR(P."VNDAT", 'MM')                 AS "V_VNDAT_MONAT",
       TO_CHAR(TO_DATE(P."JAHR", 'RR'), 'YYYY') AS "V_JAHR",
       P."PNAME"                                AS "V_PNAME",
       P."PSTRASSE"                             AS "V_PSTRASSE",
       P."KAUF"                                 AS "V_KAUF",
       P."PROJART"                              AS "V_PROJART",
       F."BEZEICHNUNG"                          AS "V_FOB_BEZEICHNUNG",
       P."KUR_KURZBEZ"                          AS "V_KUR_KURZBEZ",
       Z."BEZEICHNUNG"                          AS "V_KUR_BEZEICHNUNG",
       P."UAS_UA"                               AS "V_UAS_UA",
       U."BEZEICHNUNG"                          AS "V_UAS_BEZEICHNUNG",
       P."BEZ_STADTBEZIRK"                      AS "V_BEZ_STADTBEZIRK",
       S."BEZEICHNUNG"                          AS "V_BEZ_BEZEICHNUNG",
       P."KRN_KRHNAME"                          AS "V_KRN_KRHNAME",
       P."KRISOFP"                              AS "V_KRISOFP",
       T."A1_ANTRAGSTYP"                        AS "V_A1_ANTRAGSTYP",
       T."A1_ANTRAGSDATUM"                      AS "V_VNEANTRAG",
       T."B_BEWILL_Z"                           AS "V_VNBEW_Z",
       T."B_BEWILL_D"                           As "V_VNBEW_D",
       T."B_BEWILL_K"                           AS "V_VNBEW_K",
       T."R_ERH_Z"                              AS "V_VNERH_Z",
       T."R_ERH_D"                              AS "V_VNERH_D",
       T."R_ERH_K"                              AS "V_VNERH_K",
       T."A1_GESKOSTEN"                         AS "V_VNGESKOSTEN",
       T."A1_ZWFKOSTEN"                         AS "V_ZWFKOSTEN",
       P."VNGESAMTZUWENDUNG"                    AS "V_VNGESAMTZUWENDUNG",
       P."VNKOSTEN"                             AS "V_VNKOSTEN",
       P."VNZWFKOSTEN"                          AS "V_VNZWFKOSTEN",
       P."VNPRUEFDAT"                           AS "V_VNPRUEFDAT",
       P."VNRUECK_Z"                            AS "V_VNRUECK_Z",
       P."VNNACHFOERDERUNG"                     AS "V_VNNACHFOERDERUNG",
       P."VNPRUEFZWF"                           AS "V_VNPRUEFZWF",
       P."VNSCHLUSSZWF"                         AS "V_VNSCHLUSSZWF",
       P."VNSCHLUSSBEW"                         AS "V_VNSCHLUSSBEW",
       P."NOTIZEN"                              AS "V_NOTIZEN",
       P."FIPO"                                 AS "V_FIPO",
       P."BUCHUNGSKREIS"                        AS "V_BUCHUNGSKREIS",
       P."SACHKONTO"                            AS "V_SACHKONTO",
       P."FIPO_K"                               AS "V_FIPO_K",
       P."BUCHUNGSKREIS_K"                      AS "V_BUCHUNGSKREIS_K",
       P."SACHKONTO_K"                          AS "V_SACHKONTO_K",
       P."KRIPPLATZ"                            AS "V_KRIPPLATZ",
       P."KIGAPLATZ"                            AS "V_KIGAPLATZ",
       P."HORTPLATZ"                            AS "V_HORTPLATZ",
       P."PSBAUBUCH"                            AS "V_PSBAUBUCH",
       P."PSBAUREF"                             AS "V_PSBAUREF",
       P."PSBAUNR"                              AS "V_PSBAUNR",
       P."SGT_SIEDLUNGSGEBIET"                  AS "V_SGT_SIEDLUNGSGEBIET",
       I."BEZEICHNUNG"                          AS "V_SGT_BEZEICHNUNG",
       P."BPG_BAUPROGRAMM"                      AS "V_BPG_BAUPROGRAMM",
       B."BEZEICHNUNG"                          AS "V_BPG_BEZEICHNUNG",
       M."B_GESZUWENDUNGEN"                     AS "V_B_GESZUWENDUNGEN",
       M."B_BZWFKOSTEN"                         AS "V_B_BZWFKOSTEN",
       P."BAUENDE"                              AS "V_BAUENDE",
       P."BAUBEENDET"                           AS "V_BAUBEENDET",
       P."BAUVERGABE1"                          AS "V_BAUVERGABE1",
       P."BAUBEGINN"                            AS "V_BAUBEGINN",
       P."BAUMITTEILUNG"                        AS "V_BAUMITTEILUNG",
       P."KREDITNUMMER"                         AS "V_KREDITNUMMER",
       P."STADTANLEIHE"                         AS "V_STADTANLEIHE",
       P."ANLEIHENENNWERT"                      AS "V_ANLEIHENENNWERT",
       P."ANLEIHEJAHRVON"                       AS "V_ANLEIHEJAHRVON",
       P."ANLEIHEJAHRBIS"                       AS "V_ANLEIHEJAHRBIS"
FROM "FP_PROJEKTE" P
         JOIN "FP_FOERDERBEREICHE" F ON P."FOB_FB" = F."FB"
         JOIN "FP_UNTERABSCHNITTE" U ON P."UAS_UA" = U."UA"
         JOIN "FP_KURZBEZEICHNUNGEN" Z ON P."KUR_KURZBEZ" = Z."KURZBEZ"
         LEFT JOIN "FP_STADTBEZIRKE" S ON P."BEZ_STADTBEZIRK" = S."STADTBEZIRK"
         LEFT JOIN "FP_V_PROJEKTSTAT" T ON P."PROJNR" = T."P_PROJNR"
         LEFT JOIN "FP_BAUPROGRAMME" B ON P."BPG_BAUPROGRAMM" = B."BAUPROGRAMM"
         LEFT JOIN "FP_SIEDLUNGSGEBIETE" I ON P."SGT_SIEDLUNGSGEBIET" = I."SIEDLUNGSGEBIET"
         LEFT JOIN "FP_V_MITTELEINPLANUNG" M ON P."PROJNR" = M."B_PROJNR"
;