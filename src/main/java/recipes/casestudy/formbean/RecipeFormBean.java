package recipes.casestudy.formbean;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import recipes.casestudy.annotations.MultipartFileSizeValidation;

import java.util.List;

@Getter
@Setter
public class RecipeFormBean {

    private Integer id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @Length(min = 8, message = "Instructions must be at least 8 characters long")
    @NotEmpty(message = "instructions can not be empty")
    private String instructions;

    private String category;

    private String imageUrl;

    @MultipartFileSizeValidation(fileSize = 300050, message = "File too Large. The field imageFile exceeds its maximum permitted size of 300050 bytes")
    private MultipartFile imageFile;

    private List<RecipeIngredientFormBean> ingredientsInp;

    @Override
    public String toString() {
        return "RecipeFormBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ingredientsInp=" + ingredientsInp +
                '}';
    }
}
