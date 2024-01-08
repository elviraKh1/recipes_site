<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h2 class="m-0">Create Recipe</h2>
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
        <div class="row justify-content-center">
            <div class="col-8 pb-19 " style="background-color: #f1f1f1;">
                <form method="post" action="/recipe/submit" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${form.id}">
                    <div class="col-lg-12 col-md-6">
                        <label for="name" class="form-label">name</label>
                        <input type="text" class="form-control" id="name" name="name" aria-describedby="recipeNameHelp"
                               value="${form.name}">
                        <div id="recipeNameHelp" class="form-text">Enter recipe name.</div>

                        <c:if test="${errors.hasFieldErrors('name')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('name')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="category" class="form-label"  >Category  </label>
                        <select  class="form-control" id="category" name="category" aria-describedby="categoryNameHelp">
                            <option value="Breakfast"  <c:if test="${form.category eq 'Breakfast'}" >selected</c:if>>Breakfast</option>
                            <option value="Lunch" <c:if test="${form.category eq 'Lunch'}" >selected</c:if>>Lunch</option>
                            <option value="Dinner" <c:if test="${form.category eq 'Dinner'}" >selected</c:if>>Dinner</option>
                        </select>

                        <div id="categoryNameHelp" class="form-text">Choose category name.</div>

                        <c:if test="${errors.hasFieldErrors('category')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('category')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-lg-12 col-md-6">
                        <label for="instructions" class="form-label">instructions</label>
                        <textarea id="instructions" class="form-control" name="instructions" rows="15"
                                  placeholder="instructions"
                                  aria-describedby="instructionsHelp">${form.instructions}</textarea>

                        <div id="instructionsHelp" class="form-text">enter instructions.</div>
                        <c:if test="${errors.hasFieldErrors('instructions')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('instructions')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-lg-12 col-md-6">
                        <img src="${form.imageUrl}"    style="width: 280px;">
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="image_url" class="form-label">image</label>
                        <input type="file" class="form-control" id="image_url" name="imageFile"
                               aria-describedby="imageUrlHelp" >
                        <div id="imageUrlHelp" class="form-text">image_url</div>
                        <c:if test="${errors.hasFieldErrors('imageFile')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('imageFile')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-lg-12 col-md-6" id="formFields">
                        <label for="ingredientsInp" class="form-label">ingredients</label>
                        <c:if test="${errors.hasFieldErrors('ingredientsInp')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('ingredientsInp')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:forEach items="${form.getIngredientsInp()}" var="ingredientFild" varStatus="loopStatus">
                            <div class="row gy-4">
                                    <%--                                <label for="ingredient" class="form-label">ingredient ${loopStatus.index}</label>--%>
                                <div class="col-lg-4 col-md-5">
                                    <input type="text" class="form-control __ingredient" id="ingredient"
                                           name="ingredientsInp[${loopStatus.index}].name"
                                           aria-describedby="nameHelp" value="${ingredientFild.name}">
                                    <div id="nameHelp" class="form-text">enter ingredient name min 3 character.</div>
                                </div>
                                <div class="col-lg-4 col-md-6">
                                    <input type="text" class="form-control"
                                           name="ingredientsInp[${loopStatus.index}].measure"
                                           aria-describedby="measureHelp" value="${ingredientFild.measure}">
                                    <div id="measureHelp" class="form-text">enter ingredient measure.</div>
                                    <input id="ingredientId_${loopStatus.index}" type="hidden" class="form-control"
                                           name="ingredientsInp[${loopStatus.index}].id" value="${ingredientFild.id}">
                                </div>
                                <div class="col-lg-4 col-md-6">
                                    <button type="button" class="btn btn-secondary" onclick="removeElement(event)"
                                            id="reqsr${loopStatus.index}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                             fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
                                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <button type="button" onclick="addFields()">add ingredient</button>


                    <div class="text-center pb-3">
                        <button type="submit" class="btn btn-secondary mt-4  ">Submit</button>
                        <c:if test="${form.id != null}">
                            <a href="/recipe/detail/?id=${form.id}"
                               class="btn btn-secondary  ">View</a> </c:if>
                    </div>

                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>