import type {
  ArchivFormContext,
  ArchivResponseDTO,
  CreateArchivRequest,
  DeleteArchivRequest,
  GetArchiveintraegeRequest,
  PagedModelArchivResponseDTO,
  UpdateArchivRequest,
} from "@/api/generated/foerdermittel-backend";

import { ArchivControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateArchiv,
  useUpdate: useUpdateArchiv,
  useGetAll: useGetArchiv,
  useDelete: useDeleteArchiv,
  useContext: useGetArchivFormContext,
} = requireComposables(
  createAPIComposables<
    ArchivControllerApi,
    CreateArchivRequest,
    UpdateArchivRequest,
    never,
    DeleteArchivRequest,
    GetArchiveintraegeRequest,
    ArchivResponseDTO,
    PagedModelArchivResponseDTO,
    ArchivFormContext
  >(ArchivControllerApi, {
    create: (api, req) => api.createArchiv(req),
    update: (api, req) => api.updateArchiv(req),
    getAll: (api, req) => api.getArchiveintraege(req),
    delete: (api, req) => api.deleteArchiv(req),
    context: (api) => api.getArchivFormContext(),
  })
);
