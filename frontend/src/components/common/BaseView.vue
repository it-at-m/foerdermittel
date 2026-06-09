<template>
  <v-container class="fill-height py-14 px-8">
    <v-row class="justify-space-between align-center">
      <v-col cols="auto">
        <h1
          v-if="title || domain"
          class="ma-0 text-headline-large"
        >
          {{ title ?? t("common.generics.manage", { domain }) }}
        </h1>
      </v-col>
      <v-col cols="auto">
        <slot name="actions" />
      </v-col>
    </v-row>
    <v-divider class="my-4" />
    <v-row>
      <slot />
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { useI18n } from "vue-i18n";

defineProps<{
  title?: string;
  domain?: string;
}>();

defineSlots<{
  default(): void;
  actions(): void;
}>();

const { t } = useI18n();
</script>
