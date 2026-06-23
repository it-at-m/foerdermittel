<template>
  <v-container
    class="d-flex flex-column fill-height pt-10 px-8 overflow-hidden"
  >
    <v-row class="justify-space-between align-center flex-0-0">
      <v-col cols="auto">
        <h1
          v-if="title || domainKey"
          class="ma-0 text-headline-large"
        >
          {{ title ?? t("common.generics.manage", [t(domainKey!, 2)]) }}
        </h1>
      </v-col>
      <v-col cols="auto">
        <slot name="actions" />
      </v-col>
    </v-row>
    <v-divider class="my-4" />
    <v-row class="flex-grow-1 overflow-auto">
      <slot />
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { useI18n } from "vue-i18n";

const { domainKey } = defineProps<{
  title?: string;
  domainKey?: string;
}>();

const { t } = useI18n();
</script>
