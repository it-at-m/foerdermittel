import type {
  CreateUnterabschnittRequest,
  DeleteUnterabschnittRequest,
  GetUnterabschnitteRequest,
  PagedModelUnterabschnittResponseDTO,
  UnterabschnittFormContext,
  UnterabschnittResponseDTO,
  UpdateUnterabschnittRequest,
} from "@/api/generated/foerdermittel-backend";

import { UnterabschnittControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateUnterabschnitt,
  useUpdate: useUpdateUnterabschnitt,
  useGetAll: useGetUnterabschnitte,
  useDelete: useDeleteUnterabschnitt,
  useContext: useGetUnterabschnittFormContext,
} = requireComposables(
  createAPIComposables<
    UnterabschnittControllerApi,
    CreateUnterabschnittRequest,
    UpdateUnterabschnittRequest,
    never,
    DeleteUnterabschnittRequest,
    GetUnterabschnitteRequest,
    UnterabschnittResponseDTO,
    PagedModelUnterabschnittResponseDTO,
    UnterabschnittFormContext
  >(UnterabschnittControllerApi, {
    create: (api, req) => api.createUnterabschnitt(req),
    update: (api, req) => api.updateUnterabschnitt(req),
    getAll: (api, req) => api.getUnterabschnitte(req),
    delete: (api, req) => api.deleteUnterabschnitt(req),
    context: (api) => api.getUnterabschnittFormContext(),
  })
);
