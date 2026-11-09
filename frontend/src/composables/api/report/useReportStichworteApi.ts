import type {
  GetReportStichworteRequest,
  ReportStichworteFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportStichworteControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportStichworteOpts,
  useContext: useGetReportStichworteFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportStichworteControllerApi,
    GetReportStichworteRequest,
    ReportStichworteFormContext
  >(ReportStichworteControllerApi, {
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
