import type {
  BenutzerhinweisResponseDTO,
  CreateBenutzerhinweisRequest,
  GetBenutzerhinweisRequest,
  UpdateBenutzerhinweisRequest,
} from "@/api/generated/foerdermittel-backend";

import { createAPIComposables } from "@/api/create-api-composables";
import { BenutzerhinweisControllerApi } from "@/api/generated/foerdermittel-backend";

export const {
  useCreate: useCreateBenutzerhinweis,
  useUpdate: useUpdateBenutzerhinweis,
  useGet: useGetBenutzerhinweis,
} = createAPIComposables<
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
});
