import { createTestingPinia } from "@pinia/testing";
import { flushPromises, mount } from "@vue/test-utils";
import { beforeEach, describe, expect, test, vi } from "vitest";
import { createMemoryHistory, createRouter } from "vue-router";

import JsonSchemaCrudFormPage from "@/components/json-schema-form/JsonSchemaCrudFormPage.vue";
import i18n from "@/plugins/i18n";
import vuetify from "@/plugins/vuetify";
import { krankenhausResource } from "@/resources/krankenhausResource";
import { useSnackbarStore } from "@/stores/snackbar";

const createSchemaResponse = {
  type: "object",
  properties: {
    krhname: {
      type: "string",
    },
    bezeichnung: {
      type: "string",
    },
  },
};

const updateSchemaResponse = {
  type: "object",
  properties: {
    bezeichnung: {
      type: "string",
    },
  },
};

const formStub = {
  name: "JsonSchemaForm",
  props: ["modelValue", "schema", "uiSchema", "readonly", "loading"],
  emits: ["update:modelValue", "validity-change"],
  template: '<div data-testid="json-schema-form" />',
};

function createFetchResponse(body: unknown, ok = true, status = 200): Response {
  return {
    ok,
    status,
    type: "basic",
    json: async () => body,
  } as Response;
}

function createWrapper(path: string) {
  const router = createRouter({
    history: createMemoryHistory(),
    routes: [
      {
        path: "/krankenhaeuser",
        component: { template: "<div>index</div>" },
      },
      {
        path: "/krankenhaeuser/create",
        component: JsonSchemaCrudFormPage,
        props: {
          resource: krankenhausResource,
          mode: "create",
        },
      },
      {
        path: "/krankenhaeuser/:entityId/edit",
        component: JsonSchemaCrudFormPage,
        props: {
          resource: krankenhausResource,
          mode: "edit",
        },
      },
    ],
  });

  return router.push(path).then(async () => {
    await router.isReady();

    const wrapper = mount(JsonSchemaCrudFormPage, {
      props: {
        resource: krankenhausResource,
        mode: path.endsWith("/edit") ? "edit" : "create",
      },
      global: {
        plugins: [
          router,
          i18n,
          vuetify,
          createTestingPinia({
            createSpy: vi.fn,
            stubActions: false,
          }),
        ],
        stubs: {
          JsonSchemaForm: formStub,
          YesNoDialog: true,
        },
      },
    });

    await flushPromises();
    return { wrapper, router };
  });
}

describe("JsonSchemaCrudFormPage.vue", () => {
  beforeEach(() => {
    vi.restoreAllMocks();
    vi.stubGlobal("fetch", vi.fn());
  });

  test("loads create schema and submits create payload", async () => {
    vi.mocked(fetch)
      .mockResolvedValueOnce(createFetchResponse(createSchemaResponse))
      .mockResolvedValueOnce(createFetchResponse(undefined, true, 204));

    const { wrapper, router } = await createWrapper("/krankenhaeuser/create");

    expect(fetch).toHaveBeenNthCalledWith(
      1,
      "/krankenhaeuser/schema/create",
      expect.any(Object)
    );

    const form = wrapper.getComponent(formStub);
    form.vm.$emit("update:modelValue", {
      krhname: "B",
      bezeichnung: "Krankenhaus B",
    });
    form.vm.$emit("validity-change", []);
    await flushPromises();

    await wrapper.get('[data-testid="save-button"]').trigger("click");
    await flushPromises();

    expect(fetch).toHaveBeenNthCalledWith(
      2,
      "/krankenhaeuser/",
      expect.objectContaining({
        method: "POST",
      })
    );
    expect(router.currentRoute.value.path).toBe("/krankenhaeuser");
  });

  test("loads update schema and entity data before submit", async () => {
    vi.mocked(fetch)
      .mockResolvedValueOnce(createFetchResponse(updateSchemaResponse))
      .mockResolvedValueOnce(
        createFetchResponse({
          bezeichnung: "Krankenhaus B",
        })
      )
      .mockResolvedValueOnce(createFetchResponse(undefined, true, 204));

    const { wrapper } = await createWrapper("/krankenhaeuser/B/edit");

    expect(fetch).toHaveBeenNthCalledWith(
      1,
      "/krankenhaeuser/schema/update",
      expect.any(Object)
    );
    expect(fetch).toHaveBeenNthCalledWith(
      2,
      "/krankenhaeuser/B",
      expect.any(Object)
    );

    const form = wrapper.getComponent(formStub);
    form.vm.$emit("update:modelValue", {
      bezeichnung: "Neues Krankenhaus",
    });
    form.vm.$emit("validity-change", []);
    await flushPromises();

    await wrapper.get('[data-testid="save-button"]').trigger("click");
    await flushPromises();

    expect(fetch).toHaveBeenNthCalledWith(
      3,
      "/krankenhaeuser/B",
      expect.objectContaining({
        method: "PUT",
      })
    );
  });

  test("shows snackbar error and redirects when edit entity cannot be loaded", async () => {
    vi.mocked(fetch)
      .mockResolvedValueOnce(createFetchResponse(updateSchemaResponse))
      .mockResolvedValueOnce(createFetchResponse({}, false, 404));

    const { router } = await createWrapper("/krankenhaeuser/UNKNOWN/edit");
    const snackbarStore = useSnackbarStore();

    expect(snackbarStore.queue).toHaveLength(1);
    expect(router.currentRoute.value.path).toBe("/krankenhaeuser");
  });

  test("disables save while validation errors are present", async () => {
    vi.mocked(fetch).mockResolvedValueOnce(
      createFetchResponse(createSchemaResponse)
    );

    const { wrapper } = await createWrapper("/krankenhaeuser/create");
    const form = wrapper.getComponent(formStub);

    form.vm.$emit("validity-change", [
      {
        instancePath: "/bezeichnung",
        schemaPath: "#/required",
        keyword: "required",
        params: {},
        message: "required",
      },
    ]);
    await flushPromises();

    const saveButton = wrapper.get('[data-testid="save-button"]');
    expect(saveButton.attributes("disabled")).toBeDefined();
  });
});
