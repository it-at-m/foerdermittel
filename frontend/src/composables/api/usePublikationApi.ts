import type {
  CreatePublikationRequest,
  DeletePublikationRequest,
  GetPublikationenRequest,
  PagedModelPublikationResponseDTO,
  PublikationFormContext,
  PublikationResponseDTO,
  UpdatePublikationRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

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
    GetPublikationenRequest,
    PublikationResponseDTO,
    PagedModelPublikationResponseDTO,
    PublikationFormContext
  >(PublikationControllerApi, {
    create: (api, req) => api.createPublikation(req),
    update: (api, req) => api.updatePublikation(req),
    getAll: (api, req) => api.getPublikationen(req),
    delete: (api, req) => api.deletePublikation(req),
    context: (api) => api.getPublikationFormContext(),
  })
);

export function usePublikationApi(): ApiComposables<
  PublikationResponseDTO,
  PublikationFormContext,
  CreatePublikationRequest,
  PublikationResponseDTO,
  UpdatePublikationRequest,
  PublikationResponseDTO,
  DeletePublikationRequest
> {
  return {
    getAll: useGetPublikationen(),
    context: useGetPublikationFormContext(),
    create: useCreatePublikation(),
    update: useUpdatePublikation(),
    delete: useDeletePublikation(),
  };
}
