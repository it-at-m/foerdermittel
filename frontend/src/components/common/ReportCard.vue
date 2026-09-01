<template>
  <v-card class="d-flex flex-column fill-height w-100">
    <v-card-title class="pa-0">
      <v-row
        align-content="center"
        justify="center"
        class="mb-4"
      >
        <v-col class="d-flex align-center justify-end">
          <v-btn
            variant="flat"
            color="accent"
            class="text-body-large"
            :append-icon="mdiDownload"
            :text="t('common.action.download')"
            :disabled="loading || !isFormSlotValid"
            @click="download"
          />
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text class="pa-0">
      <div class="d-flex flex-column h-100 pb-5">
        <slot
          name="form"
          :item="currentValue"
          :update-validity="updateFormValidity"
        />
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts" generic="T">
import { mdiDownload } from "@mdi/js";
import { ref, toRaw } from "vue";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const { emptyItemTemplate, loading = false } = defineProps<{
  emptyItemTemplate: T;
  loading?: boolean;
}>();

const currentValue = ref(structuredClone(toRaw(emptyItemTemplate)));

const isFormSlotValid = ref(false);
const updateFormValidity = (valid: boolean | null) => {
  isFormSlotValid.value = !!valid;
};

const emit = defineEmits<{
  download: [item: T];
}>();
const download = () => {
  emit("download", currentValue.value);
};
</script>
