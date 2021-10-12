function Api() {

    let req = function (url, method, data, success, error) {
        let xhr = new XMLHttpRequest();
        const m = method ? method : "GET";
        xhr.open(m, url, true);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.onload = function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    success(JSON.parse(xhr.response))
                } else {
                    if (error) {
                        error()
                    }
                }
            }
        };
        xhr.onerror = function (e) {
            error();
        };
        xhr.send(JSON.stringify(data));
    }

    let get = function (url, success, error) {
        req(url, "GET", null, success, error)
    }

    let post = function (url, data, success, error) {
        req(url, "POST", data, success, error)
    }

    Api.prototype.code = function (success, error) {
        get('/api/code', success, error)
    }

    Api.prototype.update = function (data, success, error) {
        post('/api/code/new', data, success, error)
    }
}

window.API = new Api();
