const inputs = document.getElementsByClassName("input");
for (let i=0; i < inputs.length; i++) {
    inputs[i].addEventListener("keyup", function (event) {
        const bottoms = document.getElementsByClassName("redirection");
        for (let j=0; j < bottoms.length; j++) {
            const url = bottoms[j].getAttribute("href");
            bottoms[j].setAttribute("href", url.replace(/=.*/, "=" + inputs[i].value));
        }
    });
}

