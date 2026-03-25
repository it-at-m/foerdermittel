--------------------------------------------------------
--  DDL for View FP_V_ABRUFSUCHE
--------------------------------------------------------

CREATE VIEW fp_v_abrufsuche AS
SELECT
    -- Projekt
    p.projnr              AS v_projnr,
    p.fob_fb              AS v_fob_fb,
    f.bezeichnung         AS v_fob_bezeichnung,
    p.pname               AS v_pname,
    p.pstrasse            AS v_pstrasse,
    p.kur_kurzbez         AS v_kur_kurzbez,
    z.bezeichnung         AS v_kur_bezeichnung,
    p.uas_ua              AS v_uas_ua,
    u.bezeichnung         AS v_uas_bezeichnung,
    p.bez_stadtbezirk     AS v_bez_stadtbezirk,
    s.bezeichnung         AS v_bez_bezeichnung,
    p.krisofp             AS v_krisofp,
    p.vndat               AS v_vndat,
    p.krhzweck            AS v_krhzweck,
    p.krn_krhname         AS v_krn_krhname,
    k.bezeichnung         AS v_krn_bezeichnung,
    p.sapstatauf          AS v_sapstatauf,
    p.sapanlagennr        AS v_sapanlagennr,
    -- Abruf
    a.vnabr               AS v_vnabr,
    a.abruf_z             AS v_abruf_z,
    a.abruf_d             AS v_abruf_d,
    a.abruf_k             AS v_abruf_k,
    a.abruf_datum         AS v_abruf_datum,
    a.erh_z               AS v_erh_z,
    a.erh_d               AS v_erh_d,
    a.erh_k               AS v_erh_k,
    a.erh_datum           AS v_erh_datum,
    a.ref_refnr           AS v_ref_refnr,
    r.bezeichnung         AS v_ref_bezeichnung,
    a.sapabrufauftragsnr  AS v_sapabrufauftragsnr,
    a.sapfakturanr        AS v_sapfakturanr,
    a.fipo                AS v_fipo,
    a.buchungskreis       AS v_buchungskreis,
    a.sachkonto           AS v_sachkonto,
    a.fipo_k              AS v_fipo_k,
    a.buchungskreis_k     AS v_buchungskreis_k,
    a.sachkonto_k         AS v_sachkonto_k,
    -- Bewilligung
    b.bdatum              AS v_bdatum,
    b.bfsatz              AS v_bfsatz,
    b.bzwfkosten          AS v_bzwfkosten,
    -- SIEDLUNGSGEBIET BAUPROGRAMM
    p.sgt_siedlungsgebiet AS v_sgt_siedlungsgebiet,
    i.bezeichnung         AS v_sgt_bezeichnung,
    p.bpg_bauprogramm     AS v_bpg_bauprogramm,
    o.bezeichnung         AS v_bpg_bezeichnung
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_abrufe a ON p.projnr = a.pro_projnr
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_bewilligungen b ON a.bwi_id = b.id
         LEFT JOIN fp_krankenhaeuser k ON p.krn_krhname = k.krhname
         LEFT JOIN fp_referate r ON a.ref_refnr = r.refnr
         LEFT JOIN fp_bauprogramme o ON p.bpg_bauprogramm = o.bauprogramm
         LEFT JOIN fp_siedlungsgebiete i ON p.sgt_siedlungsgebiet = i.siedlungsgebiet
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_BEWILL
--------------------------------------------------------

CREATE VIEW fp_v_antrag_bewill AS
SELECT a.id              AS v_id,
       a.pro_projnr      AS v_pro_projnr,
       p.vndat           AS v_vndat,
       p.fob_fb          AS v_fob_fb,
       f.bezeichnung     AS v_fob_bezeichnung,
       p.pname           AS v_pname,
       p.pstrasse        AS v_pstrasse,
       p.kauf            AS v_kauf,
       p.kur_kurzbez     AS v_kur_kurzbez,
       z.bezeichnung     AS v_kur_bezeichnung,
       p.uas_ua          AS v_uas_ua,
       u.bezeichnung     AS v_uas_bezeichnung,
       p.bez_stadtbezirk AS v_bez_stadtbezirk,
       s.bezeichnung     AS v_bez_bezeichnung,
       p.krisofp         AS v_krisofp,
       a.antragsdatum    AS v_antragsdatum,
       a.antragstyp      AS v_antragstyp,
       a.geskosten       AS v_geskosten,
       a.zwfkosten       AS v_zwfkosten,
       a.a_su_z          AS v_a_su_z,
       a.a_su_d          AS v_a_su_d,
       a.a_su_k          AS v_a_su_k,
       b.notizen         AS v_notizen,
       b.ant_id          AS v_ant_id,
       b.bdatum          AS v_bdatum,
       b.bfsatz          AS v_bfsatz,
       b.bzwfkosten      AS v_bzwfkosten,
       b.bzuwendung_z    AS v_bzuwendung_z,
       b.bzuwendung_d    AS v_bzuwendung_d,
       b.bzuwendung_k    AS v_bzuwendung_k,
       b.bzuwart         AS v_bzuwart
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_antraege a ON b.ant_id = a.id
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_BEWILL_ABRUF
--------------------------------------------------------

CREATE VIEW fp_v_antrag_bewill_abruf AS
SELECT x0  AS v_p_typ,
       x1  AS v_p_projnr,
       x2  AS v_p_vndat,
       x3  AS v_p_fob_fb,
       x4  AS v_p_pname,
       x5  AS v_p_pstrasse,
       x6  AS v_a_id,
       x7  AS v_a_antragstyp,
       x8  AS v_a_antragsdatum,
       x9  AS v_a_geskosten,
       x10 AS v_a_zwfkosten,
       x11 AS v_a_su_z,
       x12 AS v_a_su_d,
       x13 AS v_a_su_k,
       x14 AS v_b_id,
       x15 AS v_b_ant_id,
       x16 AS v_b_bzuwart,
       x17 AS v_b_bdatum,
       x18 AS v_b_bzuwendung_z,
       x19 AS v_b_bzuwendung_d,
       x20 AS v_b_bzuwendung_k,
       x21 AS v_r_bwi_id,
       x22 AS v_r_abruf_datum,
       x23 AS v_r_abruf_z,
       x24 AS v_r_abruf_d,
       x25 AS v_r_abruf_k,
       x26 AS v_r_erh_datum,
       x27 AS v_r_erh_z,
       x28 AS v_r_erh_d,
       x29 AS v_r_erh_k
