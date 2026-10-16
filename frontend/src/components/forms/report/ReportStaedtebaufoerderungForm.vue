<template>
  <v-form
    ref="form"
    :disabled="!reportStaedtebaufoerderungFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.jahrVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrVon"
          :label="t('domain.report.staedtebaufoerderung.jahrVon')"
          :error-messages="jahrVonError"
        />
      </v-col>
      <v-col md="6">
        <fm-text-field
          v-model="modelValue.parameters!.jahrBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrBis"
          :label="t('domain.report.staedtebaufoerderung.jahrBis')"
          :error-messages="jahrBisError"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-autocomplete
          v-model="modelValue.parameters!.traeger"
          :items="reportStaedtebaufoerderungFormContext?.traeger"
          :item-title="getTraegerTitle"
          item-value="kurzform"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="traeger"
          :label="t('domain.report.staedtebaufoerderung.traeger')"
        />
      </v-col>
      <v-col cols="12">
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('model.projekt.pname')"
          density="comfortable"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.notiz"
          :label="t('model.report.staedtebaufoerderung.notizen')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportStaedtebaufoerderungRequest,
  ReportStaedtebaufoerderungFormContext,
  TraegerFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { computed, useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

import { ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportStaedtebaufoerderungRequest>>({
  required: true,
});

const { reportStaedtebaufoerderungFormContext } = defineProps<{
  reportStaedtebaufoerderungFormContext?: DeepReadonly<ReportStaedtebaufoerderungFormContext>;
}>();

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

function getTraegerTitle(item: TraegerFormContextDTO) {
  return item ? `${item.kurzform} (${item.bezeichnung})` : "";
}
const jahrVonError = computed(() => {
  const von = modelValue.value.parameters?.jahrVon;
  const bis = modelValue.value.parameters?.jahrBis;

  if (von == null || von === "" || bis == null || bis === "") {
    return [];
  }

  return Number(von) > Number(bis)
    ? ["Jahr von darf nicht größer als Jahr bis sein."]
    : [];
});

const jahrBisError = computed(() => {
  const von = modelValue.value.parameters?.jahrVon;
  const bis = modelValue.value.parameters?.jahrBis;

  if (von == null || von === "" || bis == null || bis === "") {
    return [];
  }

  return Number(bis) < Number(von)
    ? ["Jahr bis darf nicht kleiner als Jahr von sein."]
    : [];
});
</script>
