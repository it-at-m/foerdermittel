import type { GetReportHaushaltsplanungRequest, GetReportProjektuebersichtRequest, GetReportStichworteRequest, ReportHaushaltsplanungFormContext, ReportProjektuebersichtFormContext, ReportStichworteFormContext } from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";



import { ReportControllerApi } from "@/api/generated/foerdermittel-backend";
import { createReportAPIComposables, requireComposables } from "@/util/composable-helper";


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

//Haushaltsplanung Haushaltsplanung

export const {
  useGetOpts: useGetReportHaushaltsplanungOpts,
  useContext: useGetReportHaushaltsplanungFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportControllerApi,
    GetReportHaushaltsplanungRequest,
    ReportHaushaltsplanungFormContext
  >(ReportControllerApi, {
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
