import type { ReportAuswertungAntraegeFormContextAntraegeEnum } from "@/api/generated/foerdermittel-backend/models";

const antraegeBezeichnungen = new Map<
  ReportAuswertungAntraegeFormContextAntraegeEnum,
  string
>([
  ["A", "Änderungsantrag"],
  ["E", "Erstantrag"],
  ["F", "Fortsetzungsantrag"],
  ["U", "Unbedenklichkeitsantrag"],
  ["V", "Schlussantrag (VN)"],
]);

export function getAntraegeTitle(
  item: ReportAuswertungAntraegeFormContextAntraegeEnum
): string {
  return item ? `${item} (${antraegeBezeichnungen.get(item) ?? item})` : "";
}
