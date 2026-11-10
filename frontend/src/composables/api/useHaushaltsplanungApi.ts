import type {
  GetHhplanRequest,
  HhplanResponseDTO,
  PagedModelHhplanResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { HhplanControllerApi } from "@/api/generated/foerdermittel-backend";
import { createAPIComposables } from "@/util/composable-helper";

export const {
  useCreate: useCreateHaushaltsplanung,
  useUpdate: useUpdateHaushaltsplanung,
  useGetAll: useGetHaushaltsplanungen,
  useDelete: useDeleteHaushaltsplanung,
  useContext: useGetHaushaltsplanungFormContext,
} = createAPIComposables<
  HhplanControllerApi,
  never,
  never,
  never,
  never,
  GetHhplanRequest,
  HhplanResponseDTO,
  PagedModelHhplanResponseDTO,
  never
>(HhplanControllerApi, {
  getAll: (api, req) => api.getHhplan(req),
});

export function useHaushaltsplanungApi(): ApiComposables<
  HhplanResponseDTO,
  never,
  never,
  HhplanResponseDTO,
  never,
  HhplanResponseDTO,
  never
> {
  return {
    getAll: useGetHaushaltsplanungen?.(),
    context: useGetHaushaltsplanungFormContext?.(),
    create: useCreateHaushaltsplanung?.(),
    update: useUpdateHaushaltsplanung?.(),
    delete: useDeleteHaushaltsplanung?.(),
  };
}
