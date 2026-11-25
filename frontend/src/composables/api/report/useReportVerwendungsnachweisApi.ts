import type {
  GetReportVerwendungsnachweisRequest,
  ReportVerwendungsnachweisFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportVerwendungsnachweisControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportVerwendungsnachweisOpts,
  useContext: useGetReportVerwendungsnachweisFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportVerwendungsnachweisControllerApi,
    GetReportVerwendungsnachweisRequest,
    ReportVerwendungsnachweisFormContext
  >(ReportVerwendungsnachweisControllerApi, {
    getOpts: (api, req) => api.getReportVerwendungsnachweisRequestOpts(req),

    context: (api) => api.getReportVerwendungsnachweisFormContext(),
  })
);

export function useReportVerwendungsnachweisApi(): ReportApiComposables<
  GetReportVerwendungsnachweisRequest,
  ReportVerwendungsnachweisFormContext
> {
  return {
    getOpts: useGetReportVerwendungsnachweisOpts(),
    context: useGetReportVerwendungsnachweisFormContext(),
  };
}
