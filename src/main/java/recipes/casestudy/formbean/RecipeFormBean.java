package recipes.casestudy.formbean;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeFormBean {

    private Integer id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    //    @Length(min = 8, message = "Password must be at least 8 characters long")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    @NotEmpty(message = "instructions can not be empty")
    private String instructions;

    //    @Length(min = 8, message = "Password must be at least 8 characters long")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Confirm Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String category;

    private String imageUrl;

    @Override
    public String toString() {
        return "RecipeFormBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
