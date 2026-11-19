const MIN_JAHR = 1970;
const MAX_JAHR = 2100;

const JAHR_BEREICH_ERROR = "Muss im Bereich von 1970 - 2100 liegen.";

export function getJahrVonError(
  von?: string | number | null,
  bis?: string | number | null
): string[] {
  if (von == null || von === "") {
    return [];
  }

  if (Number(von) < MIN_JAHR || Number(von) > MAX_JAHR) {
    return [JAHR_BEREICH_ERROR];
  }

  if (bis == null || bis === "") {
    return [];
  }

  return Number(von) > Number(bis)
    ? ["Jahr von darf nicht größer als Jahr bis sein."]
    : [];
}

export function getJahrBisError(
  von?: string | number | null,
  bis?: string | number | null
): string[] {
  if (bis == null || bis === "") {
    return [];
  }

  if (Number(bis) < MIN_JAHR || Number(bis) > MAX_JAHR) {
    return [JAHR_BEREICH_ERROR];
  }

  if (von == null || von === "") {
    return [];
  }

  return Number(bis) < Number(von)
    ? ["Jahr bis darf nicht kleiner als Jahr von sein."]
    : [];
}
