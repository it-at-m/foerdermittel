import { readonly, ref } from "vue";

/**
 * A composable utility for managing API calls with loading and error states.
 * @template TRequest - The type of the request parameters.
 * @template TResponse - The type of the API response.
 * @param apiMethod - The API method to be called.
 * @returns An object containing the state of the API call and a method to execute the call.
 */
export default function useAPI<TRequest, TResponse = void>(
  apiMethod: (params: TRequest) => Promise<TResponse>
) {
  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<TResponse | undefined>(undefined);

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  /**
   * Executes the API method and updates the state accordingly.
   * @param params - The parameters for the API call as an object.
   * @returns A promise that resolves when the call completes (check `data` state for retrieved data and `error` state for failures).
   */
  const call = async (params: TRequest) => {
    loadingInternal.value = true;
    errorInternal.value = false;

    try {
      dataInternal.value = await apiMethod(params);
    } catch (error) {
      console.debug(error);
      errorInternal.value = true;
    } finally {
      loadingInternal.value = false;
    }
  };

  return {
    loading,
    error,
    data,
    call,
  };
}
