package ma.fstt.atelier_2.entities;

import jakarta.persistence.*;  // ✅ JAVAX
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "internautes")
public class Internaute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @OneToOne(mappedBy = "internaute", cascade = CascadeType.ALL)
    private Panier panier;

    @OneToMany(mappedBy = "internaute", cascade = CascadeType.ALL)
    private List<Commande> commandes = new ArrayList<>();
}