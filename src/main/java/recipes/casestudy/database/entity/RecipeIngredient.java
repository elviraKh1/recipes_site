package recipes.casestudy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "measure")
    private String measure;

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", recipe=" + recipe +
                ", ingredient=" + ingredient +
                ", measure='" + measure + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient recipeIngredient  = (RecipeIngredient) o;
        return recipeIngredient.ingredient.equals(ingredient) && recipeIngredient.recipe.equals(recipe)  && recipeIngredient.measure.equals(measure)  ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe,ingredient);
    }
}