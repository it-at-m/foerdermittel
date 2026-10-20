import type {
  GetReportAuswertungProjektRequest,
  ReportAuswertungProjektFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportAuswertungProjekteControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportAuswertungProjekteOpts,
  useContext: useGetReportAuswertungProjekteFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportAuswertungProjekteControllerApi,
    GetReportAuswertungProjektRequest,
    ReportAuswertungProjektFormContext
  >(ReportAuswertungProjekteControllerApi, {
    getOpts: (api, req) => api.getReportAuswertungProjektRequestOpts(req),

    context: (api) => api.getReportAuswertungProjektFormContext(),
  })
);

export function useReportAuswertungProjekteApi(): ReportApiComposables<
  GetReportAuswertungProjektRequest,
  ReportAuswertungProjektFormContext
> {
  return {
    getOpts: useGetReportAuswertungProjekteOpts(),
    context: useGetReportAuswertungProjekteFormContext(),
  };
}
