import type {
  GetReportBewilligungAuszahlungProjektRequest,
  ReportBewilligungAuszahlungProjektFormContext,
} from "@/api/generated/foerdermittel-backend";
import type { ReportApiComposables } from "@/util/composable-helper";

import { ReportBewilligungAuszahlungProjektControllerApi } from "@/api/generated/foerdermittel-backend";
import {
  createReportAPIComposables,
  requireComposables,
} from "@/util/composable-helper";

export const {
  useGetOpts: useGetReportBewilligungAuszahlungProjektOpts,
  useContext: useGetReportBewilligungAuszahlungProjektFormContext,
} = requireComposables(
  createReportAPIComposables<
    ReportBewilligungAuszahlungProjektControllerApi,
    GetReportBewilligungAuszahlungProjektRequest,
    ReportBewilligungAuszahlungProjektFormContext
  >(ReportBewilligungAuszahlungProjektControllerApi, {
    getOpts: (api, req) =>
      api.getReportBewilligungAuszahlungProjektRequestOpts(req),
    context: (api) => api.getReportBewilligungAuszahlungProjektFormContext(),
  })
);

export function useReportBewilligungAuszahlungProjektApi(): ReportApiComposables<
  GetReportBewilligungAuszahlungProjektRequest,
  ReportBewilligungAuszahlungProjektFormContext
> {
  return {
    getOpts: useGetReportBewilligungAuszahlungProjektOpts(),
    context: useGetReportBewilligungAuszahlungProjektFormContext(),
  };
}
