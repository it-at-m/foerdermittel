import { ApiError } from "@/api/ApiError";
import {
  defaultResponseHandler,
  deleteConfig,
  getConfig,
  postConfig,
  putConfig,
} from "@/api/fetch-utils";

function normalizeBasePath(apiBase: string): string {
  return apiBase.endsWith("/") ? apiBase.slice(0, -1) : apiBase;
}

function toResourceUrl(apiBase: string, id?: string): string {
  const normalizedBase = normalizeBasePath(apiBase);

  if (!id) {
    return `${normalizedBase}/`;
  }

  return `${normalizedBase}/${encodeURIComponent(id)}`;
}

function toApiError(error: unknown, fallbackMessage: string): ApiError {
  if (error instanceof ApiError) {
    return error;
  }

  return new ApiError({
    message: fallbackMessage,
  });
}

async function fetchJson<T>(
  url: string,
  errorMessage: string,
  config = getConfig()
): Promise<T> {
  try {
    const response = await fetch(url, config);
    defaultResponseHandler(response, errorMessage);
    return (await response.json()) as T;
  } catch (error) {
    throw toApiError(error, errorMessage);
  }
}

async function fetchVoid(
  url: string,
  config: RequestInit,
  errorMessage: string
): Promise<void> {
  try {
    const response = await fetch(url, config);
    defaultResponseHandler(response, errorMessage);
  } catch (error) {
    throw toApiError(error, errorMessage);
  }
}

export function getCreateSchema<TSchema = Record<string, unknown>>(
  apiBase: string
): Promise<TSchema> {
  return fetchJson<TSchema>(
    `${normalizeBasePath(apiBase)}/schema/create`,
    "Das Formularschema konnte nicht geladen werden."
  );
}

export function getUpdateSchema<TSchema = Record<string, unknown>>(
  apiBase: string
): Promise<TSchema> {
  return fetchJson<TSchema>(
    `${normalizeBasePath(apiBase)}/schema/update`,
    "Das Formularschema konnte nicht geladen werden."
  );
}

export function getIndex<TIndex = Record<string, unknown>>(
  apiBase: string
): Promise<TIndex[]> {
  return fetchJson<TIndex[]>(
    toResourceUrl(apiBase),
    "Die Liste konnte nicht geladen werden."
  );
}

export function getById<TUpdate = Record<string, unknown>>(
  apiBase: string,
  id: string
): Promise<TUpdate> {
  return fetchJson<TUpdate>(
    toResourceUrl(apiBase, id),
    "Der Datensatz konnte nicht geladen werden."
  );
}

export function create(
  apiBase: string,
  payload: Record<string, unknown>
): Promise<void> {
  return fetchVoid(
    toResourceUrl(apiBase),
    postConfig(payload),
    "Der Datensatz konnte nicht gespeichert werden."
  );
}

export function update(
  apiBase: string,
  id: string,
  payload: Record<string, unknown>
): Promise<void> {
  return fetchVoid(
    toResourceUrl(apiBase, id),
    putConfig(payload),
    "Der Datensatz konnte nicht gespeichert werden."
  );
}

export function remove(apiBase: string, id: string): Promise<void> {
  return fetchVoid(
    toResourceUrl(apiBase, id),
    deleteConfig(),
    "Der Datensatz konnte nicht gelöscht werden."
  );
}
