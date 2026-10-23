import { computed, ref, toRaw } from "vue";
import { useRoute, useRouter } from "vue-router";

import { deepEqualTrimmed } from "@/util/validation";

type FilterRecord = Record<string, unknown>;

/**
 * Creates a deep clone of a value, removing Vue's reactive proxy if present.
 */
function clone<T>(value: T): T {
  return structuredClone(toRaw(value));
}

/**
 * Parses a route query value into the expected type and returns undefined if parsing failed.
 */
function parseQueryValue<T>(value: unknown, defaultValue: T): T | undefined {
  if (value === null || value === undefined) return undefined;

  const values = Array.isArray(value)
    ? value.filter((item): item is string => item != null)
    : [String(value)];

  if (Array.isArray(defaultValue)) {
    return values as T;
  }

  const primitiveValue = values[0];
  if (primitiveValue === undefined) return undefined;

  if (typeof defaultValue === "number") {
    if (primitiveValue.trim() === "") return undefined;

    const parsed = Number(primitiveValue);
    if (!Number.isFinite(parsed)) return undefined;

    return parsed as T;
  }

  if (typeof defaultValue === "boolean") {
    if (primitiveValue !== "true" && primitiveValue !== "false") {
      return undefined;
    }

    return (primitiveValue === "true") as T;
  }

  return primitiveValue as T;
}

/**
 * Checks if a value is considered as an empty query parameter and thus should be omitted from the route query.
 */
const isEmptyQueryParameter = (value: unknown): boolean =>
  value === null ||
  value === undefined ||
  value === "" ||
  (Array.isArray(value) && value.length === 0);

/**
 * Manages filter state and synchronizes applied filters with the route query.
 *
 * Filter values are initialized from the current route, using the supplied
 * defaults to determine the expected types of query parameters.
 */
export function useFilter<T>(defaultValue: T | undefined) {
  const route = useRoute();
  const router = useRouter();

  const defaults = computed<FilterRecord>(() =>
    defaultValue === null || defaultValue === undefined
      ? {}
      : clone(defaultValue)
  );

  const keys = ref(new Set(Object.keys(defaults.value)));

  /**
   * Builds a filter object from the current route query.
   *
   * Query values are parsed according to the corresponding default values.
   */
  function filterFromRoute(): FilterRecord {
    const filter = clone(defaults.value);

    for (const key of Object.keys(filter)) {
      const value = route.query[key];

      if (value != null) {
        filter[key] = parseQueryValue(value, defaults.value[key]);
      }
    }

    return filter;
  }

  const initial = filterFromRoute();
  const filterFormValue = ref(clone(initial));
  const appliedFilter = ref(clone(initial));
  const search = ref(String(Date.now()));

  const isFilterActive = computed(
    () => !deepEqualTrimmed(appliedFilter.value, defaults.value)
  );

  /**
   * Synchronizes the supplied filter with the route query.
   *
   * Empty and default values are omitted. Query parameters not managed by
   * this composable are preserved.
   */
  async function syncFilterToRoute(filter: FilterRecord): Promise<boolean> {
    Object.keys(filter).forEach((key) => keys.value.add(key));

    // Create query preserving query parameters not part of the FilterRecord (e.g. sort, page, ..)
    const query = Object.fromEntries(
      Object.entries(route.query).filter(([key]) => !keys.value.has(key))
    );

    // Add relevant query parameters from FilterRecord
    for (const [key, value] of Object.entries(filter)) {
      const defaultValue = defaults.value[key];

      if (
        isEmptyQueryParameter(value) ||
        deepEqualTrimmed(value, defaultValue)
      ) {
        continue;
      }

      query[key] = Array.isArray(value) ? value.map(String) : String(value);
    }

    if (deepEqualTrimmed(route.query, query)) return false;

    await router.replace({ query });
    return true;
  }

  /**
   * Applies a filter and synchronizes it with the route.
   *
   * If the route query does change, refreshes the search trigger so
   * dependent consumers can rerun their search.
   */
  async function setFilter(filter: FilterRecord): Promise<void> {
    const value = clone(filter);
    appliedFilter.value = value;

    const changed = await syncFilterToRoute(value);

    if (changed) search.value = String(Date.now());
  }

  /**
   * Applies the current form values as the active filter.
   */
  async function applyFilter(): Promise<void> {
    await setFilter(filterFormValue.value);
  }

  /**
   * Syncs form state with active filter.
   */
  async function syncForm(): Promise<void> {
    filterFormValue.value = clone(appliedFilter.value);
  }

  /**
   * Resets the form and applied filter to their default values.
   */
  async function resetFilter(): Promise<void> {
    const filter = clone(defaults.value);
    filterFormValue.value = filter;
    await setFilter(filter);
  }

  return {
    filterFormValue,
    appliedFilter,
    search,
    isFilterActive,
    applyFilter,
    resetFilter,
    syncForm,
  };
}
