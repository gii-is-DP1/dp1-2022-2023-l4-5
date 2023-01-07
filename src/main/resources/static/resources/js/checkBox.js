const checkBoxes = document.querySelectorAll('.custom-box');
for (let i = 0; i < checkBoxes.length; i++) {
    checkBoxes[i].addEventListener('change', function (event) {
        const div = document.getElementById(i + 'd');
        const image = document.getElementById(i + 'i');
        const newImage = document.createElement('img');
        newImage.setAttribute('src', image.getAttribute('alt'));
        newImage.setAttribute('class', 'overlay');
        div.innerHTML = (checkBoxes[i].checked) ? image.outerHTML + newImage.outerHTML: image.outerHTML;
    });
}
