<template>
  <v-form
    ref="form"
    :disabled="!reportProjektuebersichtFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <fm-autocomplete
          v-model="modelValue.parameters!.projnr"
          :items="reportProjektuebersichtFormContext?.projekte"
          :item-title="getProjektTitle"
          item-value="projnr"
          :validation-attribute-map="
            ReportProjektuebersichtDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="projnr"
          :label="t('model.projekt.projnr')"
        />
      </v-col>
      <v-col cols="12">
        <fm-text-field
          :model-value="selectedProjekt?.pname ?? ''"
          :display-mode="InputDisplayMode.EDIT"
          :disable-edit="true"
          :label="t('model.projekt.name')"
        />
      </v-col>
      <v-col cols="12">
        <fm-text-field
          :model-value="selectedProjekt?.pstrasse ?? ''"
          :display-mode="InputDisplayMode.EDIT"
          :disable-edit="true"
          :label="t('model.projekt.strasse')"
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
import type {
  GetReportProjektuebersichtRequest,
  ReportProjektuebersichtFormContext,
  ReportProjektuebersichtFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportProjektuebersichtDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportProjektuebersichtRequest>>({
  required: true,
});

const { reportProjektuebersichtFormContext } = defineProps<{
  reportProjektuebersichtFormContext?: DeepReadonly<ReportProjektuebersichtFormContext>;
}>();

const selectedProjekt = computed(() =>
  reportProjektuebersichtFormContext?.projekte.find(
    (projekt) => projekt.projnr === modelValue.value.parameters?.projnr
  )
);

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

function getProjektTitle(item: ReportProjektuebersichtFormContextDTO) {
  return item ? `${item.projnr} (${item.pname})` : "";
}
</script>
