import type {
  GetReportAuswertungBewilligungenRequest,
  ReportAuswertungBewilligungenFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungBewilligungenControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungBewilligungenOpts,
  useContext: useGetReportAuswertungBewilligungenFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungBewilligungenControllerApi,
    GetReportAuswertungBewilligungenRequest,
    ReportAuswertungBewilligungenFormContext
  >(ReportAuswertungBewilligungenControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungBewilligungenRequestOpts(req),

    context: (api) => api.getReportAuswertungBewilligungenFormContext(),
  })
);

export function useReportAuswertungBewilligungenApi(): ReportApiComposables<
  GetReportAuswertungBewilligungenRequest,
  ReportAuswertungBewilligungenFormContext
> {
  return {
    getOpts: useGetReportAuswertungBewilligungenOpts(),
    context: useGetReportAuswertungBewilligungenFormContext(),
  };
}
