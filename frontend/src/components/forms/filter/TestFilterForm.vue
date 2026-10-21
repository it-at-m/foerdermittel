<template>
  <v-form>
    <v-row>
      <v-col cols="3">
        <fm-text-field
          v-model="modelValue.test"
          label="Testform"
          clearable
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script setup lang="ts">
import type { DummyFilterType } from "@/types/DummyFilterType";

import FmTextField from "@/components/common/FmTextField.vue";

const modelValue = defineModel<Partial<DummyFilterType>>({
  required: true,
});
</script>
