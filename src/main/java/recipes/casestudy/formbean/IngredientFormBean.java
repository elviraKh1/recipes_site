package recipes.casestudy.formbean;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientFormBean {

    private Integer id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

}

