<template>
  <v-table density="compact">
    <thead>
      <tr>
        <th>{{ t("model.stadtbezirk.stadtbezirk") }}</th>
        <th>{{ t("model.stadtbezirk.bezeichnung") }}</th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="stadtbezirk in assignedStadtbezirke"
        :key="stadtbezirk.stadtbezirkId"
      >
        <td>{{ stadtbezirk.stadtbezirkId }}</td>
        <td>{{ stadtbezirk.bezeichnung }}</td>
      </tr>

      <tr v-if="assignedStadtbezirke.length === 0">
        <td
          colspan="2"
          class="text-center"
        >
          {{ t("common.noEntries") }}
        </td>
      </tr>
    </tbody>
  </v-table>
</template>

<script setup lang="ts">
import type { StadtbezirkslisteAssignmentResponseDTO } from "@/api/generated/foerdermittel-backend";
import type { DeepReadonly } from "vue";

import { useI18n } from "vue-i18n";

const { t } = useI18n();

defineProps<{
  assignedStadtbezirke: DeepReadonly<StadtbezirkslisteAssignmentResponseDTO[]>;
}>();
</script>
