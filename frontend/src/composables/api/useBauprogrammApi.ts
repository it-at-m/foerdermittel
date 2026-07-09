import type {
  BauprogrammFormContext,
  BauprogrammResponseDTO,
  CreateBauprogrammRequest,
  DeleteBauprogrammRequest,
  GetBauprogrammeByPageableRequest,
  PagedModelBauprogrammResponseDTO,
  UpdateBauprogrammRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { BauprogrammControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateBauprogramm,
  useUpdate: useUpdateBauprogramm,
  useGetAll: useGetBauprogramme,
  useDelete: useDeleteBauprogramm,
  useContext: useGetBauprogrammFormContext,
} = createAPIComposables<
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
});
