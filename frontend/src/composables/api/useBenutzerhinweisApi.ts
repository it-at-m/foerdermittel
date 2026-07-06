import type {
  BenutzerhinweisResponseDTO,
  CreateBenutzerhinweisRequest,
  GetBenutzerhinweisRequest,
  UpdateBenutzerhinweisRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { BenutzerhinweisControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreateBenutzerhinweis() {
  const api = ApiFactory.getInstance(BenutzerhinweisControllerApi);

  return useAPI<CreateBenutzerhinweisRequest, BenutzerhinweisResponseDTO>((params) =>
    api.createBenutzerhinweis(params)
  );
}

export function useUpdateBenutzerhinweis() {
  const api = ApiFactory.getInstance(BenutzerhinweisControllerApi);

  return useAPI<UpdateBenutzerhinweisRequest, BenutzerhinweisResponseDTO>((params) =>
    api.updateBenutzerhinweis(params)
  );
}

export function useGetBenutzerhinweis() {
  const api = ApiFactory.getInstance(BenutzerhinweisControllerApi);

  return useAPI<GetBenutzerhinweisRequest, BenutzerhinweisResponseDTO>((params) =>
    api.getBenutzerhinweis(params)
  );
}
