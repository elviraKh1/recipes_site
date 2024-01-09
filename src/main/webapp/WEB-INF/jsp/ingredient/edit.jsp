<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">  Ingredient</h2>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container">

        <c:if test="${not empty success}">
            <div class="row justify-content-center">
                <div class="col-6 text-center">
                    <div class="alert alert-success" role="alert">
                            ${success}
                    </div>
                </div>
            </div>
        </c:if>

        <form method="GET" action="/ingredient/submit">
            <input type="hidden" name="id" value="${form.id}">
            <div class="mt-3">
                <label for="name" class="form-label">name</label>
                <input type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp"
                       value="${form.name}">
                <div id="nameHelp" class="form-text">Enter ingredient name.</div>

                <c:if test="${errors.hasFieldErrors('name')}">
                    <div style="color:red">
                        <c:forEach items="${errors.getFieldErrors('name')}" var="error">
                            ${error.defaultMessage}<br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-secondary">Submit</button>
                <c:if test="${form.id != null}"> <a href="/ingredient/add" class="btn btn-secondary  ">Add more</a> </c:if>
            </div>
        </form>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>