FROM (SELECT NULL           AS x0, -- Typ1
             p.projnr       AS x1,
             p.vndat        AS x2,
             p.fob_fb       AS x3,
             p.pname        AS x4,
             p.pstrasse     AS x5,
             a.id           AS x6,
             a.antragstyp   AS x7,
             a.antragsdatum AS x8,
             a.geskosten    AS x9,
             a.zwfkosten    AS x10,
             a.a_su_z       AS x11,
             a.a_su_d       AS x12,
             a.a_su_k       AS x13,
             b.id           AS x14,
             b.ant_id       AS x15,
             b.bzuwart      AS x16,
             b.bdatum       AS x17,
             b.bzuwendung_z AS x18,
             b.bzuwendung_d AS x19,
             b.bzuwendung_k AS x20,
             r.bwi_id       AS x21,
             r.abruf_datum  AS x22,
             r.abruf_z      AS x23,
             r.abruf_d      AS x24,
             r.abruf_k      AS x25,
             r.erh_datum    AS x26,
             r.erh_z        AS x27,
             r.erh_d        AS x28,
             r.erh_k        AS x29
      FROM fp_projekte p
               JOIN fp_antraege a ON p.projnr = a.pro_projnr
               LEFT JOIN fp_bewilligungen b
                         ON a.id = b.ant_id -- Typ 1: vollständige Verknüpfungen selektieren mit outerjoin
               LEFT JOIN fp_abrufe r ON b.id = r.bwi_id

      UNION

      SELECT 'Bewilligung ohne Antrag', -- Typ2
             p.projnr,
             p.vndat,
             p.fob_fb,
             p.pname,
             p.pstrasse,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             b.id,
             b.ant_id,
             b.bzuwart,
             b.bdatum,
             b.bzuwendung_z,
             b.bzuwendung_d,
             b.bzuwendung_k,
             r.bwi_id,
             r.abruf_datum,
             r.abruf_z,
             r.abruf_d,
             r.abruf_k,
             r.erh_datum,
             r.erh_z,
             r.erh_d,
             r.erh_k
      FROM fp_projekte p
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
               LEFT JOIN fp_abrufe r ON b.id = r.bwi_id
      WHERE b.ant_id IS NULL

      UNION

      SELECT 'Abruf ohne Bewilligung', -- Typ3
             p.projnr,
             p.vndat,
             p.fob_fb,
             p.pname,
             p.pstrasse,
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
             NULL,
             NULL,
             NULL,
             r.bwi_id,
             r.abruf_datum,
             r.abruf_z,
             r.abruf_d,
             r.abruf_k,
             r.erh_datum,
             r.erh_z,
             r.erh_d,
             r.erh_k
      FROM fp_projekte p
               JOIN fp_abrufe r ON p.projnr = r.pro_projnr
      WHERE r.bwi_id IS NULL) AS x
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAGSUCHE
--------------------------------------------------------

CREATE VIEW fp_v_antragsuche AS
SELECT
    --  geändert Sep 2024:
    --  Sicherstellen, dass je Antrag nur ein Record geliefert wird.
    --  unabhängig wieviele Bewilligungen vorliegen (0 .. viele)
    --  deshalb zwei Select mit UNION.
    --
    --  1. Select: Antrag besitzt min. 1 Bewilligung --> liefert nur die jüngste Bewilligung
    a.id                  AS v_id,
    a.pro_projnr          AS v_pro_projnr,
    p.vndat               AS v_vndat,
    p.fob_fb              AS v_fob_fb,
    f.bezeichnung         AS v_fob_bezeichnung,
    p.pname               AS v_pname,
    p.pstrasse            AS v_pstrasse,
    p.kauf                AS v_kauf,
    p.kur_kurzbez         AS v_kur_kurzbez,
    z.bezeichnung         AS v_kur_bezeichnung,
    p.uas_ua              AS v_uas_ua,
    u.bezeichnung         AS v_uas_bezeichnung,
    p.bez_stadtbezirk     AS v_bez_stadtbezirk,
    s.bezeichnung         AS v_bez_bezeichnung,
    p.krisofp             AS v_krisofp,
    a.antragsdatum        AS v_antragsdatum,
    a.antragstyp          AS v_antragstyp,
    a.geskosten           AS v_geskosten,
    a.zwfkosten           AS v_zwfkosten,
    a.vorzbeg             AS v_vorzbeg,
    a.vbdatum             AS v_vbdatum,
    a.unbeddat            AS v_unbeddat,
    a.unbedja             AS v_unbedja,
    a.unbedbis            AS v_unbedbis,
    a.a_su_z              AS v_a_su_z,
    a.a_su_d              AS v_a_su_d,
    a.a_su_k              AS v_a_su_k,
    a.b_vor_su_z          AS v_b_vor_su_z,
    a.b_vor_su_d          AS v_b_vor_su_d,
    a.b_vor_su_k          AS v_b_vor_su_k,
    a.notizen             AS v_notizen,
    p.sgt_siedlungsgebiet AS v_sgt_siedlungsgebiet,
    i.bezeichnung         AS v_sgt_bezeichnung,
    p.bpg_bauprogramm     AS v_bpg_bauprogramm,
    o.bezeichnung         AS v_bpg_bezeichnung,
    b.id                  AS v_bid,
    b.geszuwendungen      AS v_bgeszuwendungen,
    b.bdatum              AS v_bdatum
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_antraege a ON p.projnr = a.pro_projnr
         JOIN fp_bewilligungen b ON a.id = b.ant_id
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_bauprogramme o ON p.bpg_bauprogramm = o.bauprogramm
         LEFT JOIN fp_siedlungsgebiete i ON p.sgt_siedlungsgebiet = i.siedlungsgebiet
WHERE b.id = (SELECT MAX(b1.id) FROM fp_bewilligungen b1 WHERE b1.ant_id = a.id)

UNION ALL

SELECT
--  2. Select: Antrag noch ohne Bewilligung --> liefert null für BEW Columns
a.id,
a.pro_projnr,
p.vndat,
p.fob_fb,
f.bezeichnung,
p.pname,
p.pstrasse,
p.kauf,
p.kur_kurzbez,
z.bezeichnung,
p.uas_ua,
u.bezeichnung,
p.bez_stadtbezirk,
s.bezeichnung,
p.krisofp,
a.antragsdatum,
a.antragstyp,
a.geskosten,
a.zwfkosten,
a.vorzbeg,
a.vbdatum,
a.unbeddat,
a.unbedja,
a.unbedbis,
a.a_su_z,
a.a_su_d,
a.a_su_k,
a.b_vor_su_z,
a.b_vor_su_d,
a.b_vor_su_k,
a.notizen,
p.sgt_siedlungsgebiet,
i.bezeichnung,
p.bpg_bauprogramm,
o.bezeichnung,
NULL, --B.ID,
NULL, --B.GESZUWENDUNGEN,
NULL  --B.BDATUM
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_antraege a ON p.projnr = a.pro_projnr
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_bauprogramme o ON p.bpg_bauprogramm = o.bauprogramm
         LEFT JOIN fp_siedlungsgebiete i ON p.sgt_siedlungsgebiet = i.siedlungsgebiet
WHERE NOT EXISTS (SELECT 1 FROM fp_bewilligungen b WHERE b.ant_id = a.id)
;
--------------------------------------------------------
--  DDL for View FP_V_ANTRAG_UNBED
--------------------------------------------------------

CREATE VIEW fp_v_antrag_unbed AS
SELECT pro_projnr    AS v_pro_projnr,
       MIN(unbeddat) AS v_unbeddat,
       MIN(unbedja)  AS v_unbedja,
       MIN(unbedbis) AS v_unbedbis,
       MIN(vbdatum)  AS v_vbdatum
FROM fp_antraege
GROUP BY pro_projnr
;
--------------------------------------------------------
--  DDL for View FP_V_BEWILL_ABRUFE
--------------------------------------------------------

