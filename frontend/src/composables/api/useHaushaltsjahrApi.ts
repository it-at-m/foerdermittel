import type {
  CreateHhjahrRequest,
  DeleteHhjahrRequest,
  GetHhjahreRequest,
  HhjahrFormContext,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
  UpdateHhjahrRequest,
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
  CreateHhjahrRequest,
  UpdateHhjahrRequest,
  never,
  DeleteHhjahrRequest,
  GetHhjahreRequest,
  HhjahrResponseDTO,
  PagedModelHhjahrResponseDTO,
  HhjahrFormContext
>(HhjahrControllerApi, {
  create: (api, req) => api.createHhjahr(req),
  update: (api, req) => api.updateHhjahr(req),
  getAll: (api, req) => api.getHhjahre(req),
  delete: (api, req) => api.deleteHhjahr(req),
  context: (api) => api.getHhjahrFormContext(),
});

export function useHaushaltsjahrApi(): ApiComposables<
  HhjahrResponseDTO,
  HhjahrFormContext,
  CreateHhjahrRequest,
  HhjahrResponseDTO,
  UpdateHhjahrRequest,
  HhjahrResponseDTO,
  DeleteHhjahrRequest
> {
  return {
    getAll: useGetHaushaltsjahre?.(),
    context: useGetHaushaltsjahrFormContext?.(),
    create: useCreateHaushaltsjahr?.(),
    update: useUpdateHaushaltsjahr?.(),
    delete: useDeleteHaushaltsjahr?.(),
  };
}
