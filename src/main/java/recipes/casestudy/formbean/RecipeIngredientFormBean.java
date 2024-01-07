package recipes.casestudy.formbean;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientFormBean {

    private Integer id;

//    @NotEmpty(message = "Name can not be empty")
    private String name;

//    @NotEmpty(message = "Measure can not be empty")
    private String measure;

    @Override
    public String toString() {
        return "RecipeIngredientFormBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }
}
