<template>
  <v-container class="fill-height py-14 px-8">
    <v-row class="justify-space-between align-center">
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
    <v-row>
      <slot :domain-key="domainKey" />
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
