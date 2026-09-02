import type { RequestOpts } from "@/api/generated/foerdermittel-backend";

import { BASE_API_PATH } from "@/constants";

function flattenQuery(obj: Record<string, unknown>): Record<string, string> {
  const result: Record<string, string> = {};

  for (const [key, value] of Object.entries(obj)) {
    if (value !== null && typeof value === "object" && !Array.isArray(value)) {
      Object.assign(result, flattenQuery(value as Record<string, unknown>));
    } else {
      result[key] = String(value);
    }
  }

  return result;
}

export function toURL(requestOpts: RequestOpts) {
  const url = new URL(BASE_API_PATH + requestOpts.path, window.location.origin);
  if (requestOpts.query) {
    url.search = new URLSearchParams(
      flattenQuery(requestOpts.query)
    ).toString();
  }
  return url;
}

export function openURL(url: URL, blank = false) {
  const link = document.createElement("a");
  link.href = url.toString();

  if (blank) {
    link.target = "_blank";
    link.rel = "noopener noreferrer";
  }

  document.body.appendChild(link);

  link.click();
  link.remove();
}
