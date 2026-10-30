<template>
  <v-expansion-panels
    v-if="$slots['form']"
    elevation="0"
    static
    @update:model-value="checkOpened"
  >
    <v-expansion-panel
      :expand-icon="mdiMagnify"
      :collapse-icon="mdiMagnify"
      class="border rounded-lg"
    >
      <template #title>
        <div class="d-flex align-center justify-start w-100 mr-2">
          <span class="pr-5">{{ t("component.filterPanel.title") }}</span>

          <v-btn
            v-if="isFilterActive"
            size="small"
            variant="tonal"
            :prepend-icon="mdiClose"
            :text="t('component.filterPanel.reset')"
            @click.stop="resetFilter"
          />
        </div>
      </template>
      <v-expansion-panel-text>
        <slot
          name="form"
          :filter="filterFormValue"
          :update-validity="updateFormValidity"
        />

        <div class="d-flex justify-end mt-4">
          <v-btn
            :text="t('component.filterPanel.reset')"
            class="mr-3"
            :disabled="loading || !isFilterActive"
            @click="resetFilter"
          />
          <v-btn
            :text="t('component.filterPanel.showResults')"
            variant="flat"
            color="accent"
            :loading="loading"
            :disabled="!formValid"
            @click="applyFilter"
          />
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup lang="ts">
import { mdiClose, mdiMagnify } from "@mdi/js";
import { nextTick } from "vue";
import { useI18n } from "vue-i18n";

import { useFilter } from "@/composables/useFilter";

const { filterDefault, loading = false } = defineProps<{
  filterDefault?: Record<string, unknown>;
  loading?: boolean;
}>();

const { t } = useI18n();

const {
  formValid,
  updateFormValidity,
  filterFormValue,
  appliedFilter,
  search,
  isFilterActive,
  applyFilter,
  resetFilter,
  syncForm,
} = useFilter(filterDefault);

async function checkOpened(panelValue: unknown) {
  if (panelValue === 0) {
    await nextTick(); // wait until expansion panel is opened before syncing the state
    syncForm();
  }
}

defineExpose({
  appliedFilter,
  search,
});
</script>
