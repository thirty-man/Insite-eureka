// cookeId를 위한 uniqueId 생성합니다.
function generateUniqueId() {
  return "id_" + Math.random().toString(36).substr(2, 16);
}

// 기존의 cookieId를 가져옵니다.
function getCookieId() {
  const cookieId = document.cookie.replace(
    /(?:(?:^|.*;\s*)analyticsId\s*\=\s*([^;]*).*$)|^.*$/,
    "$1"
  );
  return cookieId;
}

// 새로운 cookieId 생성을 위한 함수입니다.
function createCookieId() {
  const newCookieId = generateUniqueId();
  document.cookie = `analyticsId=${newCookieId};path=/;expires=${new Date(
    new Date().setFullYear(new Date().getFullYear() + 1)
  ).toUTCString()}`;
  return newCookieId;
}

// 집계된 데이터를 전송하는 함수입니다.
function sendData(data) {
  console.log(data);
  fetch("http://localhost:8082/api/v1/data/page", {
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
    .catch((error) => {
      console.error(
        "There has been a problem with your fetch operation:",
        error
      );
    });
}

// Document의 정보를 집계하는 함수입니다.
export function traceButton(buttonName) {
  const path = window.location.pathname;
  const currentCookieId = getCookieId() || createCookieId();
  const data = {
    cookie_id: currentCookieId,
    current_url: path,
    name: buttonName,
    application_token: "test",
    application_url: window.location.origin,
    activity_id: "test",
  };

  sendData(data);
}
