import type {
  CreateListennameRequest,
  DeleteListennameRequest,
  GetStadtbezirklistenByPageableRequest,
  GetStadtbezirklisteRequest,
  PagedModelStadtbezirkslisteResponseDTO,
  StadtbezirkslisteFormContext,
  StadtbezirkslisteResponseDTO,
  UpdateListennameRequest
} from "@/api/generated/foerdermittel-backend";



import { ApiFactory } from "@/api/ApiFactory";
import { StadtbezirkslisteControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";


export function useCreateListenname() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<CreateListennameRequest, StadtbezirkslisteResponseDTO>(
    (params) => api.createListenname(params)
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
    GetStadtbezirklistenByPageableRequest,
    PagedModelStadtbezirkslisteResponseDTO
  >((params) => api.getStadtbezirklistenByPageable(params));
}

export function useGetStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<GetStadtbezirklisteRequest, StadtbezirkslisteResponseDTO>(
    (params) => api.getStadtbezirkliste(params)
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
