import type {
  CreatePublikationRequest,
  DeletePublikationRequest,
  GetPublikationenByPageableRequest,
  GetPublikationRequest,
  PagedModelPublikationResponseDTO,
  PublikationFormContext,
  PublikationResponseDTO,
  UpdatePublikationRequest,
} from "@/api/generated/foerdermittel-backend";

import { ApiFactory } from "@/api/ApiFactory";
import { PublikationControllerApi } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function useCreatePublikation() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<CreatePublikationRequest, PublikationResponseDTO>((params) =>
    api.createPublikation(params)
  );
}

export function useUpdatePublikation() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<UpdatePublikationRequest, PublikationResponseDTO>((params) =>
    api.updatePublikation(params)
  );
}

export function useGetPublikationen() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<
    GetPublikationenByPageableRequest,
    PagedModelPublikationResponseDTO
  >((params) => api.getPublikationenByPageable(params));
}

export function useGetPublikation() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<GetPublikationRequest, PublikationResponseDTO>((params) =>
    api.getPublikation(params)
  );
}

export function useDeletePublikation() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<DeletePublikationRequest, void>((params) =>
    api.deletePublikation(params)
  );
}

export function useGetPublikationFormContext() {
  const api = ApiFactory.getInstance(PublikationControllerApi);

  return useAPI<void, PublikationFormContext>(() =>
    api.getPublikationFormContext()
  );
}
