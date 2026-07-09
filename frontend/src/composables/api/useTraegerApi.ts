import type {
  CreateTraegerRequest,
  DeleteTraegerRequest,
  GetTraegerByPageableRequest,
  PagedModelTraegerResponseDTO,
  TraegerFormContext,
  TraegerResponseDTO,
  UpdateTraegerRequest,
} from "@/api/generated/foerdermittel-backend";

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
  })
);
