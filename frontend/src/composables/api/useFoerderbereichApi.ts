import type {
  CreateFoerderbereichRequest,
  DeleteFoerderbereichRequest,
  FoerderbereichFormContext,
  FoerderbereichResponseDTO,
  GetFoerderbereicheByPageableRequest,
  PagedModelFoerderbereichResponseDTO,
  UpdateFoerderbereichRequest,
} from "@/api/generated/foerdermittel-backend";

import { FoerderbereichControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateFoerderbereich,
  useUpdate: useUpdateFoerderbereich,
  useGetAll: useGetFoerderbereiche,
  useDelete: useDeleteFoerderbereich,
  useContext: useGetFoerderbereichFormContext,
} = requireComposables(
  createAPIComposables<
    FoerderbereichControllerApi,
    CreateFoerderbereichRequest,
    UpdateFoerderbereichRequest,
    never,
    DeleteFoerderbereichRequest,
    GetFoerderbereicheByPageableRequest,
    FoerderbereichResponseDTO,
    PagedModelFoerderbereichResponseDTO,
    FoerderbereichFormContext
  >(FoerderbereichControllerApi, {
    create: (api, req) => api.createFoerderbereich(req),
    update: (api, req) => api.updateFoerderbereich(req),
    getAll: (api, req) => api.getFoerderbereicheByPageable(req),
    delete: (api, req) => api.deleteFoerderbereich(req),
    context: (api) => api.getFoerderbereichFormContext(),
  })
);
