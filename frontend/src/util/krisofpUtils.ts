import type { ReportProjektuebersichtMsFormContextKrisofpsEnum } from "@/api/generated/foerdermittel-backend/models";

const krisofpBezeichnungen = new Map<
  ReportProjektuebersichtMsFormContextKrisofpsEnum,
  string
>([
  ["X", "1.+2. SIP - KriSoFö 2008-2014"],
  ["Y", "3. SIP - Kinderbetreuung 2015-2019"],
  ["Z", "4. SIP - Kinderbetreuung 2017-2020/2021"],
  ["W", "5. SIP - Schulkindbetreuung Land (Hortförd. GS)"],
  ["V", "Schulkindbetreuung - Bund (GaFöG)"],
  ["K", "KJP II"],
  ["F", "FAGplus15"],
  ["S", "Startchancen-Programm (SCP)"],
]);

export function getKrisofpTitle(
  item: ReportProjektuebersichtMsFormContextKrisofpsEnum
): string {
  return `${item} (${krisofpBezeichnungen.get(item) ?? item})`;
}
