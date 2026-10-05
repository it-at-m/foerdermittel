import type {
  GetReportHaushalt1Request,
  GetReportProjektuebersichtRequest,
  GetReportStichworteRequest,
  ReportHaushalt1FormContext,
  ReportProjektuebersichtFormContext,
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

//Haushalt1 Haushaltsplanung

export const {
  useGetOpts: useGetReportHaushalt1Opts,
  useContext: useGetReportHaushalt1FormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportControllerApi,
    GetReportHaushalt1Request,
    ReportHaushalt1FormContext
  >(ReportControllerApi, {
    getOpts: (api, req) => api.getReportHaushalt1RequestOpts(req),
    context: (api) => api.getReportHaushalt1FormContext(),
  })
);

export function useReportHaushalt1Api(): ReportApiComposables<
  GetReportHaushalt1Request,
  ReportHaushalt1FormContext
> {
  return {
    getOpts: useGetReportHaushalt1Opts(),
    context: useGetReportHaushalt1FormContext(),
  };
}

export const {
  useGetOpts: useGetReportProjektuebersichtOpts,
  useContext: useGetReportProjektuebersichtFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportControllerApi,
    GetReportProjektuebersichtRequest,
    ReportProjektuebersichtFormContext
  >(ReportControllerApi, {
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
