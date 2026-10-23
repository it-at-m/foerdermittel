import type {
  GetReportFortsetzungsantragRequest,
  ReportFortsetzungsantragFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportFortsetzungsantragControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

//Fortsetzungsantrag
export const {
  useGetOpts: useGetReportFortsetzungsantragOpts,
  useContext: useGetReportFortsetzungsantragFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportFortsetzungsantragControllerApi,
    GetReportFortsetzungsantragRequest,
    ReportFortsetzungsantragFormContext
  >(ReportFortsetzungsantragControllerApi, {
    getOpts: (api, req) => api.getReportFortsetzungsantragRequestOpts(req),
    context: (api) => api.getReportFortsetzungsantragFormContext(),
  })
);

export function useReportFortsetzungsantragApi(): ReportApiComposables<
  GetReportFortsetzungsantragRequest,
  ReportFortsetzungsantragFormContext
> {
  return {
    getOpts: useGetReportFortsetzungsantragOpts(),
    context: useGetReportFortsetzungsantragFormContext(),
  };
}
