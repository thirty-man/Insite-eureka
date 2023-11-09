import axios from "axios";

const { VITE_REALTIME_API_URI, VITE_ACCUMUL_API_URI, VITE_MEMBER_API_URI } =
  import.meta.env;
const token = sessionStorage.getItem("Authorization");
const refresh = sessionStorage.getItem("RefreshToken");

const realTimeAPI = axios.create({
  baseURL: `${VITE_REALTIME_API_URI}/api/v1`,
  headers: {
    Authorization: token,
    RefreshToken: refresh,
    "Content-Type": "application/json",
  },
});

const accumulAPI = axios.create({
  baseURL: `${VITE_ACCUMUL_API_URI}/api/v1`,
  headers: {
    Authorization: token,
    RefreshToken: refresh,
    "Content-Type": "application/json",
  },
});

const memberAPI = axios.create({
  baseURL: `${VITE_MEMBER_API_URI}/api/v1`,
  headers: {
    Authorization: token,
    RefreshToken: refresh,
    "Content-Type": "application/json",
  },
});

export { realTimeAPI, accumulAPI, memberAPI };
