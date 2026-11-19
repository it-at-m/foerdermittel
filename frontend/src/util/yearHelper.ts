const MIN_JAHR = 1970;
const MAX_JAHR = 2100;

export function getJahrVonError(
  von: string | number | null | undefined,
  bis: string | number | null | undefined,
  bereichError: string,
  vergleichError: string
): string[] {
  if (von == null || von === "") {
    return [];
  }

  if (Number(von) < MIN_JAHR || Number(von) > MAX_JAHR) {
    return [bereichError];
  }

  if (bis == null || bis === "") {
    return [];
  }

  return Number(von) > Number(bis) ? [vergleichError] : [];
}

export function getJahrBisError(
  von: string | number | null | undefined,
  bis: string | number | null | undefined,
  bereichError: string,
  vergleichError: string
): string[] {
  if (bis == null || bis === "") {
    return [];
  }

  if (Number(bis) < MIN_JAHR || Number(bis) > MAX_JAHR) {
    return [bereichError];
  }

  if (von == null || von === "") {
    return [];
  }

  return Number(bis) < Number(von) ? [vergleichError] : [];
}
