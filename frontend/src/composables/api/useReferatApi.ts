import type {
  CreateReferatRequest,
  DeleteReferatRequest,
  GetReferateRequest,
  PagedModelReferatResponseDTO,
  ReferatFormContext,
  ReferatResponseDTO,
  UpdateReferatRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { ReferatControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateReferat,
  useUpdate: useUpdateReferat,
  useGetAll: useGetReferate,
  useDelete: useDeleteReferat,
  useContext: useGetReferatFormContext,
} = requireComposables(
  createAPIComposables<
    ReferatControllerApi,
    CreateReferatRequest,
    UpdateReferatRequest,
    never,
    DeleteReferatRequest,
    GetReferateRequest,
    ReferatResponseDTO,
    PagedModelReferatResponseDTO,
    ReferatFormContext
  >(ReferatControllerApi, {
    create: (api, req) => api.createReferat(req),
    update: (api, req) => api.updateReferat(req),
    getAll: (api, req) => api.getReferate(req),
    delete: (api, req) => api.deleteReferat(req),
    context: (api) => api.getReferatFormContext(),
  })
);

export function useReferatApi(): ApiComposables<
  ReferatResponseDTO,
  ReferatFormContext,
  CreateReferatRequest,
  ReferatResponseDTO,
  UpdateReferatRequest,
  ReferatResponseDTO,
  DeleteReferatRequest
> {
  return {
    getAll: useGetReferate(),
    context: useGetReferatFormContext(),
    create: useCreateReferat(),
    update: useUpdateReferat(),
    delete: useDeleteReferat(),
  };
}
