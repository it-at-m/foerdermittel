import type {
  IstkostenFormContext,
  IstkostenResponseDTO,
  CreateIstkostenRequest,
  DeleteIstkostenRequest,
  GetIstkostenEintraegeRequest,
  PagedModelIstkostenResponseDTO,
  UpdateIstkostenRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { IstkostenControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateIstkosten,
  useUpdate: useUpdateIstkosten,
  useGetAll: useGetIstkosten,
  useDelete: useDeleteIstkosten,
  useContext: useGetIstkostenFormContext,
} = requireComposables(
  createAPIComposables<
    IstkostenControllerApi,
    CreateIstkostenRequest,
    UpdateIstkostenRequest,
    never,
    DeleteIstkostenRequest,
    GetIstkostenEintraegeRequest,
    IstkostenResponseDTO,
    PagedModelIstkostenResponseDTO,
    IstkostenFormContext
  >(IstkostenControllerApi, {
    create: (api, req) => api.createIstkosten(req),
    update: (api, req) => api.updateIstkosten(req),
    getAll: (api, req) => api.getIstkostenEintraege(req),
    delete: (api, req) => api.deleteIstkosten(req),
    context: (api) => api.getIstkostenFormContext(),
  })
);

export function useIstkostenApi(): ApiComposables<
  IstkostenResponseDTO,
  IstkostenFormContext,
  CreateIstkostenRequest,
  IstkostenResponseDTO,
  UpdateIstkostenRequest,
  IstkostenResponseDTO,
  DeleteIstkostenRequest
> {
  return {
    getAll: useGetIstkosten(),
    context: useGetIstkostenFormContext(),
    create: useCreateIstkosten(),
    update: useUpdateIstkosten(),
    delete: useDeleteIstkosten(),
  };
}
