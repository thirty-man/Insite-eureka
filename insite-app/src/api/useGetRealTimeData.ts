import useAxios from "./useAxios";

function useGetRealTimeData(add: string) {
  // const address: string = add;
  const address = add;

  const response = useAxios({
    method: "get",
    url: `/realtime-data/${address}`,
  });

  return response;
}

export default useGetRealTimeData;
