<template>
  <v-form
    ref="form"
    @update:model-value="onValidityChanged"
  >
    <v-row>
      <v-col cols="12">
        <!-- TODO ADD VALIDATION ATTRIBUTE MAP -->
        <fm-autocomplete
          v-model="modelValue.bereich"
          :additional-rules="[rules.required()]"
          :items="reportStichworteFormContext.bereiche"
          :item-title="getBereichTitle"
          item-value="bereich"
          :label="t('model.stichwortbereich.modelName')"
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type {
  GetReportStichworteRequest,
  ReportStichworteFormContext,
  StichwortbereichFormContextDTO,
} from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";
import type { VForm } from "vuetify/components";

import { useTemplateRef } from "vue";
import { useI18n } from "vue-i18n";
import { useRules } from "vuetify/labs/rules";

import FmAutocomplete from "@/components/common/FmAutocomplete.vue";

const { t } = useI18n();

const modelValue = defineModel<Partial<GetReportStichworteRequest>>({
  required: true,
});

const { reportStichworteFormContext } = defineProps<{
  reportStichworteFormContext: DeepReadonly<ReportStichworteFormContext>;
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

function getBereichTitle(item: StichwortbereichFormContextDTO) {
  return item ? `${item.bereich} (${item.bezeichnung})` : "";
}
</script>
