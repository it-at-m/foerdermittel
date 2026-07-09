import type {
  CreateReferatRequest,
  DeleteReferatRequest,
  GetReferateByPageableRequest,
  PagedModelReferatResponseDTO,
  ReferatFormContext,
  ReferatResponseDTO,
  UpdateReferatRequest,
} from "@/api/generated/foerdermittel-backend";

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
    GetReferateByPageableRequest,
    ReferatResponseDTO,
    PagedModelReferatResponseDTO,
    ReferatFormContext
  >(ReferatControllerApi, {
    create: (api, req) => api.createReferat(req),
    update: (api, req) => api.updateReferat(req),
    getAll: (api, req) => api.getReferateByPageable(req),
    delete: (api, req) => api.deleteReferat(req),
    context: (api) => api.getReferatFormContext(),
  })
);
