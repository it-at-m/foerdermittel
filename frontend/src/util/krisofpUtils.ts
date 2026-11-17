import type { ReportProjektuebersichtMsFormContextKrisofpsEnum } from "@/api/generated/foerdermittel-backend/models";

const krisofpBezeichnungen = new Map<
  ReportProjektuebersichtMsFormContextKrisofpsEnum,
  string
>([
  ["F", "FAGplus15"],
  ["K", "KJP II"],
  ["S", "Startchancen-Programm (SCP)"],
  ["V", "Schulkindbetreuung - Bund (GaFöG)"],
  ["W", "5. SIP - Schulkindbetreuung Land (Hortförd. GS)"],
  ["X", "1.+2. SIP - KriSoFö 2008-2014"],
  ["Y", "3. SIP - Kinderbetreuung 2015-2019"],
  ["Z", "4. SIP - Kinderbetreuung 2017-2020/2021"],
]);

export function getKrisofpTitle(
  item: ReportProjektuebersichtMsFormContextKrisofpsEnum
): string {
  return item ? `${item} (${krisofpBezeichnungen.get(item) ?? item})` : "";
}
