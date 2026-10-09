import type { GetReportEuinformationenRequest, ReportEuinformationFormContext } from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";



import { ReportEuinformationControllerApi } from "@/api/generated/foerdermittel-backend";
import { createReportAPIComposables, requireComposables } from "@/util/composable-helper";


export const {
  useGetOpts: useGetReportEuinformationOpts,
  useContext: useGetReportEuinformationFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportEuinformationControllerApi,
    GetReportEuinformationenRequest,
    ReportEuinformationFormContext
  >(ReportEuinformationControllerApi, {
    getOpts: (api, req) => api.getReportEuinformationenRequestOpts(req),
    context: (api) => api.getReportEuinformationenFormContext(),
  })
);

export function useReportEuinformationApi(): ReportApiComposables<
  GetReportEuinformationenRequest,
  ReportEuinformationFormContext
> {
  return {
    getOpts: useGetReportEuinformationOpts(),
    context: useGetReportEuinformationFormContext(),
  };
}
