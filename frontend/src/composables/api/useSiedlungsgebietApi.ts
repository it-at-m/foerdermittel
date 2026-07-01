import type {
  SiedlungsgebietFormContext,
  SiedlungsgebietResponseDTO,
  CreateSiedlungsgebietRequest,
  DeleteSiedlungsgebietRequest,
  GetSiedlungsgebieteByPageableRequest,
  GetSiedlungsgebietRequest,
  PagedModelSiedlungsgebietResponseDTO,
  UpdateSiedlungsgebietRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { SiedlungsgebietControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateSiedlungsgebiet() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<CreateSiedlungsgebietRequest, SiedlungsgebietResponseDTO>((params) =>
    api.createSiedlungsgebiet(params)
  );
}

export function useUpdateSiedlungsgebiet() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<UpdateSiedlungsgebietRequest, SiedlungsgebietResponseDTO>((params) =>
    api.updateSiedlungsgebiet(params)
  );
}

export function useGetSiedlungsgebiete() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<
    GetSiedlungsgebieteByPageableRequest,
    PagedModelSiedlungsgebietResponseDTO
  >((params) => api.getSiedlungsgebieteByPageable(params));
}

export function useGetSiedlungsgebiet() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<GetSiedlungsgebietRequest, SiedlungsgebietResponseDTO>((params) =>
    api.getSiedlungsgebiet(params)
  );
}

export function useDeleteSiedlungsgebiet() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<DeleteSiedlungsgebietRequest, void>((params) =>
    api.deleteSiedlungsgebiet(params)
  );
}

export function useGetSiedlungsgebietFormContext() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<void, SiedlungsgebietFormContext>(() =>
    api.getSiedlungsgebietFormContext()
  );
}
