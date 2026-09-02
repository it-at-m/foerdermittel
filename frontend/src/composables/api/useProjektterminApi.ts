import type {
  CreateProjektterminRequest,
  DeleteProjektterminRequest,
  GetProjekttermineRequest,
  PagedModelProjektterminResponseDTO,
  ProjektterminFormContext,
  ProjektterminResponseDTO,
  UpdateProjektterminRequest,
} from "@/api/generated/foerdermittel-backend";

import { ProjektterminControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateProjekttermin,
  useUpdate: useUpdateProjekttermin,
  useGetAll: useGetProjekttermin,
  useDelete: useDeleteProjekttermin,
  useContext: useGetProjektterminFormContext,
} = requireComposables(
  createAPIComposables<
    ProjektterminControllerApi,
    CreateProjektterminRequest,
    UpdateProjektterminRequest,
    never,
    DeleteProjektterminRequest,
    GetProjekttermineRequest,
    ProjektterminResponseDTO,
    PagedModelProjektterminResponseDTO,
    ProjektterminFormContext
  >(ProjektterminControllerApi, {
    create: (api, req) => api.createProjekttermin(req),
    update: (api, req) => api.updateProjekttermin(req),
    getAll: (api, req) => api.getProjekttermine(req),
    delete: (api, req) => api.deleteProjekttermin(req),
    context: (api) => api.getProjektterminFormContext(),
  })
);
