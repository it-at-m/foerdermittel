import type { Ref } from "vue";

import { computed, onMounted, onUnmounted, ref, toRaw } from "vue";
import { onBeforeRouteLeave, onBeforeRouteUpdate } from "vue-router";
import { deepEqualTrimmed } from "@/util/validation";

function cloneValue<T>(value: T): T {
  return structuredClone(toRaw(value));
}

export function useDirtyFlag<T>(
  defaultValue: T,
  enabled: Ref<boolean> = ref(true)
) {
  const currentValue = ref(cloneValue(defaultValue));
  const initialValue = ref(null) as Ref<T | null>;
  const showUnsavedChangesDialog = ref(false);
  const pendingNavigationDecision = ref<
    ((allowNavigation: boolean) => void) | null
  >(null);

  const isDirty = computed<boolean>(
    () =>
      enabled.value &&
      initialValue.value != null &&
      !deepEqualTrimmed(initialValue.value, currentValue.value)
  );

  const clearDirtyState = () => {
    initialValue.value = null;
    showUnsavedChangesDialog.value = false;
  };

  const reset = (value: T = defaultValue) => {
    currentValue.value = cloneValue(value);
    clearDirtyState();
  };

  const track = (value: T) => {
    currentValue.value = cloneValue(value);
    initialValue.value = cloneValue(value);
    showUnsavedChangesDialog.value = false;
  };

  const requestClose = (onClose: () => void) => {
    if (isDirty.value) {
      showUnsavedChangesDialog.value = true;
      return;
    }
    onClose();
  };

  const continueEditing = () => {
    showUnsavedChangesDialog.value = false;
    pendingNavigationDecision.value?.(false);
    pendingNavigationDecision.value = null;
  };

  const continuePendingNavigation = () => {
    pendingNavigationDecision.value?.(true);
    pendingNavigationDecision.value = null;
  };

  const discardChanges = () => {
    reset();
    continuePendingNavigation();
  };

  function onBeforeRouteChange() {
    if (!isDirty.value) {
      return true;
    }

    showUnsavedChangesDialog.value = true;
    return new Promise<boolean>((resolve) => {
      pendingNavigationDecision.value?.(false);
      pendingNavigationDecision.value = resolve;
    });
  }

  onBeforeRouteLeave(onBeforeRouteChange);
  onBeforeRouteUpdate(onBeforeRouteChange);

  function onBeforeUnload(event: BeforeUnloadEvent) {
    if (!isDirty.value) {
      return;
    }

    event.preventDefault();
  }

  onMounted(() => {
    window.addEventListener("beforeunload", onBeforeUnload);
  });

  onUnmounted(() => {
    window.removeEventListener("beforeunload", onBeforeUnload);
  });

  return {
    currentValue,
    isDirty,
    showUnsavedChangesDialog,
    reset,
    track,
    requestClose,
    continueEditing,
    continuePendingNavigation,
    discardChanges,
  };
}
