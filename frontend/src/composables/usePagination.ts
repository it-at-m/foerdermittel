import type { Pageable } from "@/types/Pageable";
import type { Ref } from "vue";
import type { DataTableSortItem } from "vuetify";

import { useRouteQuery } from "@vueuse/router";
import { computed, watch } from "vue";

export const ITEMS_PER_PAGE_OPTIONS = [10, 25, 50, 100];

export const PAGINATION_DEFAULTS = {
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

export default function usePagination(totalPages: Ref<number | undefined>) {
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

  const pageModel = computed({
    get: () => normalizePage(page.value),
    set: (value) => {
      page.value = String(value);
    },
  });

  const itemsPerPageModel = computed({
    get: () => normalizeItemsPerPage(itemsPerPage.value),
    set: (value) => {
      itemsPerPage.value = String(value);
    },
  });

  const sortByModel = computed({
    get: () => (sortBy.value ? urlDecodeSortOptions(sortBy.value) : []),
    set: (value) => {
      sortBy.value = urlEncodeSortOptions(value);
    },
  });

  const pageable = computed<Pageable>(() => {
    return {
      page: pageModel.value - 1,
      size: itemsPerPageModel.value,
      sort: sortByModel.value.map((sortOption) =>
        Object.values(sortOption).join(",")
      ),
    };
  });

  return {
    page: pageModel,
    itemsPerPage: itemsPerPageModel,
    sortBy: sortByModel,
    pageable,
  };
}
