import { useRouteQuery } from "@vueuse/router";
import { computed, ref, toRaw, watch } from "vue";

import { writeToUrlParam, writeUrlParamToObject } from "@/util/url-helper";
import { deepEqualTrimmed } from "@/util/validation";

type FilterRecord = Record<string, unknown>;

/**
 * Creates a deep clone of a value, removing Vue's reactive proxy if present.
 */
function clone<T>(value: T): T {
  return structuredClone(toRaw(value));
}

/**
 * Manages filter state and synchronizes applied filters with the route query.
 *
 * Filter values are stored as Base64url-encoded UTF-8 JSON in the filter query parameter.
 */
export function useFilter(defaultValue?: FilterRecord) {
  const formValid = ref(false);

  const updateFormValidity = (valid: boolean | null) => {
    formValid.value = !!valid;
  };

  const defaults = computed<FilterRecord>(() =>
    defaultValue === null || defaultValue === undefined
      ? {}
      : clone(defaultValue)
  );

  const filterQuery = useRouteQuery<string | undefined>("filter", undefined);

  /**
   * Builds a filter object from the current route query.
   */
  function filterFromRoute(): FilterRecord {
    const encoded = filterQuery.value;
    if (encoded === undefined) return clone(defaults.value);

    try {
      const filter = writeUrlParamToObject<unknown>(encoded);
      if (!filter || typeof filter !== "object" || Array.isArray(filter)) {
        throw new Error("Invalid filter object");
      }
      return { ...clone(defaults.value), ...filter };
    } catch {
      // Invalid query values should not prevent the filter form from loading.
    }

    filterQuery.value = undefined;
    return clone(defaults.value);
  }

  const initial = filterFromRoute();
  const filterFormValue = ref(clone(initial));
  const appliedFilter = ref(clone(initial));
  const search = ref(String(Date.now()));

  const isFilterActive = computed(
    () => !deepEqualTrimmed(appliedFilter.value, defaults.value)
  );

  watch(
    appliedFilter,
    (filter) => {
      const encoded =
        JSON.stringify(filter) === JSON.stringify(defaults.value)
          ? undefined
          : writeToUrlParam(filter);
      if (filterQuery.value === encoded) return;

      filterQuery.value = encoded;
      search.value = String(Date.now());
    },
    { deep: true }
  );

  /**
   * Applies a filter and synchronizes it with the route.
   */
  function setFilter(filter: FilterRecord): void {
    appliedFilter.value = clone(filter);
  }

  /**
   * Applies the current form values as the active filter.
   */
  function applyFilter(): void {
    setFilter(filterFormValue.value);
  }

  /**
   * Syncs form state with active filter.
   */
  function syncForm(): void {
    filterFormValue.value = clone(appliedFilter.value);
  }

  /**
   * Resets the form and applied filter to their default values.
   */
  function resetFilter(): void {
    const filter = clone(defaults.value);
    filterFormValue.value = filter;
    setFilter(filter);
  }

  return {
    formValid,
    updateFormValidity,
    filterFormValue,
    appliedFilter,
    search,
    isFilterActive,
    applyFilter,
    resetFilter,
    syncForm,
  };
}
