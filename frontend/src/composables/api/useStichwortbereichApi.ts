import type {
  CreateStichwortbereichRequest,
  DeleteStichwortbereichRequest,
  GetStichwortbereicheByPageableRequest,
  GetStichwortbereichRequest,
  PagedModelStichwortbereichResponseDTO,
  StichwortbereichFormContext,
  StichwortbereichResponseDTO,
  UpdateStichwortbereichRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { StichwortbereichControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateStichwortbereich() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<CreateStichwortbereichRequest, StichwortbereichResponseDTO>(
    (params) => api.createStichwortbereich(params)
  );
}

export function useUpdateStichwortbereich() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<UpdateStichwortbereichRequest, StichwortbereichResponseDTO>(
    (params) => api.updateStichwortbereich(params)
  );
}

export function useGetStichwortbereiche() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<
    GetStichwortbereicheByPageableRequest,
    PagedModelStichwortbereichResponseDTO
  >((params) => api.getStichwortbereicheByPageable(params));
}

export function useGetStichwortbereich() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<GetStichwortbereichRequest, StichwortbereichResponseDTO>(
    (params) => api.getStichwortbereich(params)
  );
}

export function useDeleteStichwortbereich() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<DeleteStichwortbereichRequest, void>((params) =>
    api.deleteStichwortbereich(params)
  );
}

export function useGetStichwortbereichFormContext() {
  const api = ApiFactory.getInstance(StichwortbereichControllerApi);

  return useAPI<void, StichwortbereichFormContext>(() =>
    api.getStichwortbereichFormContext()
  );
}
