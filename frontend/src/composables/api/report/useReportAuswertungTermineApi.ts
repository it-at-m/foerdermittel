import type {
  GetReportAuswertungTermineRequest,
  ReportAuswertungTermineFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungTermineControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungTermineOpts,
  useContext: useGetReportAuswertungTermineFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungTermineControllerApi,
    GetReportAuswertungTermineRequest,
    ReportAuswertungTermineFormContext
  >(ReportAuswertungTermineControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungTermineRequestOpts(req),

    context: (api) => api.getReportAuswertungTermineFormContext(),
  })
);

export function useReportAuswertungTermineApi(): ReportApiComposables<
  GetReportAuswertungTermineRequest,
  ReportAuswertungTermineFormContext
> {
  return {
    getOpts: useGetReportAuswertungTermineOpts(),
    context: useGetReportAuswertungTermineFormContext(),
  };
}
