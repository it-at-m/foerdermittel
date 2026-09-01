import type { RequestOpts } from "@/api/generated/foerdermittel-backend";

import { BASE_API_PATH } from "@/constants";

export function toURL(requestOpts: RequestOpts) {
  const url = new URL(BASE_API_PATH + requestOpts.path, window.location.origin);
  if (requestOpts.query) {
    url.search = new URLSearchParams(
      Object.entries(requestOpts.query).map(([key, value]) => [
        key,
        String(value),
      ])
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
