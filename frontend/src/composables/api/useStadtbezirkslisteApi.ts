import type {
  CreateListennameRequest,
  DeleteListennameRequest,
  GetListennamenByPageableRequest,
  GetListennameRequest,
  ListennameFormContext,
  PagedModelListennameResponseDTO,
  StadtbezirkslisteResponseDTO,
  UpdateListennameRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import {
  ListennameControllerApi,
  
} from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateStadtbezirksliste() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<CreateListennameRequest, StadtbezirkslisteResponseDTO>(
    (params) => api.createListenname(params)
  );
}

export function useUpdateStadtbezirksliste() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<UpdateListennameRequest, StadtbezirkslisteResponseDTO>(
    (params) => api.updateListenname(params)
  );
}

export function useGetStadtbezirkslisten() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<
    GetListennamenByPageableRequest,
    PagedModelListennameResponseDTO
  >((params) => api.getListennamenByPageable(params));
}

export function useGetStadtbezirksliste() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<GetListennameRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.getListenname(params)
  );
}

export function useDeleteStadtbezirksliste() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<DeleteListennameRequest, void>((params) =>
    api.deleteListenname(params)
  );
}

export function useGetStadtbezirkslisteFormContext() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<void, ListennameFormContext>(() =>
    api.getListennameFormContext()
  );
}
