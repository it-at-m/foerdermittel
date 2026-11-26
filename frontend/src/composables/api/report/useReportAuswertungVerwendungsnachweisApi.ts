import type {
  GetReportAuswertungVerwendungsnachweisRequest,
  ReportAuswertungVerwendungsnachweisFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungVerwendungsnachweisControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungVerwendungsnachweisOpts,
  useContext: useGetReportAuswertungVerwendungsnachweisFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungVerwendungsnachweisControllerApi,
    GetReportAuswertungVerwendungsnachweisRequest,
    ReportAuswertungVerwendungsnachweisFormContext
  >(ReportAuswertungVerwendungsnachweisControllerApi, {
    getOpts: (api, req) =>
      api.getReportAuswertungVerwendungsnachweisRequestOpts(req),

    context: (api) => api.getReportAuswertungVerwendungsnachweisFormContext(),
  })
);

export function useReportAuswertungVerwendungsnachweisApi(): ReportApiComposables<
  GetReportAuswertungVerwendungsnachweisRequest,
  ReportAuswertungVerwendungsnachweisFormContext
> {
  return {
    getOpts: useGetReportAuswertungVerwendungsnachweisOpts(),
    context: useGetReportAuswertungVerwendungsnachweisFormContext(),
  };
}
