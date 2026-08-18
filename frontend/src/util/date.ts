export function normalizeDate(value?: string | Date | null): Date | undefined {
  if (!value) {
    return undefined;
  }

  if (value instanceof Date) {
    return new Date(
      value.getFullYear(),
      value.getMonth(),
      value.getDate(),
      12,
      0,
      0
    );
  }

  const [year, month, day] = value.substring(0, 10).split("-");

  if (!year || !month || !day) {
    return undefined;
  }

  return new Date(Number(year), Number(month) - 1, Number(day), 12, 0, 0);
}

export function toDateString(value?: Date | null): string | undefined {
  if (!value) {
    return undefined;
  }

  return [
    value.getFullYear(),
    String(value.getMonth() + 1).padStart(2, "0"),
    String(value.getDate()).padStart(2, "0"),
  ].join("-");
}

export function formatDate(value?: string | Date | null): string {
  const date = normalizeDate(value);

  return (
    date?.toLocaleDateString("de-DE", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    }) ?? ""
  );
}
