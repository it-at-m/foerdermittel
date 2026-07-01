import type {
  DeleteTraegerRequest,
  GetTraegerByPageableRequest,
  GetTraegerRequest,
  PagedModelTraegerResponseDTO,
  SaveTraegerRequest,
  TraegerFormContextDTO,
  TraegerResponseDTO,
  UpdateTraegerRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { TraegerControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateTraeger() {
  const api = ApiFactory.getInstance(TraegerControllerApi);

  return useAPI<CreateTraegerRequest, TraegerResponseDTO>((params) =>
    api.createTraeger(params)
  );
}

export function useUpdateTraeger() {
  const api = ApiFactory.getInstance(TraegerControllerApi);

  return useAPI<UpdateTraegerRequest, TraegerResponseDTO>((params) =>
    api.updateTraeger(params)
  );
}

export function useGetTraeger() {
  const api = ApiFactory.getInstance(TraegerControllerApi);

  return useAPI<GetTraegerByPageableRequest, PagedModelTraegerResponseDTO>(
    (params) => api.getTraegerByPageable(params)
  );
}

export function useDeleteTraeger() {
  const api = ApiFactory.getInstance(TraegerControllerApi);

  return useAPI<DeleteTraegerRequest, void>((params) =>
    api.deleteTraeger(params)
  );
}

export function useGetTraegerFormContext() {
  const api = ApiFactory.getInstance(TraegerControllerApi);

  return useAPI<void, TraegerFormContextDTO>(() => api.getTraegerFormContext());
}
