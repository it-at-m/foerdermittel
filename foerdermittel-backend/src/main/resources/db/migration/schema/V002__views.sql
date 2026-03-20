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

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_ANTRAGSUCHE" ("V_ID", "V_PRO_PROJNR", "V_VNDAT", "V_FOB_FB", "V_FOB_BEZEICHNUNG", "V_PNAME", "V_PSTRASSE", "V_KAUF", "V_KUR_KURZBEZ", "V_KUR_BEZEICHNUNG", "V_UAS_UA", "V_UAS_BEZEICHNUNG", "V_BEZ_STADTBEZIRK", "V_BEZ_BEZEICHNUNG", "V_KRISOFP", "V_ANTRAGSDATUM", "V_ANTRAGSTYP", "V_GESKOSTEN", "V_ZWFKOSTEN", "V_VORZBEG", "V_VBDATUM", "V_UNBEDDAT", "V_UNBEDJA", "V_UNBEDBIS", "V_A_SU_Z", "V_A_SU_D", "V_A_SU_K", "V_B_VOR_SU_Z", "V_B_VOR_SU_D", "V_B_VOR_SU_K", "V_NOTIZEN", "V_SGT_SIEDLUNGSGEBIET", "V_SGT_BEZEICHNUNG", "V_BPG_BAUPROGRAMM", "V_BPG_BEZEICHNUNG", "V_BID", "V_BGESZUWENDUNGEN", "V_BDATUM") AS
SELECT
    --  ge�ndert Sep 2024:
    --  Sicherstellen, dass je Antrag nur ein Record geliefert wird.
    --  unabh�ngig wieviele Bewilligungen vorliegen (0 .. viele)
    --  deshalb zwei Select mit UNION.
    --
    --  1. Select: Antrag besitzt min. 1 Bewilligung --> liefert nur die j�ngste Bewilligung
    A.ID,
    A.PRO_PROJNR,
    P.VNDAT,
    P.FOB_FB,
    F.BEZEICHNUNG,
    P.PNAME,
    P.PSTRASSE,
    P.KAUF,
    P.KUR_KURZBEZ,
    Z.BEZEICHNUNG,
    P.UAS_UA,
    U.BEZEICHNUNG,
    P.BEZ_STADTBEZIRK,
    S.BEZEICHNUNG,
    P.KRISOFP,
    A.ANTRAGSDATUM,
    A.ANTRAGSTYP,
    A.GESKOSTEN,
    A.ZWFKOSTEN,
    A.VORZBEG,
    A.VBDATUM,
    A.UNBEDDAT,
    A.UNBEDJA,
    A.UNBEDBIS,
    A.A_SU_Z,
    A.A_SU_D,
    A.A_SU_K,
    A.B_VOR_SU_Z,
    A.B_VOR_SU_D,
    A.B_VOR_SU_K,
    A.NOTIZEN,
    P.SGT_SIEDLUNGSGEBIET,
    I.BEZEICHNUNG,
    P.BPG_BAUPROGRAMM,
    O.BEZEICHNUNG,
    B.ID,
    B.GESZUWENDUNGEN,
    B.BDATUM
FROM FP_PROJEKTE P,
     FP_ANTRAEGE A,
     FP_BEWILLIGUNGEN B,
     FP_FOERDERBEREICHE F,
     FP_UNTERABSCHNITTE U,
     FP_KURZBEZEICHNUNGEN Z,
     FP_STADTBEZIRKE S,
     FP_BAUPROGRAMME O,
     FP_SIEDLUNGSGEBIETE I
WHERE P.FOB_FB = F.FB
  AND P.PROJNR = A.PRO_PROJNR
  AND A.ID = B.ANT_ID
  AND B.ID = (select max(B1.ID) from FP_BEWILLIGUNGEN B1 WHERE B1.ANT_ID = A.ID)
  AND P.UAS_UA = U.UA
  AND P.KUR_KURZBEZ = Z.KURZBEZ
  AND P.BEZ_STADTBEZIRK = S.STADTBEZIRK(+)
  AND P.BPG_BAUPROGRAMM = O.BAUPROGRAMM(+)
  AND P.SGT_SIEDLUNGSGEBIET = I.SIEDLUNGSGEBIET(+)

UNION ALL

SELECT
--  2. Select: Antrag noch ohne Bewilligung --> liefert null f�r BEW Columns
A.ID,
A.PRO_PROJNR,
P.VNDAT,
P.FOB_FB,
F.BEZEICHNUNG,
P.PNAME,
P.PSTRASSE,
P.KAUF,
P.KUR_KURZBEZ,
Z.BEZEICHNUNG,
P.UAS_UA,
U.BEZEICHNUNG,
P.BEZ_STADTBEZIRK,
S.BEZEICHNUNG,
P.KRISOFP,
A.ANTRAGSDATUM,
A.ANTRAGSTYP,
A.GESKOSTEN,
A.ZWFKOSTEN,
A.VORZBEG,
A.VBDATUM,
A.UNBEDDAT,
A.UNBEDJA,
A.UNBEDBIS,
A.A_SU_Z,
A.A_SU_D,
A.A_SU_K,
A.B_VOR_SU_Z,
A.B_VOR_SU_D,
A.B_VOR_SU_K,
A.NOTIZEN,
P.SGT_SIEDLUNGSGEBIET,
I.BEZEICHNUNG,
P.BPG_BAUPROGRAMM,
O.BEZEICHNUNG,
null, --B.ID,
null, --B.GESZUWENDUNGEN,
null  --B.BDATUM
FROM FP_PROJEKTE P,
     FP_ANTRAEGE A,
     FP_FOERDERBEREICHE F,
     FP_UNTERABSCHNITTE U,
     FP_KURZBEZEICHNUNGEN Z,
     FP_STADTBEZIRKE S,
     FP_BAUPROGRAMME O,
     FP_SIEDLUNGSGEBIETE I
WHERE P.FOB_FB = F.FB
  AND P.PROJNR = A.PRO_PROJNR
  AND not exists (select 1 from FP_BEWILLIGUNGEN B WHERE B.ANT_ID = A.ID)
  AND P.UAS_UA = U.UA
  AND P.KUR_KURZBEZ = Z.KURZBEZ
  AND P.BEZ_STADTBEZIRK = S.STADTBEZIRK(+)
  AND P.BPG_BAUPROGRAMM = O.BAUPROGRAMM(+)
  AND P.SGT_SIEDLUNGSGEBIET = I.SIEDLUNGSGEBIET(+)
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

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_BEWILL_ABRUFE" ("V_PROJNR", "V_FOB_FB", "V_FOB_BEZEICHNUNG", "V_PNAME", "V_PSTRASSE", "V_KUR_KURZBEZ", "V_KUR_BEZEICHNUNG", "V_UAS_UA", "V_UAS_BEZEICHNUNG", "V_BEZ_STADTBEZIRK", "V_BEZ_BEZEICHNUNG", "V_KRISOFP", "V_VNDAT", "V_SUM_ABRUF_Z", "V_SUM_ABRUF_D", "V_SUM_ABRUF_K", "V_MIN_ABRUF_DATUM", "V_SUM_ERH_Z", "V_SUM_ERH_D", "V_SUM_ERH_K", "V_MIN_ERH_DATUM", "V_MIN_FIPO", "V_ID", "V_ANT_ID", "V_BDATUM", "V_AFSATZ", "V_BFSATZ", "V_BZWFKOSTEN", "V_BZUWENDUNG_Z", "V_BZUWENDUNG_D", "V_BZUWENDUNG_K", "V_BZUWART", "V_BAKTENZEICHEN", "V_GESZUWENDUNGEN", "V_GESKONNEX", "V_KRW") AS
SELECT
    -- Projekt
    P.PROJNR,
    P.FOB_FB,
    F.BEZEICHNUNG,
    P.PNAME,
    P.PSTRASSE,
    P.KUR_KURZBEZ,
    Z.BEZEICHNUNG,
    P.UAS_UA,
    U.BEZEICHNUNG,
    P.BEZ_STADTBEZIRK,
    S.BEZEICHNUNG,
    P.KRISOFP,
    P.VNDAT,
    -- Abruf
    sum(nvl(A.ABRUF_Z, 0)),
    sum(nvl(A.ABRUF_D, 0)),
    sum(nvl(A.ABRUF_K, 0)),
    min(A.ABRUF_DATUM),
    sum(nvl(A.ERH_Z, 0)),
    sum(nvl(A.ERH_D, 0)),
    sum(nvl(A.ERH_K, 0)),
    min(A.ERH_DATUM),
    min(A.FIPO),
    -- Bewilligung
    B.ID,
    B.ANT_ID,
    B.BDATUM,
    B.AFSATZ,
    B.BFSATZ,
    B.BZWFKOSTEN,
    B.BZUWENDUNG_Z,
    B.BZUWENDUNG_D,
    B.BZUWENDUNG_K,
    B.BZUWART,
    B.BAKTENZEICHEN,
    B.GESZUWENDUNGEN,
    B.GESKONNEX,
    B.KRW
