let oldDiv = new Map();
let oldImage = new Map();
let pointer = document.getElementsByClassName('pointer')
for (let j = 0; j < pointer.length; j++) {
    const radioButtons = pointer.item(j).querySelectorAll('.custom-radio');

    for (let i = 0; i < radioButtons.length; i++) {
        radioButtons[i].addEventListener('change', function (event) {
            const div = document.getElementById(i + "" + j + 'd');
            const image = document.getElementById(i + "" + j + 'i');
            const newImage = document.createElement('img');
            newImage.setAttribute('src', image.getAttribute('alt'));
            newImage.setAttribute('class', 'overlay');
            if (oldDiv.get(j) !== undefined) oldDiv.get(j).innerHTML = oldImage.get(j).outerHTML;
            div.innerHTML = image.outerHTML + newImage.outerHTML;
            oldImage.set(j, image);
            oldDiv.set(j, div);
        });
    }
}

