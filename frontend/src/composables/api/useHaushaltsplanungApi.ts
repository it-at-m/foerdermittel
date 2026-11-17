
import type {
  GetHhplanByHhjahrRequest,
  GetHhplanRequest,
  HhplanResponseDTO,
  PagedModelHhplanResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { ApiComposables } from "@/util/composable-helper";

import { HhplanControllerApi } from "@/api/generated/foerdermittel-backend";
import { createAPIComposables } from "@/util/composable-helper";

type GetHhplanCombinedRequest =
    | GetHhplanRequest
    | GetHhplanByHhjahrRequest;

export const {
  useGetAll: useGetHaushaltsplanungen,
} = createAPIComposables<
    HhplanControllerApi,
    never,
    never,
    never,
    never,
    GetHhplanCombinedRequest,
    HhplanResponseDTO,
    PagedModelHhplanResponseDTO,
    never
>(HhplanControllerApi, {
  getAll: async (api, req) => {
    if ("hhjahr" in req) {
      const content = await api.getHhplanByHhjahr({
        hhjahr: req.hhjahr,
      });

      return { content };
    }

    return api.getHhplan(req);
  },
});

export function useHaushaltsplanungApi(): ApiComposables<
    HhplanResponseDTO,
    never,
    never,
    HhplanResponseDTO,
    never,
    HhplanResponseDTO,
    never,
    GetHhplanCombinedRequest
> {
  return {
    getAll: useGetHaushaltsplanungen?.(),
  };
}
