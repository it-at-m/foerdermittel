import type { DataTableOptions, SortOption } from "@/types/DataTableOptions";
import type { Pageable } from "@/types/Pageable";

import { useRouteQuery } from "@vueuse/router";
import { computed, useTemplateRef, watch } from "vue";

import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";

export const ITEMS_PER_PAGE_OPTIONS = [25, 50, 100];

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

  if (!Number.isFinite(parsed)) {
    return PAGINATION_DEFAULTS.page;
  }

  return parsed > 0 ? parsed : PAGINATION_DEFAULTS.page;
}

function urlEncodeSortOptions(sortOptions: SortOption[]) {
  return sortOptions.map(({ key, order }) => `${key},${order}`).join(";");
}

function urlDecodeSortOptions(sortOptionString: string) {
  return sortOptionString.split(";").map((s): SortOption => {
    const [key, order] = s.split(",");
    return {
      key: key as string,
      order: order as "asc" | "desc",
    };
  });
}

export default function usePagination(
  getEntitiesFunction: (pageable: Pageable) => Promise<void>
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
    page,
    (newValue) => {
      const normalized = normalizePage(newValue);

      if (String(normalized) !== newValue) {
        page.value = String(normalized);
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

  const crudRef = useTemplateRef("crudRef");
  const snackbarStore = useSnackbarStore();
  const onSuccess = async (msg: string) => {
    snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
    if (crudRef.value) {
      // @ts-expect-error closeDialog method always exists on the CrudCard component
      crudRef.value.closeDialog();
    }
    await getEntitiesFunction(pageable.value);
  };
  const onFailure = (msg: string) => {
    snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
  };

  return {
    dataTableOptions,
    onSuccess,
    onFailure,
  };
}
