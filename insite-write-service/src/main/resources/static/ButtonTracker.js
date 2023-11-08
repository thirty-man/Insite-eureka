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

    // Data를 전송하는 함수입니다.
    function sendData(data) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8082/api/v1/data/button', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status !== 200) {
                console.error('There has been a problem with your fetch operation:', xhr.statusText);
            }
        };
        xhr.send(JSON.stringify(data));
    }

    window.addEventListener('load', function() {
        var path = window.location.pathname;
        var currentCookieId = getCookieId() || createCookieId();
        var data = {
            cookie_id: currentCookieId,
            current_url: path,
            name: buttonName,
            application_token: "test",
            application_url: window.location.origin,
            activity_id: "test",
        };
        sendData(data);
    });


})();
