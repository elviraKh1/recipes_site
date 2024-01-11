async function setBookmark(recipeId) {
    let xml = new XMLHttpRequest();
    xml.open('GET', '/recipe/bookmark?recipeId=' + recipeId, true);
    xml.onload = function () {
        if (this.status == 200) {
            if (this.readyState == 4 && this.status == 200) {
                const obj = JSON.parse(this.responseText);
                const icon = document.querySelector(`#bookmark${obj.recipe.recipeId}`)
                if (obj.recipe.bookmark){
                    icon.classList.add("fa-bookmark");
                    icon.classList.remove("fa-bookmark-o");
                }else {
                    icon.classList.remove("fa-bookmark");
                    icon.classList.add("fa-bookmark-o");
                }
            }
        }
    }
    xml.send();
}
