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
                        <label for="category" class="form-label">Category </label>
                        <select class="form-control" id="category" name="category" aria-describedby="categoryNameHelp">
                            <option value="Breakfast" <c:if test="${form.category eq 'Breakfast'}">selected</c:if>>
                                Breakfast
                            </option>
                            <option value="Lunch" <c:if test="${form.category eq 'Lunch'}">selected</c:if>>Lunch
                            </option>
                            <option value="Dinner" <c:if test="${form.category eq 'Dinner'}">selected</c:if>>Dinner
                            </option>
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
                        <img src="${form.imageUrl}" style="width: 280px;">
                    </div>
                    <div class="col-lg-12 col-md-6">
                        <label for="image_url" class="form-label">image</label>
                        <input type="file" class="form-control" id="image_url" name="imageFile"
                               aria-describedby="imageUrlHelp">
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
                        <c:if test="${errors.hasFieldErrors('ingredientsInp')}">
                            <div style="color:red">
                                <c:forEach items="${errors.getFieldErrors('ingredientsInp')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:forEach items="${form.getIngredientsInp()}" var="ingredientFild" varStatus="loopStatus">
                            <div>
                                <input type="text" name="ingredientsInp[${loopStatus.index}].name" placeholder="Ingredient name" class="__ingredient ui-autocomplete-input" autocomplete="off" value="${ingredientFild.name}">
                                <input type="hidden" id="ingredientId_${loopStatus.index}" name="ingredientsInp[${loopStatus.index}].id" value="${ingredientFild.id}">
                                <input type="text" name="ingredientsInp[${loopStatus.index}].measure" placeholder="Ingredient measure" value="${ingredientFild.measure}">
                                <button id="reqsr${loopStatus.index}" type="button"  onclick="removeElement(event)">Remove ${loopStatus.index}</button>
                            </div>
                        </c:forEach>

                    </div>
                    <button type="button" onclick="addFields()">add ingredient</button>

                    <div class="text-center pb-3">
                        <button type="submit" class="btn btn-secondary  ">Save</button>
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