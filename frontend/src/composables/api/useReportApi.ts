import type {
  GetReportProjektuebersichtRequest,
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

export const {
  useGetOpts: useGetReportProjektuebersichtOpts,
  useContext: useGetReportProjektuebersichtFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportControllerApi,
    GetReportProjektuebersichtRequest,
    object
  >(ReportControllerApi, {
    getOpts: (api, req) => api.getReportProjektuebersichtRequestOpts(req),
    context: (api) => api.getReportProjektuebersichtFormContext(),
  })
);

export function useReportProjektuebersichtApi(): ReportApiComposables<
  GetReportProjektuebersichtRequest,
  object
> {
  return {
    getOpts: useGetReportProjektuebersichtOpts(),
    context: useGetReportProjektuebersichtFormContext(),
  };
}
