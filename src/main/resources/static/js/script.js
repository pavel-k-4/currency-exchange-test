const activate = () => {
    document.getElementById("button-convert").onclick = convert;
    document.getElementById("button-search").onclick = search;
    document.getElementById("button-search-cancel").onclick = cancelSearch;
};
function showError() {
    document.getElementById("target-val").value = "";
    let input = document.getElementById("initial-val")
    input.classList.add("error")
    setTimeout(() => input.classList.remove("error"), 500);
}
const makeConvertCall = async (dto, names) => {
    const token = document.querySelector("meta[name='_csrf']").content;
    const response = await fetch("/convert", {
        method: 'POST',
        headers: {
            'Accept': 'text/plain',
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(dto),
    });

    response.text().then(data => {
        console.log(data);
        if (!data) {
            showError();
            return;
        }
        document.getElementById("target-val").value = data;
        let table = document.getElementById("history-table")
        let row = table.insertRow(1);
        row.insertCell(0).innerHTML = names.initialName
        row.insertCell(1).innerHTML = names.targetName
        row.insertCell(2).innerHTML = dto.initialValue
        row.insertCell(3).innerHTML = data
        row.insertCell(4).innerHTML = "только что"
    }, () => {
        showError();
    });
}

const makeSearchCall = async (date) => {
    const token = document.querySelector("meta[name='_csrf']").content;
    let uri;
    if (date) {
        uri = '/history?' + new URLSearchParams({ 'd': date });
    } else {
        uri = '/history';
    }
    const response = await fetch(uri, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'text/plain',
            'X-CSRF-TOKEN': token
        }
    });

    return response.json()
}

const getSelectedText = (id) => {
    let select = document.getElementById(id)
    return select.options[select.selectedIndex].text
}

const convert = () => {
    let dto = {
        "initialCurrency": document.getElementById("initial-cur").value,
        "targetCurrency": document.getElementById("target-cur").value,
        "initialValue": document.getElementById("initial-val").value
    };
    if (dto.initialValue) {
        makeConvertCall(dto, {
            "initialName": getSelectedText("initial-cur"),
            "targetName": getSelectedText("target-cur")
        }).then(() => activate())
    } else {
        activate()
    }
};

const search = () => {
    let date = document.getElementById("search-date").value
    if (!date) {
        return activate();
    }
    makeSearchCall(date)
        .then((dto) => {
            console.log(dto);
            let cancel = document.getElementById("button-search-cancel")
            cancel.hidden = false
        })
        .then(() => activate())
}

const cancelSearch = () => {
    let cancel = document.getElementById("button-search-cancel")
    cancel.hidden = true
    makeSearchCall()
        .then((dto) => {
            console.log(dto);
        })
        .then(() => activate())
}
activate()
