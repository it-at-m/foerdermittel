import type {
  HhplanResponseDTO,
  Pageable,
  PagedModelHhplanResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";
import type { ComputedRef } from "vue";

import { HhplanControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export function useHaushaltsplanungApi(
  hhjahr: ComputedRef<number | undefined>
): ApiComposables<
  HhplanResponseDTO,
  never,
  never,
  never,
  never,
  HhplanResponseDTO,
  never
> {
  const { useGetAll } = requireComposables(
    createAPIComposables<
      HhplanControllerApi,
      never,
      never,
      never,
      never,
      Pageable,
      HhplanResponseDTO,
      PagedModelHhplanResponseDTO,
      never
    >(HhplanControllerApi, {
      getAll: (api, req) => {
        const year = hhjahr.value;

        if (year === undefined) {
          throw new Error("Es wurde kein Haushaltsjahr ausgewählt.");
        }

        return api.getHhplan({
          ...req,
          hhjahr: year,
        });
      },
    })
  );

  return {
    getAll: useGetAll(),
  };
}
