import type {
  GetReportAuswertungAbrufeRequest,
  ReportAuswertungAbrufeFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungAbrufeControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungAbrufeOpts,
  useContext: useGetReportAuswertungAbrufeFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungAbrufeControllerApi,
    GetReportAuswertungAbrufeRequest,
    ReportAuswertungAbrufeFormContext
  >(ReportAuswertungAbrufeControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungAbrufeRequestOpts(req),

    context: (api) => api.getReportAuswertungAbrufeFormContext(),
  })
);

export function useReportAuswertungAbrufeApi(): ReportApiComposables<
  GetReportAuswertungAbrufeRequest,
  ReportAuswertungAbrufeFormContext
> {
  return {
    getOpts: useGetReportAuswertungAbrufeOpts(),
    context: useGetReportAuswertungAbrufeFormContext(),
  };
}
