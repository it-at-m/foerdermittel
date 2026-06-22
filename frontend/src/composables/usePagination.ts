import type { Pageable } from "@/api/generated/foerdermittel-backend/models/Pageable";
import type { DataTableOptions, SortOption } from "@/types/DataTableOptions";

import { useRouteQuery } from "@vueuse/router";
import { computed, watch } from "vue";

export const PAGINATION_DEFAULTS: DataTableOptions = {
  page: 1,
  itemsPerPage: 25,
  sortBy: [],
};

function urlEncodeSortOptions(sortOptions: SortOption[]) {
  return sortOptions.map(({ key, order }) => `${key},${order}`).join(";");
}

export default function usePagination(
  getEntitiesFunction: (pageable: Pageable) => Promise<void>
) {
  const page = useRouteQuery<string>("page", String(PAGINATION_DEFAULTS.page));
  const size = useRouteQuery<string>(
    "itemsPerPage",
    String(PAGINATION_DEFAULTS.itemsPerPage)
  );
  const sort = useRouteQuery<string>(
    "sortBy",
    urlEncodeSortOptions(PAGINATION_DEFAULTS.sortBy)
  );

  const dataTableOptions = computed<DataTableOptions>({
    get() {
      return {
        page: Number(page.value),
        itemsPerPage: Number(size.value),
        sortBy: sort.value
          ? sort.value.split(";").map((s): SortOption => {
              const [key, order] = s.split(",");
              return {
                key: key as string,
                order: order as "asc" | "desc",
              };
            })
          : [],
      };
    },
    set(newDataTableOptions) {
      page.value = String(newDataTableOptions.page);
      size.value = String(newDataTableOptions.itemsPerPage);
      sort.value = urlEncodeSortOptions(newDataTableOptions.sortBy);
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

  return {
    dataTableOptions,
  };
}