CREATE VIEW fp_v_bewill_abrufe AS
SELECT
    -- Projekt
    p.projnr                    AS v_projnr,
    p.fob_fb                    AS v_fob_fb,
    f.bezeichnung               AS v_fob_bezeichnung,
    p.pname                     AS v_pname,
    p.pstrasse                  AS v_pstrasse,
    p.kur_kurzbez               AS v_kur_kurzbez,
    z.bezeichnung               AS v_kur_bezeichnung,
    p.uas_ua                    AS v_uas_ua,
    u.bezeichnung               AS v_uas_bezeichnung,
    p.bez_stadtbezirk           AS v_bez_stadtbezirk,
    s.bezeichnung               AS v_bez_bezeichnung,
    p.krisofp                   AS v_krisofp,
    p.vndat                     AS v_vndat,
    -- Abruf
    SUM(COALESCE(a.abruf_z, 0)) AS v_sum_abruf_z,
    SUM(COALESCE(a.abruf_d, 0)) AS v_sum_abruf_d,
    SUM(COALESCE(a.abruf_k, 0)) AS v_sum_abruf_k,
    MIN(a.abruf_datum)          AS v_min_abruf_datum,
    SUM(COALESCE(a.erh_z, 0))   AS v_sum_erh_z,
    SUM(COALESCE(a.erh_d, 0))   AS v_sum_erh_d,
    SUM(COALESCE(a.erh_k, 0))   AS v_sum_erh_k,
    MIN(a.erh_datum)            AS v_min_erh_datum,
    MIN(a.fipo)                 AS v_min_fipo,
    -- Bewilligung
    b.id                        AS v_id,
    b.ant_id                    AS v_ant_id,
    b.bdatum                    AS v_bdatum,
    b.afsatz                    AS v_afsatz,
    b.bfsatz                    AS v_bfsatz,
    b.bzwfkosten                AS v_bzwfkosten,
    b.bzuwendung_z              AS v_bzuwendung_z,
    b.bzuwendung_d              AS v_bzuwendung_d,
    b.bzuwendung_k              AS v_bzuwendung_k,
    b.bzuwart                   AS v_bzuwart,
    b.baktenzeichen             AS v_baktenzeichen,
    b.geszuwendungen            AS v_geszuwendungen,
    b.geskonnex                 AS v_geskonnex,
    b.krw                       AS v_krw
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_abrufe a ON b.id = a.bwi_id
GROUP BY p.projnr,
         p.fob_fb,
         f.bezeichnung,
         p.pname,
         p.pstrasse,
         p.kur_kurzbez,
         z.bezeichnung,
         p.uas_ua,
         u.bezeichnung,
         p.bez_stadtbezirk,
         s.bezeichnung,
         p.krisofp,
         p.vndat,
         b.id,
         b.ant_id,
         b.bdatum,
         b.afsatz,
         b.bfsatz,
         b.bzwfkosten,
         b.bzuwendung_z,
         b.bzuwendung_d,
         b.bzuwendung_k,
         b.bzuwart,
         b.baktenzeichen,
         b.geszuwendungen,
         b.geskonnex,
         b.krw
;
--------------------------------------------------------
--  DDL for View FP_V_BEWILLSUCHE
--------------------------------------------------------

CREATE VIEW fp_v_bewillsuche AS
SELECT p.projnr              AS v_projnr,
       p.fob_fb              AS v_fob_fb,
       f.bezeichnung         AS v_fob_bezeichnung,
       p.pname               AS v_pname,
       p.pstrasse            AS v_pstrasse,
       p.kur_kurzbez         AS v_kur_kurzbez,
       z.bezeichnung         AS v_kur_bezeichnung,
       p.uas_ua              AS v_uas_ua,
       u.bezeichnung         AS v_uas_bezeichnung,
       p.bez_stadtbezirk     AS v_bez_stadtbezirk,
       s.bezeichnung         AS v_bez_bezeichnung,
       p.krisofp             AS v_krisofp,
       p.vndat               AS v_vndat,
       -- Antrag
       a.antragsdatum        AS v_antragsdatum,
       a.antragstyp          AS v_antragstyp,
       a.geskosten           AS v_geskosten,
       a.zwfkosten           AS v_zwfkosten,
       a.a_su_z              AS v_a_su_z,
       a.a_su_d              AS v_a_su_d,
       a.a_su_k              AS v_a_su_k,
       a.b_vor_su_z          AS v_b_vor_su_z,
       a.b_vor_su_d          AS v_b_vor_su_d,
       a.b_vor_su_k          AS v_b_vor_su_k,
       -- Bewilligung
       b.id                  AS v_id,
       b.ant_id              AS v_ant_id,
       b.bdatum              AS v_bdatum,
       b.afsatz              AS v_afsatz,
       b.bfsatz              AS v_bfsatz,
       b.bzwfkosten          AS v_bzwfkosten,
       b.bzuwendung_z        AS v_bzuwendung_z,
       b.bzuwendung_d        AS v_bzuwendung_d,
       b.bzuwendung_k        AS v_bzuwendung_k,
       b.bzuwart             AS v_bzuwart,
       b.baktenzeichen       AS v_baktenzeichen,
       b.geszuwendungen      AS v_geszuwendungen,
       b.geskonnex           AS v_geskonnex,
       b.krw                 AS v_krw,
       b.notizen             AS v_notizen,
       -- SIEDLUNGSGEBIET BAUPROGRAMM
       p.sgt_siedlungsgebiet AS v_sgt_siedlungsgebiet,
       i.bezeichnung         AS v_sgt_bezeichnung,
       p.bpg_bauprogramm     AS v_bpg_bauprogramm,
       o.bezeichnung         AS v_bpg_bezeichnung
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_antraege a ON b.ant_id = a.id
         LEFT JOIN fp_bauprogramme o ON p.bpg_bauprogramm = o.bauprogramm
         LEFT JOIN fp_siedlungsgebiete i ON p.sgt_siedlungsgebiet = i.siedlungsgebiet
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN1
--------------------------------------------------------

CREATE VIEW fp_v_checklisten1 AS
SELECT '1 Offene Projekte ohne Förderantrag' AS v_hinweis,
       p.projnr                              AS v_projnr,
       p.pstrasse                            AS v_pstrasse,
       NULL                                  AS v_info
FROM fp_projekte p
WHERE p.vndat IS NULL
  AND NOT EXISTS (SELECT 1 FROM fp_antraege a WHERE p.projnr = a.pro_projnr)

UNION ALL

SELECT '2 Offene Projekte mit Anträgen auf Unbedenklichkeit ohne Förderantrag',
       p.projnr,
       p.pstrasse,
       CONCAT('Unbedenk. am: ', TO_CHAR(a.unbeddat, 'DD.MM.YYYY'))
FROM fp_antraege a
         JOIN fp_projekte p ON a.pro_projnr = p.projnr
WHERE a.unbeddat IS NOT NULL
  AND a.antragsdatum IS NULL
  AND p.vndat IS NULL

UNION ALL

SELECT '3 Offene Projekte und ausstehende Genehmigungen zum vorzeitigen Baubeginn',
       p.projnr,
       p.pstrasse,
       CONCAT('Vorz. Baubeginn: ', a.vorzbeg)
FROM fp_antraege a
         JOIN fp_projekte p ON p.projnr = a.pro_projnr
WHERE p.vndat IS NULL
  AND a.vorzbeg IS TRUE
  AND a.vbdatum IS NULL

UNION ALL

SELECT '4 Offene Projekte und ausstehende Bewilligungen (ohne Datum)',
       p.projnr,
       p.pstrasse,
       'Bewilligung ausstehend'
FROM fp_projekte p
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
WHERE p.vndat IS NULL
  AND b.bdatum IS NULL

UNION ALL

SELECT '5 Offene Projekte und Bewilligungen ohne Verknüpfung zu einem Antrag',
       p.projnr,
       p.pstrasse,
       NULL
FROM fp_projekte p
         JOIN fp_bewilligungen b ON b.pro_projnr = p.projnr
WHERE p.vndat IS NULL
  AND b.ant_id IS NULL

UNION ALL

SELECT '6 Offene Projekte und Abrufe ohne Verknüpfung zu einer Bewilligung',
       p.projnr,
       p.pstrasse,
       NULL
