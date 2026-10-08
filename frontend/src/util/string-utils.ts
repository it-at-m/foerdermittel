export function toCamelCase(str: string) {
  return str
    .trim()
    .toLowerCase()
    .replace(/[-_\s]+(.)?/g, (_, char) => (char ? char.toUpperCase() : ""));
}
