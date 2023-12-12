package recipes.casestudy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Column(name = "recipe_id")
    private Integer recipeId;

    @Column(name = "measurement")
    private String measurement;
}