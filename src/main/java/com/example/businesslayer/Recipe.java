package com.example.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table (name = "recipeBook")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @Column (name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User author;

    @Column (name = "name")
    @NotNull
    @NotBlank
    private String name;

    @Column (name = "category")
    @NotNull
    @NotBlank
    private String category;

    @Column (name = "date")
    private LocalDateTime date;


    @Column (name = "description")
    @NotNull
    @NotBlank
    private String description;


    @Column (name = "ingredients")
    @ElementCollection
    @Size(min = 1)
    private List<String> ingredients = new ArrayList<>();


    @Column (name = "directions")
    @ElementCollection
    @Size(min = 1)
    private List<String> directions =  new ArrayList<>();

}
