import axios from "axios";

const { VITE_API_URI } = import.meta.env;
const token = sessionStorage.getItem("Authorization");
const refresh = sessionStorage.getItem("RefreshToken");

const API = axios.create({
  baseURL: `${VITE_API_URI}/api/v1`,
  headers: {
    Authorization: token,
    RefreshToken: refresh,
    "Content-Type": "application/json",
  },
});

export default API;
