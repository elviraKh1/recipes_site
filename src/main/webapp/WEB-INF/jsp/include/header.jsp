<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Recipes</title>
    <link href="/pub/css/global-style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


    <script type="text/javascript">
        var currentFileld=1;
        function addFields() {
            currentFileld++;
            let formFields = document.getElementById('formFields');
            let fieldDiv = document.createElement('div');

            // name
            let nameInput = document.createElement('input');
            nameInput.type = 'text';
            nameInput.name = 'ingredientName_'+currentFileld;
            nameInput.placeholder = 'Ingredient name';
            nameInput.classList.add('__ingredient')

            //hidddent id
            let hiddenIdInput = document.createElement('input');
            hiddenIdInput.type = 'hidden';
            hiddenIdInput.id = 'ingredientId_'+currentFileld;
            hiddenIdInput.name = 'ingredientId_'+currentFileld;

            // quantity
            // let quantityInput = document.createElement('input');
            // quantityInput.type = 'number';
            // quantityInput.name = 'quantity_'+currentFileld;
            // quantityInput.placeholder = 'Ingredient quantity';

            // measure
            let measureInput = document.createElement('input');
            measureInput.type = 'text';
            measureInput.name = 'measure_'+currentFileld;
            measureInput.placeholder = 'Ingredient measure';


            // Добавляем поля в div
            fieldDiv.appendChild(nameInput);
            fieldDiv.appendChild(hiddenIdInput);
            // fieldDiv.appendChild(quantityInput);
            fieldDiv.appendChild(measureInput);
            // Добавляем div с полями в форму
            formFields.appendChild(fieldDiv);
        }

        // call Autocomplete function
            $(document).on("focus", '.__ingredient', function() {
            $(this).autocomplete({
                source: "ingredientAutocomplete",
                minLength: 3,
                select: function(event, ui) {
                    this.value = ui.item.label;
                    $("#ingredientId_"+this.name.substring(this.name.indexOf('_') + 1)).val(ui.item.value);
                    return false;
                }
            });
        })
        // })
    </script>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/recipe/search">Search recipe</a>
                </li>

                <sec:authorize access="hasAnyAuthority('ADMIN', 'MEMBER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/index">Admin</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ingredient/add">Create Ingredient</a>
                    </li>

                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">

                    <li class="nav-item">
                        <a class="nav-link" href="/auth/register">Sign Up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/login">Sign In</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/recipe/add">Create recipe</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/logout">Logout</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><sec:authentication property="principal.username"/></a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>