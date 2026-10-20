<template>
  <v-expansion-panels
    v-if="$slots['filter-form']"
    class="pb-5"
    elevation="0"
    static
  >
    <v-expansion-panel
      title="Suchen & Filtern"
      :expand-icon="mdiFilterCog"
      :collapse-icon="mdiFilterCog"
      class="border rounded-lg"
    >
      <v-expansion-panel-text>
        <slot name="filter-form" />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup lang="ts">
import { mdiFilterCog } from "@mdi/js";
</script>