package ru.skypro.socks_app.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "sock")
public class Sock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(nullable = false)
    private int cottonPart;

    @Column(nullable = false)
    private int quantity;
}
