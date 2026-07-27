import type { CreateListennameRequest, CreateStadtbezirkRequest, DeleteListennameRequest, GetStadtbezirklistenByPageableRequest, GetStadtbezirklisteRequest, PagedModelStadtbezirkslisteResponseDTO, StadtbezirkResponseDTO, StadtbezirkslisteFormContext, StadtbezirkslisteResponseDTO, UpdateListennameRequest } from "@/api/generated/foerdermittel-backend";
import type {
  GetListennamenByPageableRequest,
  GetListennameRequest,
} from "@/api/generated/foerdermittel-backend/apis/ListennameControllerApi";

import { ApiFactory } from "@/api/ApiFactory";
import { StadtbezirkslisteControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";


export function useCreateListenname() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<CreateListennameRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.createListenname(params)
  );
}

export function useUpdateListenname() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<UpdateListennameRequest, StadtbezirkslisteResponseDTO>(
      (params) => api.updateListenname(params)
  );
}

export function useGetStadtbezirkslisten() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<
    GetListennamenByPageableRequest,
    PagedModelStadtbezirkslisteResponseDTO
  >((params) => api.getStadtbezirklistenByPageable(params));
}

export function useGetStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<GetListennameRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.getStadtbezirkliste(params)
  );
}

export function useDeleteStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<DeleteListennameRequest, void>((params) =>
    api.deleteListenname(params)
  );
}

export function useGetStadtbezirkslisteFormContext() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<void, StadtbezirkslisteFormContext>(() =>
    api.getStadtbezirkslisteFormContext()
  );
}