import type {
  GetReportStichworteRequest,
  ReportStichworteFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportStichworteOpts,
  useContext: useGetReportStichworteFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportControllerApi,
    GetReportStichworteRequest,
    ReportStichworteFormContext
  >(ReportControllerApi, {
    getOpts: (api, req) => api.getReportStichworteRequestOpts(req),
    context: (api) => api.getReportStichworteFormContext(),
  })
);

export function useReportStichworteApi(): ReportApiComposables<
  GetReportStichworteRequest,
  ReportStichworteFormContext
> {
  return {
    getOpts: useGetReportStichworteOpts(),
    context: useGetReportStichworteFormContext(),
  };
}
