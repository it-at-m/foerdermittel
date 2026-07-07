import type {
  CreateStadtbezirkRequest,
  DeleteStadtbezirkRequest,
  GetStadtbezirkeByPageableRequest,
  GetStadtbezirkRequest,
  PagedModelStadtbezirkResponseDTO,
  StadtbezirkFormContext,
  StadtbezirkResponseDTO,
  UpdateStadtbezirkRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { StadtbezirkControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateStadtbezirk() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<CreateStadtbezirkRequest, StadtbezirkResponseDTO>((params) =>
    api.createStadtbezirk(params)
  );
}

export function useUpdateStadtbezirk() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<UpdateStadtbezirkRequest, StadtbezirkResponseDTO>((params) =>
    api.updateStadtbezirk(params)
  );
}

export function useGetStadtbezirke() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<
    GetStadtbezirkeByPageableRequest,
    PagedModelStadtbezirkResponseDTO
  >((params) => api.getStadtbezirkeByPageable(params));
}

export function useGetStadtbezirk() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<GetStadtbezirkRequest, StadtbezirkResponseDTO>((params) =>
    api.getStadtbezirk(params)
  );
}

export function useDeleteStadtbezirk() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<DeleteStadtbezirkRequest, void>((params) =>
    api.deleteStadtbezirk(params)
  );
}

export function useGetStadtbezirkFormContext() {
  const api = ApiFactory.getInstance(StadtbezirkControllerApi);

  return useAPI<void, StadtbezirkFormContext>(() =>
    api.getStadtbezirkFormContext()
  );
}
