package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Setter
@Getter
@Data
@Entity
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;


    @ManyToMany(mappedBy = "categories")
    // we need to assert on both sides of recipe and category to make it into one table
    // otherwise hibernate is going to give us 2 tables named recipe_cate and cate_recipe
    private Set<Recipe> recipes;

    //one recipe can belong to multiple categories and vice versa
}
