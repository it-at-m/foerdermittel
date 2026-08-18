<template>
  <v-form
    ref="form"
    :readonly="displayMode === InputDisplayMode.READ"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="3">
        <fm-autocomplete
          v-model="modelValue.projnr"
          :items="projektItems"
          item-title="anzeige"
          item-value="projnr"
          :display-mode="displayMode"
          :label="t('model.archiv.projnr')"
          :rules="[rules.required()]"
          :validation-attribute-map="validationAttributeMap"
          validation-attribute-key="projnr"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4">
        <fm-date-field
          v-model="modelValue.speicherDatum"
          :display-mode="displayMode"
          :label="t('model.archiv.speicherDatum')"
          :validation-attribute-map="validationAttributeMap"
          validation-attribute-key="speicherDatum"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <fm-date-field
          v-model="modelValue.mikroDatPlan"
          :display-mode="displayMode"
          :label="t('model.archiv.mikroDatPlan')"
          :validation-attribute-map="validationAttributeMap"
          validation-attribute-key="mikroDatPlan"
          clearable
        />
      </v-col>

      <v-col cols="4">
        <fm-date-field
          v-model="modelValue.mikroDat"
          :display-mode="displayMode"
          :label="t('model.archiv.mikroDat')"
          :validation-attribute-map="validationAttributeMap"
          validation-attribute-key="mikroDat"
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
          :validation-attribute-map="validationAttributeMap"
          validation-attribute-key="notizen"
          :label="t('model.archiv.notizen')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  ArchivResponseDTO,
  ProjektResponseDTO,
} from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmDateField from "@/components/common/FmDateField.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();
const rules = useRules();

const {
  projekte,
  displayMode = InputDisplayMode.CREATE,
  validationAttributeMap,
} = defineProps<{
  projekte: ProjektResponseDTO[];
  displayMode?: InputDisplayMode;
  validationAttributeMap: Record<string, string>;
}>();

const modelValue = defineModel<Partial<ArchivResponseDTO>>({
  required: true,
});

const projektItems = computed(() =>
  projekte.map((projekt) => ({
    ...projekt,
    anzeige: `${projekt.projnr} (${projekt.pname})`,
  })),
);

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(value: boolean | null) {
  emit("isValid", value);
}

const formRef = useTemplateRef<VForm>("form");

async function validate(): Promise<boolean> {
  if (!formRef.value) {
    return false;
  }

  const result = await formRef.value.validate();

  emit("isValid", result.valid);

  return result.valid;
}

defineExpose({
  validate,
});
</script>