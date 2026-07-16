import type {
  BauprogrammFormContext,
  BauprogrammResponseDTO,
  CreateBauprogrammRequest,
  DeleteBauprogrammRequest,
  GetBauprogrammeByPageableRequest,
  PagedModelBauprogrammResponseDTO,
  UpdateBauprogrammRequest,
} from "@/api/generated/foerdermittel-backend";

import { BauprogrammControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateBauprogramm,
  useUpdate: useUpdateBauprogramm,
  useGetAll: useGetBauprogramme,
  useDelete: useDeleteBauprogramm,
  useContext: useGetBauprogrammFormContext,
} = requireComposables(
  createAPIComposables<
    BauprogrammControllerApi,
    CreateBauprogrammRequest,
    UpdateBauprogrammRequest,
    never,
    DeleteBauprogrammRequest,
    GetBauprogrammeByPageableRequest,
    BauprogrammResponseDTO,
    PagedModelBauprogrammResponseDTO,
    BauprogrammFormContext
  >(BauprogrammControllerApi, {
    create: (api, req) => api.createBauprogramm(req),
    update: (api, req) => api.updateBauprogramm(req),
    getAll: (api, req) => api.getBauprogrammeByPageable(req),
    delete: (api, req) => api.deleteBauprogramm(req),
    context: (api) => api.getBauprogrammFormContext(),
  })
);
