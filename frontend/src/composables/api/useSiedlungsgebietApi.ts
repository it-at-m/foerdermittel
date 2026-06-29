import type {
  SiedlungsgebietFormContextDTO,
  SiedlungsgebietResponseDTO,
  DeleteSiedlungsgebietRequest,
  GetSiedlungsgebieteByPageableRequest,
  GetSiedlungsgebietRequest,
  PagedModelSiedlungsgebietResponseDTO,
  SaveSiedlungsgebietRequest,
  UpdateSiedlungsgebietRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { SiedlungsgebietControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateSiedlungsgebiet() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  return useAPI<SaveSiedlungsgebietRequest, SiedlungsgebietResponseDTO>((params) =>
    api.saveSiedlungsgebiet(params)
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

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useAPI<DeleteSiedlungsgebietRequest, void>((params) =>
    api.deleteSiedlungsgebiet(params)
  );
}

export function useGetSiedlungsgebietFormContext() {
  const api = ApiFactory.getInstance(SiedlungsgebietControllerApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useAPI<void, SiedlungsgebietFormContextDTO>(() =>
    api.getSiedlungsgebietFormContext()
  );
}
