<template>
  <v-row
    v-if="enableActions"
    justify="end"
    align="center"
    class="mb-2"
  >
    <v-col
      v-for="action in actions"
      :key="action.label"
      cols="auto"
    >
      <v-btn
        v-if="enableActions"
        variant="flat"
        color="accent"
        class="text-body-large"
        :append-icon="action.icon"
        :text="action.label"
        :disabled="action.disabled || loading"
        :loading="action.loading"
        @click="action.onClick"
      >
        {{ action.label }}
      </v-btn>
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
export interface ActionButtonItem {
  label: string;
  onClick: () => void;
  icon?: string;
  disabled?: boolean;
  loading?: boolean;
}

withDefaults(
  defineProps<{
    actions: ActionButtonItem[];
    loading?: boolean;
    enableActions?: boolean;
  }>(),
  {
    loading: false,
    enableActions: true,
  }
);
</script>
