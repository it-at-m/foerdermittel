import type {
  GetReportAuswertungAntraegeRequest,
  ReportAuswertungAntraegeFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungAntraegeControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungAntraegeOpts,
  useContext: useGetReportAuswertungAntraegeFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungAntraegeControllerApi,
    GetReportAuswertungAntraegeRequest,
    ReportAuswertungAntraegeFormContext
  >(ReportAuswertungAntraegeControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungAntraegeRequestOpts(req),

    context: (api) => api.getReportAuswertungAntraegeFormContext(),
  })
);

export function useReportAuswertungAntraegeApi(): ReportApiComposables<
  GetReportAuswertungAntraegeRequest,
  ReportAuswertungAntraegeFormContext
> {
  return {
    getOpts: useGetReportAuswertungAntraegeOpts(),
    context: useGetReportAuswertungAntraegeFormContext(),
  };
}
