import type {
  CreateKrankenhausRequest,
  DeleteKrankenhausRequest,
  GetKrankenhaeuserByPageableRequest,
  GetKrankenhausRequest,
  KrankenhausFormContext,
  KrankenhausResponseDTO,
  PagedModelKrankenhausResponseDTO,
  UpdateKrankenhausRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { KrankenhausControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateKrankenhaus() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<CreateKrankenhausRequest, KrankenhausResponseDTO>((params) =>
    api.createKrankenhaus(params)
  );
}

export function useUpdateKrankenhaus() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<UpdateKrankenhausRequest, KrankenhausResponseDTO>((params) =>
    api.updateKrankenhaus(params)
  );
}

export function useGetKrankenhaeuser() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<
    GetKrankenhaeuserByPageableRequest,
    PagedModelKrankenhausResponseDTO
  >((params) => api.getKrankenhaeuserByPageable(params));
}

export function useGetKrankenhaus() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<GetKrankenhausRequest, KrankenhausResponseDTO>((params) =>
    api.getKrankenhaus(params)
  );
}

export function useDeleteKrankenhaus() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<DeleteKrankenhausRequest, void>((params) =>
    api.deleteKrankenhaus(params)
  );
}

export function useGetKrankenhausFormContext() {
  const api = ApiFactory.getInstance(KrankenhausControllerApi);

  return useAPI<void, KrankenhausFormContext>(() =>
    api.getKrankenhausFormContext()
  );
}