FROM FP_PROJEKTE P,
     FP_ABRUFE A,
     FP_FOERDERBEREICHE F,
     FP_UNTERABSCHNITTE U,
     FP_KURZBEZEICHNUNGEN Z,
     FP_STADTBEZIRKE S,
     FP_BEWILLIGUNGEN B
WHERE P.FOB_FB = F.FB
  AND P.PROJNR = B.PRO_PROJNR
  AND P.UAS_UA = U.UA
  AND P.KUR_KURZBEZ = Z.KURZBEZ
  AND P.BEZ_STADTBEZIRK = S.STADTBEZIRK(+)
  AND B.ID = A.BWI_ID(+)
GROUP BY P.PROJNR,
         P.FOB_FB,
         F.BEZEICHNUNG,
         P.PNAME,
         P.PSTRASSE,
         P.KUR_KURZBEZ,
         Z.BEZEICHNUNG,
         P.UAS_UA,
         U.BEZEICHNUNG,
         P.BEZ_STADTBEZIRK,
         S.BEZEICHNUNG,
         P.KRISOFP,
         P.VNDAT,
         B.ID,
         B.ANT_ID,
         B.BDATUM,
         B.AFSATZ,
         B.BFSATZ,
         B.BZWFKOSTEN,
         B.BZUWENDUNG_Z,
         B.BZUWENDUNG_D,
         B.BZUWENDUNG_K,
         B.BZUWART,
         B.BAKTENZEICHEN,
         B.GESZUWENDUNGEN,
         B.GESKONNEX,
         B.KRW
;
--------------------------------------------------------
--  DDL for View FP_V_BEWILLSUCHE
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_BEWILLSUCHE" ("V_PROJNR", "V_FOB_FB", "V_FOB_BEZEICHNUNG", "V_PNAME", "V_PSTRASSE", "V_KUR_KURZBEZ", "V_KUR_BEZEICHNUNG", "V_UAS_UA", "V_UAS_BEZEICHNUNG", "V_BEZ_STADTBEZIRK", "V_BEZ_BEZEICHNUNG", "V_KRISOFP", "V_VNDAT", "V_ANTRAGSDATUM", "V_ANTRAGSTYP", "V_GESKOSTEN", "V_ZWFKOSTEN", "V_A_SU_Z", "V_A_SU_D", "V_A_SU_K", "V_B_VOR_SU_Z", "V_B_VOR_SU_D", "V_B_VOR_SU_K", "V_ID", "V_ANT_ID", "V_BDATUM", "V_AFSATZ", "V_BFSATZ", "V_BZWFKOSTEN", "V_BZUWENDUNG_Z", "V_BZUWENDUNG_D", "V_BZUWENDUNG_K", "V_BZUWART", "V_BAKTENZEICHEN", "V_GESZUWENDUNGEN", "V_GESKONNEX", "V_KRW", "V_NOTIZEN", "V_SGT_SIEDLUNGSGEBIET", "V_SGT_BEZEICHNUNG", "V_BPG_BAUPROGRAMM", "V_BPG_BEZEICHNUNG") AS
SELECT
-- Projekt
P.PROJNR,
P.FOB_FB,
F.BEZEICHNUNG,
P.PNAME,
P.PSTRASSE,
P.KUR_KURZBEZ,
Z.BEZEICHNUNG,
P.UAS_UA,
U.BEZEICHNUNG,
P.BEZ_STADTBEZIRK,
S.BEZEICHNUNG,
P.KRISOFP,
P.VNDAT,
-- Antrag
A.ANTRAGSDATUM,
A.ANTRAGSTYP,
A.GESKOSTEN,
A.ZWFKOSTEN,
A.A_SU_Z,
A.A_SU_D,
A.A_SU_K,
A.B_VOR_SU_Z,
A.B_VOR_SU_D,
A.B_VOR_SU_K,
-- Bewilligung
B.ID,
B.ANT_ID,
B.BDATUM,
B.AFSATZ,
B.BFSATZ,
B.BZWFKOSTEN,
B.BZUWENDUNG_Z,
B.BZUWENDUNG_D,
B.BZUWENDUNG_K,
B.BZUWART,
B.BAKTENZEICHEN,
B.GESZUWENDUNGEN,
B.GESKONNEX,
B.KRW,
B.NOTIZEN,
-- SIEDLUNGSGEBIET BAUPROGRAMM
P.SGT_SIEDLUNGSGEBIET,
I.BEZEICHNUNG,
P.BPG_BAUPROGRAMM,
O.BEZEICHNUNG
FROM FP_PROJEKTE P,
     FP_ANTRAEGE A,
     FP_FOERDERBEREICHE F,
     FP_UNTERABSCHNITTE U,
     FP_KURZBEZEICHNUNGEN Z,
     FP_STADTBEZIRKE S,
     FP_BEWILLIGUNGEN B,
     FP_BAUPROGRAMME O,
     FP_SIEDLUNGSGEBIETE I
WHERE P.FOB_FB = F.FB
  AND P.PROJNR = B.PRO_PROJNR
  AND P.UAS_UA = U.UA
  AND P.KUR_KURZBEZ = Z.KURZBEZ
  AND P.BEZ_STADTBEZIRK = S.STADTBEZIRK(+)
  AND B.ANT_ID = A.ID(+)
  AND P.BPG_BAUPROGRAMM = O.BAUPROGRAMM(+)
  AND P.SGT_SIEDLUNGSGEBIET = I.SIEDLUNGSGEBIET(+)
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN1
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_CHECKLISTEN1" ("V_HINWEIS", "V_PROJNR", "V_PSTRASSE", "V_INFO") AS
SELECT '1 Offene Projekte ohne F�rderantrag' HINWEIS,
       PROJNR                                PROJNR,
       PSTRASSE                              PSTRASSE,
       null                                  INFO
from FP_PROJEKTE P
where VNDAT is null
  and not exists (select 1 from FP_ANTRAEGE A where P.PROJNR = A.PRO_PROJNR)
