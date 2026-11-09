import type {
  AntragFormContext,
  AntragResponseDTO,
  CreateAntragRequest,
  DeleteAntragRequest,
  GetAntraegeRequest,
  PagedModelAntragResponseDTO,
  UpdateAntragRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { AntragControllerApi } from "@/api/generated/foerdermittel-backend";
import { createAPIComposables } from "@/util/composable-helper";

export const {
  useCreate: useCreateAntrag,
  useUpdate: useUpdateAntrag,
  useGetAll: useGetAntrag,
  useDelete: useDeleteAntrag,
  useContext: useGetAntragFormContext,
} = createAPIComposables<
  AntragControllerApi,
  CreateAntragRequest,
  UpdateAntragRequest,
  never,
  DeleteAntragRequest,
  GetAntraegeRequest,
  AntragResponseDTO,
  PagedModelAntragResponseDTO,
  AntragFormContext
>(AntragControllerApi, {
  create: (api, req) => api.createAntrag(req),
  update: (api, req) => api.updateAntrag(req),
  getAll: (api, req) => api.getAntraege(req),
  delete: (api, req) => api.deleteAntrag(req),
  context: (api) => api.getAntragFormContext(),
});

export function useAntragApi(): ApiComposables<
  AntragResponseDTO,
  AntragFormContext,
  CreateAntragRequest,
  AntragResponseDTO,
  UpdateAntragRequest,
  AntragResponseDTO,
  DeleteAntragRequest
> {
  return {
    getAll: useGetAntrag?.(),
    context: useGetAntragFormContext?.(),
    create: useCreateAntrag?.(),
    update: useUpdateAntrag?.(),
    delete: useDeleteAntrag?.(),
  };
}
