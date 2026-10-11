package ma.enset.ikram.tp6.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "creneaux_concurrents")
public class CreneauReservationConcurrent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "num_version")
    private Long version;

    @Column(name = "code_creneau", nullable = false, unique = true, length = 30)
    private String codeCreneau;

    @Column(name = "nom_salle", nullable = false)
    private String nomSalle;

    @Column(name = "places_disponibles", nullable = false)
    private int placesDisponibles;

    @Column(name = "statut")
    private String statut = "OUVERT";

    public CreneauReservationConcurrent() {}
    public CreneauReservationConcurrent(String codeCreneau, String nomSalle, int placesDisponibles) {
        this.codeCreneau = codeCreneau;
        this.nomSalle = nomSalle;
        this.placesDisponibles = placesDisponibles;
        this.statut = "OUVERT";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public String getCodeCreneau() { return codeCreneau; }
    public void setCodeCreneau(String codeCreneau) { this.codeCreneau = codeCreneau; }
    public String getNomSalle() { return nomSalle; }
    public void setNomSalle(String nomSalle) { this.nomSalle = nomSalle; }
    public int getPlacesDisponibles() { return placesDisponibles; }
    public void setPlacesDisponibles(int placesDisponibles) { this.placesDisponibles = placesDisponibles; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}