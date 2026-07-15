import type {
  CreateKurzbezeichnungRequest,
  DeleteKurzbezeichnungRequest,
  GetKurzbezeichnungenByPageableRequest,
  GetKurzbezeichnungRequest,
  KurzbezeichnungFormContext,
  KurzbezeichnungResponseDTO,
  PagedModelKurzbezeichnungResponseDTO,
  UpdateKurzbezeichnungRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { KurzbezeichnungControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateKurzbezeichnung() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<CreateKurzbezeichnungRequest, KurzbezeichnungResponseDTO>(
    (params) => api.createKurzbezeichnung(params)
  );
}

export function useUpdateKurzbezeichnung() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<UpdateKurzbezeichnungRequest, KurzbezeichnungResponseDTO>(
    (params) => api.updateKurzbezeichnung(params)
  );
}

export function useGetKurzbezeichnungen() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<
    GetKurzbezeichnungenByPageableRequest,
    PagedModelKurzbezeichnungResponseDTO
  >((params) => api.getKurzbezeichnungenByPageable(params));
}

export function useGetKurzbezeichnung() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<GetKurzbezeichnungRequest, KurzbezeichnungResponseDTO>(
    (params) => api.getKurzbezeichnung(params)
  );
}

export function useDeleteKurzbezeichnung() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<DeleteKurzbezeichnungRequest, void>((params) =>
    api.deleteKurzbezeichnung(params)
  );
}

export function useGetKurzbezeichnungFormContext() {
  const api = ApiFactory.getInstance(KurzbezeichnungControllerApi);

  return useAPI<void, KurzbezeichnungFormContext>(() =>
    api.getKurzbezeichnungFormContext()
  );
}
