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
          :counter="3"
          :rules="[
            rules.required(),
            rules.minLength(1),
            rules.maxLength(3),
            rules.pattern(/^[A-Z0-9]{1,3}$/),
            rules['unique']!(
              listennameFormContext.stadtbezirksliste,
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
          :counter="200"
          :rules="[rules.required(), rules.maxLength(200)]"
          :label="t('model.stadtbezirksliste.bezeichnung')"
        />
      </v-col>
    </v-row>
    <v-divider class="my-4" />

    <v-table density="compact">
      <thead>
        <tr>
          <th width="100">ID</th>
          <th>Stadtbezirk</th>
          <th>Bezeichnung</th>
          <th width="100"></th>
        </tr>
      </thead>

      <tbody>
        <tr
          v-for="stadtbezirk in modelValue.assignedStadtbezirke"
          :key="stadtbezirk.stadtbezirkId"
        >
          <td>{{ stadtbezirk.stadtbezirkId }}</td>
          <td>{{ stadtbezirk.stadtbezirkBezeichnung }}</td>
          <td>
            <v-text-field
              v-model="stadtbezirk.bezeichnung"
              variant="outlined"
              density="compact"
              hide-details
              :rules="[rules.maxLength(200)]"
            />
          </td>
          <td class="text-end">
            <v-btn
              :icon="mdiDelete"
              variant="text"
              @click="removeStadtbezirk(stadtbezirk.stadtbezirkId)"
            />
          </td>
        </tr>
        <tr height="100">
          <td style="width: 100px"></td>

          <td style="width: 250px">
            <v-autocomplete
              v-model="selectedStadtbezirk"
              :items="availableStadtbezirke"
              item-title="bezeichnung"
              :return-object="true"
              label="Stadtbezirk"
              variant="outlined"
              density="compact"
              :menu-props="{
                location: 'bottom',
                contained: true,
              }"
            />
          </td>

          <td>
            <fm-text-field
              v-model="newBezeichnung"
              label="Bezeichnung"
              variant="outlined"
              density="compact"
              :counter="200"
              :rules="[rules.maxLength(200)]"
            />
          </td>

          <td
            style="width: 100px"
            class="text-end"
          >
            <v-btn
              :icon="mdiPlus"
              variant="text"
              @click="addStadtbezirk"
            >
            </v-btn>
          </td>
        </tr>
      </tbody>
    </v-table>
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

import { mdiDelete, mdiPlus } from "@mdi/js";
import { computed, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmTextField from "@/components/common/FmTextField.vue";
import { useGetStadtbezirke } from "@/composables/api/useStadtbezirkApi";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<StadtbezirkslisteResponseDTO>>({
  required: true,
});

const { data: stadtbezirke, call: getStadtbezirke } = useGetStadtbezirke();

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
