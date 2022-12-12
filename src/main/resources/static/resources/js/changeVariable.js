const inputs = document.getElementsByClassName("input");
for (let input in inputs) {
    input.addEventListener("keyup", function (event) {
        const bottoms = document.getElementsByClassName("redirection");
        for (let bottom in bottoms) {
            const url = bottom.getAttribute("href");
            bottom.setAttribute("href", url.replace(/=.*/, "=" + input.value));
        }
    });
}

