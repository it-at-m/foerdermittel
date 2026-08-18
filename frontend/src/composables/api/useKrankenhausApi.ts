import type {
  CreateKrankenhausRequest,
  DeleteKrankenhausRequest,
  GetKrankenhaeuserRequest,
  KrankenhausFormContext,
  KrankenhausResponseDTO,
  PagedModelKrankenhausResponseDTO,
  UpdateKrankenhausRequest,
} from "@/api/generated/foerdermittel-backend";

import { KrankenhausControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateKrankenhaus,
  useUpdate: useUpdateKrankenhaus,
  useGetAll: useGetKrankenhaeuser,
  useDelete: useDeleteKrankenhaus,
  useContext: useGetKrankenhausFormContext,
} = requireComposables(
  createAPIComposables<
    KrankenhausControllerApi,
    CreateKrankenhausRequest,
    UpdateKrankenhausRequest,
    never,
    DeleteKrankenhausRequest,
    GetKrankenhaeuserRequest,
    KrankenhausResponseDTO,
    PagedModelKrankenhausResponseDTO,
    KrankenhausFormContext
  >(KrankenhausControllerApi, {
    create: (api, req) => api.createKrankenhaus(req),
    update: (api, req) => api.updateKrankenhaus(req),
    getAll: (api, req) => api.getKrankenhaeuser(req),
    delete: (api, req) => api.deleteKrankenhaus(req),
    context: (api) => api.getKrankenhausFormContext(),
  })
);
