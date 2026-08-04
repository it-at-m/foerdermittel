<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.projnr"
          :display-mode="displayMode"
          required
          :counter="50"
          :rules="[rules.required(), rules.maxLength(50)]"
          :label="t('model.archiv.projnr')"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <v-date-input
          v-model="speicherDatum"
          required
          :label="t('model.archiv.speicherDatum')"
          :rules="[rules.required()]"
          prepend-icon=""
          locale="de-DE"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <v-date-input
          v-model="mikroDatPlan"
          required
          :label="t('model.archiv.mikroDatPlan')"
          :rules="[rules.required()]"
          prepend-icon=""
          locale="de-DE"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <v-date-input
          v-model="mikroDat"
          required
          :label="t('model.archiv.mikroDat')"
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
import type {
  ArchivFormContext,
  ArchivResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<ArchivResponseDTO>>({
  required: true,
});

const { displayMode = InputDisplayMode.CREATE } = defineProps<{
  archivFormContext: DeepReadonly<ArchivFormContext>;
  displayMode?: InputDisplayMode;
}>();

function toDate(value?: string | Date | null): Date | undefined {
  if (!value) {
    return undefined;
  }

  if (value instanceof Date) {
    return value;
  }

  const datePart = value.substring(0, 10);

  const [year, month, day] = datePart.split("-");

  if (!year || !month || !day) {
    return undefined;
  }

  return new Date(Number(year), Number(month) - 1, Number(day));
}

const speicherDatum = computed({
  get() {
    return toDate(modelValue.value.speicherDatum);
  },

  set(value: Date | undefined) {
    modelValue.value.speicherDatum = value;
  },
});

const mikroDatPlan = computed({
  get() {
    return toDate(modelValue.value.mikroDatPlan);
  },

  set(value: Date | undefined) {
    modelValue.value.mikroDatPlan = value;
  },
});

const mikroDat = computed({
  get() {
    return toDate(modelValue.value.mikroDat);
  },

  set(value: Date | undefined) {
    modelValue.value.mikroDat = value;
  },
});

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(value: boolean | null) {
  emit("isValid", value);
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
