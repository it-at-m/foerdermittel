import { useRouteQuery } from "@vueuse/router";
import { computed, ref, toRaw, watch } from "vue";

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
 * Filter values are stored as URL-encoded JSON in the filter query parameter.
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

  const filterQuery = useRouteQuery<string>(
    "filter",
    encodeURIComponent(JSON.stringify(defaults.value))
  );

  /**
   * Builds a filter object from the current route query.
   */
  function filterFromRoute(): FilterRecord {
    try {
      const filter: unknown = JSON.parse(decodeURIComponent(filterQuery.value));
      if (filter && typeof filter === "object" && !Array.isArray(filter)) {
        return { ...clone(defaults.value), ...filter };
      }
    } catch {
      // Invalid query values should not prevent the filter form from loading.
    }

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
      const encoded = encodeURIComponent(JSON.stringify(filter));
      if (filterQuery.value === encoded) return;

      filterQuery.value = encoded;
      search.value = String(Date.now());
    },
    { deep: true }
  );

  /**
   * Applies a filter and synchronizes it with the route.
   */
  async function setFilter(filter: FilterRecord): Promise<void> {
    appliedFilter.value = clone(filter);
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
