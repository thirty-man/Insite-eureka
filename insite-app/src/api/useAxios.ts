import { useState, useEffect, useCallback } from "react";
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";

const { VITE_API_URI } = import.meta.env;
axios.defaults.baseURL = VITE_API_URI;

/**
 * method : "get", "post" ...
 * url: "members/..."
 */
const useAxios = (initialConfig: AxiosRequestConfig) => {
  const [response, setResponse] = useState<AxiosResponse | undefined>();
  const [error, setError] = useState<AxiosError | undefined>();
  const [loading, setLoading] = useState(true);
  const token = sessionStorage.getItem("Authorization");
  const fetchData = useCallback(
    async (params: AxiosRequestConfig) => {
      try {
        const res = await axios.request({
          ...params,
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        });
        setResponse(res);
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } catch (err: any) {
        setError(err);
      } finally {
        setLoading(false);
      }
    },
    [token],
  );

  const sendData = () => {
    fetchData(initialConfig);
  };

  useEffect(() => {
    fetchData(initialConfig);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return { response, error, loading, fetchData: sendData };
};

export default useAxios;
