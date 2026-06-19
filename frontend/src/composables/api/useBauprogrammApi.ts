import type {
  BauprogrammResponseDTO,
  DeleteBauprogrammRequest,
  GetBauprogrammeByPageableRequest,
  GetBauprogrammRequest,
  PagedModelBauprogrammResponseDTO,
  SaveBauprogrammRequest,
  UpdateBauprogrammRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { BauprogrammControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateBauprogramm() {
  const api = ApiFactory.getInstance(BauprogrammControllerApi);

  return useAPI<SaveBauprogrammRequest, BauprogrammResponseDTO>((params) =>
    api.saveBauprogramm(params)
  );
}

export function useUpdateBauprogramm() {
  const api = ApiFactory.getInstance(BauprogrammControllerApi);

  return useAPI<UpdateBauprogrammRequest, BauprogrammResponseDTO>((params) =>
    api.updateBauprogramm(params)
  );
}

export function useGetBauprogramme() {
  const api = ApiFactory.getInstance(BauprogrammControllerApi);

  return useAPI<
    GetBauprogrammeByPageableRequest,
    PagedModelBauprogrammResponseDTO
  >((params) => api.getBauprogrammeByPageable(params));
}

export function useGetBauprogramm() {
  const api = ApiFactory.getInstance(BauprogrammControllerApi);

  return useAPI<GetBauprogrammRequest, BauprogrammResponseDTO>((params) =>
    api.getBauprogramm(params)
  );
}

export function useDeleteBauprogramm() {
  const api = ApiFactory.getInstance(BauprogrammControllerApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useAPI<DeleteBauprogrammRequest, void>((params) =>
    api.deleteBauprogramm(params)
  );
}
