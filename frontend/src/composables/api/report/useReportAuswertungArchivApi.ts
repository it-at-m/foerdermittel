import type {
  GetReportAuswertungArchivRequest,
  ReportAuswertungArchivFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungArchivControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungArchivOpts,
  useContext: useGetReportAuswertungArchivFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungArchivControllerApi,
    GetReportAuswertungArchivRequest,
    ReportAuswertungArchivFormContext
  >(ReportAuswertungArchivControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungArchivRequestOpts(req),

    context: (api) => api.getReportAuswertungArchivFormContext(),
  })
);

export function useReportAuswertungArchivApi(): ReportApiComposables<
  GetReportAuswertungArchivRequest,
  ReportAuswertungArchivFormContext
> {
  return {
    getOpts: useGetReportAuswertungArchivOpts(),
    context: useGetReportAuswertungArchivFormContext(),
  };
}
