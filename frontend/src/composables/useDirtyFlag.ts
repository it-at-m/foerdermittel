import type { Ref } from "vue";
import type { NavigationGuardNext } from "vue-router";

import equal from "fast-deep-equal";
import { computed, onMounted, onUnmounted, ref, toRaw } from "vue";
import { onBeforeRouteLeave } from "vue-router";

export function useDirtyFlag<T>(defaultValue: T, enabled?: Ref<boolean>) {
  const cloneValue = (value: T): T => structuredClone(toRaw(value));

  const currentValue = ref(cloneValue(defaultValue));
  const initialValue = ref(null) as Ref<T | null>;
  const showUnsavedChangesDialog = ref(false);
  const pendingRouteNext = ref<NavigationGuardNext | null>(null);

  const isDirty = computed(
    () =>
      enabled?.value &&
      initialValue.value != null &&
      !equal(initialValue.value, currentValue.value)
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
    initialValue.value = cloneValue(currentValue.value);
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
    if (pendingRouteNext.value != null) {
      pendingRouteNext.value(false);
      pendingRouteNext.value = null;
    }
  };

  const continuePendingNavigation = () => {
    const next = pendingRouteNext.value;
    pendingRouteNext.value = null;
    next?.();
  };

  const discardChanges = () => {
    reset();
    continuePendingNavigation();
  };

  const onBeforeUnload = (event: BeforeUnloadEvent) => {
    if (!isDirty.value) {
      return;
    }

    event.preventDefault();
    event.returnValue = "";
  };

  onBeforeRouteLeave((_to, _from, next) => {
    if (!isDirty.value) {
      next();
      return;
    }

    pendingRouteNext.value = next;
    showUnsavedChangesDialog.value = true;
  });

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
