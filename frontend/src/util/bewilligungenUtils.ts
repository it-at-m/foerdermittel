import type {
  ReportAuswertungBewilligungenFormContextZuwendungenEnum,
} from "@/api/generated/foerdermittel-backend/models";





const antraegeBezeichnungen = new Map<
  ReportAuswertungBewilligungenFormContextZuwendungenEnum,
  string
>([
  ["G", "Gesamtzuwendung"],
  ["R", "Restzuwendung"],
  ["T", "Teilzuwendung"],
  ["V", "Mittelverfall"],
]);

export function getBewilligungenTitle(
  item: ReportAuswertungBewilligungenFormContextZuwendungenEnum
): string {
  return item ? `${item} (${antraegeBezeichnungen.get(item) ?? item})` : "";
}
