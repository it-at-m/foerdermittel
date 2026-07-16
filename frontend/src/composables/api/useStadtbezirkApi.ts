import type {
  CreateStadtbezirkRequest,
  DeleteStadtbezirkRequest,
  GetStadtbezirkeRequest,
  PagedModelStadtbezirkResponseDTO,
  StadtbezirkFormContext,
  StadtbezirkResponseDTO,
  UpdateStadtbezirkRequest,
} from "@/api/generated/foerdermittel-backend";

import { StadtbezirkControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateStadtbezirk,
  useUpdate: useUpdateStadtbezirk,
  useGetAll: useGetStadtbezirke,
  useDelete: useDeleteStadtbezirk,
  useContext: useGetStadtbezirkFormContext,
} = requireComposables(
  createAPIComposables<
    StadtbezirkControllerApi,
    CreateStadtbezirkRequest,
    UpdateStadtbezirkRequest,
    never,
    DeleteStadtbezirkRequest,
    GetStadtbezirkeRequest,
    StadtbezirkResponseDTO,
    PagedModelStadtbezirkResponseDTO,
    StadtbezirkFormContext
  >(StadtbezirkControllerApi, {
    create: (api, req) => api.createStadtbezirk(req),
    update: (api, req) => api.updateStadtbezirk(req),
    getAll: (api, req) => api.getStadtbezirke(req),
    delete: (api, req) => api.deleteStadtbezirk(req),
    context: (api) => api.getStadtbezirkFormContext(),
  })
);
