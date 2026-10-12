<template>
  <v-form
    ref="form"
    :disabled="!reportEuinformationenFormContext"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <fm-autocomplete
          v-model="modelValue.parameters!.publikation"
          :items="reportEuinformationenFormContext?.publikationen"
          :item-title="getBereichTitle"
          item-value="bereich"
          :validation-attribute-map="
            ReportEuinformationenDTOPropertyValidationAttributesMap
          "
          validation-attribute-key="bereich"
          :label="t('domain.report.euinformation.publikation')"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-select
          v-model="modelValue.parameters!.sort"
          :items="sortOptions"
          item-value="value"
          label="Sortierung"
          variant="outlined"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportEuinformationenRequest,
  ReportEuinformationFormContext,
  ReportEuinformationFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";

//import { ReportEuinformationenDTOPropertyValidationAttributesMap } from "@/api/generated/foerdermittel-backend";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportEuinformationenRequest>>({
  required: true,
});

const { reportEuinformationenFormContext } = defineProps<{
  reportEuinformationenFormContext?: DeepReadonly<ReportEuinformationFormContext>;
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

function getBereichTitle(item: ReportEuinformationFormContextDTO) {
  return item ? `${item.kurzform} (${item.bezeichnung})` : "";
}

const sortOptions = [
  {
    title: t("domain.report.euinformation.sortEnum.publikation"),
    value: "PUBLIKATION",
  },
  {
    title: t("domain.report.euinformation.sortEnum.jahr"),
    value: "JAHR",
  },
  {
    title: t("domain.report.euinformation.sortEnum.referat"),
    value: "REFERAT",
  },
  {
    title: t("domain.report.euinformation.sortEnum.referatJahr"),
    value: "REFERAT_JAHR",
  },
];
</script>
