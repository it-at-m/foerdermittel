import type {
  GetReportAuswertungProjektRequest,
  GetReportStichworteRequest, ReportAuswertungProjektFormContext,
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

export const {
  useGetOpts: useGetReportAuswertungProjekteOpts,
  useContext: useGetReportAuswertungProjekteFormContext,
} = requireComposables(
    createReportAPIComposables<
        ReportControllerApi,
        GetReportAuswertungProjektRequest,
        ReportAuswertungProjektFormContext
    >(ReportControllerApi, {
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
