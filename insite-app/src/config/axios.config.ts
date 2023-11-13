/* eslint-disable consistent-return */
import { accumulAPI, memberAPI, realTimeAPI } from "@api/Api";
import axios from "axios";

const { VITE_SERVER_URL, VITE_LOGIN_API_URI } = import.meta.env;

realTimeAPI.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;

      try {
        const refreshToken = sessionStorage.getItem("RefreshToken");

        // headers를 설정 객체 내에 포함시켜야 합니다.
        const response = await axios.post(
          `${VITE_LOGIN_API_URI}/api/v1/members/reissue`,
          {},
          {
            headers: {
              RefreshToken: refreshToken,
            },
          },
        );

        // 서버에서 "no"를 반환하는 경우 로그인 페이지로 리디렉션
        if (response.data === "no") {
          window.location.href = `${VITE_SERVER_URL}/login`;
          return;
        }

        const newToken = response.headers.authorization;
        sessionStorage.setItem("Authorization", newToken);

        // headers 객체가 존재하는지 확인합니다.
        originalRequest.headers = originalRequest.headers || {};
        originalRequest.headers.Authorization = newToken;

        // 원래 요청 재실행
        return await axios(originalRequest);
      } catch (refreshError) {
        window.location.href = `${VITE_SERVER_URL}/login`;
        // 새 토큰 요청 실패 처리
        // eslint-disable-next-line no-console
        console.error("Error Refresh:", refreshError);
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  },
);

accumulAPI.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;

      try {
        const refreshToken = sessionStorage.getItem("RefreshToken");

        // headers를 설정 객체 내에 포함시켜야 합니다.
        const response = await axios.post(
          `${VITE_LOGIN_API_URI}/api/v1/members/reissue`,
          {},
          {
            headers: {
              RefreshToken: refreshToken,
            },
          },
        );

        // 서버에서 "no"를 반환하는 경우 로그인 페이지로 리디렉션
        if (response.data === "no") {
          window.location.href = `${VITE_SERVER_URL}/login`;
          return;
        }

        const newToken = response.headers.authorization;
        sessionStorage.setItem("Authorization", newToken);

        // headers 객체가 존재하는지 확인합니다.
        originalRequest.headers = originalRequest.headers || {};
        originalRequest.headers.Authorization = newToken;

        // 원래 요청 재실행
        return await axios(originalRequest);
      } catch (refreshError) {
        window.location.href = `${VITE_SERVER_URL}/login`;
        // 새 토큰 요청 실패 처리
        // eslint-disable-next-line no-console
        console.error("Error Refresh:", refreshError);
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  },
);

memberAPI.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;

      try {
        const refreshToken = sessionStorage.getItem("RefreshToken");

        // headers를 설정 객체 내에 포함시켜야 합니다.
        const response = await axios.post(
          `${VITE_LOGIN_API_URI}/api/v1/members/reissue`,
          {},
          {
            headers: {
              RefreshToken: refreshToken,
            },
          },
        );

        // 서버에서 "no"를 반환하는 경우 로그인 페이지로 리디렉션
        if (response.data === "no") {
          window.location.href = `${VITE_SERVER_URL}/login`;
          return;
        }

        const newToken = response.headers.authorization;
        sessionStorage.setItem("Authorization", newToken);

        // headers 객체가 존재하는지 확인합니다.
        originalRequest.headers = originalRequest.headers || {};
        originalRequest.headers.Authorization = newToken;

        // 원래 요청 재실행
        return await axios(originalRequest);
      } catch (refreshError) {
        window.location.href = `${VITE_SERVER_URL}/login`;
        // 새 토큰 요청 실패 처리
        // eslint-disable-next-line no-console
        console.error("Error Refresh:", refreshError);
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  },
);
