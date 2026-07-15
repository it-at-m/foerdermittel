import type {
  CreateFoerderbereichRequest,
  DeleteFoerderbereichRequest,
  FoerderbereichFormContext,
  FoerderbereichResponseDTO,
  GetFoerderbereicheByPageableRequest,
  GetFoerderbereichRequest,
  PagedModelFoerderbereichResponseDTO,
  UpdateFoerderbereichRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { FoerderbereichControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateFoerderbereich() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<CreateFoerderbereichRequest, FoerderbereichResponseDTO>(
    (params) => api.createFoerderbereich(params)
  );
}

export function useUpdateFoerderbereich() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<UpdateFoerderbereichRequest, FoerderbereichResponseDTO>(
    (params) => api.updateFoerderbereich(params)
  );
}

export function useGetFoerderbereiche() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<
    GetFoerderbereicheByPageableRequest,
    PagedModelFoerderbereichResponseDTO
  >((params) => api.getFoerderbereicheByPageable(params));
}

export function useGetFoerderbereich() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<GetFoerderbereichRequest, FoerderbereichResponseDTO>((params) =>
    api.getFoerderbereich(params)
  );
}

export function useDeleteFoerderbereich() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<DeleteFoerderbereichRequest, void>((params) =>
    api.deleteFoerderbereich(params)
  );
}

export function useGetFoerderbereichFormContext() {
  const api = ApiFactory.getInstance(FoerderbereichControllerApi);

  return useAPI<void, FoerderbereichFormContext>(() =>
    api.getFoerderbereichFormContext()
  );
}
