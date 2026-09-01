import type { Ref } from "vue";

import { readonly, ref } from "vue";

export default function useAPI<TResponse>(
  apiMethod: () => Promise<TResponse>
): {
  loading: Readonly<Ref<boolean>>;
  error: Readonly<Ref<boolean>>;
  data: Readonly<Ref<TResponse>>;
  call: () => Promise<void>;
};

export default function useAPI<TRequest, TResponse>(
  apiMethod: (params: TRequest) => Promise<TResponse>
): {
  loading: Readonly<Ref<boolean>>;
  error: Readonly<Ref<boolean>>;
  data: Readonly<Ref<TResponse>>;
  call: (params: TRequest) => Promise<void>;
};

/**
 * A composable utility for managing API calls with loading and error states.
 * @template TRequest - The type of the request parameters.
 * @template TResponse - The type of the API response.
 * @param apiMethod - The API method to be called.
 * @returns An object containing the state of the API call and a method to execute the call.
 */
export default function useAPI<TRequest, TResponse>(
  apiMethod:
    ((params: TRequest) => Promise<TResponse>) | (() => Promise<TResponse>)
) {
  const {
    loading,
    error,
    call: apiCall,
  } = useAPICall<TRequest, TResponse>(apiMethod);

  const dataInternal = ref<TResponse>();
  const data = readonly(dataInternal);

  async function call(params?: TRequest): Promise<void> {
    const result = await apiCall(params);

    if (result !== undefined) {
      dataInternal.value = result;
    }
  }

  return {
    loading,
    error,
    data,
    call,
  };
}

/**
 * A composable utility for managing API calls data loading as well as loading and error states.
 * @template TRequest - The type of the request parameters.
 * @template TResponse - The type of the API response.
 * @param apiMethod - The API method to be called.
 * @returns An object containing the state of the API call and a method to execute the call.
 */
export function useAPICall<TRequest, TResponse>(
  apiMethod:
    ((params: TRequest) => Promise<TResponse>) | (() => Promise<TResponse>)
) {
  const loadingInternal = ref(false);
  const errorInternal = ref(false);

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);

  /**
   * Executes the API method and updates the loading / error state accordingly.
   * @param params - The parameters for the API call as an object.
   * @returns A promise that resolves when the call completes (check `data` state for retrieved data and `error` state for failures).
   */
  async function call(params?: TRequest): Promise<TResponse | undefined> {
    loadingInternal.value = true;
    errorInternal.value = false;

    try {
      return await (apiMethod as (params?: TRequest) => Promise<TResponse>)(
        params
      );
    } catch (error) {
      console.debug(error);
      errorInternal.value = true;
    } finally {
      loadingInternal.value = false;
    }
  }

  return {
    loading,
    error,
    call,
  };
}
