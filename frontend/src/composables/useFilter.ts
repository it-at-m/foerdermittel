import { computed, ref, toRaw } from "vue";

function cloneValue<T>(value: T): T {
  return structuredClone(toRaw(value));
}

export function useFilter<T>(defaultValue: T) {
  const filterFormValue = ref(cloneValue(defaultValue));

  // TODO REFACTOR THIS CALCULATION TO CHECK search value of DataTable
  const isFilterActive = computed(() => filterFormValue.value == defaultValue);

  function reset() {
    filterFormValue.value = cloneValue(defaultValue);
  }

  return {
    filterFormValue,
    isFilterActive,
    reset,
  };
}
