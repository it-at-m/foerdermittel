<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.kurzbez"
          :display-mode="displayMode"
          disable-edit
          required
          uppercase
          :validation-attribute-map="
            ListennameCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="kurzbez"
          :additional-rules="[
            rules['unique']!(
              listennameFormContext.stadtbezirkslistenIds,
              currentKurzbez
            ),
          ]"
          :label="t('model.stadtbezirksliste.lnaKurzbez')"
        />
      </v-col>

      <v-col cols="9">
        <fm-text-field
          v-model="modelValue.bezeichnung"
          :display-mode="displayMode"
          required
          :validation-attribute-map="
            ListennameCreateDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bezeichnung"
          :label="t('model.stadtbezirksliste.bezeichnung')"
        />
      </v-col>
    </v-row>
    <v-divider class="my-4" />

    <!-- Stadtbezirke -->
    <div class="d-flex align-center mb-4">
      <div>
        <div class="text-subtitle-1 font-weight-medium">
          {{ t("model.stadtbezirksliste.stadtBezirkeDerListe") }}
        </div>
        <div class="text-body-2 text-medium-emphasis">
          {{ assignedStadtbezirkeCount }}
          {{ assignedStadtbezirkeCount === 1 ? "Bezirk" : "Bezirke" }}
          hinzugefügt
        </div>
      </div>
      <v-spacer />
    </div>
    <!-- Bereits zugeordnete Stadtbezirke -->
    <v-sheet
      v-if="assignedStadtbezirkeCount"
      border
      rounded="lg"
      class="mb-4 overflow-hidden"
    >
      <div
        v-for="(stadtbezirk, index) in modelValue.assignedStadtbezirke"
        :key="stadtbezirk.stadtbezirkId"
      >
        <div class="d-flex align-center px-4 py-3">
          <v-avatar
            size="32"
            variant="tonal"
            class="mr-3"
          >
            <span class="text-caption font-weight-bold">
              {{ stadtbezirk.stadtbezirkId }}
            </span>
          </v-avatar>

          <div
            class="text-body-1 font-weight-medium mr-4"
            style="min-width: 180px"
          >
            {{ stadtbezirk.stadtbezirkBezeichnung }}
          </div>

          <div class="flex-grow-1">
            <fm-text-field
              v-model="stadtbezirk.bezeichnung"
              :display-mode="displayMode"
              :label="t('model.stadtbezirksliste.listeBezeichnung')"
              variant="outlined"
              density="compact"
              hide-details
              :validation-attribute-map="
                StadtbezirkslisteAssignmentResponseDTOPropertyValidationAttributesMap
              "
              validation-attribute-key="bezeichnung"
            />
          </div>

          <v-btn
            :icon="mdiDelete"
            variant="text"
            class="ml-2"
            :disabled="displayMode === InputDisplayMode.READ"
            @click="removeStadtbezirk(stadtbezirk.stadtbezirkId!)"
          />
        </div>

        <v-divider v-if="index < assignedStadtbezirkeCount - 1" />
      </div>
    </v-sheet>

    <!-- Empty State -->
    <v-sheet
      v-else
      border
      rounded="lg"
      class="d-flex flex-column align-center justify-center pa-6 mb-4"
    >
      <v-icon
        :icon="mdiMapMarkerRemoveOutline"
        size="36"
        class="mb-2 text-medium-emphasis"
      />
      <div class="text-body-1 font-weight-medium">
        {{ t("model.stadtbezirksliste.noDistricts") }}
      </div>
      <div class="text-body-2 text-medium-emphasis mt-1">
        {{ t("model.stadtbezirksliste.selectHint") }}
      </div>
    </v-sheet>

    <!-- Stadtbezirk hinzufügen -->
    <v-sheet
      border
      rounded="lg"
      class="pa-4"
    >
      <div class="text-subtitle-2 font-weight-medium mb-3">
        {{ t("model.stadtbezirksliste.addBezirke") }}
      </div>

      <v-row
        align="start"
        density="comfortable"
      >
        <v-col
          cols="12"
          md="3"
        >
          <fm-autocomplete
            v-model="selectedStadtbezirk"
            :loading="stadtbezirkLoading"
            :items="availableStadtbezirke"
            :item-title="formatStadtbezirk"
            :return-object="true"
            :label="t('model.stadtbezirksliste.bezStadtbezirk')"
            variant="outlined"
            density="comfortable"
            :menu-props="{
              location: 'bottom',
              contained: true,
            }"
          />
        </v-col>

        <v-col
          cols="12"
          md="6"
        >
          <fm-text-field
            v-model="newBezeichnung"
            :label="t('model.stadtbezirksliste.listeBezeichnung')"
            variant="outlined"
            density="comfortable"
            :validation-attribute-map="
              StadtbezirkslisteAssignmentResponseDTOPropertyValidationAttributesMap
            "
            validation-attribute-key="bezeichnung"
          />
        </v-col>

        <v-col
          cols="12"
          md="3"
          class="d-flex"
        >
          <v-btn
            color="primary"
            variant="tonal"
            block
            height="48"
            :disabled="!selectedStadtbezirk"
            @click="addStadtbezirk"
          >
            {{ t("common.action.add") }}
          </v-btn>
        </v-col>
      </v-row>
    </v-sheet>
  </v-form>
