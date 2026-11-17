export function getJahrVonError(
  von?: string | number | null,
  bis?: string | number | null
): string[] {
  if (von == null || von === "" || bis == null || bis === "") {
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
  if (von == null || von === "" || bis == null || bis === "") {
    return [];
  }

  return Number(bis) < Number(von)
    ? ["Jahr bis darf nicht kleiner als Jahr von sein."]
    : [];
}
