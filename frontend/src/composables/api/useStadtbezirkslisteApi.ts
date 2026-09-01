import type {
  CreateListennameRequest,
  DeleteListennameRequest,
  GetStadtbezirklistenRequest,
  PagedModelStadtbezirkslisteResponseDTO,
  StadtbezirkslisteFormContext,
  StadtbezirkslisteResponseDTO,
  UpdateListennameRequest,
} from "@/api/generated/foerdermittel-backend";

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
    GetStadtbezirklistenRequest,
    StadtbezirkslisteResponseDTO,
    PagedModelStadtbezirkslisteResponseDTO,
    StadtbezirkslisteFormContext
  >(StadtbezirkslisteControllerApi, {
    create: (api, req) => api.createListenname(req),
    update: (api, req) => api.updateListenname(req),
    getAll: (api, req) => api.getStadtbezirklisten(req),
    delete: (api, req) => api.deleteListenname(req),
    context: (api) => api.getStadtbezirkslisteFormContext(),
  })
);