FROM fp_projekte p
         JOIN fp_abrufe a ON p.projnr = a.pro_projnr
WHERE a.bwi_id IS NULL
  AND p.vndat IS NULL

UNION ALL

SELECT '7 Offene Projekte und leere Bewilligungen',
       p.projnr,
       p.pstrasse,
       CONCAT('Leer angelegt am: ', TO_CHAR(b.anlagedatum, 'DD.MM.YYYY'))
FROM fp_projekte p
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
WHERE p.vndat IS NULL
  AND b.bdatum IS NULL
  AND COALESCE(b.afsatz, 0) = 0
  AND COALESCE(b.bfsatz, 0) = 0
  AND COALESCE(b.bzwfkosten, 0) = 0
  AND COALESCE(b.bzuwendung_z, 0) = 0
  AND COALESCE(b.bzuwendung_d, 0) = 0
  AND COALESCE(b.bzuwendung_k, 0) = 0
  AND b.bzuwart IS NULL
  AND b.baktenzeichen IS NULL
  AND COALESCE(b.geszuwendungen, 0) = 0
  AND COALESCE(b.geskonnex, 0) = 0
  AND b.krw IS NULL
  AND b.aenderungsdatum IS NULL
  AND b.aenderungvon IS NULL

UNION ALL

SELECT '8 Projekte mit VN-Datum ab 1.1.2000 und leere Bewilligungen',
       p.projnr,
       p.pstrasse,
       CONCAT('Leer angelegt am: ', TO_CHAR(b.anlagedatum, 'DD.MM.YYYY'))
FROM fp_projekte p
         JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
WHERE p.vndat >= TO_DATE('01.01.2000', 'dd.mm.yyyy')
  AND b.bdatum IS NULL
  AND COALESCE(b.afsatz, 0) = 0
  AND COALESCE(b.bfsatz, 0) = 0
  AND COALESCE(b.bzwfkosten, 0) = 0
  AND COALESCE(b.bzuwendung_z, 0) = 0
  AND COALESCE(b.bzuwendung_d, 0) = 0
  AND COALESCE(b.bzuwendung_k, 0) = 0
  AND b.bzuwart IS NULL
  AND b.baktenzeichen IS NULL
  AND COALESCE(b.geszuwendungen, 0) = 0
  AND COALESCE(b.geskonnex, 0) = 0
  AND b.krw IS NULL
  AND b.aenderungsdatum IS NULL
  AND b.aenderungvon IS NULL

UNION ALL

SELECT '9 Projekte mit VN-Datum ab 1.1.2000 aber ohne Schlußbescheid',
       p.projnr,
       p.pstrasse,
       CONCAT('VN: ', TO_CHAR(p.vndat, 'DD.MM.YYYY'))
FROM fp_projekte p
WHERE p.vndat >= TO_DATE('01.01.2000', 'DD.MM.YYYY')
  AND p.vnschlussbew IS NULL

UNION ALL

SELECT '10 Projekte mit VN-Datum ab 1.1.2000 aber ohne Endkosten',
       p.projnr,
       p.pstrasse,
       CONCAT('VN: ', TO_CHAR(p.vndat, 'DD.MM.YYYY'))
FROM fp_projekte p
WHERE p.vndat >= TO_DATE('01.01.2000', 'DD.MM.YYYY')
  AND p.vnkosten IS NULL

ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_CHECKLISTEN2
--------------------------------------------------------

CREATE VIEW fp_v_checklisten2 AS
SELECT '1 Verwendungsnachweise ohne VN Datum'                                          AS v_fehler,
       p.projnr                                                                        AS v_projnr,
       p.pstrasse                                                                      AS v_pstrasse,
       CONCAT_WS(' ', 'VN ZWF KOSTEN:', p.vnzwfkosten, 'VN Gesamtkosten:', p.vnkosten) AS v_info
FROM fp_projekte p
WHERE p.vndat IS NULL
  AND (p.vnzwfkosten > 0 OR p.vnkosten > 0)

UNION ALL

SELECT '2 Erfolgte Bewilligungen ohne Bewilligungsdatum',
       p.projnr,
       p.pstrasse,
       CONCAT('Summe Zuwendungen: ',
              (COALESCE(b.bzuwendung_z, 0) + COALESCE(b.bzuwendung_d, 0) + COALESCE(b.bzuwendung_k, 0)))
FROM fp_bewilligungen b
         JOIN fp_projekte p ON b.pro_projnr = p.projnr
WHERE b.bdatum IS NULL
  AND (b.bzuwendung_z > 0 OR b.bzuwendung_d > 0 OR b.bzuwendung_k > 0)

UNION ALL

SELECT '3 Erfolgte Abrufe ohne Abrufdatum',
       p.projnr,
       p.pstrasse,
       CONCAT('Summe Abrufe: ', (a.abruf_z + a.abruf_d + a.abruf_k))
FROM fp_abrufe a
         JOIN fp_projekte p ON a.pro_projnr = p.projnr
WHERE a.abruf_datum IS NULL
  AND (a.abruf_z > 0 OR a.abruf_d > 0 OR a.abruf_k > 0)

UNION ALL

SELECT '4 Erhaltene Abrufe ohne Erhaltdatum',
       p.projnr,
       p.pstrasse,
       CONCAT('Summe erhalten: ', (a.erh_z + a.erh_d + a.erh_k))
FROM fp_abrufe a
         JOIN fp_projekte p ON a.pro_projnr = p.projnr
WHERE a.erh_datum IS NULL
  AND (a.erh_z > 0 OR a.erh_d > 0 OR a.erh_k > 0)

UNION ALL

SELECT '5 Anträge ohne Antragsdatum und ohne Unbedenklichkeitsantrag',
       p.projnr,
       p.pstrasse,
       NULL
FROM fp_antraege a
         JOIN fp_projekte p ON a.pro_projnr = p.projnr
WHERE a.unbeddat IS NULL
  AND a.antragsdatum IS NULL
  AND p.vndat IS NULL

ORDER BY 1, 2
;
--------------------------------------------------------
--  DDL for View FP_V_EUINFORMATIONEN
--------------------------------------------------------

CREATE VIEW fp_v_euinformationen AS
SELECT e.jahr         AS v_jahr,
       e.hefta        AS v_hefta,
       e.nummer       AS v_nummer,
       e.seitennr     AS v_seitennr,
       e.pub_kurzform AS v_pub_kurzform,
       p.bezeichnung  AS v_bezeichnung,
       e.inhalt       AS v_inhalt,
       e.infodat      AS v_infodat,
       1              AS v_refid,
       'RAW'          AS v_refbez
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.rawi IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       2,
       'RBS'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.schulref IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       3,
       'Sozref R 5'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.sozref_r_5 IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       4,
       'RGU 11'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.rgu_11 IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       5,
       'RGU Cs'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.rgu_cs IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       6,
       'Krh'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.krh IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       7,
       'AWB'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.afa IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       8,
       'SWM'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.swm IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       9,
       'Kulturreferat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.kulturref IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       10,
       'Baureferat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.bauref IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       11,
       'Planungsreferat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.planref IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       12,
       'Direktorium'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.direktorium IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       13,
       'Personal- und Org.referat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.por IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       14,
       'Kreisverwaltungsreferat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.kvr IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       15,
       'Kommunalreferat'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.kommref IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       16,
       'MSE'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.sew IS TRUE

UNION ALL

