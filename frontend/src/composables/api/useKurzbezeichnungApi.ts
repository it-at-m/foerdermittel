import type {
  CreateKurzbezeichnungRequest,
  DeleteKurzbezeichnungRequest,
  GetKurzbezeichnungenRequest,
  KurzbezeichnungFormContext,
  KurzbezeichnungResponseDTO,
  PagedModelKurzbezeichnungResponseDTO,
  UpdateKurzbezeichnungRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

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
    GetKurzbezeichnungenRequest,
    KurzbezeichnungResponseDTO,
    PagedModelKurzbezeichnungResponseDTO,
    KurzbezeichnungFormContext
  >(KurzbezeichnungControllerApi, {
    create: (api, req) => api.createKurzbezeichnung(req),
    update: (api, req) => api.updateKurzbezeichnung(req),
    getAll: (api, req) => api.getKurzbezeichnungen(req),
    delete: (api, req) => api.deleteKurzbezeichnung(req),
    context: (api) => api.getKurzbezeichnungFormContext(),
  })
);

export function useKurzbezeichnungApi(): ApiComposables<
  KurzbezeichnungResponseDTO,
  KurzbezeichnungFormContext,
  CreateKurzbezeichnungRequest,
  KurzbezeichnungResponseDTO,
  UpdateKurzbezeichnungRequest,
  KurzbezeichnungResponseDTO,
  DeleteKurzbezeichnungRequest
> {
  return {
    getAll: useGetKurzbezeichnungen(),
    context: useGetKurzbezeichnungFormContext(),
    create: useCreateKurzbezeichnung(),
    update: useUpdateKurzbezeichnung(),
    delete: useDeleteKurzbezeichnung(),
  };
}
