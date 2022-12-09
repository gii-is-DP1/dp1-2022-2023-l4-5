export default function sendPetitionInInterval(url, action, time) {
    $(document).ready(function () {
        setInterval(function () {
            const http = new XMLHttpRequest();
            http.open('GET', url, true);
            http.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    action(this.responseText)
                }
            }
            http.send()
        }, time);
    });
}
