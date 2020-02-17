package guru.springframework.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //The primary key of the first entity object in the database is 1, the primary key of the second entity object is 2, etc
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private Byte[] image;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    //mappedBy will be assigned the target object in ingredient class
    private Set<Ingredient> ingredients = new HashSet<>(); // note here we created a new

    @OneToOne(cascade = CascadeType.ALL)
    // this defines the relationship and all operation on recipe will propagated to notes!!!
    private Notes notes;

    @Enumerated(value = EnumType.STRING)
    //if we set as ordinal then easy, moderate, hard, when we insert kind_of_hard, the hard will
    // be assigned value 4 instead of 3
    // when we use string, then it does not change
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
            // the name of the joined table is recipe_category
    // in this table we will use recipe id, on the other side we join on category_id
    //note taht we assign a hashset here to aviod nullpointer exception
    private Set<Category> categories = new HashSet<>();


    // below are the 2 helper method that defines the bidirectional relations
    public void setNotes(Notes notes){
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredients(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
