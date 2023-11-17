/* eslint-disable prefer-destructuring */
/* eslint-disable no-useless-escape */
/* eslint-disable object-shorthand */
/* eslint-disable prefer-template */
/* eslint-disable react/jsx-props-no-spreading */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable react/function-component-definition */
/* eslint-disable react/display-name */
/* eslint-disable import/prefer-default-export */

import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import { JSX } from "react/jsx-runtime";

type AnalyticsData = {
  cookie_id: string;
  current_url: string;
  before_url: string | null;
  referrer: string | null;
  language: string;
  response_time: number;
  os_id: string;
  is_new: boolean;
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

// Document의 정보를 집계하는 함수입니다.
function gatherData(path: string) {
  const navigationEntries = performance.getEntriesByType("navigation");
  let responseTime = 0;
  let isNew = false;
  if (navigationEntries.length > 0) {
    const navigationTiming =
      navigationEntries[0] as PerformanceNavigationTiming;
    responseTime = navigationTiming.loadEventEnd - navigationTiming.startTime;
  }
  const cookieId = getCookieId() || ((isNew = true), createCookieId());
  const referrerOrigin = document.referrer
    ? new URL(document.referrer).origin
    : "";
  const referrer =
    referrerOrigin === window.location.origin ? null : document.referrer;

  const data = {
    cookie_id: cookieId,
    current_url: path,
    before_url: sessionStorage.getItem("previousPath") || "",
    referrer: referrer,
    language: navigator.language,
    response_time: responseTime,
    os_id: navigator.platform,
    is_new: isNew,
    application_token: "87114d66-9228-4e89-80f6-7a4f94663a6b",
    application_url: window.location.origin,
    activity_id: "test",
  };

  return data;
}

// 집계된 데이터를 전송하는 함수입니다.
function sendData(data: AnalyticsData) {
  fetch("https://takeinsite.com:8081/write-service/api/v1/data/page", {
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

function trackPageView(path: string) {
  const data = gatherData(path);
  sendData(data);
  sessionStorage.setItem("previousPath", path);
}

export const withAnalytics = (WrappedComponent: React.ComponentType) => {
  const ComponentWithAnalytics = (props: JSX.IntrinsicAttributes) => {
    const location = useLocation();

    useEffect(() => {
      trackPageView(location.pathname);
    }, [location.pathname]);

    return <WrappedComponent {...props} />;
  };

  ComponentWithAnalytics.displayName = `WithAnalytics(${
    WrappedComponent.displayName || WrappedComponent.name || "Component"
  })`;

  return <ComponentWithAnalytics />;
};
