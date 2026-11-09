import type {
  GetReportProjektuebersichtRequest,
  ReportProjektuebersichtFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportProjektuebersichtControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportProjektuebersichtOpts,
  useContext: useGetReportProjektuebersichtFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportProjektuebersichtControllerApi,
    GetReportProjektuebersichtRequest,
    ReportProjektuebersichtFormContext
  >(ReportProjektuebersichtControllerApi, {
    getOpts: (api, req) => api.getReportProjektuebersichtRequestOpts(req),
    context: (api) => api.getReportProjektuebersichtFormContext(),
  })
);

export function useReportProjektuebersichtApi(): ReportApiComposables<
  GetReportProjektuebersichtRequest,
  ReportProjektuebersichtFormContext
> {
  return {
    getOpts: useGetReportProjektuebersichtOpts(),
    context: useGetReportProjektuebersichtFormContext(),
  };
}
