import type {
  CreateListennameRequest,
  DeleteListennameRequest,
  GetListennamenByPageableRequest,
  GetListennameRequest,
  ListennameFormContext,
  ListennameResponseDTO,
  PagedModelListennameResponseDTO,
  UpdateListennameRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { ListennameControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateListenname() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<CreateListennameRequest, ListennameResponseDTO>((params) =>
    api.createListenname(params)
  );
}

export function useUpdateListenname() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<UpdateListennameRequest, ListennameResponseDTO>((params) =>
    api.updateListenname(params)
  );
}

export function useGetListennamen() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<
    GetListennamenByPageableRequest,
    PagedModelListennameResponseDTO
  >((params) => api.getListennamenByPageable(params));
}

export function useGetListenname() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<GetListennameRequest, ListennameResponseDTO>((params) =>
    api.getListenname(params)
  );
}

export function useDeleteListenname() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<DeleteListennameRequest, void>((params) =>
    api.deleteListenname(params)
  );
}

export function useGetListennameFormContext() {
  const api = ApiFactory.getInstance(ListennameControllerApi);

  return useAPI<void, ListennameFormContext>(() =>
    api.getListennameFormContext()
  );
}
