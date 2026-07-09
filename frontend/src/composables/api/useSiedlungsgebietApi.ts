import type {
  SiedlungsgebietFormContext,
  SiedlungsgebietResponseDTO,
  CreateSiedlungsgebietRequest,
  DeleteSiedlungsgebietRequest,
  GetSiedlungsgebieteByPageableRequest,
  PagedModelSiedlungsgebietResponseDTO,
  UpdateSiedlungsgebietRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { SiedlungsgebietControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateSiedlungsgebiet,
  useUpdate: useUpdateSiedlungsgebiet,
  useGetAll: useGetSiedlungsgebiete,
  useDelete: useDeleteSiedlungsgebiet,
  useContext: useGetSiedlungsgebietFormContext,
} = createAPIComposables<
  SiedlungsgebietControllerApi,
  CreateSiedlungsgebietRequest,
  UpdateSiedlungsgebietRequest,
  never,
  DeleteSiedlungsgebietRequest,
  GetSiedlungsgebieteByPageableRequest,
  SiedlungsgebietResponseDTO,
  PagedModelSiedlungsgebietResponseDTO,
  SiedlungsgebietFormContext
>(SiedlungsgebietControllerApi, {
  create: (api, req) => api.createSiedlungsgebiet(req),
  update: (api, req) => api.updateSiedlungsgebiet(req),
  getAll: (api, req) => api.getSiedlungsgebieteByPageable(req),
  delete: (api, req) => api.deleteSiedlungsgebiet(req),
  context: (api) => api.getSiedlungsgebietFormContext(),
});
