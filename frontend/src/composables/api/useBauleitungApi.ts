import type {
  BauleitungFormContext,
  BauleitungResponseDTO,
  CreateBauleitungRequest,
  DeleteBauleitungRequest,
  GetBauleitungenByPageableRequest,
  PagedModelBauleitungResponseDTO,
  UpdateBauleitungRequest,
} from "@/api/generated/foerdermittel-backend";

import { BauleitungControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateBauleitung,
  useUpdate: useUpdateBauleitung,
  useGetAll: useGetBauleitungen,
  useDelete: useDeleteBauleitung,
  useContext: useGetBauleitungFormContext,
} = requireComposables(
  createAPIComposables<
    BauleitungControllerApi,
    CreateBauleitungRequest,
    UpdateBauleitungRequest,
    never,
    DeleteBauleitungRequest,
    GetBauleitungenByPageableRequest,
    BauleitungResponseDTO,
    PagedModelBauleitungResponseDTO,
    BauleitungFormContext
  >(BauleitungControllerApi, {
    create: (api, req) => api.createBauleitung(req),
    update: (api, req) => api.updateBauleitung(req),
    getAll: (api, req) => api.getBauleitungenByPageable(req),
    delete: (api, req) => api.deleteBauleitung(req),
    context: (api) => api.getBauleitungFormContext(),
  })
);
