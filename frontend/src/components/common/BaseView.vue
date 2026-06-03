<template>
  <v-container class="fill-height py-14 px-8">
    <v-row class="justify-space-between align-center">
      <v-col
        cols="auto"
      >
        <h1 v-if="title" class="ma-0 text-headline-large">
          {{ title }}
        </h1>
      </v-col>
      <v-col
        cols="auto"
      >
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
defineProps<{
  title?: string;
}>();

defineSlots<{
  default(): void;
  actions(): void;
}>();
</script>
