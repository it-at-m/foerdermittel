import type { ApiCtor } from "@/api/ApiFactory";

import { ApiFactory } from "@/api/ApiFactory";
import { BaseAPI } from "@/api/generated/foerdermittel-backend";
import useAPI from "@/composables/useAPI";

export function createAPIComposables<
  Api extends BaseAPI,
  CreateReq,
  UpdateReq,
  GetReq,
  DeleteReq,
  GetAllReq,
  Response,
  ListResponse,
  Context,
>(
  ApiClass: ApiCtor<Api>,
  methods: {
    create?: (api: Api, req: CreateReq) => Promise<Response>;
    update?: (api: Api, req: UpdateReq) => Promise<Response>;
    get?: (api: Api, req: GetReq) => Promise<Response>;
    getAll?: (api: Api, req: GetAllReq) => Promise<ListResponse>;
    delete?: (api: Api, req: DeleteReq) => Promise<void>;
    context?: (api: Api) => Promise<Context>;
  }
) {
  const api = ApiFactory.getInstance(ApiClass);

  const { create, update, get, getAll, delete: deleteFn, context } = methods;

  return {
    ...(create && {
      useCreate: () => useAPI((req: CreateReq) => create(api, req)),
    }),
    ...(update && {
      useUpdate: () => useAPI((req: UpdateReq) => update(api, req)),
    }),
    ...(get && {
      useGet: () => useAPI((req: GetReq) => get(api, req)),
    }),
    ...(getAll && {
      useList: () => useAPI((req: GetAllReq) => getAll(api, req)),
    }),
    ...(deleteFn && {
      useDelete: () => useAPI((req: DeleteReq) => deleteFn(api, req)),
    }),
    ...(context && {
      useContext: () => useAPI(() => context(api)),
    }),
  };
}
