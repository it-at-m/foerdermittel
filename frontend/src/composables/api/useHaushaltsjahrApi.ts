import type {
  GetHhjahreRequest,
  HhjahrFormContext,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { HhjahrControllerApi } from "@/api/generated/foerdermittel-backend";
import { createAPIComposables } from "@/util/composable-helper";

export const {
  useCreate: useCreateHaushaltsjahr,
  useUpdate: useUpdateHaushaltsjahr,
  useGetAll: useGetHaushaltsjahre,
  useDelete: useDeleteHaushaltsjahr,
  useContext: useGetHaushaltsjahrFormContext,
} = createAPIComposables<
  HhjahrControllerApi,
  never,
  never,
  never,
  never,
  GetHhjahreRequest,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
  HhjahrFormContext
>(HhjahrControllerApi, {
  getAll: (api, req) => api.getHhjahre(req),
});

export function useHaushaltsjahrApi(): ApiComposables<
  HhjahrResponseDTO,
  HhjahrFormContext,
  never,
  HhjahrResponseDTO,
  never,
  HhjahrResponseDTO,
  never
> {
  return {
    getAll: useGetHaushaltsjahre?.(),
    context: useGetHaushaltsjahrFormContext?.(),
    create: useCreateHaushaltsjahr?.(),
    update: useUpdateHaushaltsjahr?.(),
    delete: useDeleteHaushaltsjahr?.(),
  };
}
