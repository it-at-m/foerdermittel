import type {
  CreateProjektRequest,
  DeleteProjektRequest,
  GetProjekteRequest,
  PagedModelProjektResponseDTO,
  ProjektFormContext,
  ProjektResponseDTO,
  UpdateProjektRequest,
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
    CreateProjektRequest,
    UpdateProjektRequest,
    never,
    DeleteProjektRequest,
    GetProjekteRequest,
    ProjektResponseDTO,
    PagedModelProjektResponseDTO,
    ProjektFormContext
  >(ProjektControllerApi, {
    create: (api, req) => api.createProjekt(req),
    update: (api, req) => api.updateProjekt(req),
    getAll: (api, req) => api.getProjekte(req),
    delete: (api, req) => api.deleteProjekt(req),
    context: (api) => api.getProjektFormContext(),
  })
);

export function useProjektApi(): ApiComposables<
  ProjektResponseDTO,
  ProjektFormContext,
  CreateProjektRequest,
  ProjektResponseDTO,
  UpdateProjektRequest,
  ProjektResponseDTO,
  DeleteProjektRequest
> {
  return {
    getAll: useGetProjekte(),
    context: useGetProjektFormContext(),
    create: useCreateProjekt(),
    update: useUpdateProjekt(),
    delete: useDeleteProjekt(),
  };
}
