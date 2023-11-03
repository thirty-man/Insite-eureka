(function() {
    // cookeId 를 위한 uniqueId 생성합니다.
    function generateUniqueId() {
        return "id_" + Math.random().toString(36).substr(2, 16);
    }

    // 기존의 cookieId 를 가져옵니다.
    function getCookieId() {
        var cookieId = document.cookie.replace(
            /(?:(?:^|.*;\s*)analyticsId\s*\=\s*([^;]*).*$)|^.*$/,
            "$1",
        );
        return cookieId;
    }

    // 새로운 cookieId 생성을 위한 함수입니다.
    function createCookieId() {
        var cookieId = generateUniqueId();
        document.cookie = 'analyticsId=' + cookieId + ';path=/;expires=' + new Date(
                        new Date().setFullYear(new Date().getFullYear() + 1)
                    ).toUTCString();
        return cookieId;
    }

    function gatherData() {
        var navigationEntries = performance.getEntriesByType("navigation");
        var responseTime = 0;
        var isNew = false;
        if (navigationEntries.length > 0) {
            var navigationTiming = navigationEntries[0];
            responseTime = navigationTiming.loadEventStart;
        }
        var cookieId = getCookieId() || ((isNew = true), createCookieId());
        var referrerOrigin = document.referrer ? new URL(document.referrer).origin : "";
        var referrer = referrerOrigin === window.location.origin ? null : document.referrer;

        var data = {
            cookie_id: cookieId,
            current_url: window.location.pathname,
            before_url: sessionStorage.getItem("previousPath") || "",
            referrer: referrer,
            language: navigator.language,
            response_time: responseTime,
            os_id: navigator.platform,
            is_new: isNew,
            application_token: "",
            application_url: window.location.origin,
            activity_id: "",
        };

        return data;
    }

    function sendData(data) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8082/api/v1/data/page', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status !== 200) {
                console.error('There has been a problem with your fetch operation:', xhr.statusText);
            }
        };
        xhr.send(JSON.stringify(data));
    }

    window.addEventListener('load', function() {
        // Execution
        var data = gatherData();
        sendData(data);
        sessionStorage.setItem("previousPath", window.location.pathname);
    });
})();
