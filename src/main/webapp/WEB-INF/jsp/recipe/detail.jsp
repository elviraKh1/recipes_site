<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section id="hero" class="hero d-flex align-items-center section-bg pt-5">
    <div class="container">
        <div class="row justify-content-between gy-5">
            <div class="col-lg-5 order-2 order-lg-1 d-flex flex-column justify-content-center align-items-center align-items-lg-start text-center text-lg-start">
                <h2 data-aos="fade-up">${recipe.name}</h2>
                <div class="d-flex" data-aos="fade-up" data-aos-delay="200">
                    <a href="/recipe/category?c=${recipe.category}"
                       class="btn-book-a-table">Catefory: ${recipe.category} </a>
                </div>
                <div class="d-flex" data-aos="fade-up" data-aos-delay="200">
                    <a href="#book-a-table" class="btn-book-a-table">Author: ${recipe.authorId} </a>
                </div>
                <br>
                <pre data-aos="fade-up" data-aos-delay="100" style="color: #085f63">${recipe.instructions}.</pre>
            </div>
            <div class="col-lg-5 order-1 order-lg-2 text-center text-lg-start">
                <img src="${recipe.imageUrl}" class="img-fluid" alt="" data-aos="zoom-out" data-aos-delay="300">
            </div>
        </div>
        <div class="col-lg-5 order-2 order-lg-1 d-flex flex-column justify-content-center align-items-center align-items-lg-start text-center text-lg-start" style="color: #085f63">
            <h3> Ingredients:</h3>
            <table class="table  table-striped table-hover">
                <thead>
                <tr>
                    <th>ingredient</th>
                    <th>Measure</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ingredients}" var="ingredient">
                    <tr>
                        <td>${ingredient.ingredient.name}</td>
                        <td>${ingredient.measure}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-lg-15 pb-6 pt-6  ">
            <sec:authorize access="isAuthenticated()">
                <c:if test="${user != null && recipe.authorId == user.id}">
                    <a href='/recipe/edit/${recipe.id}' class='btn btn-secondary'>Edit</a>
                </c:if>
            </sec:authorize>
        </div>
    </div>
</section>


<%--<section style="background-color: #eee;">--%>
<%--    <div class="container py-5">--%>

<%--        <div class="row">--%>
<%--            <div class="col-lg-3">--%>
<%--                <div class="card mb-4">--%>
<%--                    <div class="card-body text-center">--%>
<%--                        <img src="${recipe.imageUrl}" width="500" height="auto">--%>
<%--                        <h5 class="my-3">${recipe.name}</h5>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>


<%--            <div class="col-lg-8">--%>
<%--                <div class="card mb-4">--%>
<%--                    <div class="card-body">--%>

<%--                        <div class="row">--%>
<%--                            <div class="col-sm-3">--%>
<%--                                <p class="mb-0">Name</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <p class="text-muted mb-0">${recipe.name}</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>

<%--                        <hr>--%>
<%--                        <div class="row">--%>
<%--                            <div class="col-sm-3">--%>
<%--                                <p class="mb-0">instructions</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <p class="text-muted mb-0">${recipe.instructions}</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <hr>--%>

<%--                        <div class="row">--%>
<%--                            <div class="col-sm-3">--%>
<%--                                <p class="mb-0">Author</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <p class="text-muted mb-0">${recipe.authorId}</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <hr>--%>

<%--                        <div class="row">--%>
<%--                            <div class="col-sm-3"></div>--%>
<%--                            <div class="col-sm-9">--%>
<%--                                <sec:authorize access="isAuthenticated()">--%>
<%--                                    <c:if test="${user != null && recipe.authorId == user.id}">--%>
<%--                                        <a href='/recipe/edit/${recipe.id}' class='btn btn-primary '>Edit</a>--%>
<%--                                    </c:if>--%>
<%--                                </sec:authorize>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>
<%--<h2>Ingredients:</h2>--%>
<%--<table>--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th>ingredient</th>--%>
<%--        <th>Measure</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <c:forEach items="${ingredients}" var="ingredient">--%>
<%--        <tr>--%>
<%--            <td>${ingredient.ingredient.name}</td>--%>
<%--            <td>${ingredient.measure}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>
<%--</table>--%>
<jsp:include page="../include/footer.jsp"/>
