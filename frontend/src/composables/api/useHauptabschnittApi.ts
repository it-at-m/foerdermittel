import type {
  CreateHauptabschnittRequest,
  DeleteHauptabschnittRequest,
  GetHauptabschnitteRequest,
  HauptabschnittFormContext,
  HauptabschnittResponseDTO,
  PagedModelHauptabschnittResponseDTO,
  UpdateHauptabschnittRequest,
} from "@/api/generated/foerdermittel-backend";

import { HauptabschnittControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateHauptabschnitt,
  useUpdate: useUpdateHauptabschnitt,
  useGetAll: useGetHauptabschnitte,
  useDelete: useDeleteHauptabschnitt,
  useContext: useGetHauptabschnittFormContext,
} = requireComposables(
  createAPIComposables<
    HauptabschnittControllerApi,
    CreateHauptabschnittRequest,
    UpdateHauptabschnittRequest,
    never,
    DeleteHauptabschnittRequest,
    GetHauptabschnitteRequest,
    HauptabschnittResponseDTO,
    PagedModelHauptabschnittResponseDTO,
    HauptabschnittFormContext
  >(HauptabschnittControllerApi, {
    create: (api, req) => api.createHauptabschnitt(req),
    update: (api, req) => api.updateHauptabschnitt(req),
    getAll: (api, req) => api.getHauptabschnitte(req),
    delete: (api, req) => api.deleteHauptabschnitt(req),
    context: (api) => api.getHauptabschnittFormContext(),
  })
);
