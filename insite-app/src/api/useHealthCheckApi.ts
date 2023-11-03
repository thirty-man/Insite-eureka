import useAxios from "./useAxios";

function useHealthCheckApi() {
  const response = useAxios({
    method: "get",
    url: "health/check",
  });

  return response;
}

export default useHealthCheckApi;
