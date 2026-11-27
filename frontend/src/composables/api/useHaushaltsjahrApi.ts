import type {
  GetHhjahreRequest,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { HhjahrControllerApi } from "@/api/generated/foerdermittel-backend";
import { createAPIComposables } from "@/util/composable-helper";

export const { useGetAll: useGetHaushaltsjahre } = createAPIComposables<
  HhjahrControllerApi,
  never,
  never,
  never,
  never,
  GetHhjahreRequest,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
  never
>(HhjahrControllerApi, {
  getAll: (api, req) => api.getHhjahre(req),
});

export function useHaushaltsjahrApi(): ApiComposables<
  HhjahrResponseDTO,
  never,
  never,
  never,
  never,
  never,
  never,
  GetHhjahreRequest
> {
  return {
    getAll: useGetHaushaltsjahre?.(),
  };
}