SELECT e.jahr,
       e.hefta,
       e.nummer,
       e.seitennr,
       e.pub_kurzform,
       p.bezeichnung,
       e.inhalt,
       e.infodat,
       17,
       'Stk'
FROM fp_euinformationen e
         JOIN fp_publikationen p ON e.pub_kurzform = p.kurzform
WHERE e.stk IS TRUE
;
--------------------------------------------------------
--  DDL for View FP_V_FAGSTAT
--------------------------------------------------------

CREATE VIEW fp_v_fagstat AS
SELECT x1                   AS f_fb,
       x2                   AS f_bezeichnung,
       x3                   AS f_jahr,
       SUM(COALESCE(x4, 0)) AS b_bewill,
       SUM(COALESCE(x5, 0)) AS b_rueck,
       SUM(COALESCE(x6, 0)) AS e_erhalten,
       SUM(COALESCE(x7, 0)) AS e_rueck
FROM
    -- bewilligter Zuschuss ohne Rückzahlungen
    (SELECT f.fb                             AS x1,
            f.bezeichnung                    AS x2,
            EXTRACT(YEAR FROM b.bdatum)      AS x3,
            SUM(COALESCE(b.bzuwendung_z, 0)) AS x4,
            0                                AS x5,
            0                                AS x6,
            0                                AS x7
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_z >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     -- bewilligtes Darlehen ohne Rückzahlungen
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM b.bdatum),
            SUM(COALESCE(b.bzuwendung_d, 0)),
            0,
            0,
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_d >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     -- bewilligte Kostenerstattung ohne Rückzahlungen
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM b.bdatum),
            SUM(COALESCE(b.bzuwendung_k, 0)),
            0,
            0,
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_k >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     --  Bewilligter Zuschuss und rückgefordert
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM b.bdatum),
            0,
            SUM(COALESCE(b.bzuwendung_z, 0)),
            0,
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_z < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     --  Bewilligtes Darlehen und rückgefordert
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM b.bdatum),
            0,
            SUM(COALESCE(b.bzuwendung_d, 0)),
            0,
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_d < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     --  Bewilligte Kostenerstattung und rückgefordert
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM b.bdatum),
            0,
            SUM(COALESCE(b.bzuwendung_k, 0)),
            0,
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
     WHERE b.bdatum IS NOT NULL
       AND b.bzuwendung_k < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM b.bdatum)

     UNION

     -- Erhalten Zuschuss ohne Rückzahlung
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            SUM(a.erh_z),
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr

     WHERE a.erh_datum IS NOT NULL
       AND a.erh_z >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)

     UNION

     -- erhalten Darlehen ohne Rückzahlung
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            SUM(a.erh_d),
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr
     WHERE a.erh_datum IS NOT NULL
       AND a.erh_d >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)

     UNION

     -- erhalten Kostenerstattung ohne Rückzahlung
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            SUM(a.erh_k),
            0
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr
     WHERE a.erh_datum IS NOT NULL
       AND a.erh_k >= 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)

     UNION

     -- Erhalten Rückzahlung von Zuschüssen
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            0,
            SUM(a.erh_z)
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr
     WHERE a.erh_datum IS NOT NULL
       AND a.erh_z < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)

     UNION

     -- erhalten Rückzahlung von Darlehen
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            0,
            SUM(a.erh_d)
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr
     WHERE a.erh_datum IS NOT NULL
       AND a.erh_d < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)

     UNION

     -- erhalten Rückzahlung von Kostenerstattungen
     SELECT f.fb,
            f.bezeichnung,
            EXTRACT(YEAR FROM a.erh_datum),
            0,
            0,
            0,
            SUM(a.erh_k)
     FROM fp_projekte p
              JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
              JOIN fp_abrufe a ON p.projnr = a.pro_projnr
     WHERE a.erh_datum IS NOT NULL
       AND a.erh_k < 0
     GROUP BY f.fb,
              f.bezeichnung,
              EXTRACT(YEAR FROM a.erh_datum)) AS x

GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_FBSTAT
--------------------------------------------------------

CREATE VIEW fp_v_fbstat AS
SELECT x1                   AS f_fb,
       x2                   AS f_bezeichnung,
       x3                   AS f_jahr,
       SUM(COALESCE(x4, 0)) AS b_bzuwendung_z,
       SUM(COALESCE(x5, 0)) AS b_bzuwendung_d,
       SUM(COALESCE(x6, 0)) AS b_bzuwendung_k,
       SUM(COALESCE(x7, 0)) AS a_erh_z,
       SUM(COALESCE(x8, 0)) AS a_erh_d,
       SUM(COALESCE(x9, 0)) AS a_erh_k
FROM (SELECT f.fb                             AS x1,
             f.bezeichnung                    AS x2,
             EXTRACT(YEAR FROM b.bdatum)      AS x3,
             SUM(COALESCE(b.bzuwendung_z, 0)) AS x4,
             SUM(COALESCE(b.bzuwendung_d, 0)) AS x5,
             SUM(COALESCE(b.bzuwendung_k, 0)) AS x6,
             NULL                             AS x7,
             NULL                             AS x8,
             NULL                             AS x9
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.erh_datum),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(a.erh_z, 0)),
             SUM(COALESCE(a.erh_d, 0)),
             SUM(COALESCE(a.erh_k, 0))
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_abrufe a ON p.projnr = a.pro_projnr
      WHERE a.erh_datum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.erh_datum)) AS x
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_ISTKOSTENMAX
--------------------------------------------------------

CREATE VIEW fp_v_istkostenmax AS
SELECT x1               AS p_projnr,
       x2               AS p_max_istdatum,
       SUBSTR(x2, 1, 4) AS p_jahr,
       SUBSTR(x2, 5, 2) AS p_monat,
       x3               AS p_istkosten
FROM (SELECT i1.pro_projnr                                 AS x1,
             CONCAT(i1.jahr, LPAD(i1.monat::TEXT, 2, '0')) AS x2,
             i1.istkosten                                  AS x3
      FROM fp_projektistkosten i1
      WHERE CONCAT(i1.jahr, LPAD(i1.monat::TEXT, 2, '0')) =
            (SELECT MAX(CONCAT(i2.jahr, LPAD(i2.monat::TEXT, 2, '0')))
             FROM fp_projektistkosten i2
             WHERE i2.pro_projnr = i1.pro_projnr)) AS x
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK1
--------------------------------------------------------

CREATE VIEW fp_v_jahresstatistik1 AS
SELECT x1                    AS f_fb,
       x2                    AS f_bezeichnung,
       x3                    AS f_jahr,
       SUM(COALESCE(x4, 0))  AS b_bzuwendung_z,
       SUM(COALESCE(x5, 0))  AS b_bzuwendung_d,
       SUM(COALESCE(x6, 0))  AS b_bzuwendung_k,
       SUM(COALESCE(x7, 0))  AS a_erh_z,
       SUM(COALESCE(x8, 0))  AS a_erh_d,
       SUM(COALESCE(x9, 0))  AS a_erh_k,
       SUM(COALESCE(x10, 0)) AS e_geskosten,
       SUM(COALESCE(x11, 0)) AS e_zwfkosten
