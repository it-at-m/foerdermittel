import type { Test } from "@/types/Test";

import useAPI from "@/composables/useAPI";

export function useCreateTest() {
  // const api = ApiFactory.getInstance(MyControllerApi);

  return useAPI<Test, Test>((params) =>
    // api.myControllerEndpoint();
    Promise.resolve(params)
  );
}

export function useUpdateTest() {
  // const api = ApiFactory.getInstance(MyControllerApi);

  return useAPI<Test, Test>((params) =>
    // api.myControllerEndpoint();
    Promise.resolve(params)
  );
}

export function useDeleteTest() {
  // const api = ApiFactory.getInstance(MyControllerApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useAPI<string, void>((params: string) => {
    // api.myControllerEndpoint();
    console.debug(`${params} is used to send delete request`);
    return Promise.resolve();
  });
}

export function useGetTests() {
  // const api = ApiFactory.getInstance(MyControllerApi);

  // eslint-disable-next-line @typescript-eslint/no-invalid-void-type
  return useAPI<void, Test[]>(() => {
    // api.myControllerEndpoint();
    const dummyData: Test[] = [
      {
        id: "dummy",
        stringField: "Test",
        numberField: 5,
        booleanField: true,
      },
      {
        id: "dummy2",
        stringField: "Test 2",
        numberField: 7,
        booleanField: false,
      },
      {
        id: "dummy3",
        stringField: "Test 3",
        numberField: 1,
        booleanField: false,
      },
      {
        id: "dummy4",
        stringField: "Test 4",
        numberField: 3,
        booleanField: true,
      },
    ];
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(dummyData);
      }, 2000);
    });
  });
}
