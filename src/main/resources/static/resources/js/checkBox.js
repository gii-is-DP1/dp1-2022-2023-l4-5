const checkBoxes = pointer.item(j).querySelectorAll('.custom-radio');
for (let i = 0; i < checkBoxes.length; i++) {
    checkBoxes[i].addEventListener('change', function (event) {
        const div = document.getElementById(i + "" + j + 'd');
        const image = document.getElementById(i + "" + j + 'i');
        const newImage = document.createElement('img');
        newImage.setAttribute('src', image.getAttribute('alt'));
        newImage.setAttribute('class', 'overlay');
        div.innerHTML = (checkBoxes[i].checked()) ? image.outerHTML + newImage.outerHTML: image.outerHTML;
    });
}
