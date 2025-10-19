package ma.fstt.atelier_2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "paniers")
public class Panier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "internaute_id", unique = true)
    private Internaute internaute;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    // Méthodes utilitaires
    public Double getTotal() {
        return lignes.stream()
                .mapToDouble(LignePanier::getSousTotal)
                .sum();
    }

    public void setDateCreation(Date date) {
    }

    // Constructeurs, getters et setters
}