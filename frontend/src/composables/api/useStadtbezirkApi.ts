import type {
  StadtbezirkFormContext,
  StadtbezirkResponseDTO,
  CreateStadtbezirkRequest,
  DeleteStadtbezirkRequest,
  GetStadtbezirkeByPageableRequest,
  PagedModelStadtbezirkResponseDTO,
  UpdateStadtbezirkRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { StadtbezirkControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateStadtbezirk,
  useUpdate: useUpdateStadtbezirk,
  useGetAll: useGetStadtbezirke,
  useDelete: useDeleteStadtbezirk,
  useContext: useGetStadtbezirkFormContext,
} = createAPIComposables<
  StadtbezirkControllerApi,
  CreateStadtbezirkRequest,
  UpdateStadtbezirkRequest,
  never,
  DeleteStadtbezirkRequest,
  GetStadtbezirkeByPageableRequest,
  StadtbezirkResponseDTO,
  PagedModelStadtbezirkResponseDTO,
  StadtbezirkFormContext
>(StadtbezirkControllerApi, {
  create: (api, req) => api.createStadtbezirk(req),
  update: (api, req) => api.updateStadtbezirk(req),
  getAll: (api, req) => api.getStadtbezirkeByPageable(req),
  delete: (api, req) => api.deleteStadtbezirk(req),
  context: (api) => api.getStadtbezirkFormContext(),
});
