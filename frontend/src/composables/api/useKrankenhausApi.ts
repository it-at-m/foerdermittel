import type {
  KrankenhausFormContext,
  KrankenhausResponseDTO,
  CreateKrankenhausRequest,
  DeleteKrankenhausRequest,
  GetKrankenhaeuserByPageableRequest,
  PagedModelKrankenhausResponseDTO,
  UpdateKrankenhausRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { KrankenhausControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateKrankenhaus,
  useUpdate: useUpdateKrankenhaus,
  useGetAll: useGetKrankenhaeuser,
  useDelete: useDeleteKrankenhaus,
  useContext: useGetKrankenhausFormContext,
} = createAPIComposables<
  KrankenhausControllerApi,
  CreateKrankenhausRequest,
  UpdateKrankenhausRequest,
  never,
  DeleteKrankenhausRequest,
  GetKrankenhaeuserByPageableRequest,
  KrankenhausResponseDTO,
  PagedModelKrankenhausResponseDTO,
  KrankenhausFormContext
>(KrankenhausControllerApi, {
  create: (api, req) => api.createKrankenhaus(req),
  update: (api, req) => api.updateKrankenhaus(req),
  getAll: (api, req) => api.getKrankenhaeuserByPageable(req),
  delete: (api, req) => api.deleteKrankenhaus(req),
  context: (api) => api.getKrankenhausFormContext(),
});
