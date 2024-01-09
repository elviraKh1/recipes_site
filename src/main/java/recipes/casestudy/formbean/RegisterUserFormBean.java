package recipes.casestudy.formbean;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class RegisterUserFormBean {


    @NotEmpty(message = "Email message can not be empty")
    @Email(message = "Email must be a valid address")
    private String email;

    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Confirm Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String confirmPassword;

    private String name;
}
