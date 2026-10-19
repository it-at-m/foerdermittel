import type {
  GetReportProjektuebersichtMsRequest,
  ReportProjektuebersichtMsFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import {
  ReportProjektuebersichtMsControllerApi,
} from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportProjektuebersichtMsOpts,
  useContext: useGetReportProjektuebersichtMsFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportProjektuebersichtMsControllerApi,
    GetReportProjektuebersichtMsRequest,
    ReportProjektuebersichtMsFormContext
  >(ReportProjektuebersichtMsControllerApi, {
    getOpts: (api, req) => api.getReportProjektuebersichtMsRequestOpts(req),
    context: (api) => api.getReportProjektuebersichtMsFormContext(),
  })
);

export function useReportProjektuebersichtMsApi(): ReportApiComposables<
  GetReportProjektuebersichtMsRequest,
  ReportProjektuebersichtMsFormContext
> {
  return {
    getOpts: useGetReportProjektuebersichtMsOpts(),
    context: useGetReportProjektuebersichtMsFormContext(),
  };
}
