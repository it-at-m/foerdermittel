import type { DataTableOptions } from "@/types/DataTableOptions";
import type { Pageable } from "@/types/Pageable";
import type { Ref } from "vue";
import type { DataTableSortItem } from "vuetify";

import { useRouteQuery } from "@vueuse/router";
import { computed, onMounted, useTemplateRef, watch } from "vue";

import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";

export const ITEMS_PER_PAGE_OPTIONS = [10, 25, 50, 100];

export const PAGINATION_DEFAULTS: DataTableOptions = {
  page: 1,
  itemsPerPage: ITEMS_PER_PAGE_OPTIONS[0] as number,
  sortBy: [],
};

function normalizeItemsPerPage(value: string): number {
  const parsed = Number(value);

  if (!Number.isFinite(parsed)) {
    return PAGINATION_DEFAULTS.itemsPerPage;
  }

  return ITEMS_PER_PAGE_OPTIONS.includes(parsed)
    ? parsed
    : PAGINATION_DEFAULTS.itemsPerPage;
}

function normalizePage(value: string): number {
  const parsed = Number(value);

  if (!Number.isInteger(parsed) || parsed <= 0) {
    return PAGINATION_DEFAULTS.page;
  }

  return parsed;
}

function urlEncodeSortOptions(sortOptions: DataTableSortItem[]) {
  return sortOptions.map(({ key, order }) => `${key},${order}`).join(";");
}

function urlDecodeSortOptions(sortOptionString: string) {
  return sortOptionString
    .split(";")
    .map((token) => {
      const [key, order] = token.split(",");
      if (!key || (order !== "asc" && order !== "desc")) {
        return undefined;
      }
      return { key, order } as DataTableSortItem;
    })
    .filter((item): item is DataTableSortItem => item !== undefined);
}

export default function usePagination(
  totalPages: Ref<number | undefined>,
  getEntitiesFunction: (pageable: Pageable) => Promise<void>,
  shouldLoadFormContext?: Ref<boolean>,
  getFormContext?: () => void | Promise<void>,
  validate?: () => void | Promise<void>
) {
  const page = useRouteQuery<string>("page", String(PAGINATION_DEFAULTS.page));
  const itemsPerPage = useRouteQuery<string>(
    "itemsPerPage",
    String(PAGINATION_DEFAULTS.itemsPerPage)
  );
  const sortBy = useRouteQuery<string>(
    "sortBy",
    urlEncodeSortOptions(PAGINATION_DEFAULTS.sortBy)
  );

  watch(
    itemsPerPage,
    (newValue) => {
      const normalized = normalizeItemsPerPage(newValue);

      if (String(normalized) !== newValue) {
        itemsPerPage.value = String(normalized);
      }
    },
    { immediate: true }
  );

  watch(
    [page, totalPages],
    ([newPage, newTotalPages]) => {
      const normalized = normalizePage(newPage);

      if (newTotalPages == null) {
        if (String(normalized) !== newPage) {
          page.value = String(normalized);
        }
        return;
      }

      const clamped = Math.max(
        PAGINATION_DEFAULTS.page,
        Math.min(normalized, newTotalPages)
      );

      if (String(clamped) !== newPage) {
        page.value = String(clamped);
      }
    },
    { immediate: true }
  );

  const dataTableOptions = computed<DataTableOptions>({
    get() {
      return {
        page: normalizePage(page.value),
        itemsPerPage: normalizeItemsPerPage(itemsPerPage.value),
        sortBy: sortBy.value ? urlDecodeSortOptions(sortBy.value) : [],
      };
    },
    set(newDataTableOptions) {
      page.value = String(newDataTableOptions.page);
      itemsPerPage.value = String(newDataTableOptions.itemsPerPage);
      sortBy.value = urlEncodeSortOptions(newDataTableOptions.sortBy);
    },
  });

  const pageable = computed<Pageable>(() => {
    return {
      page: dataTableOptions.value.page - 1,
      size: dataTableOptions.value.itemsPerPage,
      sort: dataTableOptions.value.sortBy.map((sortOption) =>
        Object.values(sortOption).join(",")
      ),
    };
  });

  watch(
    pageable,
    async (newPageable) => {
      await getEntitiesFunction(newPageable);
    },
    { immediate: true }
  );

  onMounted(async () => {
    await fetchFormContext();
  });

  const crudRef = useTemplateRef("crudRef");
  const snackbarStore = useSnackbarStore();
  const onSuccess = async (msg: string) => {
    snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
    if (crudRef.value) {
      // @ts-expect-error closeDialog method always exists on the CrudCard component
      crudRef.value.closeDialog();
    }
    await getEntitiesFunction(pageable.value);
    await fetchFormContext();
  };
  const onFailure = async (msg: string) => {
    snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
    await fetchFormContext();
    if (validate) {
      await validate();
    }
  };

  async function fetchFormContext() {
    if (
      shouldLoadFormContext &&
      shouldLoadFormContext.value &&
      getFormContext
    ) {
      await getFormContext();
    }
  }

  return {
    dataTableOptions,
    onSuccess,
    onFailure,
  };
}
