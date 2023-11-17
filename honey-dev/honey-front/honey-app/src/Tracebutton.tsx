/* eslint-disable prefer-destructuring */
/* eslint-disable no-useless-escape */
/* eslint-disable object-shorthand */
/* eslint-disable prefer-template */
/* eslint-disable react/jsx-props-no-spreading */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable react/function-component-definition */
/* eslint-disable react/display-name */
/* eslint-disable import/prefer-default-export */

type AnalyticsData = {
  cookie_id: string;
  current_url: string;
  name: string;
  application_token: string;
  application_url: string;
  activity_id: string;
};

// cookeId 를 위한 uniqueId 생성합니다.
function generateUniqueId() {
  return "id_" + Math.random().toString(36).substr(2, 16);
}

// 기존의 cookieId 를 가져옵니다.
function getCookieId() {
  const cookieId = document.cookie.replace(
    /(?:(?:^|.*;\s*)analyticsId\s*\=\s*([^;]*).*$)|^.*$/,
    "$1",
  );
  return cookieId;
}

// 새로운 cookieId 생성을 위한 함수입니다.
function createCookieId() {
  const cookieId = generateUniqueId();
  document.cookie = `analyticsId=${cookieId};path=/;expires=${new Date(
    new Date().setFullYear(new Date().getFullYear() + 1),
  ).toUTCString()}`;
  return cookieId;
}

// 집계된 데이터를 전송하는 함수입니다.
function sendData(data: AnalyticsData) {
  fetch("https://takeinsite.com:8081/write-service/api/v1/data/button", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .catch(() => {
      // console.log("ok");
      // console.error(
      //   "There has been a problem with your fetch operation:",
      //   error,
      // );
    });
}

// Document의 정보를 집계하는 함수입니다.
export function traceButton(name: string) {
  const path = window.location.pathname;
  const cookieId = getCookieId() || createCookieId();
  const data = {
    cookie_id: cookieId,
    current_url: path,
    name: name,
    application_token: "87114d66-9228-4e89-80f6-7a4f94663a6b",
    application_url: window.location.origin,
    activity_id: "test",
  };

  sendData(data);
}
