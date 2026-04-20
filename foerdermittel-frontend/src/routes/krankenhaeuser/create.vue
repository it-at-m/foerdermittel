<script setup lang="ts">
import JsonSchemaCrudFormPage from "@/components/json-schema-form/JsonSchemaCrudFormPage.vue";
import { krankenhausResource } from "@/resources/krankenhausResource";
</script>

<template>
  <json-schema-crud-form-page
    :resource="krankenhausResource"
    mode="create"
  />
</template>
