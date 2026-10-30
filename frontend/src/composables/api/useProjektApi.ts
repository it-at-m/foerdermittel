import type {
  GetProjekteRequest,
  PagedModelProjektResponseDTO,
  ProjektResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { ProjektControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateProjekt,
  useUpdate: useUpdateProjekt,
  useGetAll: useGetProjekte,
  useDelete: useDeleteProjekt,
  useContext: useGetProjektFormContext,
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

export function useProjektApi(): ApiComposables<
  ProjektResponseDTO,
  never,
  never,
  ProjektResponseDTO,
  never,
  ProjektResponseDTO,
  never
> {
  return {
    getAll: useGetProjekte(),
    context: useGetProjektFormContext(),
    create: useCreateProjekt(),
    update: useUpdateProjekt(),
    delete: useDeleteProjekt(),
  };
}
