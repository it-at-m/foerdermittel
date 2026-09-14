<template>
  <v-form
    ref="form"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.parameters!.projnr"
          :validation-attribute-map="
            ReportProjektuebersichtDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :label="t('model.projekt.projnr')"
        />
      </v-col>
      <v-col cols="12">
        <fm-checkbox
          v-model="modelValue.parameters!.notiz"
          :label="t('model.projekt.ausgabeNotiz')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type { GetReportProjektuebersichtRequest } from "@/api/generated/foerdermittel-backend";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportProjektuebersichtDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportProjektuebersichtRequest>>({
  required: true,
});

const emit = defineEmits<{
  isValid: [boolean | null];
}>();

function onValidityChanged(newIsValid: boolean | null) {
  emit("isValid", newIsValid);
}

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
