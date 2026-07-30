import type {
  ArchivFormContext,
  ArchivResponseDTO,
  CreateArchivRequest,
  DeleteArchivRequest,
  GetArchiveintragRequest,
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
  useGetAll: useGetArchive,
  useDelete: useDeleteArchiv,
  useContext: useGetArchivFormContext,
} = requireComposables(
  createAPIComposables<
    ArchivControllerApi,
    CreateArchivRequest,
    UpdateArchivRequest,
    never,
    DeleteArchivRequest,
    GetArchiveintragRequest,
    ArchivResponseDTO,
    PagedModelArchivResponseDTO,
    ArchivFormContext
  >(ArchivControllerApi, {
    create: (api, req) => api.createArchive(req),
    update: (api, req) => api.updateArchivNotiz(req),
    getAll: (api, req) => api.getArchiveintrag(req),
    delete: (api, req) => api.deleteArchiv(req),
    context: (api) => api.getArchivFormContext(),
  })
);