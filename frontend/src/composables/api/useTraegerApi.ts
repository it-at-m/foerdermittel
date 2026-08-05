import type {
  CreateTraegerRequest,
  DeleteTraegerRequest,
  GetTraegerRequest,
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
