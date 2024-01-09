<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>The magic porridge pot</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="../../../pub/css/main.css"/>

    <script src="../../../pub/js/form.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm">
    <div class="container-fluid">
        <a href="/" class="navbar-brand me-auto me-lg-0"> The magic porridge pot </a>
        <ul class="navbar-nav ">
            <li class="nav-item">
                <a class="nav-link" href="/recipe/search">Recipes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/ingredient/all">Ingredients</a>
            </li>
            <sec:authorize access="hasAnyAuthority('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link" href="/ingredient/add">Create Ingredient</a></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/index">Admin</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/recipe/add">Create recipe</a>
                </li>
            </sec:authorize>
        </ul>

        <ul class="  navbar-nav justify-content-end ">
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/recipe/ny">My recipes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/auth/logout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><sec:authentication property="principal.username"/></a>
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
        </ul>
        <form class="d-flex" action="/recipe/search" method="GET">
            <input class="form-control me-2" type="text" placeholder="Search" id="search" name="search">
            <button class="btn btn-secondary" type="submit">Search</button>
        </form>
    </div>
</nav>