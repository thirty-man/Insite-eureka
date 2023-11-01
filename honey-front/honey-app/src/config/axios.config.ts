/* eslint-disable consistent-return */
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

// AxiosRequestConfig 인터페이스를 확장하여 'retry' 속성을 추가합니다.
interface CustomAxiosRequestConfig extends AxiosRequestConfig {
  retry?: boolean;
}
const { VITE_API_URL } = import.meta.env;

axios.interceptors.response.use(
  (response: AxiosResponse) => response,
  async (error) => {
    // CustomAxiosRequestConfig을 사용합니다.
    const originalRequest: CustomAxiosRequestConfig = error.config;

    if (error.response?.status === 401 && !originalRequest.retry) {
      originalRequest.retry = true;

      try {
        const refreshToken = sessionStorage.getItem("RefreshToken");

        // headers를 설정 객체 내에 포함시켜야 합니다.
        const response = await axios.post(
          `${VITE_API_URL}/api/v1/members/reissue`,
          {},
          {
            headers: {
              RefreshToken: refreshToken,
            },
          },
        );

        // 서버에서 "no"를 반환하는 경우 로그인 페이지로 리디렉션
        if (response.data === "no") {
          window.location.href = `http://rollinghoney.com/login`;
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
        // 새 토큰 요청 실패 처리
        // eslint-disable-next-line no-console
        console.error("Error Refresh:", refreshError);
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  },
);