</template>

<script setup lang="ts">
import type {
  StadtbezirkResponseDTO,
  StadtbezirkslisteFormContext,
  StadtbezirkslisteResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { mdiDelete, mdiMapMarkerRemoveOutline } from "@mdi/js";
import { computed, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import {
  ListennameCreateDTOPropertyValidationAttributesMap,
  StadtbezirkslisteAssignmentResponseDTOPropertyValidationAttributesMap,
} from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { useGetStadtbezirke } from "@/composables/api/useStadtbezirkApi";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<StadtbezirkslisteResponseDTO>>({
  required: true,
});

const {
  data: stadtbezirke,
  call: getStadtbezirke,
  loading: stadtbezirkLoading,
} = useGetStadtbezirke();

getStadtbezirke({
  page: 0,
  size: 1000,
});

const availableStadtbezirke = computed(
  () =>
    stadtbezirke.value?.content?.filter(
      (sb) =>
        !modelValue.value.assignedStadtbezirke?.some(
          (assigned) => assigned.stadtbezirkId === Number(sb.id)
        )
    ) ?? []
);

const assignedStadtbezirkeCount = computed(
  () => modelValue.value.assignedStadtbezirke?.length ?? 0
);

function formatStadtbezirk(stadtbezirk: StadtbezirkResponseDTO) {
  return `${stadtbezirk.id} - ${stadtbezirk.bezeichnung}`;
}

const selectedStadtbezirk = ref<StadtbezirkResponseDTO | null>(null);
const newBezeichnung = ref("");

function addStadtbezirk() {
  if (!selectedStadtbezirk.value) {
    return;
  }

  modelValue.value.assignedStadtbezirke ??= [];

  modelValue.value.assignedStadtbezirke.push({
    stadtbezirkId: Number(selectedStadtbezirk.value.id),
    stadtbezirkBezeichnung: selectedStadtbezirk.value.bezeichnung,
    bezeichnung: newBezeichnung.value,
  });

  selectedStadtbezirk.value = null;
  newBezeichnung.value = "";
}

// Initialwert merken, damit die Unique-Regel beim Bearbeiten funktioniert
const currentKurzbez = ref(modelValue.value.kurzbez);

const { listennameFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    listennameFormContext: DeepReadonly<StadtbezirkslisteFormContext>;
    displayMode?: InputDisplayMode;
  }>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

function removeStadtbezirk(stadtbezirkId: number) {
  modelValue.value.assignedStadtbezirke =
    modelValue.value.assignedStadtbezirke?.filter(
      (s) => s.stadtbezirkId !== stadtbezirkId
    ) ?? [];
}

const rules = useRules();

const formRef = useTemplateRef<VForm>("form");

async function validate() {
  if (formRef.value) {
    await formRef.value.validate();
  }
}

defineExpose({
  validate,
});
</script>
