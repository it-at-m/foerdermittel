import type {
  BenutzerhinweisResponseDTO,
  CreateBenutzerhinweisRequest,
  GetBenutzerhinweisRequest,
  UpdateBenutzerhinweisRequest,
} from "@/api/generated/foerdermittel-backend";

import { BenutzerhinweisControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useCreate: useCreateBenutzerhinweis,
  useUpdate: useUpdateBenutzerhinweis,
  useGet: useGetBenutzerhinweis,
} = requireComposables(
  createAPIComposables<
    BenutzerhinweisControllerApi,
    CreateBenutzerhinweisRequest,
    UpdateBenutzerhinweisRequest,
    GetBenutzerhinweisRequest,
    never,
    never,
    BenutzerhinweisResponseDTO,
    never,
    never
  >(BenutzerhinweisControllerApi, {
    create: (api, req) => api.createBenutzerhinweis(req),
    update: (api, req) => api.updateBenutzerhinweis(req),
    get: (api, req) => api.getBenutzerhinweis(req),
  })
);
