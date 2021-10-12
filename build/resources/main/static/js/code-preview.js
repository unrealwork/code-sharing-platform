document.addEventListener('DOMContentLoaded', function () {
    const elements = {
        codeSnippet: document.getElementById('code_snippet'),
        loadDate: document.getElementById('load_date')
    }


    API.code(function (data) {
        if (data.date) {
            elements.loadDate.innerText = data.date
        }
        elements.codeSnippet.innerHTML = data.code;
    })
})