--
UNION ALL
--
SELECT '2 Offene Projekte mit Antr�gen auf Unbedenklichkeit ohne F�rderantrag' HINWEIS,
       PROJNR                                                                  PROJNR,
       PSTRASSE                                                                PSTRASSE,
       'Unbedenk. am: ' || to_char(A.UNBEDDAT, 'DD.MM.YYYY')                   INFO
from FP_ANTRAEGE A,
     FP_PROJEKTE P
where A.pro_projnr = P.PROJNR
  and A.unbeddat is not null
  and A.antragsdatum is null
  and P.VNDAT is null
--
UNION ALL
--
SELECT '3 Offene Projekte und ausstehende Genehmigungen zum vorzeitigen Baubeginn' HINWEIS,
       PROJNR                                                                      PROJNR,
       PSTRASSE                                                                    PSTRASSE,
       'Vorz. Baubeginn: ' || A.VORZBEG                                            INFO
from FP_ANTRAEGE A,
     FP_PROJEKTE P
where P.PROJNR = A.PRO_PROJNR
  and P.VNDAT is null
  and A.VORZBEG = 1
  and A.VBDATUM is null
--
UNION ALL
--
SELECT '4 Offene Projekte und ausstehende Bewilligungen (ohne Datum)' HINWEIS,
       PROJNR                                                         PROJNR,
       PSTRASSE                                                       PSTRASSE,
       'Bewilligt am: ' || to_char(B.BDATUM, 'DD.MM.YYYY')            INFO
from FP_PROJEKTE P,
     FP_BEWILLIGUNGEN B
where P.PROJNR = B.PRO_PROJNR
  and P.VNDAT is null
  and BDATUM is null
--
UNION ALL
--
SELECT '5 Offene Projekte und Bewilligungen ohne Verkn�pfung zu einem Antrag' HINWEIS,
       PROJNR                                                                 PROJNR,
       PSTRASSE                                                               PSTRASSE,
       null                                                                   INFO
from FP_PROJEKTE P,
     FP_BEWILLIGUNGEN B
where B.PRO_PROJNR = P.PROJNR
  and P.VNDAT is null
  and B.ANT_ID is null
--
UNION ALL
--
SELECT '6 Offene Projekte und Abrufe ohne Verkn�pfung zu einer Bewilligung' HINWEIS,
       PROJNR                                                               PROJNR,
       PSTRASSE                                                             PSTRASSE,
       null                                                                 INFO
from FP_PROJEKTE P,
     FP_ABRUFE A
where P.PROJNR = A.PRO_PROJNR
  and A.BWI_ID is null
  and P.VNDAT is null
--
UNION ALL
--
SELECT '7 Offene Projekte und leere Bewilligungen'                  HINWEIS,
       PROJNR                                                       PROJNR,
       PSTRASSE                                                     PSTRASSE,
       'Leer angelegt am: ' || to_char(B.ANLAGEDATUM, 'DD.MM.YYYY') INFO
from FP_PROJEKTE P,
     FP_BEWILLIGUNGEN B
where B.PRO_PROJNR = P.PROJNR
  and P.VNDAT is null
  and B.BDATUM is null
  and nvl(B.AFSATZ, 0) = 0
  and nvl(B.BFSATZ, 0) = 0
  and nvl(B.BZWFKOSTEN, 0) = 0
  and nvl(B.BZUWENDUNG_Z, 0) = 0
  and nvl(B.BZUWENDUNG_D, 0) = 0
  and nvl(B.BZUWENDUNG_K, 0) = 0
  and B.BZUWART is null
  and B.BAKTENZEICHEN is null
  and nvl(B.GESZUWENDUNGEN, 0) = 0
  and nvl(B.GESKONNEX, 0) = 0
  and B.KRW is null
  and B.AENDERUNGSDATUM is null
  and B.AENDERUNGVON is null
--
UNION ALL
--
SELECT '8 Projekte mit VN-Datum ab 1.1.2000 und leere Bewilligungen' HINWEIS,
       PROJNR                                                        PROJNR,
       PSTRASSE                                                      PSTRASSE,
       'Leer angelegt am: ' || to_char(B.ANLAGEDATUM, 'DD.MM.YYYY')  INFO
from FP_PROJEKTE P,
     FP_BEWILLIGUNGEN B
where B.PRO_PROJNR = P.PROJNR
  and P.VNDAT > to_date('01.01.2000', 'dd.mm.yyyy')
  and B.BDATUM is null
  and nvl(B.AFSATZ, 0) = 0
  and nvl(B.BFSATZ, 0) = 0
  and nvl(B.BZWFKOSTEN, 0) = 0
  and nvl(B.BZUWENDUNG_Z, 0) = 0
  and nvl(B.BZUWENDUNG_D, 0) = 0
  and nvl(B.BZUWENDUNG_K, 0) = 0
  and B.BZUWART is null
  and B.BAKTENZEICHEN is null
  and nvl(B.GESZUWENDUNGEN, 0) = 0
  and nvl(B.GESKONNEX, 0) = 0
  and B.KRW is null
  and B.AENDERUNGSDATUM is null
  and B.AENDERUNGVON is null
--
UNION ALL
--
SELECT '9 Projekte mit VN-Datum ab 1.1.2000 aber ohne Schlu�bescheid' HINWEIS,
       PROJNR                                                         PROJNR,
       PSTRASSE                                                       PSTRASSE,
       'VN: ' || to_char(P.VNDAT, 'DD.MM.YYYY')                       INFO
from FP_PROJEKTE P
where VNDAT > to_date('01.01.2000', 'dd.mm.yyyy')
  and VNSCHLUSSBEW is null
--
UNION ALL
--
SELECT '10 Projekte mit VN-Datum ab 1.1.2000 aber ohne Endkosten' HINWEIS,
       PROJNR                                                     PROJNR,
       PSTRASSE                                                   PSTRASSE,
       'VN: ' || to_char(P.VNDAT, 'DD.MM.YYYY')                   INFO
from FP_PROJEKTE P
where VNDAT > to_date('01.01.2000', 'dd.mm.yyyy')
  and VNKOSTEN is null
--
--
ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN2
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_CHECKLISTEN2" ("V_FEHLER", "V_PROJNR", "V_PSTRASSE", "V_INFO") AS
SELECT '1 Verwendungsnachweise ohne VN Datum'                                FEHLER,
       PROJNR                                                                PROJNR,
       PSTRASSE                                                              PSTRASSE,
       'VN ZWF KOSTEN: ' || VNZWFKOSTEN || '  VN Gesamtkosten: ' || VNKOSTEN INFO
from FP_PROJEKTE P
where VNDAT is null
  and (VNZWFKOSTEN > 0 or VNKOSTEN > 0)
--
UNION ALL
--
SELECT '2 Erfolgte Bewilligungen ohne Bewilligungsdatum'                            FEHLER,
       PROJNR                                                                       PROJNR,
       PSTRASSE                                                                     PSTRASSE,
       'Summe Zuwendungen: ' || to_char(BZUWENDUNG_Z + BZUWENDUNG_D + BZUWENDUNG_K) INFO
from FP_BEWILLIGUNGEN B,
     FP_PROJEKTE P
where B.PRO_PROJNR = P.PROJNR
  and B.BDATUM is null
  and (BZUWENDUNG_Z > 0 or BZUWENDUNG_D > 0 or BZUWENDUNG_K > 0)
--
UNION ALL
--
SELECT '3 Erfolgte Abrufe ohne Abrufdatum'                      FEHLER,
       PROJNR                                                   PROJNR,
       PSTRASSE                                                 PSTRASSE,
       'Summe Abrufe: ' || to_char(ABRUF_Z + ABRUF_D + ABRUF_K) INFO
