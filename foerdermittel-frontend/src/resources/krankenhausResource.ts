import type { CrudResourceDefinition } from "@/types/json-schema-crud/CrudResourceDefinition";
import type { UISchemaElement } from "@jsonforms/core";

const createUiSchema: UISchemaElement = {
  type: "VerticalLayout",
  elements: [
    {
      type: "Control",
      scope: "#/properties/krhname",
    },
    {
      type: "Control",
      scope: "#/properties/bezeichnung",
    },
  ],
} as const;

const updateUiSchema: UISchemaElement = {
  type: "VerticalLayout",
  elements: [
    {
      type: "Control",
      scope: "#/properties/bezeichnung",
    },
  ],
};

export const krankenhausResource: CrudResourceDefinition = {
  key: "krankenhaus",
  routeBase: "/krankenhaeuser",
  apiBase: "/api/backend-service/krankenhaeuser",
  routeParam: "entityId",
  texts: {
    indexTitle: "Krankenhäuser",
    createTitle: "Krankenhaus anlegen",
    editTitle: "Krankenhaus bearbeiten",
    createAction: "Krankenhaus anlegen",
    saveSuccess: "Der Datensatz wurde erfolgreich gespeichert.",
    deleteTitle: "Krankenhaus löschen?",
    deleteSuccess: "Der Datensatz wurde erfolgreich gelöscht.",
    getDeleteText: (name) => `Soll „${name}“ wirklich gelöscht werden?`,
  },
  columns: [
    {
      key: "krhname",
      title: "Kürzel",
    },
    {
      key: "bezeichnung",
      title: "Bezeichnung",
    },
  ],
  getEntityId: (item) => String(item.krhname ?? ""),
  getEntityLabel: (item) =>
    String(item.bezeichnung ?? item.krhname ?? "Unbekanntes Krankenhaus"),
  createUiSchema,
  updateUiSchema,
};
