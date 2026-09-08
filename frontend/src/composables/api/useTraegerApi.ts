import type {
  CreateTraegerRequest,
  DeleteTraegerRequest,
  GetTraegerRequest,
  PagedModelTraegerResponseDTO,
  TraegerFormContext,
  TraegerResponseDTO,
  UpdateTraegerRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { TraegerControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateTraeger,
  useUpdate: useUpdateTraeger,
  useGetAll: useGetAllTraeger,
  useDelete: useDeleteTraeger,
  useContext: useGetTraegerFormContext,
} = requireComposables(
  createAPIComposables<
    TraegerControllerApi,
    CreateTraegerRequest,
    UpdateTraegerRequest,
    never,
    DeleteTraegerRequest,
    GetTraegerRequest,
    TraegerResponseDTO,
    PagedModelTraegerResponseDTO,
    TraegerFormContext
  >(TraegerControllerApi, {
    create: (api, req) => api.createTraeger(req),
    update: (api, req) => api.updateTraeger(req),
    getAll: (api, req) => api.getTraeger(req),
    delete: (api, req) => api.deleteTraeger(req),
    context: (api) => api.getTraegerFormContext(),
  })
);

export function useTraegerApi(): ApiComposables<
  TraegerResponseDTO,
  TraegerFormContext,
  CreateTraegerRequest,
  TraegerResponseDTO,
  UpdateTraegerRequest,
  TraegerResponseDTO,
  DeleteTraegerRequest
> {
  return {
    getAll: useGetAllTraeger(),
    context: useGetTraegerFormContext(),
    create: useCreateTraeger(),
    update: useUpdateTraeger(),
    delete: useDeleteTraeger(),
  };
}
