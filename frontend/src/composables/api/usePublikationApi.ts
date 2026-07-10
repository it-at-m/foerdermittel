import type {
  CreatePublikationRequest,
  DeletePublikationRequest,
  GetPublikationenByPageableRequest,
  PagedModelPublikationResponseDTO,
  PublikationFormContext,
  PublikationResponseDTO,
  UpdatePublikationRequest,
} from "@/api/generated/foerdermittel-backend";

import { PublikationControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreatePublikation,
  useUpdate: useUpdatePublikation,
  useGetAll: useGetPublikationen,
  useDelete: useDeletePublikation,
  useContext: useGetPublikationFormContext,
} = requireComposables(
  createAPIComposables<
    PublikationControllerApi,
    CreatePublikationRequest,
    UpdatePublikationRequest,
    never,
    DeletePublikationRequest,
    GetPublikationenByPageableRequest,
    PublikationResponseDTO,
    PagedModelPublikationResponseDTO,
    PublikationFormContext
  >(PublikationControllerApi, {
    create: (api, req) => api.createPublikation(req),
    update: (api, req) => api.updatePublikation(req),
    getAll: (api, req) => api.getPublikationenByPageable(req),
    delete: (api, req) => api.deletePublikation(req),
    context: (api) => api.getPublikationFormContext(),
  })
);
