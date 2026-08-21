import type {
  GetProjekteRequest,
  PagedModelProjektResponseDTO,
  ProjektResponseDTO,
} from "@/api/generated/foerdermittel-backend";

import { ProjektControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetAll: useGetProjekte,
} = requireComposables(
  createAPIComposables<
    ProjektControllerApi,
    never,
    never,
    never,
    never,
    GetProjekteRequest,
    ProjektResponseDTO,
    PagedModelProjektResponseDTO,
    never
  >(ProjektControllerApi, {
    getAll: (api, req) => api.getProjekte(req),
  })
);