const activate = () => document.getElementById("button-convert").onclick = check;
const makeCall = async (dto) => {

    const token = document.querySelector("meta[name='_csrf']").content;
    const response = await fetch("/convert", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(dto),
    });

    response.json().then(data => {
        document.getElementById("target-val").value = data;
    }, () => {
        document.getElementById("target-val").value = "";
        let input = document.getElementById("initial-val")
        input.classList.add("error")
        setTimeout(() => input.classList.remove("error"), 500);
    });
}


const check = () => {
    let dto = {
        "initialCurrency": document.getElementById("initial-cur").value,
        "targetCurrency": document.getElementById("target-cur").value,
        "initialValue": document.getElementById("initial-val").value
    };
    if (dto.initialValue) {
        makeCall(dto).then(() => activate())
    } else {
        activate()
    }
};
activate()
