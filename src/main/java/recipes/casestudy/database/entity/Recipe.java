package recipes.casestudy.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "recipe",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients)
    {
        this.recipeIngredients.addAll(recipeIngredients);
    }

}

