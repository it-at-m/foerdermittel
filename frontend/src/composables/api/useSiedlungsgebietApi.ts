import type {
  CreateSiedlungsgebietRequest,
  DeleteSiedlungsgebietRequest,
  GetSiedlungsgebieteByPageableRequest,
  PagedModelSiedlungsgebietResponseDTO,
  SiedlungsgebietFormContext,
  SiedlungsgebietResponseDTO,
  UpdateSiedlungsgebietRequest,
} from "@/api/generated/foerdermittel-backend";

import { SiedlungsgebietControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateSiedlungsgebiet,
  useUpdate: useUpdateSiedlungsgebiet,
  useGetAll: useGetSiedlungsgebiete,
  useDelete: useDeleteSiedlungsgebiet,
  useContext: useGetSiedlungsgebietFormContext,
} = requireComposables(
  createAPIComposables<
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
  })
);
