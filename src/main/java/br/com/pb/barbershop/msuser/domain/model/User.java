package br.com.pb.barbershop.msuser.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String document;

    public User(@NonNull Long id, @NonNull String name, @NonNull String email, @NonNull String phone, @NonNull String document) {
        this.Id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phone = Objects.requireNonNull(phone);
        this.document = Objects.requireNonNull(document);

    }
}