from FP_ABRUFE A,
     FP_PROJEKTE P
where A.PRO_PROJNR = P.PROJNR
  and A.ABRUF_DATUM is null
  and (A.ABRUF_Z > 0 or A.ABRUF_D > 0 or A.ABRUF_K > 0)
--
UNION ALL
--
SELECT '4 Erhaltene Abrufe ohne Erhaltdatum'                      FEHLER,
       PROJNR                                                     PROJNR,
       PSTRASSE                                                   PSTRASSE,
       'Summe erhalten: ' || to_char(A.ERH_Z + A.ERH_D + A.ERH_K) INFO
from FP_ABRUFE A,
     FP_PROJEKTE P
where A.PRO_PROJNR = P.PROJNR
  and A.ERH_DATUM is null
  and (A.ERH_Z > 0 or A.ERH_D > 0 or A.ERH_K > 0)
--
UNION ALL
--
SELECT '5 Antr�ge ohne Antragsdatum und ohne Unbedenklichkeitsantrag' FEHLER,
       PROJNR                                                         PROJNR,
       PSTRASSE                                                       PSTRASSE,
       null                                                           INFO
from FP_ANTRAEGE A,
     FP_PROJEKTE P
where A.PRO_PROJNR = P.PROJNR
  and A.unbeddat is null
  and A.antragsdatum is null
  and P.VNDAT is null
--
ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_EUINFORMATIONEN
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_EUINFORMATIONEN" ("V_JAHR", "V_HEFTA", "V_NUMMER", "V_SEITENNR", "V_PUB_KURZFORM", "V_BEZEICHNUNG", "V_INHALT", "V_INFODAT", "V_REFID", "V_REFBEZ") AS
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       1,
       'RAW'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND RAWI = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       2,
       'RBS'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND SCHULREF = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       3,
       'Sozref R 5'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND SOZREF_R_5 = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       4,
       'RGU 11'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND RGU_11 = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       5,
       'RGU Cs'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND RGU_CS = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       6,
       'Krh'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND KRH = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       7,
       'AWB'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND AFA = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       8,
       'SWM'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND SWM = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       9,
       'Kulturreferat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND KULTURREF = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       10,
       'Baureferat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND BAUREF = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       11,
       'Planungsreferat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND PLANREF = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       12,
       'Direktorium'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND DIREKTORIUM = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       13,
       'Personal- und Org.referat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND POR = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       14,
       'Kreisverwaltungsreferat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND KVR = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       15,
       'Kommunalreferat'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND KOMMREF = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       16,
       'MSE'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND SEW = 1
UNION ALL
SELECT JAHR,
       HEFTA,
       NUMMER,
       SEITENNR,
       PUB_KURZFORM,
       BEZEICHNUNG,
       INHALT,
       INFODAT,
       17,
       'Stk'
FROM FP_EUINFORMATIONEN,
     FP_PUBLIKATIONEN
WHERE PUB_KURZFORM = KURZFORM
  AND STK = 1
;
--------------------------------------------------------
--  DDL for View FP_V_FAGSTAT
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_FAGSTAT" ("F_FB", "F_BEZEICHNUNG", "F_JAHR", "B_BEWILL", "B_RUECK", "E_ERHALTEN", "E_RUECK") AS
SELECT x1,
       x2,
       x3,
       SUM(NVL(x4, 0)),
       SUM(NVL(x5, 0)),
       SUM(NVL(x6, 0)),
       SUM(NVL(x7, 0))
FROM
    -- bewilligter Zuschuss ohne R�ckzahlungen
    (SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            SUM(NVL(b.BZUWENDUNG_Z, 0))          x4,
            0                                    x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_Z >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY')),
              0,
              0,
              0
     -- bewilligtes Darlehen ohne R�ckzahlungen
     UNION
     SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            SUM(NVL(b.BZUWENDUNG_D, 0))          x4,
            0                                    x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_D >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY')),
              0,
              0,
              0
     -- bewilligte Kostenerstattung ohne R�ckzahlungen
     UNION
     SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            SUM(NVL(b.BZUWENDUNG_K, 0))          x4,
            0                                    x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_K >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY')),
              0,
              0,
              0
     --  Bewilligter Zuschuss und r�ckgefordert
     UNION
     SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            0                                    x4,
            SUM(NVL(b.BZUWENDUNG_Z, 0))          x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_Z < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY'))
     --  Bewilligtes Darlehen und r�ckgefordert
     UNION
     SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            0                                    x4,
            SUM(NVL(b.BZUWENDUNG_D, 0))          x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_D < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY'))
     --  Bewilligte Kostenerstattung und r�ckgefordert
     UNION
     SELECT F.FB                                 x1,
            F.BEZEICHNUNG                        x2,
            to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
            0                                    x4,
            SUM(NVL(b.BZUWENDUNG_K, 0))          x5,
            0                                    x6,
            0                                    x7
     FROM FP_PROJEKTE P,
          FP_BEWILLIGUNGEN B,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = B.PRO_PROJNR
       AND B.BDATUM IS NOT NULL
       AND B.BZUWENDUNG_K < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(B.BDATUM, 'YYYY'))
     -- Erhalten Zuschuss ohne R�ckzahlung
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            SUM(a.erh_z)                            X6,
            0                                       X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_Z >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
     -- erhalten Darlehen ohne R�ckzahlung
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            SUM(a.erh_d)                            X6,
            0                                       X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_D >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
     -- erhalten Kostenerstattung ohne R�ckzahlung
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            SUM(a.erh_k)                            X6,
            0                                       X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_K >= 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
     -- Erhalten R�ckzahlung von Zusch�ssen
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            0                                       X6,
            SUM(a.erh_z)                            X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_Z < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
     -- erhalten R�ckzahlung von Darlehen
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            0                                       X6,
            SUM(a.erh_d)                            X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_D < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
     -- erhalten R�ckzahlung von Kostenerstattungen
     UNION
     SELECT F.FB                                    X1,
            F.BEZEICHNUNG                           X2,
            to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
            0                                       X4,
            0                                       X5,
            0                                       X6,
            SUM(a.erh_k)                            X7
     FROM FP_PROJEKTE P,
          FP_ABRUFE A,
          FP_FOERDERBEREICHE F
     WHERE F.FB = P.FOB_FB
       AND P.PROJNR = A.PRO_PROJNR
       AND A.ERH_DATUM IS NOT NULL
       AND A.ERH_K < 0
     GROUP BY F.FB,
              F.BEZEICHNUNG,
              to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')))
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_FBSTAT
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_FBSTAT" ("F_FB", "F_BEZEICHNUNG", "F_JAHR", "B_BZUWENDUNG_Z", "B_BZUWENDUNG_D", "B_BZUWENDUNG_K", "A_ERH_Z", "A_ERH_D", "A_ERH_K") AS
SELECT x1,
       x2,
       x3,
       SUM(NVL(x4, 0)),
       SUM(NVL(x5, 0)),
       SUM(NVL(x6, 0)),
       SUM(NVL(x7, 0)),
       SUM(NVL(x8, 0)),
       SUM(NVL(x9, 0))