FROM (SELECT f.fb                             AS x1,
             f.bezeichnung                    AS x2,
             EXTRACT(YEAR FROM b.bdatum)      AS x3,
             SUM(COALESCE(b.bzuwendung_z, 0)) AS x4,
             SUM(COALESCE(b.bzuwendung_d, 0)) AS x5,
             SUM(COALESCE(b.bzuwendung_k, 0)) AS x6,
             NULL::NUMERIC                    AS x7,
             NULL::NUMERIC                    AS x8,
             NULL::NUMERIC                    AS x9,
             NULL::NUMERIC                    AS x10,
             NULL::NUMERIC                    AS x11
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.erh_datum),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(a.erh_z, 0)),
             SUM(COALESCE(a.erh_d, 0)),
             SUM(COALESCE(a.erh_k, 0)),
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_abrufe a ON p.projnr = a.pro_projnr
      WHERE a.erh_datum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.erh_datum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.antragsdatum),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(a.geskosten, 0)),
             SUM(COALESCE(a.zwfkosten, 0))
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_antraege a ON p.projnr = a.pro_projnr
      WHERE a.antragstyp = 'E'
        AND a.antragsdatum =
            (SELECT MIN(y.antragsdatum)
             FROM fp_antraege y
             WHERE y.pro_projnr = p.projnr
               AND y.antragstyp = 'E')
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.antragsdatum)) AS x
GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK2
--------------------------------------------------------

CREATE VIEW fp_v_jahresstatistik2 AS
SELECT x1                    AS f_fb,
       x2                    AS f_bezeichnung,
       x3                    AS f_jahr,
       SUM(COALESCE(x4, 0))  AS v_anzahl_abrufe,
       SUM(COALESCE(x5, 0))  AS v_anzahl_vn,
       SUM(COALESCE(x6, 0))  AS v_anzahl_bewilligungen,
       SUM(COALESCE(x7, 0))  AS v_vngeskosten,
       SUM(COALESCE(x8, 0))  AS v_anzahl_erst,
       SUM(COALESCE(x9, 0))  AS v_a_su_z_erst,
       SUM(COALESCE(x10, 0)) AS v_a_su_k_erst,
       SUM(COALESCE(x11, 0)) AS v_anzahl_folge,
       SUM(COALESCE(x12, 0)) AS v_a_su_z_folge,
       SUM(COALESCE(x13, 0)) AS v_a_su_k_folge,
       SUM(COALESCE(x14, 0)) AS v_a_vor_su_z_gesamt,
       SUM(COALESCE(x15, 0)) AS v_a_vor_su_k_gesamt,
       SUM(COALESCE(x16, 0)) AS v_anzahl_unbed
FROM (SELECT f.fb                             AS x1,
             f.bezeichnung                    AS x2,
             EXTRACT(YEAR FROM a.abruf_datum) AS x3,
             COUNT(*)                         AS x4,
             NULL::BIGINT                     AS x5,
             NULL::BIGINT                     AS x6,
             NULL::NUMERIC                    AS x7,
             NULL::BIGINT                     AS x8,
             NULL::NUMERIC                    AS x9,
             NULL::NUMERIC                    AS x10,
             NULL::BIGINT                     AS x11,
             NULL::NUMERIC                    AS x12,
             NULL::NUMERIC                    AS x13,
             NULL::NUMERIC                    AS x14,
             NULL::NUMERIC                    AS x15,
             NULL::BIGINT                     AS x16
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_abrufe a ON p.projnr = a.pro_projnr
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.abruf_datum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM p.vndat),
             NULL,
             COUNT(*),
             NULL,
             SUM(p.vnkosten),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
      WHERE p.vndat IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM p.vndat)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
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
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.antragsdatum),
             NULL,
             NULL,
             NULL,
             NULL,
             COUNT(*),
             SUM(a.a_su_z),
             SUM(a.a_su_k),
             NULL,
             NULL,
             NULL,
             SUM(a.b_vor_su_z),
             SUM(a.b_vor_su_k),
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_antraege a ON p.projnr = a.pro_projnr
      WHERE a.antragstyp = 'E'
        AND a.antragsdatum =
            (SELECT MIN(y.antragsdatum)
             FROM fp_antraege y
             WHERE y.pro_projnr = p.projnr
               AND y.antragstyp = 'E')
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.antragsdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.antragsdatum),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             COUNT(*),
             SUM(a.a_su_z),
             SUM(a.a_su_k),
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_antraege a ON p.projnr = a.pro_projnr
      WHERE a.antragstyp = 'F' -- nicht A und nicht V
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.antragsdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.unbeddat),
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
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_antraege a ON p.projnr = a.pro_projnr
      WHERE a.unbeddat IS NOT NULL
        AND a.antragstyp = 'E'
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.unbeddat)) AS x

GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_JAHRESSTATISTIK3
--------------------------------------------------------

CREATE VIEW fp_v_jahresstatistik3 AS
SELECT x1                    AS f_fb,
       x2                    AS f_bezeichnung,
       x3                    AS f_jahr,
       SUM(COALESCE(x4, 0))  AS v_bzuwendung_z_plus,
       SUM(COALESCE(x5, 0))  AS v_bzuwendung_d_plus,
       SUM(COALESCE(x6, 0))  AS v_bzuwendung_k_plus,
       SUM(COALESCE(x7, 0))  AS v_bzuwendung_z_minus,
       SUM(COALESCE(x8, 0))  AS v_bzuwendung_d_minus,
       SUM(COALESCE(x9, 0))  AS v_bzuwendung_k_minus,
       SUM(COALESCE(x10, 0)) AS v_erh_z,
       SUM(COALESCE(x11, 0)) AS v_erh_d,
       SUM(COALESCE(x12, 0)) AS v_erh_k
FROM (SELECT f.fb                             AS x1,
             f.bezeichnung                    AS x2,
             EXTRACT(YEAR FROM b.bdatum)      AS x3,
             SUM(COALESCE(b.bzuwendung_z, 0)) AS x4,
             NULL::NUMERIC                    AS x5,
             NULL::NUMERIC                    AS x6,
             NULL::NUMERIC                    AS x7,
             NULL::NUMERIC                    AS x8,
             NULL::NUMERIC                    AS x9,
             NULL::NUMERIC                    AS x10,
             NULL::NUMERIC                    AS x11,
             NULL::NUMERIC                    AS x12
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_z >= 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
             NULL,
             SUM(COALESCE(b.bzuwendung_d, 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_d >= 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
             NULL,
             NULL,
             SUM(COALESCE(b.bzuwendung_k, 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_k >= 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(b.bzuwendung_z, 0)),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_z < 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(b.bzuwendung_d, 0)),
             NULL,
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_d < 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM b.bdatum),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(b.bzuwendung_k, 0)),
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr
      WHERE b.bdatum IS NOT NULL
        AND b.bzuwendung_k < 0
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM b.bdatum)

      UNION

      SELECT f.fb,
             f.bezeichnung,
             EXTRACT(YEAR FROM a.erh_datum),
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             SUM(COALESCE(a.erh_z, 0)),
             SUM(COALESCE(a.erh_d, 0)),
             SUM(COALESCE(a.erh_k, 0))
      FROM fp_projekte p
               JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
               JOIN fp_abrufe a ON p.projnr = a.pro_projnr
      WHERE a.erh_datum IS NOT NULL
      GROUP BY f.fb,
               f.bezeichnung,
               EXTRACT(YEAR FROM a.erh_datum)) AS x

GROUP BY x1,
         x2,
         x3
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTERSTANTRAG
--------------------------------------------------------

