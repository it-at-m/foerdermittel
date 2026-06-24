import type { DataTableSortItem } from "vuetify";

export interface DataTableOptions {
  page: number;
  itemsPerPage: number;
  sortBy: DataTableSortItem[];
  search?: string;
}
