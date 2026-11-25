<template>
  <v-form
    ref="form"
    :disabled="!reportStaedtebaufoerderungFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col
        cols="12"
        md="3"
      >
        <fm-number-input
          v-model="modelValue.parameters!.jahrVon"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrVon"
          :additional-rules="jahrVonAdditionalRules"
          :label="t('domain.report.staedtebaufoerderung.jahrVon')"
          density="comfortable"
          control-variant="hidden"
        />
      </v-col>

      <v-col
        cols="12"
        md="3"
      >
        <fm-number-input
          v-model="modelValue.parameters!.jahrBis"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="jahrBis"
          :additional-rules="jahrBisAdditionalRules"
          :label="t('domain.report.staedtebaufoerderung.jahrBis')"
          density="comfortable"
          control-variant="hidden"
        />
      </v-col>
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
          :error-messages="traegerError"
          density="comfortable"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        md="6"
      >
        <fm-text-field
          v-model="modelValue.parameters!.pname"
          :display-mode="InputDisplayMode.EDIT"
          :validation-attribute-map="
            ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="pname"
          :label="t('domain.report.staedtebaufoerderung.pname')"
          density="comfortable"
        />
      </v-col>

      <v-col
        cols="12"
        md="6"
      >
        <fm-checkbox
          v-model="modelValue.parameters!.notizen"
          :label="t('domain.report.staedtebaufoerderung.notizen')"
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
import { useRules } from "vuetify";

import { ReportStaedtebaufoerderungDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";
import FmAutocomplete from "@/components/common/FmAutocomplete.vue";
import FmCheckbox from "@/components/common/FmCheckbox.vue";
import FmNumberInput from "@/components/common/FmNumberInput.vue";
import FmTextField from "@/components/common/FmTextField.vue";
import { InputDisplayMode } from "@/types/InputDisplayMode";

const { t } = useI18n();

const rules = useRules();

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

const traegerError = computed(() => {
  const selectedTraeger = modelValue.value.parameters?.traeger;
  const traegerOptions = reportStaedtebaufoerderungFormContext?.traeger;

  if (!selectedTraeger || !traegerOptions) {
    return [];
  }

  return traegerOptions.some((item) => item.kurzform === selectedTraeger)
    ? []
    : ["Der ausgewählte Träger wurde nicht gefunden."];
});

const jahrVonAdditionalRules = computed(() => {
  const jahrBis = modelValue.value.parameters?.jahrBis;
  const rule = jahrBis != null && rules["max"]?.(jahrBis, false);
  return rule ? [rule] : [];
});

const jahrBisAdditionalRules = computed(() => {
  const jahrVon = modelValue.value.parameters?.jahrVon;
  const rule = jahrVon != null && rules["min"]?.(jahrVon, false);
  return rule ? [rule] : [];
});
</script>
