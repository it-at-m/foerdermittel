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

export function openURL(url: URL) {
  window.open(url.toString(), "_blank", "noopener,noreferrer");
}

/** Encodes UTF-8 JSON as unpadded Base64url. */
export function writeToUrlParam<T>(object: T): string {
  const bytes = new TextEncoder().encode(JSON.stringify(object));
  const binary = Array.from(bytes, (byte) => String.fromCharCode(byte)).join(
    ""
  );

  return btoa(binary)
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/, "");
}

/** Decodes Base64url-encoded UTF-8 JSON. */
export function writeUrlParamToObject<T>(urlParam: string): T {
  if (!/^[A-Za-z0-9_-]+$/.test(urlParam)) {
    throw new Error("Invalid URL parameter encoding");
  }

  const binary = atob(urlParam.replace(/-/g, "+").replace(/_/g, "/"));
  const bytes = Uint8Array.from(binary, (character) => character.charCodeAt(0));
  const json = new TextDecoder("utf-8", { fatal: true }).decode(bytes);

  return JSON.parse(json) as T;
}
