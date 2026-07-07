import type {
  CreateReferatRequest,
  DeleteReferatRequest,
  GetReferateByPageableRequest,
  GetReferatRequest,
  PagedModelReferatResponseDTO,
  ReferatFormContext,
  ReferatResponseDTO,
  UpdateReferatRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { ReferatControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateReferat() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<CreateReferatRequest, ReferatResponseDTO>((params) =>
    api.createReferat(params)
  );
}

export function useUpdateReferat() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<UpdateReferatRequest, ReferatResponseDTO>((params) =>
    api.updateReferat(params)
  );
}

export function useGetReferate() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<GetReferateByPageableRequest, PagedModelReferatResponseDTO>(
    (params) => api.getReferateByPageable(params)
  );
}

export function useGetReferat() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<GetReferatRequest, ReferatResponseDTO>((params) =>
    api.getReferat(params)
  );
}

export function useDeleteReferat() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<DeleteReferatRequest, void>((params) =>
    api.deleteReferat(params)
  );
}

export function useGetReferatFormContext() {
  const api = ApiFactory.getInstance(ReferatControllerApi);

  return useAPI<void, ReferatFormContext>(() => api.getReferatFormContext());
}
