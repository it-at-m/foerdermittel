<template>
  <v-container
    fluid
    class="d-flex flex-column fill-height pt-10 px-8 overflow-hidden"
  >
    <benutzerhinweis-dialog
      :loading="loading"
      :benutzerhinweis="benutzerhinweis ?? EMPTY_ITEM_TEMPLATE"
      :display-mode="displayMode"
      @close="showBenutzerhinweisDialog = false"
      @save="handleSave"
    />
    <v-row class="justify-space-between align-center flex-0-0">
      <v-col cols="auto">
        <h1
          v-if="title || domainKey"
          class="ma-0 text-headline-large"
        >
          {{ title ?? t("common.generics.manage", [t(domainKey!, 2)]) }}
        </h1>
      </v-col>
      <v-col cols="auto">
        <slot name="actions" />
        <v-tooltip
          :text="t('common.generics.show', [benutzerhinweiseDomain])"
          location="left"
        >
          <template #activator="{ props }">
            <v-btn
              :icon="mdiHelpCircle"
              v-bind="props"
              :aria-label="t('common.generics.show', [benutzerhinweiseDomain])"
              @click="showBenutzerhinweisDialog = true"
            />
          </template>
        </v-tooltip>
      </v-col>
    </v-row>
    <v-divider class="my-4" />
    <v-row class="flex-grow-1 overflow-auto">
      <slot :base-view-loading="loading" />
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import type {
  BenutzerhinweisCreateDTO,
  BenutzerhinweisResponseDTO,
  BenutzerhinweisUpdateDTO,
} from "@/api/generated/foerdermittel-backend";

import { mdiHelpCircle } from "@mdi/js";
import { computed, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import { useRoute } from "vue-router";

import BenutzerhinweisDialog from "@/components/BenutzerhinweisDialog.vue";
import {
  useCreateBenutzerhinweis,
  useGetBenutzerhinweis,
  useUpdateBenutzerhinweis,
} from "@/composables/api/useBenutzerhinweisApi";
import useHasAnyRole from "@/composables/useHasAnyRole";
import { STATUS_INDICATORS } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import { InputDisplayMode } from "@/types/InputDisplayMode";
import { Role } from "@/types/Role";

const { domainKey } = defineProps<{
  title?: string;
  domainKey?: string;
}>();

const { t } = useI18n();

const route = useRoute();
// always contains a valid route name, implemented fallback only to make vue-tsc happy without building to generate route-map.d.ts
const routeName = computed(() => (route.name as string).replace("/", ""));

const {
  data: benutzerhinweis,
  call: getBenutzerhinweis,
  loading: getBenutzerhinweisLoading,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useGetBenutzerhinweis!();

const {
  call: createBenutzerhinweis,
  loading: createBenutzerhinweisLoading,
  error: createBenutzerhinweisError,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useCreateBenutzerhinweis!();

const {
  call: updateBenutzerhinweis,
  loading: updateBenutzerhinweisLoading,
  error: updateBenutzerhinweisError,
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
} = useUpdateBenutzerhinweis!();

watch(
  routeName,
  async (newRouteName) => {
    if (newRouteName) {
      await getBenutzerhinweis({
        id: newRouteName,
      });
    }
  },
  { immediate: true }
);

const loading = computed(
  () =>
    getBenutzerhinweisLoading.value ||
    createBenutzerhinweisLoading.value ||
    updateBenutzerhinweisLoading.value
);

const isAdmin = useHasAnyRole(Role.ADMIN);
const showBenutzerhinweisDialog = ref(false);
const displayMode = computed(() => {
  if (!showBenutzerhinweisDialog.value) {
    return undefined;
  }
  if (!isAdmin.value) {
    return InputDisplayMode.READ;
  }
  return benutzerhinweis.value
    ? InputDisplayMode.EDIT
    : InputDisplayMode.CREATE;
});

const EMPTY_ITEM_TEMPLATE: Partial<BenutzerhinweisResponseDTO> = {
  viewId: routeName.value,
  funktionsbeschreibung: "",
  bedienung: "",
  pruefungVorgaben: "",
};

const benutzerhinweiseDomain = t("model.benutzerhinweis.modelName");
async function handleSave(
  benutzerhinweis: Partial<BenutzerhinweisResponseDTO>
) {
  if (displayMode.value === InputDisplayMode.CREATE) {
    await createBenutzerhinweis({
      benutzerhinweisCreateDTO: benutzerhinweis as BenutzerhinweisCreateDTO,
    });
    if (!createBenutzerhinweisError.value) {
      await onSuccess(t("common.message.updated", [benutzerhinweiseDomain]));
    } else {
      onFailure(t("common.message.updatedError", [benutzerhinweiseDomain]));
    }
  } else if (displayMode.value === InputDisplayMode.EDIT) {
    await updateBenutzerhinweis({
      benutzerhinweisUpdateDTO: benutzerhinweis as BenutzerhinweisUpdateDTO,
      id: routeName.value,
    });
    if (!updateBenutzerhinweisError.value) {
      await onSuccess(t("common.message.updated", [benutzerhinweiseDomain]));
    } else {
      onFailure(t("common.message.updatedError", [benutzerhinweiseDomain]));
    }
  }
}

const snackbarStore = useSnackbarStore();
const onSuccess = async (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.SUCCESS });
  await getBenutzerhinweis({
    id: routeName.value,
  });
  showBenutzerhinweisDialog.value = false;
};
const onFailure = (msg: string) => {
  snackbarStore.push({ text: msg, color: STATUS_INDICATORS.ERROR });
};
</script>
