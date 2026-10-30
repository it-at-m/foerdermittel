import type {
  GetReportAnnahmeAnordnungSapRequest,
  ReportAnnahmeAnordnungSapFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAnnahmeAnordnungSapControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAnnahmeAnordnungSapOpts,
  useContext: useGetReportAnnahmeAnordnungSapFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAnnahmeAnordnungSapControllerApi,
    GetReportAnnahmeAnordnungSapRequest,
    ReportAnnahmeAnordnungSapFormContext
  >(ReportAnnahmeAnordnungSapControllerApi, {
    getOpts: (api, req) =>
      api.getReportAnnahmeAnordnungSapRequestOpts(req),
    context: (api) => api.getReportAnnahmeAnordnungSapFormContext(),
  })
);

export function useReportAnnahmeAnordnungSapApi(): ReportApiComposables<
  GetReportAnnahmeAnordnungSapRequest,
  ReportAnnahmeAnordnungSapFormContext
> {
  return {
    getOpts: useGetReportAnnahmeAnordnungSapOpts(),
    context: useGetReportAnnahmeAnordnungSapFormContext(),
  };
}
