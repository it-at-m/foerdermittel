import type {
  CreateStichwortbereichRequest,
  DeleteStichwortbereichRequest,
  GetStichwortbereicheRequest,
  PagedModelStichwortbereichResponseDTO,
  StichwortbereichFormContext,
  StichwortbereichResponseDTO,
  UpdateStichwortbereichRequest,
} from "@/api/generated/foerdermittel-backend";

import { StichwortbereichControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateStichwortbereich,
  useUpdate: useUpdateStichwortbereich,
  useGetAll: useGetStichwortbereiche,
  useDelete: useDeleteStichwortbereich,
  useContext: useGetStichwortbereichFormContext,
} = requireComposables(
  createAPIComposables<
    StichwortbereichControllerApi,
    CreateStichwortbereichRequest,
    UpdateStichwortbereichRequest,
    never,
    DeleteStichwortbereichRequest,
    GetStichwortbereicheRequest,
    StichwortbereichResponseDTO,
    PagedModelStichwortbereichResponseDTO,
    StichwortbereichFormContext
  >(StichwortbereichControllerApi, {
    create: (api, req) => api.createStichwortbereich(req),
    update: (api, req) => api.updateStichwortbereich(req),
    getAll: (api, req) => api.getStichwortbereiche(req),
    delete: (api, req) => api.deleteStichwortbereich(req),
    context: (api) => api.getStichwortbereichFormContext(),
  })
);