FROM (SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             SUM(NVL(b.bzuwendung_z, 0))          x4,
             SUM(NVL(b.bzuwendung_d, 0))          x5,
             SUM(NVL(b.bzuwendung_k, 0))          x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
      UNION
      SELECT F.FB                                    x1,
             F.BEZEICHNUNG                           x2,
             to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) x3,
             NULL                                    x4,
             NULL                                    x5,
             NULL                                    x6,
             SUM(NVL(a.erh_z, 0))                    x7,
             SUM(NVL(a.erh_d, 0))                    x8,
             SUM(NVL(a.erh_k, 0))                    x9
      FROM FP_PROJEKTE P,
           FP_ABRUFE A,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A.PRO_PROJNR
        AND A.ERH_DATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')))
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_ISTKOSTENMAX
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_ISTKOSTENMAX" ("P_PROJNR", "P_MAX_ISTDATUM", "P_JAHR", "P_MONAT", "P_ISTKOSTEN") AS
SELECT X1,
       X2,
       SUBSTR(X2, 1, 4) X3,
       SUBSTR(X2, 5, 2) x4,
       X5
FROM (SELECT I1.PRO_PROJNR                            X1,
             TO_CHAR(I1.JAHR || lpad(I1.MONAT, 2, 0)) X2,
             I1.ISTKOSTEN                             X5
      FROM FP_PROJEKTISTKOSTEN I1
      WHERE TO_CHAR(I1.JAHR || lpad(I1.MONAT, 2, 0)) =
            (SELECT max(TO_CHAR(I2.JAHR || lpad(I2.MONAT, 2, 0)))
             from FP_PROJEKTISTKOSTEN I2
             WHERE I2.PRO_PROJNR = I1.PRO_PROJNR))
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK1
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_JAHRESSTATISTIK1" ("F_FB", "F_BEZEICHNUNG", "F_JAHR", "B_BZUWENDUNG_Z", "B_BZUWENDUNG_D", "B_BZUWENDUNG_K", "A_ERH_Z", "A_ERH_D", "A_ERH_K", "E_GESKOSTEN", "E_ZWFKOSTEN") AS
SELECT x1,
       x2,
       x3,
       SUM(NVL(x4, 0)),
       SUM(NVL(x5, 0)),
       SUM(NVL(x6, 0)),
       SUM(NVL(x7, 0)),
       SUM(NVL(x8, 0)),
       SUM(NVL(x9, 0)),
       SUM(NVL(x10, 0)),
       SUM(NVL(x11, 0))
FROM (SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             SUM(NVL(b.bzuwendung_z, 0))          x4,
             SUM(NVL(b.bzuwendung_d, 0))          x5,
             SUM(NVL(b.bzuwendung_k, 0))          x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
      UNION
      SELECT F.FB                                    x1,
             F.BEZEICHNUNG                           x2,
             to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) x3,
             NULL                                    x4,
             NULL                                    x5,
             NULL                                    x6,
             SUM(NVL(a.erh_z, 0))                    x7,
             SUM(NVL(a.erh_d, 0))                    x8,
             SUM(NVL(a.erh_k, 0))                    x9,
             NULL                                    x10,
             NULL                                    x11
      FROM FP_PROJEKTE P,
           FP_ABRUFE A,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A.PRO_PROJNR
        AND A.ERH_DATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A.ERH_DATUM, 'YYYY'))
      UNION
      SELECT F.FB                                        x1,
             F.BEZEICHNUNG                               x2,
             to_number(TO_CHAR(A1.ANTRAGSDATUM, 'YYYY')) x3,
             NULL                                        x4,
             NULL                                        x5,
             NULL                                        x6,
             NULL                                        x7,
             NULL                                        x8,
             NULL                                        x9,
             SUM(NVL(A1.GESKOSTEN, 0))                   x10,
             SUM(NVL(A1.ZWFKOSTEN, 0))                   x11
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE A1,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A1.PRO_PROJNR
        AND A1.ANTRAGSDATUM =
            (SELECT MIN(ANTRAGSDATUM)
             FROM FP_ANTRAEGE Y
             WHERE y.pro_projnr = P.PROJNR
               AND ANTRAGSTYP = 'E'
               AND rownum = 1)
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A1.ANTRAGSDATUM, 'YYYY')))
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK2
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_JAHRESSTATISTIK2" ("F_FB", "F_BEZEICHNUNG", "F_JAHR", "V_ANZAHL_ABRUFE", "V_ANZAHL_VN", "V_ANZAHL_BEWILLIGUNGEN", "V_VNGESKOSTEN", "V_ANZAHL_ERST", "V_A_SU_Z_ERST", "V_A_SU_K_ERST", "V_ANZAHL_FOLGE", "V_A_SU_Z_FOLGE", "V_A_SU_K_FOLGE", "V_A_VOR_SU_Z_GESAMT", "V_A_VOR_SU_K_GESAMT", "V_ANZAHL_UNBED") AS
SELECT x1,
       x2,
       x3,
       SUM(NVL(x4, 0)),
       SUM(NVL(x5, 0)),
       SUM(NVL(x6, 0)),
       SUM(NVL(x7, 0)),
       SUM(NVL(x8, 0)),
       SUM(NVL(x9, 0)),
       SUM(NVL(x10, 0)),
       SUM(NVL(x11, 0)),
       SUM(NVL(x12, 0)),
       SUM(NVL(x13, 0)),
       SUM(NVL(x14, 0)),
       SUM(NVL(x15, 0)),
       SUM(NVL(x16, 0))
