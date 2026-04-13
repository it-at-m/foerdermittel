ALTER TABLE fp_benutzerhinweise
    RENAME COLUMN for_formsmodul TO view_id;
ALTER TABLE fp_benutzerhinweise
    RENAME COLUMN hinweis1 TO funktionsbeschreibung;
ALTER TABLE fp_benutzerhinweise
    RENAME COLUMN hinweis2 TO bedienung;
ALTER TABLE fp_benutzerhinweise
    RENAME COLUMN hinweis3 TO pruefung_vorgaben;

ALTER TABLE fp_foerderbereiche
    RENAME COLUMN funktion1 TO finanzausgleich;
ALTER TABLE fp_foerderbereiche
    RENAME COLUMN funktion2 TO jahresstatistik;
ALTER TABLE fp_foerderbereiche
    RENAME COLUMN funktion3 TO kindergarten;
ALTER TABLE fp_foerderbereiche
    RENAME COLUMN funktion4 TO nicht_relevant;