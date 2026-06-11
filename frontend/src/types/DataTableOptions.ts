interface SortOption {
  key: string;
  order: "asc" | "desc";
}

export interface DataTableOptions {
  page: number;
  itemsPerPage: number;
  sortBy: SortOption[];
  search?: string;
}