FROM (SELECT F.FB                                      x1,
             F.BEZEICHNUNG                             x2,
             to_number(TO_CHAR(A.ABRUF_DATUM, 'YYYY')) x3,
             COUNT(*)                                  x4,
             NULL                                      x5,
             NULL                                      x6,
             NULL                                      x7,
             NULL                                      x8,
             NULL                                      x9,
             NULL                                      x10,
             NULL                                      x11,
             NULL                                      x12,
             NULL                                      x13,
             NULL                                      x14,
             NULL                                      x15,
             NULL                                      x16
      FROM FP_ABRUFE A,
           FP_PROJEKTE P,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A.PRO_PROJNR
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A.ABRUF_DATUM, 'YYYY'))
      --
      UNION
      SELECT F.FB                                x1,
             F.BEZEICHNUNG                       x2,
             to_number(TO_CHAR(P.VNDAT, 'YYYY')) x3,
             NULL                                x4,
             COUNT(*)                            x5,
             NULL                                x6,
             SUM(VNKOSTEN)                       x7,
             NULL                                x8,
             NULL                                x9,
             NULL                                x10,
             NULL                                x11,
             NULL                                x12,
             NULL                                x13,
             NULL                                x14,
             NULL                                x15,
             NULL                                x16
      FROM FP_PROJEKTE P,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.VNDAT IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(P.VNDAT, 'YYYY'))
      --
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             NULL                                 x5,
             COUNT(*)                             x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12,
             NULL                                 x13,
             NULL                                 x14,
             NULL                                 x15,
             NULL                                 x16
      FROM FP_BEWILLIGUNGEN B,
           FP_PROJEKTE P,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
      --
      UNION
      SELECT F.FB                                        x1,
             F.BEZEICHNUNG                               x2,
             to_number(TO_CHAR(A1.ANTRAGSDATUM, 'YYYY')) x3,
             NULL                                        x4,
             NULL                                        x5,
             NULL                                        x6,
             NULL                                        x7,
             COUNT(*)                                    x8,
             SUM(A_SU_Z)                                 x9,
             SUM(A_SU_K)                                 x10,
             NULL                                        x11,
             NULL                                        x12,
             NULL                                        x13,
             SUM(B_VOR_SU_Z)                             x14,
             SUM(B_VOR_SU_K)                             x15,
             NULL                                        x16
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE A1,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A1.PRO_PROJNR
        AND A1.ANTRAGSDATUM =
            (SELECT MIN(ANTRAGSDATUM)
             FROM FP_ANTRAEGE Y
             WHERE y.pro_projnr = P.PROJNR
               AND ANTRAGSTYP = 'E'
               AND rownum = 1)
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A1.ANTRAGSDATUM, 'YYYY'))
      --
      UNION
      SELECT F.FB                                        x1,
             F.BEZEICHNUNG                               x2,
             to_number(TO_CHAR(AX.ANTRAGSDATUM, 'YYYY')) x3,
             NULL                                        x4,
             NULL                                        x5,
             NULL                                        x6,
             NULL                                        x7,
             NULL                                        x8,
             NULL                                        x9,
             NULL                                        x10,
             COUNT(*)                                    x11,
             SUM(A_SU_Z)                                 x12,
             SUM(A_SU_K)                                 x13,
             NULL                                        x14,
             NULL                                        x15,
             NULL                                        x16
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE AX,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = AX.PRO_PROJNR
        AND ANTRAGSTYP = 'F' -- nicht A und nicht V
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(AX.ANTRAGSDATUM, 'YYYY'))
      --
      UNION
      SELECT F.FB                                    x1,
             F.BEZEICHNUNG                           x2,
             to_number(TO_CHAR(A1.UNBEDDAT, 'YYYY')) x3,
             NULL                                    x4,
             NULL                                    x5,
             NULL                                    x6,
             NULL                                    x7,
             NULL                                    x8,
             NULL                                    x9,
             NULL                                    x10,
             NULL                                    x11,
             NULL                                    x12,
             NULL                                    x13,
             NULL                                    x14,
             NULL                                    x15,
             COUNT(*)                                x16
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE A1,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A1.PRO_PROJNR
        AND A1.UNBEDDAT IS NOT NULL
        AND A1.ANTRAGSTYP = 'E'
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A1.UNBEDDAT, 'YYYY'))
         --
     )
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK3
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_JAHRESSTATISTIK3" ("F_FB", "F_BEZEICHNUNG", "F_JAHR", "V_BZUWENDUNG_Z_PLUS", "V_BZUWENDUNG_D_PLUS", "V_BZUWENDUNG_K_PLUS", "V_BZUWENDUNG_Z_MINUS", "V_BZUWENDUNG_D_MINUS", "V_BZUWENDUNG_K_MINUS", "V_ERH_Z", "V_ERH_D", "V_ERH_K") AS
SELECT x1,
       x2,
       x3,
       SUM(NVL(x4, 0)),
       SUM(NVL(x5, 0)),
       SUM(NVL(x6, 0)),
       SUM(NVL(x7, 0)),
       SUM(NVL(x8, 0)),
       SUM(NVL(x9, 0)),
       SUM(NVL(x10, 0)),
       SUM(NVL(x11, 0)),
       SUM(NVL(x12, 0))
FROM (SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             SUM(NVL(b.BZUWENDUNG_Z, 0))          x4,
             NULL                                 x5,
             NULL                                 x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_Z >= 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             SUM(NVL(b.BZUWENDUNG_D, 0))          x5,
             NULL                                 x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_D >= 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             NULL                                 x5,
             SUM(NVL(b.BZUWENDUNG_K, 0))          x6,
             NULL                                 x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_K >= 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             NULL                                 x5,
             NULL                                 x6,
             SUM(NVL(b.BZUWENDUNG_Z, 0))          x7,
             NULL                                 x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_Z < 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             NULL                                 x5,
             NULL                                 x6,
             NULL                                 x7,
             SUM(NVL(b.BZUWENDUNG_D, 0))          x8,
             NULL                                 x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_D < 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                 x1,
             F.BEZEICHNUNG                        x2,
             to_number(TO_CHAR(B.BDATUM, 'YYYY')) x3,
             NULL                                 x4,
             NULL                                 x5,
             NULL                                 x6,
             NULL                                 x7,
             NULL                                 x8,
             SUM(NVL(b.BZUWENDUNG_K, 0))          x9,
             NULL                                 x10,
             NULL                                 x11,
             NULL                                 x12
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = B.PRO_PROJNR
        AND B.BDATUM IS NOT NULL
        AND B.BZUWENDUNG_K < 0
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(B.BDATUM, 'YYYY'))
--
      UNION
      SELECT F.FB                                    x1,
             F.BEZEICHNUNG                           x2,
             to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')) X3,
             NULL                                    x4,
             NULL                                    x5,
             NULL                                    x6,
             NULL                                    x7,
             NULL                                    x8,
             NULL                                    x9,
             SUM(NVL(A.ERH_Z, 0))                    x10,
             SUM(NVL(A.ERH_D, 0))                    x11,
             SUM(NVL(A.ERH_K, 0))                    x12
      FROM FP_PROJEKTE P,
           FP_ABRUFE A,
           FP_FOERDERBEREICHE F
      WHERE F.FB = P.FOB_FB
        AND P.PROJNR = A.PRO_PROJNR
        AND A.ERH_DATUM IS NOT NULL
      GROUP BY F.FB,
               F.BEZEICHNUNG,
               to_number(TO_CHAR(A.ERH_DATUM, 'YYYY')))
GROUP BY x1, x2, x3
;
--------------------------------------------------------
--  DDL for View FP_V_KINDER
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_KINDER" ("P_PROJNR", "P_FOB_FB", "P_FOB_BEZEICHNUNG", "P_BEZ_STADTBEZIRK", "P_STADTBEZIRK", "P_UAS_UA", "P_KUR_KURZBEZ", "P_PNAME", "P_PSTRASSE", "P_VNDAT", "P_VNKOSTEN", "P_KRISOFP", "P_KRIPPLATZ", "P_KIGAPLATZ", "P_HORTPLATZ", "A1_ANTRAGSTYP", "A1_ANTRAGSDATUM", "A1_GESKOSTEN", "P_ERH_Z", "P_BZUWENDUNG_Z") AS
SELECT P.PROJNR,
       P.FOB_FB,
       F.BEZEICHNUNG,
       P.BEZ_STADTBEZIRK,
       Z.BEZEICHNUNG,
       P.UAS_UA,
       P.KUR_KURZBEZ,
       P.PNAME,
       P.PSTRASSE,
       P.VNDAT,
       P.VNKOSTEN,
       P.KRISOFP,
       P.KRIPPLATZ,
       P.KIGAPLATZ,
       P.HORTPLATZ,
       S.A1_ANTRAGSTYP,
       S.A1_ANTRAGSDATUM,
       S.A1_GESKOSTEN,
       S.R_ERH_Z,
       S.B_BEWILL_Z
FROM FP_PROJEKTE P,
     FP_FOERDERBEREICHE F,
     FP_V_PROJEKTSTAT S,
     FP_STADTBEZIRKE Z
WHERE P.PROJNR = S.P_PROJNR(+)
  AND P.FOB_FB = F.FB
  AND P.BEZ_STADTBEZIRK = Z.STADTBEZIRK(+)
;
--------------------------------------------------------
--  DDL for View FP_V_MITTELEINPLANUNG
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_MITTELEINPLANUNG" ("B_ID", "B_PROJNR", "B_ANT_ID", "B_BDATUM", "B_AFSATZ", "B_BFSATZ", "B_BZUWENDUNG_Z", "B_BZWFKOSTEN", "B_KRW", "B_ZUWART", "B_BAKTENZEICHEN", "B_GESZUWENDUNGEN", "B_GESKONNEX", "B_NOTIZEN", "A_ANTRAGSTYP", "A_ANTRAGSDATUM", "A_GESKOSTEN") AS
SELECT B1.ID,
       B1.PRO_PROJNR,
       B1.ANT_ID,
       B1.BDATUM,
       B1.AFSATZ,
       B1.BFSATZ,
       B1.BZUWENDUNG_Z,
       B1.BZWFKOSTEN,
       B1.KRW,
       B1.BZUWART,
       B1.BAKTENZEICHEN,
       B1.GESZUWENDUNGEN,
       B1.GESKONNEX,
       B1.NOTIZEN,
       A1.ANTRAGSTYP,
       A1.ANTRAGSDATUM,
       A1.GESKOSTEN