CREATE VIEW fp_v_projekterstantrag AS
SELECT p.projnr        AS p_projnr,
       p.fob_fb        AS p_fob_fb,
       p.pname         AS p_pname,
       p.pstrasse      AS p_pstrasse,
       a1.antragstyp   AS a1_antragstyp,
       a1.vbdatum      AS a1_vbdatum,
       a1.vorzbeg      AS a1_vorzbeg,
       p.vndat         AS p_vndat,
       a1.geskosten    AS a1_geskosten,
       p.vnkosten      AS p_vngeskosten,
       p.vnpruefzwf    AS p_vnpruefzwf,
       p.vnpruefdat    AS p_vnpruefdat,
       p.zinsen        AS p_zinsen,
       a1.antragsdatum AS a1_antragsdatum,
       a1.zwfkosten    AS a1_zwfkosten,
       a1.a_su_z       AS a1_a_su_z,
       a1.a_su_d       AS a1_a_su_d,
       a1.a_su_k       AS a1_a_su_k,
       a1.b_vor_su_z   AS a1_b_vor_su_z,
       a1.b_vor_su_d   AS a1_b_vor_su_d,
       a1.b_vor_su_k   AS a1_b_vor_su_k,
       a1.notizen      AS a1_notizen
FROM fp_projekte p
         JOIN fp_antraege a1 ON p.projnr = a1.pro_projnr
