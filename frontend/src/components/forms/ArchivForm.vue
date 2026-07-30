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
        <fm-text-field
          v-model="modelValue.speicherDatum"
          :display-mode="displayMode"
          required
          :label="t('model.archiv.speicherDatum')"
          :rules="[rules.required()]"
        />
      </v-col>

      <v-col cols="4">
        <fm-text-field
          v-model="modelValue.mikroDatPlan"
          :display-mode="displayMode"
          required
          :label="t('model.archiv.mikroDatPlan')"
          :rules="[rules.required()]"
        />
      </v-col>

      <v-col cols="4">
        <fm-text-field
          v-model="modelValue.mikroDat"
          :display-mode="displayMode"
          required
          :label="t('model.archiv.mikroDat')"
          :rules="[rules.required()]"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="6">
        <v-checkbox
          v-model="modelValue.speicherAkt"
          :label="t('model.archiv.speicherAkt')"
          :readonly="displayMode === InputDisplayMode.READ"
        />
      </v-col>

      <v-col cols="6">
        <v-checkbox
          v-model="modelValue.speicherRechnungen"
          :label="t('model.archiv.speicherRechnungen')"
          :readonly="displayMode === InputDisplayMode.READ"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.notizen"
          :display-mode="displayMode"
          required
          :counter="1000"
          :rules="[rules.required(), rules.maxLength(1000)]"
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

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<ArchivResponseDTO>>({
  required: true,
});

const { archivFormContext, displayMode = InputDisplayMode.CREATE } =
  defineProps<{
    archivFormContext: DeepReadonly<ArchivFormContext>;
    displayMode?: InputDisplayMode;
  }>();

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
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