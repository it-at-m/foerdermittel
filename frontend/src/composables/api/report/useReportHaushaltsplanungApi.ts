import type {
  GetReportHaushaltsplanungRequest,
  ReportHaushaltsplanungFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportHaushaltsplanungControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportHaushaltsplanungOpts,
  useContext: useGetReportHaushaltsplanungFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportHaushaltsplanungControllerApi,
    GetReportHaushaltsplanungRequest,
    ReportHaushaltsplanungFormContext
  >(ReportHaushaltsplanungControllerApi, {
    getOpts: (api, req) => api.getReportHaushaltsplanungRequestOpts(req),
    context: (api) => api.getReportHaushaltsplanungFormContext(),
  })
);

export function useReportHaushaltsplanungApi(): ReportApiComposables<
  GetReportHaushaltsplanungRequest,
  ReportHaushaltsplanungFormContext
> {
  return {
    getOpts: useGetReportHaushaltsplanungOpts(),
    context: useGetReportHaushaltsplanungFormContext(),
  };
}