FROM FP_BEWILLIGUNGEN B1,
     FP_ANTRAEGE A1
WHERE A1.ID = B1.ANT_ID
  AND A1.ANTRAGSTYP in ('E', 'A')
  AND B1.ID = (SELECT max(B2.ID)
               from FP_BEWILLIGUNGEN B2,
                    FP_ANTRAEGE A2
               WHERE A2.ID = B2.ANT_ID
                 AND B2.PRO_PROJNR = B1.PRO_PROJNR
                 AND A2.ANTRAGSTYP in ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_NOTIZEN
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_NOTIZEN" ("V_TYP", "V_PROJNR", "V_DATUM", "V_NOTIZEN") AS
SELECT X0,
       X1,
       X2,
       X3
FROM (SELECT 'Projekt' X0, -- Typ projekt
             P.PROJNR  X1,
             P.VNDAT   X2,
             P.NOTIZEN X3
      FROM FP_PROJEKTE P
      WHERE P.notizen IS NOT NULL
      UNION ALL
      SELECT 'Antrag'       X0, -- Typ antraege
             A.PRO_PROJNR   X1,
             A.ANTRAGSDATUM X2,
             A.NOTIZEN      X3
      FROM FP_ANTRAEGE A
      WHERE A.notizen IS NOT NULL
      UNION ALL
      SELECT 'Bewilligung' X0, -- Typ bewilligungen
             B.PRO_PROJNR  X1,
             B.BDATUM      X2,
             B.NOTIZEN     X3
      FROM FP_BEWILLIGUNGEN B
      WHERE B.notizen IS NOT NULL
      UNION
      SELECT 'Abruf'       X0, -- Typ abrufe
             R.PRO_PROJNR  X1,
             R.ABRUF_DATUM X2,
             R.NOTIZEN     X3
      FROM FP_ABRUFE R
      WHERE R.NOTIZEN IS NOT NULL
      UNION
      SELECT 'Termin'                                                                                      X0, -- Typ termine
             T.PRO_PROJNR                                                                                  X1,
             T.TERMIN                                                                                      X2,
             T.NOTIZEN || chr(13) || chr(10) || 'Zust�ndig: ' || T.ZUSTAENDIG || ' Telefon: ' || T.TELEFON X3
      FROM FP_PROJEKTTERMINE T
      WHERE T.NOTIZEN IS NOT NULL)
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTERSTANTRAG
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_PROJEKTERSTANTRAG" ("P_PROJNR", "P_FOB_FB", "P_PNAME", "P_PSTRASSE", "A1_ANTRAGSTYP", "A1_VBDATUM", "A1_VORZBEG", "P_VNDAT", "A1_GESKOSTEN", "P_VNGESKOSTEN", "P_VNPRUEFZWF", "P_VNPRUEFDAT", "P_ZINSEN", "A1_ANTRAGSDATUM", "A1_ZWFKOSTEN", "A1_A_SU_Z", "A1_A_SU_D", "A1_A_SU_K", "A1_B_VOR_SU_Z", "A1_B_VOR_SU_D", "A1_B_VOR_SU_K", "A1_NOTIZEN") AS
SELECT P.PROJNR,
       P.FOB_FB,
       P.PNAME,
       P.PSTRASSE,
       A1.ANTRAGSTYP,
       A1.VBDATUM,
       A1.VORZBEG,
       P.VNDAT,
       A1.GESKOSTEN,
       P.VNKOSTEN,
       P.VNPRUEFZWF,
       P.VNPRUEFDAT,
       P.ZINSEN,
       A1.ANTRAGSDATUM,
       A1.ZWFKOSTEN,
       A1.A_SU_Z,
       A1.A_SU_D,
       A1.A_SU_K,
       A1.B_VOR_SU_Z,
       A1.B_VOR_SU_D,
       A1.B_VOR_SU_K,
       A1.NOTIZEN
FROM FP_PROJEKTE P,
     FP_ANTRAEGE A1
WHERE P.PROJNR = A1.PRO_PROJNR
  AND ANTRAGSTYP in ('E', 'A')
  AND A1.ID =
      (SELECT MAX(ID)
       FROM FP_ANTRAEGE Y
       WHERE y.pro_projnr = P.PROJNR
         AND ANTRAGSTYP in ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTFLUESSE
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_PROJEKTFLUESSE" ("P_PROJNR", "AX_A_SU_Z", "AX_A_SU_D", "AX_A_SU_K", "R_ERH_Z", "R_ERH_D", "R_ERH_K", "B_BEWILL_Z", "B_BEWILL_D", "B_BEWILL_K") AS
SELECT x1,
       SUM(NVL(x24, 0)),
       SUM(NVL(x25, 0)),
       SUM(NVL(x26, 0)),
       SUM(NVL(x27, 0)),
       SUM(NVL(x28, 0)),
       SUM(NVL(x29, 0)),
       SUM(NVL(x30, 0)),
       SUM(NVL(x31, 0)),
       SUM(NVL(x32, 0))
FROM (SELECT P.PROJNR          x1,
             NVL(AX.A_SU_Z, 0) x24,
             NVL(AX.A_SU_D, 0) x25,
             NVL(AX.A_SU_K, 0) x26,
             NULL              x27,
             NULL              x28,
             NULL              x29,
             NULL              x30,
             NULL              x31,
             NULL              x32
      FROM FP_PROJEKTE P,
           FP_ANTRAEGE AX
      WHERE P.PROJNR = AX.PRO_PROJNR
      --
      UNION ALL
      SELECT P.PROJNR        x1,
             NULL            x24,
             NULL            x25,
             NULL            x26,
             NVL(R.ERH_Z, 0) x27,
             NVL(R.ERH_D, 0) x28,
             NVL(R.ERH_K, 0) x29,
             NULL            x30,
             NULL            x31,
             NULL            x32
      FROM FP_PROJEKTE P,
           FP_ABRUFE R
      WHERE P.PROJNR = R.PRO_PROJNR
      --
      UNION ALL
      SELECT P.PROJNR               x1,
             NULL                   x24,
             NULL                   x25,
             NULL                   x26,
             NULL                   x27,
             NULL                   x28,
             NULL                   x29,
             NVL(B.bzuwendung_Z, 0) x30,
             NVL(B.bzuwendung_D, 0) x31,
             NVL(B.bzuwendung_K, 0) x32
      FROM FP_PROJEKTE P,
           FP_BEWILLIGUNGEN B
      WHERE P.PROJNR = B.PRO_PROJNR)
GROUP BY x1
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSTAT
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_PROJEKTSTAT" ("P_PROJNR", "P_FOB_FB", "P_PNAME", "P_PSTRASSE", "A1_ANTRAGSTYP", "A1_VBDATUM", "A1_VORZBEG", "P_VNDAT", "A1_GESKOSTEN", "P_VNGESKOSTEN", "P_VNPRUEFZWF", "P_VNPRUEFDAT", "P_ZINSEN", "A1_ANTRAGSDATUM", "A1_ZWFKOSTEN", "A1_A_SU_Z", "A1_A_SU_D", "A1_A_SU_K", "A1_B_VOR_SU_Z", "A1_B_VOR_SU_D", "A1_B_VOR_SU_K", "A1_NOTIZEN", "AX_A_SU_Z", "AX_A_SU_D", "AX_A_SU_K", "R_ERH_Z", "R_ERH_D", "R_ERH_K", "B_BEWILL_Z", "B_BEWILL_D", "B_BEWILL_K") AS
SELECT A.P_PROJNR,
       P_FOB_FB,
       P_PNAME,
       P_PSTRASSE,
       A1_ANTRAGSTYP,
       A1_VBDATUM,
       A1_VORZBEG,
       P_VNDAT,
       A1_GESKOSTEN,
       P_VNGESKOSTEN,
       P_VNPRUEFZWF,
       P_VNPRUEFDAT,
       P_ZINSEN,
       A1_ANTRAGSDATUM,
       A1_ZWFKOSTEN,
       A1_A_SU_Z,
       A1_A_SU_D,
       A1_A_SU_K,
       A1_B_VOR_SU_Z,
       A1_B_VOR_SU_D,
       A1_B_VOR_SU_K,
       A1_NOTIZEN,
       AX_A_SU_Z,
       AX_A_SU_D,
       AX_A_SU_K,
       R_ERH_Z,
       R_ERH_D,
       R_ERH_K,
       B_BEWILL_Z,
       B_BEWILL_D,
       B_BEWILL_K
FROM FP_V_PROJEKTERSTANTRAG A,
     FP_V_PROJEKTFLUESSE F
WHERE A.P_PROJNR = F.P_PROJNR
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSUCHE
--------------------------------------------------------

CREATE
OR
REPLACE
FORCE EDITIONABLE VIEW "FP_V_PROJEKTSUCHE" ("V_PROJNR", "V_VNDAT", "V_FOB_FB", "V_VNDAT_JAHR", "V_VNDAT_MONAT", "V_JAHR", "V_PNAME", "V_PSTRASSE", "V_KAUF", "V_PROJART", "V_FOB_BEZEICHNUNG", "V_KUR_KURZBEZ", "V_KUR_BEZEICHNUNG", "V_UAS_UA", "V_UAS_BEZEICHNUNG", "V_BEZ_STADTBEZIRK", "V_BEZ_BEZEICHNUNG", "V_KRN_KRHNAME", "V_KRISOFP", "V_A1_ANTRAGSTYP", "V_VNEANTRAG", "V_VNBEW_Z", "V_VNBEW_D", "V_VNBEW_K", "V_VNERH_Z", "V_VNERH_D", "V_VNERH_K", "V_VNGESKOSTEN", "V_ZWFKOSTEN", "V_VNGESAMTZUWENDUNG", "V_VNKOSTEN", "V_VNZWFKOSTEN", "V_VNPRUEFDAT", "V_VNRUECK_Z", "V_VNNACHFOERDERUNG", "V_VNPRUEFZWF", "V_VNSCHLUSSZWF", "V_VNSCHLUSSBEW", "V_NOTIZEN", "V_FIPO", "V_BUCHUNGSKREIS", "V_SACHKONTO", "V_FIPO_K", "V_BUCHUNGSKREIS_K", "V_SACHKONTO_K", "V_KRIPPLATZ", "V_KIGAPLATZ", "V_HORTPLATZ", "V_PSBAUBUCH", "V_PSBAUREF", "V_PSBAUNR", "V_SGT_SIEDLUNGSGEBIET", "V_SGT_BEZEICHNUNG", "V_BPG_BAUPROGRAMM", "V_BPG_BEZEICHNUNG", "V_B_GESZUWENDUNGEN", "V_B_BZWFKOSTEN", "V_BAUENDE", "V_BAUBEENDET", "V_BAUVERGABE1", "V_BAUBEGINN", "V_BAUMITTEILUNG", "V_KREDITNUMMER", "V_STADTANLEIHE", "V_ANLEIHENENNWERT", "V_ANLEIHEJAHRVON", "V_ANLEIHEJAHRBIS") AS
SELECT P.PROJNR,
       P.VNDAT,
       P.FOB_FB,
       TO_CHAR(P.VNDAT, 'YYYY'),
       TO_CHAR(P.VNDAT, 'MM'),
       TO_CHAR(to_date(P.JAHR, 'RR'), 'YYYY'),
       P.PNAME,
       P.PSTRASSE,
       P.KAUF,
       P.PROJART,
       F.BEZEICHNUNG,
       P.KUR_KURZBEZ,
       Z.BEZEICHNUNG,
       P.UAS_UA,
       U.BEZEICHNUNG,
       P.BEZ_STADTBEZIRK,
       S.BEZEICHNUNG,
       P.KRN_KRHNAME,
       P.KRISOFP,
       T.A1_ANTRAGSTYP,
       T.A1_ANTRAGSDATUM,
       T.B_BEWILL_Z,
       T.B_BEWILL_D,
       T.B_BEWILL_K,
       T.R_ERH_Z,
       T.R_ERH_D,
       T.R_ERH_K,
       T.A1_GESKOSTEN,
       T.A1_ZWFKOSTEN,
       P.VNGESAMTZUWENDUNG,
       P.VNKOSTEN,
       P.VNZWFKOSTEN,
       P.VNPRUEFDAT,
       P.VNRUECK_Z,
       P.VNNACHFOERDERUNG,
       P.VNPRUEFZWF,
       P.VNSCHLUSSZWF,
       P.VNSCHLUSSBEW,
       P.NOTIZEN,
       P.FIPO,
       P.BUCHUNGSKREIS,
       P.SACHKONTO,
       P.FIPO_K,
       P.BUCHUNGSKREIS_K,
       P.SACHKONTO_K,
       P.KRIPPLATZ,
       P.KIGAPLATZ,
       P.HORTPLATZ,
       P.PSBAUBUCH,
       P.PSBAUREF,
       P.PSBAUNR,
       P.SGT_SIEDLUNGSGEBIET,
       I.BEZEICHNUNG,
       P.BPG_BAUPROGRAMM,
       B.BEZEICHNUNG,
       M.B_GESZUWENDUNGEN,
       M.B_BZWFKOSTEN,
       P.BAUENDE,
       P.BAUBEENDET,
       P.BAUVERGABE1,
       P.BAUBEGINN,
       P.BAUMITTEILUNG,
       P.KREDITNUMMER,
       P.STADTANLEIHE,
       P.ANLEIHENENNWERT,
       P.ANLEIHEJAHRVON,
       P.ANLEIHEJAHRBIS
FROM FP_PROJEKTE P,
     FP_FOERDERBEREICHE F,
     FP_UNTERABSCHNITTE U,
     FP_KURZBEZEICHNUNGEN Z,
     FP_STADTBEZIRKE S,
     FP_V_PROJEKTSTAT T,
     FP_BAUPROGRAMME B,
     FP_SIEDLUNGSGEBIETE I,
     FP_V_MITTELEINPLANUNG M
WHERE P.FOB_FB = F.FB
  AND P.UAS_UA = U.UA
  AND P.KUR_KURZBEZ = Z.KURZBEZ
  AND P.BEZ_STADTBEZIRK = S.STADTBEZIRK(+)
  AND P.PROJNR = T.P_PROJNR(+)
  AND P.BPG_BAUPROGRAMM = B.BAUPROGRAMM(+)
  AND P.SGT_SIEDLUNGSGEBIET = I.SIEDLUNGSGEBIET(+)
  AND P.PROJNR = M.B_PROJNR(+)
;