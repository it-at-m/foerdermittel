import type {
  CreateTraegerRequest,
  DeleteTraegerRequest,
  GetTraegerByPageableRequest,
  PagedModelTraegerResponseDTO,
  TraegerFormContext,
  TraegerResponseDTO,
  UpdateTraegerRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { TraegerControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateTraeger,
  useUpdate: useUpdateTraeger,
  useGetAll: useGetAllTraeger,
  useDelete: useDeleteTraeger,
  useContext: useGetTraegerFormContext,
} = createAPIComposables<
  TraegerControllerApi,
  CreateTraegerRequest,
  UpdateTraegerRequest,
  never,
  DeleteTraegerRequest,
  GetTraegerByPageableRequest,
  TraegerResponseDTO,
  PagedModelTraegerResponseDTO,
  TraegerFormContext
>(TraegerControllerApi, {
  create: (api, req) => api.createTraeger(req),
  update: (api, req) => api.updateTraeger(req),
  getAll: (api, req) => api.getTraegerByPageable(req),
  delete: (api, req) => api.deleteTraeger(req),
  context: (api) => api.getTraegerFormContext(),
});
