<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <v-autocomplete
          v-model="modelValue.projnr"
          v-model:search="projektSuche"
          :rules="[rules.required()]"
          :items="gefilterteProjektnummern"
          :label="t('model.archiv.projnr')"
          :readonly="displayMode !== InputDisplayMode.CREATE"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col
        v-for="feld in datumsfelder"
        :key="feld.key"
        cols="4"
      >
        <v-date-input
          v-model="feld.value.value"
          :label="t(feld.label)"
          :rules="[rules.required()]"
          prepend-icon=""
          locale="de-DE"
          clearable
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="6">
        <fm-checkbox
          v-model="modelValue.speicherAkt"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherAkt')"
        />
      </v-col>

      <v-col cols="6">
        <fm-checkbox
          v-model="modelValue.speicherRechnungen"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherRechnungen')"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.notizen"
          :display-mode="displayMode"
          :counter="1000"
          :rules="[rules.maxLength(1000)]"
          :label="t('model.archiv.notizen')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type { ArchivResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, ref, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();
const rules = useRules();

const { projektnummern, displayMode = InputDisplayMode.CREATE } = defineProps<{
  projektnummern: string[];
  displayMode?: InputDisplayMode;
}>();

const modelValue = defineModel<Partial<ArchivResponseDTO>>({
  required: true,
});

const projektSuche = ref("");

const gefilterteProjektnummern = computed(() =>
  projektSuche.value
    ? projektnummern.filter((p) =>
        p.toLowerCase().includes(projektSuche.value.toLowerCase())
      )
    : projektnummern
);

function toDate(value?: string | Date | null) {
  if (!value) return undefined;
  if (value instanceof Date) return value;

  const [year, month, day] = value.substring(0, 10).split("-");

  return year && month && day
    ? new Date(Number(year), Number(month) - 1, Number(day), 12)
    : undefined;
}

function dateModel(key: keyof ArchivResponseDTO) {
  return computed({
    get: () => toDate(modelValue.value[key] as string),
    set: (value) => {
      modelValue.value[key] = value as never;
    },
  });
}

const datumsfelder = [
  {
    key: "speicherDatum",
    label: "model.archiv.speicherDatum",
    value: dateModel("speicherDatum"),
  },
  {
    key: "mikroDatPlan",
    label: "model.archiv.mikroDatPlan",
    value: dateModel("mikroDatPlan"),
  },
  {
    key: "mikroDat",
    label: "model.archiv.mikroDat",
    value: dateModel("mikroDat"),
  },
];

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(value: boolean | null) {
  emit("isValid", value);
}

const formRef = useTemplateRef<VForm>("form");

async function validate() {
  await formRef.value?.validate();
}

defineExpose({ validate });
</script>
