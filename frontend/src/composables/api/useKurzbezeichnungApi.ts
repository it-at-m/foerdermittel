import type {
  CreateKurzbezeichnungRequest,
  DeleteKurzbezeichnungRequest,
  GetKurzbezeichnungenByPageableRequest,
  KurzbezeichnungFormContext,
  KurzbezeichnungResponseDTO,
  PagedModelKurzbezeichnungResponseDTO,
  UpdateKurzbezeichnungRequest,
} from "@/api/generated/foerdermittel-backend";

import { KurzbezeichnungControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateKurzbezeichnung,
  useUpdate: useUpdateKurzbezeichnung,
  useGetAll: useGetKurzbezeichnungen,
  useDelete: useDeleteKurzbezeichnung,
  useContext: useGetKurzbezeichnungFormContext,
} = requireComposables(
  createAPIComposables<
    KurzbezeichnungControllerApi,
    CreateKurzbezeichnungRequest,
    UpdateKurzbezeichnungRequest,
    never,
    DeleteKurzbezeichnungRequest,
    GetKurzbezeichnungenByPageableRequest,
    KurzbezeichnungResponseDTO,
    PagedModelKurzbezeichnungResponseDTO,
    KurzbezeichnungFormContext
  >(KurzbezeichnungControllerApi, {
    create: (api, req) => api.createKurzbezeichnung(req),
    update: (api, req) => api.updateKurzbezeichnung(req),
    getAll: (api, req) => api.getKurzbezeichnungenByPageable(req),
    delete: (api, req) => api.deleteKurzbezeichnung(req),
    context: (api) => api.getKurzbezeichnungFormContext(),
  })
);
