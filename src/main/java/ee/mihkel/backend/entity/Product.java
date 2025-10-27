package ee.mihkel.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String image;
    private boolean active;
//    private String category;

    // parem pool on kas siin on muutuja ees List<> või mitte
    // @OneToOne
    // @OneToMany
    // @ManyToOne
    // @ManyToMany
    @ManyToOne
    private Category category;
}
