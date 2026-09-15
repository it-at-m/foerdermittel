import type {
  CreateTerminRequest,
  DeleteTerminRequest,
  GetTerminRequest,
  PagedModelTerminResponseDTO,
  TerminFormContext,
  TerminResponseDTO,
  UpdateTerminRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { TerminControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateTermin,
  useUpdate: useUpdateTermin,
  useGetAll: useGetTermin,
  useDelete: useDeleteTermin,
  useContext: useGetTerminFormContext,
} = requireComposables(
  createAPIComposables<
    TerminControllerApi,
    CreateTerminRequest,
    UpdateTerminRequest,
    never,
    DeleteTerminRequest,
    GetTerminRequest,
    TerminResponseDTO,
    PagedModelTerminResponseDTO,
    TerminFormContext
  >(TerminControllerApi, {
    create: (api, req) => api.createTermin(req),
    update: (api, req) => api.updateTermin(req),
    getAll: (api, req) => api.getTermin(req),
    delete: (api, req) => api.deleteTermin(req),
    context: (api) => api.getTerminFormContext(),
  })
);

export function useTerminApi(): ApiComposables<
  TerminResponseDTO,
  TerminFormContext,
  CreateTerminRequest,
  TerminResponseDTO,
  UpdateTerminRequest,
  TerminResponseDTO,
  DeleteTerminRequest
> {
  return {
    getAll: useGetTermin(),
    context: useGetTerminFormContext(),
    create: useCreateTermin(),
    update: useUpdateTermin(),
    delete: useDeleteTermin(),
  };
}
