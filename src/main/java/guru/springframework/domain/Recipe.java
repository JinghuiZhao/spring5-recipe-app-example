package guru.springframework.domain;

/*

Every entity object in the database is uniquely identified (and can be retrieved
from the database) by the combination of its type and its primary key.

Primary key values are unique per entity class. Instances of different entity classes,
however, may share the same primary key value.

Only entity objects have primary keys. Instances of other persistable types are always
 stored as part of their containing entity objects and do not have their own separate identity.
 */

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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
    //private Difficulty difficulty;
    private Byte[] image;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    //mappedBy will be assigned the target object in ingredient class
    private Set<Ingredient> ingredients = new HashSet<>(); // note here we created a new

    @OneToOne(cascade = CascadeType.ALL)
    // this defines the relationship and all operatiosn on recope will propogated to notes!!!
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }



}
