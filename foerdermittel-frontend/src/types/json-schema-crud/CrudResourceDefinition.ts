import type { UISchemaElement } from "@jsonforms/core";

export type CrudFormData = Record<string, unknown>;

export interface CrudIndexColumn<TItem extends CrudFormData> {
  key: keyof TItem & string;
  title: string;
}

export interface CrudResourceTexts {
  indexTitle: string;
  createTitle: string;
  editTitle: string;
  createAction: string;
  saveSuccess: string;
  deleteTitle: string;
  deleteSuccess: string;
  getDeleteText: (label: string) => string;
}

export interface CrudResourceDefinition<
  TIndex extends CrudFormData = CrudFormData,
  TCreate extends CrudFormData = CrudFormData,
  TUpdate extends CrudFormData = CrudFormData,
> {
  key: string;
  routeBase: string;
  apiBase: string;
  routeParam: string;
  texts: CrudResourceTexts;
  columns: CrudIndexColumn<TIndex>[];
  getEntityId: (item: TIndex | TUpdate) => string;
  getEntityLabel: (item: Partial<TIndex & TUpdate> & CrudFormData) => string;
  createUiSchema?: UISchemaElement;
  updateUiSchema?: UISchemaElement;
  mapCreatePayload?: (data: CrudFormData) => TCreate;
  mapUpdatePayload?: (data: CrudFormData) => TUpdate;
}
