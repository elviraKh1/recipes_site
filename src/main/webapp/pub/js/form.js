function removeElement(ev) {
    let button = ev.target;
    let div = button.parentElement;
    div.remove()
}

function addFields() {
    const num = this.name.match(/\d+/);
    let formFields = document.getElementById('formFields');
    let fieldDiv = document.createElement('div');

    let ingredientsInputs = formFields.getElementsByClassName('__ingredient')

    let currentField = 0
    if (ingredientsInputs != null)
        currentField = ingredientsInputs.length;

    // name
    let nameInput = document.createElement('input');
    nameInput.type = 'text';
    nameInput.name = 'ingredientsInp[' + currentField + '].name';
    nameInput.placeholder = 'Ingredient name';
    nameInput.classList.add('__ingredient')

    //hidddent id
    let hiddenIdInput = document.createElement('input');
    hiddenIdInput.type = 'hidden';
    hiddenIdInput.id = 'ingredientId_' + currentField;
    hiddenIdInput.name = 'ingredientsInp[' + currentField + '].id'

    // quantity
    // let quantityInput = document.createElement('input');
    // quantityInput.type = 'number';
    // quantityInput.name = 'quantity_'+currentFileld;
    // quantityInput.placeholder = 'Ingredient quantity';

    // measure
    let measureInput = document.createElement('input');
    measureInput.type = 'text';
    measureInput.name = 'ingredientsInp[' + currentField + '].measure';
    measureInput.placeholder = 'Ingredient measure';

    //create remove button
    let remove = document.createElement('button');
    remove.setAttribute('id', 'reqsr' + +currentField);
    remove.onclick = function (e) {
        removeElement(e)
    };
    remove.setAttribute("type", "button");
    remove.innerHTML = "Remove" + +currentField;


    fieldDiv.appendChild(nameInput);
    fieldDiv.appendChild(hiddenIdInput);
    // fieldDiv.appendChild(quantityInput);
    fieldDiv.appendChild(measureInput);
    fieldDiv.appendChild(remove);
    formFields.appendChild(fieldDiv);
    currentField++;
}

// call Autocomplete function
$(document).on("focus", '.__ingredient', function () {
    $(this).autocomplete({
        source: "ingredientAutocomplete",
        minLength: 3,
        select: function (event, ui) {
            this.value = ui.item.label;
            const num = this.name.match(/\d+/);
            $("#ingredientId_" + num).val(ui.item.value);
            return false;
        }
    });
})