WHERE a1.antragstyp IN ('E', 'A')
  AND a1.id = (SELECT MAX(id)
               FROM fp_antraege y
               WHERE y.pro_projnr = p.projnr
                 AND y.antragstyp IN ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTFLUESSE
--------------------------------------------------------

CREATE VIEW fp_v_projektfluesse AS
SELECT x1                    AS p_projnr,
       SUM(COALESCE(x2, 0))  AS ax_a_su_z,
       SUM(COALESCE(x3, 0))  AS ax_a_su_d,
       SUM(COALESCE(x4, 0))  AS ax_a_su_k,
       SUM(COALESCE(x5, 0))  AS r_erh_z,
       SUM(COALESCE(x6, 0))  AS r_erh_d,
       SUM(COALESCE(x7, 0))  AS r_erh_k,
       SUM(COALESCE(x8, 0))  AS b_bewill_z,
       SUM(COALESCE(x9, 0))  AS b_bewill_d,
       SUM(COALESCE(x10, 0)) AS b_bewill_k
FROM (SELECT p.projnr              AS x1,
             COALESCE(a.a_su_z, 0) AS x2,
             COALESCE(a.a_su_d, 0) AS x3,
             COALESCE(a.a_su_k, 0) AS x4,
             NULL::NUMERIC         AS x5,
             NULL::NUMERIC         AS x6,
             NULL::NUMERIC         AS x7,
             NULL::NUMERIC         AS x8,
             NULL::NUMERIC         AS x9,
             NULL::NUMERIC         AS x10
      FROM fp_projekte p
               JOIN fp_antraege a ON p.projnr = a.pro_projnr

      UNION ALL

      SELECT p.projnr,
             NULL,
             NULL,
             NULL,
             COALESCE(r.erh_z, 0),
             COALESCE(r.erh_d, 0),
             COALESCE(r.erh_k, 0),
             NULL,
             NULL,
             NULL
      FROM fp_projekte p
               JOIN fp_abrufe r ON p.projnr = r.pro_projnr

      UNION ALL

      SELECT p.projnr,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             NULL,
             COALESCE(b.bzuwendung_z, 0),
             COALESCE(b.bzuwendung_d, 0),
             COALESCE(b.bzuwendung_k, 0)
      FROM fp_projekte p
               JOIN fp_bewilligungen b ON p.projnr = b.pro_projnr) AS x
GROUP BY x1
;
--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSTAT
--------------------------------------------------------

CREATE VIEW fp_v_projektstat AS
SELECT a.p_projnr        AS p_projnr,
       a.p_fob_fb        AS p_fob_fb,
       a.p_pname         AS p_pname,
       a.p_pstrasse      AS p_pstrasse,
       a.a1_antragstyp   AS a1_antragstyp,
       a.a1_vbdatum      AS a1_vbdatum,
       a.a1_vorzbeg      AS a1_vorzbeg,
       a.p_vndat         AS p_vndat,
       a.a1_geskosten    AS a1_geskosten,
       a.p_vngeskosten   AS p_vngeskosten,
       a.p_vnpruefzwf    AS p_vnpruefzwf,
       a.p_vnpruefdat    AS p_vnpruefdat,
       a.p_zinsen        AS p_zinsen,
       a.a1_antragsdatum AS a1_antragsdatum,
       a.a1_zwfkosten    AS a1_zwfkosten,
       a.a1_a_su_z       AS a1_a_su_z,
       a.a1_a_su_d       AS a1_a_su_d,
       a.a1_a_su_k       AS a1_a_su_k,
       a.a1_b_vor_su_z   AS a1_b_vor_su_z,
       a.a1_b_vor_su_d   AS a1_b_vor_su_d,
       a.a1_b_vor_su_k   AS a1_b_vor_su_k,
       a.a1_notizen      AS a1_notizen,
       f.ax_a_su_z       AS ax_a_su_z,
       f.ax_a_su_d       AS ax_a_su_d,
       f.ax_a_su_k       AS ax_a_su_k,
       f.r_erh_z         AS r_erh_z,
       f.r_erh_d         AS r_erh_d,
       f.r_erh_k         AS r_erh_k,
       f.b_bewill_z      AS b_bewill_z,
       f.b_bewill_d      AS b_bewill_d,
       f.b_bewill_k      AS b_bewill_k
FROM fp_v_projekterstantrag a
         JOIN fp_v_projektfluesse f ON a.p_projnr = f.p_projnr
;
--------------------------------------------------------
--  DDL for View FP_V_KINDER
--------------------------------------------------------

CREATE VIEW fp_v_kinder AS
SELECT p.projnr          AS p_projnr,
       p.fob_fb          AS p_fob_fb,
       f.bezeichnung     AS p_fob_bezeichnung,
       p.bez_stadtbezirk AS p_bez_stadtbezirk,
       z.bezeichnung     AS p_stadtbezirk,
       p.uas_ua          AS p_uas_ua,
       p.kur_kurzbez     AS p_kur_kurzbez,
       p.pname           AS p_pname,
       p.pstrasse        AS p_pstrasse,
       p.vndat           AS p_vndat,
       p.vnkosten        AS p_vnkosten,
       p.krisofp         AS p_krisofp,
       p.kripplatz       AS p_kripplatz,
       p.kigaplatz       AS p_kigaplatz,
       p.hortplatz       AS p_hortplatz,
       s.a1_antragstyp   AS a1_antragstyp,
       s.a1_antragsdatum AS a1_antragsdatum,
       s.a1_geskosten    AS a1_geskosten,
       s.r_erh_z         AS p_erh_z,
       s.b_bewill_z      AS p_bzuwendung_z
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         LEFT JOIN fp_v_projektstat s ON p.projnr = s.p_projnr
         LEFT JOIN fp_stadtbezirke z ON p.bez_stadtbezirk = z.stadtbezirk
;
--------------------------------------------------------
--  DDL for View FP_V_MITTELEINPLANUNG
--------------------------------------------------------

CREATE VIEW fp_v_mitteleinplanung AS
SELECT b1.id             AS b_id,
       b1.pro_projnr     AS b_projnr,
       b1.ant_id         AS b_ant_id,
       b1.bdatum         AS b_bdatum,
       b1.afsatz         AS b_afsatz,
       b1.bfsatz         AS b_bfsatz,
       b1.bzuwendung_z   AS b_bzuwendung_z,
       b1.bzwfkosten     AS b_bzwfkosten,
       b1.krw            AS b_krw,
       b1.bzuwart        AS b_zuwart,
       b1.baktenzeichen  AS b_baktenzeichen,
       b1.geszuwendungen AS b_geszuwendungen,
       b1.geskonnex      AS b_geskonnex,
       b1.notizen        AS b_notizen,
       a1.antragstyp     AS a_antragstyp,
       a1.antragsdatum   AS a_antragsdatum,
       a1.geskosten      AS a_geskosten
FROM fp_bewilligungen b1
         JOIN fp_antraege a1 ON a1.id = b1.ant_id
WHERE a1.antragstyp IN ('E', 'A')
  AND b1.id = (SELECT MAX(b2.id)
               FROM fp_bewilligungen b2
                        JOIN fp_antraege a2 ON a2.id = b2.ant_id
               WHERE b2.pro_projnr = b1.pro_projnr
                 AND a2.antragstyp IN ('E', 'A'))
;
--------------------------------------------------------
--  DDL for View FP_V_NOTIZEN
--------------------------------------------------------

CREATE VIEW fp_v_notizen AS
SELECT x0 AS v_typ,
       x1 AS v_projnr,
       x2 AS v_datum,
       x3 AS v_notizen
FROM (
         -- Typ Projekt
         SELECT 'Projekt' AS x0,
                p.projnr  AS x1,
                p.vndat   AS x2,
                p.notizen AS x3
         FROM fp_projekte p
         WHERE p.notizen IS NOT NULL

         UNION ALL

         -- Typ Antrag
         SELECT 'Antrag'       AS x0,
                a.pro_projnr   AS x1,
                a.antragsdatum AS x2,
                a.notizen      AS x3
         FROM fp_antraege a
         WHERE a.notizen IS NOT NULL

         UNION ALL

         -- Typ Bewilligungen
         SELECT 'Bewilligung' AS x0,
                b.pro_projnr  AS x1,
                b.bdatum      AS x2,
                b.notizen     AS x3
         FROM fp_bewilligungen b
         WHERE b.notizen IS NOT NULL

         UNION

         -- Typ Abruf
         SELECT 'Abruf'       AS x0,
                r.pro_projnr  AS x1,
                r.abruf_datum AS x2,
                r.notizen     AS x3
         FROM fp_abrufe r
         WHERE r.notizen IS NOT NULL

         UNION

         -- Typ Termin
         SELECT 'Termin'                                                                     AS x0,
                t.pro_projnr                                                                 AS x1,
                t.termin                                                                     AS x2,
                CONCAT(t.notizen, E'\r\nZuständig: ', t.zustaendig, ' Telefon: ', t.telefon) AS x3
         FROM fp_projekttermine t
         WHERE t.notizen IS NOT NULL) AS x
;

--------------------------------------------------------
--  DDL for View FP_V_PROJEKTSUCHE
--------------------------------------------------------

CREATE VIEW fp_v_projektsuche AS
SELECT p.projnr                 AS v_projnr,
       p.vndat                  AS v_vndat,
       p.fob_fb                 AS v_fob_fb,
       TO_CHAR(p.vndat, 'YYYY') AS v_vndat_jahr,
       TO_CHAR(p.vndat, 'MM')   AS v_vndat_monat,
       CASE
           WHEN p.jahr::INTEGER <= 49 THEN CONCAT('20', p.jahr)
           ELSE CONCAT('19', p.jahr)
           END                  AS v_jahr,
       p.pname                  AS v_pname,
       p.pstrasse               AS v_pstrasse,
       p.kauf                   AS v_kauf,
       p.projart                AS v_projart,
       f.bezeichnung            AS v_fob_bezeichnung,
       p.kur_kurzbez            AS v_kur_kurzbez,
       z.bezeichnung            AS v_kur_bezeichnung,
       p.uas_ua                 AS v_uas_ua,
       u.bezeichnung            AS v_uas_bezeichnung,
       p.bez_stadtbezirk        AS v_bez_stadtbezirk,
       s.bezeichnung            AS v_bez_bezeichnung,
       p.krn_krhname            AS v_krn_krhname,
       p.krisofp                AS v_krisofp,
       t.a1_antragstyp          AS v_a1_antragstyp,
       t.a1_antragsdatum        AS v_vneantrag,
       t.b_bewill_z             AS v_vnbew_z,
       t.b_bewill_d             AS v_vnbew_d,
       t.b_bewill_k             AS v_vnbew_k,
       t.r_erh_z                AS v_vnerh_z,
       t.r_erh_d                AS v_vnerh_d,
       t.r_erh_k                AS v_vnerh_k,
       t.a1_geskosten           AS v_vngeskosten,
       t.a1_zwfkosten           AS v_zwfkosten,
       p.vngesamtzuwendung      AS v_vngesamtzuwendung,
       p.vnkosten               AS v_vnkosten,
       p.vnzwfkosten            AS v_vnzwfkosten,
       p.vnpruefdat             AS v_vnpruefdat,
       p.vnrueck_z              AS v_vnrueck_z,
       p.vnnachfoerderung       AS v_vnnachfoerderung,
       p.vnpruefzwf             AS v_vnpruefzwf,
       p.vnschlusszwf           AS v_vnschlusszwf,
       p.vnschlussbew           AS v_vnschlussbew,
       p.notizen                AS v_notizen,
       p.fipo                   AS v_fipo,
       p.buchungskreis          AS v_buchungskreis,
       p.sachkonto              AS v_sachkonto,
       p.fipo_k                 AS v_fipo_k,
       p.buchungskreis_k        AS v_buchungskreis_k,
       p.sachkonto_k            AS v_sachkonto_k,
       p.kripplatz              AS v_kripplatz,
       p.kigaplatz              AS v_kigaplatz,
       p.hortplatz              AS v_hortplatz,
       p.psbaubuch              AS v_psbaubuch,
       p.psbauref               AS v_psbauref,
       p.psbaunr                AS v_psbaunr,
       p.sgt_siedlungsgebiet    AS v_sgt_siedlungsgebiet,
       i.bezeichnung            AS v_sgt_bezeichnung,
       p.bpg_bauprogramm        AS v_bpg_bauprogramm,
       b.bezeichnung            AS v_bpg_bezeichnung,
       m.b_geszuwendungen       AS v_b_geszuwendungen,
       m.b_bzwfkosten           AS v_b_bzwfkosten,
       p.bauende                AS v_bauende,
       p.baubeendet             AS v_baubeendet,
       p.bauvergabe1            AS v_bauvergabe1,
       p.baubeginn              AS v_baubeginn,
       p.baumitteilung          AS v_baumitteilung,
       p.kreditnummer           AS v_kreditnummer,
       p.stadtanleihe           AS v_stadtanleihe,
       p.anleihenennwert        AS v_anleihenennwert,
       p.anleihejahrvon         AS v_anleihejahrvon,
       p.anleihejahrbis         AS v_anleihejahrbis
FROM fp_projekte p
         JOIN fp_foerderbereiche f ON p.fob_fb = f.fb
         JOIN fp_unterabschnitte u ON p.uas_ua = u.ua
         JOIN fp_kurzbezeichnungen z ON p.kur_kurzbez = z.kurzbez
         LEFT JOIN fp_stadtbezirke s ON p.bez_stadtbezirk = s.stadtbezirk
         LEFT JOIN fp_v_projektstat t ON p.projnr = t.p_projnr
         LEFT JOIN fp_bauprogramme b ON p.bpg_bauprogramm = b.bauprogramm
         LEFT JOIN fp_siedlungsgebiete i ON p.sgt_siedlungsgebiet = i.siedlungsgebiet
         LEFT JOIN fp_v_mitteleinplanung m ON p.projnr = m.b_projnr
;