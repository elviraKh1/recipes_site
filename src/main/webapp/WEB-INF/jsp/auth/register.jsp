<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">User Registration</h2>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container">

        <c:if test="${not empty success}">
            <div class="row justify-content-center">
                <div class="col-5 pb-15 alert alert-success text-center">
                        ${success}
                </div>
            </div>
        </c:if>
        <div class="row justify-content-center">
            <div class="col-5 pb-15  " style="background-color: #f1f1f1;">
                <form method="GET" action="/auth/registerSubmit">

                    <div class="col-lg-12 col-md-6">
                        <label for="email" class="form-label">Email</label>
                        <input type="text" class="form-control" id="email" name="email" aria-describedby="emailNameHelp"
                               value="${form.email}" placeholder="Your email">
                        <c:if test="${errors.hasFieldErrors('email')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" aria-describedby="nameNameHelp"
                               value="${form.name}" placeholder="Your name">
                        <c:if test="${errors.hasFieldErrors('name')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('name')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password"
                               aria-describedby="passwordHelp"
                               value="${form.password}" placeholder="Your password">
                        <c:if test="${errors.hasFieldErrors('password')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="confirmPassword" class="form-label">Password</label>
                        <input type="text" class="form-control" id="confirmPassword" name="confirmPassword"
                               aria-describedby="confirmPassworddHelp" placeholder="Confirm password"
                               value="${form.confirmPassword}">
                        <c:if test="${errors.hasFieldErrors('confirmPassword')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('confirmPassword')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="text-center pb-3">
                        <button type="submit" class="btn btn-secondary mt-4  ">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>