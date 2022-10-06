const activate = () => document.getElementById("button-convert").onclick = check;
const makeCall = async (dto, names) => {

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
        document.getElementById("target-val").value = data;
        let table = document.getElementById("history-table")
        let row = table.insertRow(-1);
        row.insertCell(0).innerHTML = names.initialName
        row.insertCell(1).innerHTML = names.targetName
        row.insertCell(2).innerHTML = dto.initialValue
        row.insertCell(3).innerHTML = data
        row.insertCell(4).innerHTML = "только что"
    }, () => {
        document.getElementById("target-val").value = "";
        let input = document.getElementById("initial-val")
        input.classList.add("error")
        setTimeout(() => input.classList.remove("error"), 500);
    });
}

function getSelectedText(id) {
    let select = document.getElementById(id)
    return select.options[select.selectedIndex].text
}

const check = () => {
    let dto = {
        "initialCurrency": document.getElementById("initial-cur").value,
        "targetCurrency": document.getElementById("target-cur").value,
        "initialValue": document.getElementById("initial-val").value
    };
    if (dto.initialValue) {
        makeCall(dto, {
            "initialName": getSelectedText("initial-cur"),
            "targetName": getSelectedText("target-cur")
        }).then(() => activate())
    } else {
        activate()
    }
};
activate()
