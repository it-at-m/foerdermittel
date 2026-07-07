import type {
  BauleitungFormContext,
  BauleitungResponseDTO,
  CreateBauleitungRequest,
  DeleteBauleitungRequest,
  GetBauleitungenByPageableRequest,
  GetBauleitungRequest,
  PagedModelBauleitungResponseDTO,
  UpdateBauleitungRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { BauleitungControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateBauleitung() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<CreateBauleitungRequest, BauleitungResponseDTO>((params) =>
    api.createBauleitung(params)
  );
}

export function useUpdateBauleitung() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<UpdateBauleitungRequest, BauleitungResponseDTO>((params) =>
    api.updateBauleitung(params)
  );
}

export function useGetBauleitungen() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<
    GetBauleitungenByPageableRequest,
    PagedModelBauleitungResponseDTO
  >((params) => api.getBauleitungenByPageable(params));
}

export function useGetBauleitung() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<GetBauleitungRequest, BauleitungResponseDTO>((params) =>
    api.getBauleitung(params)
  );
}

export function useDeleteBauleitung() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<DeleteBauleitungRequest, void>((params) =>
    api.deleteBauleitung(params)
  );
}

export function useGetBauleitungFormContext() {
  const api = ApiFactory.getInstance(BauleitungControllerApi);

  return useAPI<void, BauleitungFormContext>(() =>
    api.getBauleitungFormContext()
  );
}
