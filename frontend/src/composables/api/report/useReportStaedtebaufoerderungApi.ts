import type {
  GetReportStaedtebaufoerderungRequest,
  ReportStaedtebaufoerderungFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportStaedtebaufoerderungControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportStaedtebaufoerderungOpts,
  useContext: useGetReportStaedtebaufoerderungFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportStaedtebaufoerderungControllerApi,
    GetReportStaedtebaufoerderungRequest,
    ReportStaedtebaufoerderungFormContext
  >(ReportStaedtebaufoerderungControllerApi, {
    getOpts: (api, req) => api.getReportStaedtebaufoerderungRequestOpts(req),
    context: (api) => api.getReportStaedtebaufoerderungFormContext(),
  })
);

export function useReportStaedtebaufoerderungApi(): ReportApiComposables<
  GetReportStaedtebaufoerderungRequest,
  ReportStaedtebaufoerderungFormContext
> {
  return {
    getOpts: useGetReportStaedtebaufoerderungOpts(),
    context: useGetReportStaedtebaufoerderungFormContext(),
  };
}
