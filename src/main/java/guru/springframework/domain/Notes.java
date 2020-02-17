package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
//generate the equals and hashCode methods automatically
//exclude marks a field so that Lombok doesn’t use that field otherwise java.lang.StackOverflowError
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    //we want more than 250 characters in JPA
    private String recipeNotes;

}
