import type {
  CreateListennameRequest,
  DeleteListennameRequest,
  GetStadtbezirklistenByPageableRequest,
  PagedModelStadtbezirkslisteResponseDTO,
  StadtbezirkslisteFormContext,
  StadtbezirkslisteResponseDTO,
  UpdateListennameRequest,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { StadtbezirkslisteControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateListenname,
  useUpdate: useUpdateListenname,
  useGetAll: useGetStadtbezirkslisten,
  useDelete: useDeleteStadtbezirksliste,
  useContext: useGetStadtbezirkslisteFormContext,
} = requireComposables(
  createAPIComposables<
    StadtbezirkslisteControllerApi,
    CreateListennameRequest,
    UpdateListennameRequest,
    never,
    DeleteListennameRequest,
    GetStadtbezirklistenByPageableRequest,
    StadtbezirkslisteResponseDTO,
    PagedModelStadtbezirkslisteResponseDTO,
    StadtbezirkslisteFormContext
  >(StadtbezirkslisteControllerApi, {
    create: (api, req) => api.createListenname(req),
    update: (api, req) => api.updateListenname(req),
    getAll: (api, req) => api.getStadtbezirklistenByPageable(req),
    delete: (api, req) => api.deleteListenname(req),
    context: (api) => api.getStadtbezirkslisteFormContext(),
  })
);

export function useStadtbezirkslisteApi(): ApiComposables<
  StadtbezirkslisteResponseDTO,
  StadtbezirkslisteFormContext,
  CreateListennameRequest,
  StadtbezirkslisteResponseDTO,
  UpdateListennameRequest,
  StadtbezirkslisteResponseDTO,
  DeleteListennameRequest
> {
  return {
    getAll: useGetStadtbezirkslisten(),
    context: useGetStadtbezirkslisteFormContext(),
    create: useCreateListenname(),
    update: useUpdateListenname(),
    delete: useDeleteStadtbezirksliste(),
  };
}
