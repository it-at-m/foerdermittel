import type {
  CreateStadtbezirkslisteRequest,
  DeleteStadtbezirkslisteRequest,
  GetStadtbezirkslistenByPageableRequest,
  GetStadtbezirkslisteRequest,
  StadtbezirkslisteFormContext,
  StadtbezirkslisteResponseDTO,
  PagedModelStadtbezirkslisteResponseDTO,
  UpdateStadtbezirkslisteRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { StadtbezirkslisteControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<CreateStadtbezirkslisteRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.createStadtbezirksliste(params)
  );
}

export function useUpdateStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<UpdateStadtbezirkslisteRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.updateStadtbezirksliste(params)
  );
}

export function useGetStadtbezirkslisten() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<
    GetStadtbezirkslistenByPageableRequest,
    PagedModelStadtbezirkslisteResponseDTO
  >((params) => api.getStadtbezirkslistenByPageable(params));
}

export function useGetStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<GetStadtbezirkslisteRequest, StadtbezirkslisteResponseDTO>((params) =>
    api.getStadtbezirksliste(params)
  );
}

export function useDeleteStadtbezirksliste() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<DeleteStadtbezirkslisteRequest, void>((params) =>
    api.deleteStadtbezirksliste(params)
  );
}

export function useGetStadtbezirkslisteFormContext() {
  const api = ApiFactory.getInstance(StadtbezirkslisteControllerApi);

  return useAPI<void, StadtbezirkslisteFormContext>(() =>
    api.getStadtbezirkslisteFormContext()
  );
}
