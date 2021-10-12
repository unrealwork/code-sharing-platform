document.addEventListener('DOMContentLoaded', function () {
    const elements = {
        codeSnippet: document.getElementById('code_snippet'),
        sendSnippetBtn: document.getElementById('send_snippet'),
        time: document.getElementById('time_restriction'),
        views: document.getElementById('views_restriction')
    }


    elements.sendSnippetBtn.onclick = function (e) {
        e.preventDefault();
        const code = {
            code: elements.codeSnippet.value,
            time: elements.time.value,
            views: elements.views.value
        }
        API.update(code, function (data) {
            window.location.href = '/code/new'
        })
        return false;
    }
})


