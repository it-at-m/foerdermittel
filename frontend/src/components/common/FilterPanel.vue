<template>
  <v-expansion-panels
    v-if="$slots['form']"
    elevation="0"
    static
  >
    <v-expansion-panel
      :expand-icon="mdiMagnify"
      :collapse-icon="mdiMagnify"
      class="border rounded-lg"
    >
      <template #title>
        <div class="d-flex align-center justify-start w-100 mr-2">
          <span class="pr-3">{{ t("component.filterPanel.title") }}</span>

          <v-btn
            v-if="filterActive"
            size="small"
            variant="outlined"
            :prepend-icon="mdiClose"
            :text="t('component.filterPanel.reset')"
            @click.stop="emit('reset')"
          />
        </div>
      </template>
      <v-expansion-panel-text>
        <slot name="form" />

        <div class="d-flex justify-end mt-4">
          <v-btn
            :text="t('component.filterPanel.reset')"
            class="mr-3"
            :disabled="loading || !filterActive"
            @click="emit('reset')"
          />
          <v-btn
            :text="t('component.filterPanel.showResults')"
            variant="flat"
            color="accent"
            :loading="loading"
            @click="emit('filter')"
          />
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup lang="ts">
import { mdiClose, mdiMagnify } from "@mdi/js";
import { useI18n } from "vue-i18n";

const { loading = false, filterActive = false } = defineProps<{
  loading?: boolean;
  filterActive?: boolean;
}>();

const emit = defineEmits<{
  reset: [];
  filter: [];
}>();

const { t } = useI18n();
</script>
