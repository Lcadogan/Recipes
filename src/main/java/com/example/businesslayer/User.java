package com.example.businesslayer;


import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table (name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column (name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "email")
    @NonNull
    @NotBlank
    @Email
    private String email;

    @Column (name = "password")
    @NonNull
    @NotBlank
    @Size(min = 8)
    private String password;

    @Column (name = "role")
    private String role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && email.equals(user.email) && password.equals(user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, role);
    }
}
