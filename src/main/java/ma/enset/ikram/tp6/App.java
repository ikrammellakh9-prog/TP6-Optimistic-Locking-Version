package ma.enset.ikram.tp6;

import ma.enset.ikram.tp6.model.CreneauReservationConcurrent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  TP6 JPA - SIMULATION CONFLIT ET OPTIMISTIC LOCKING @VERSION");
        System.out.println("               (IKRAM MELLAKH - ENSET)                   ");
        System.out.println("==========================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ikramTP6_PU");
        EntityManager emInitial = emf.createEntityManager();

        Long creneauId = null;

        try {
            System.out.println("\n[1] Initialisation de l'entite en base de donnees");
            emInitial.getTransaction().begin();
            CreneauReservationConcurrent creneau = new CreneauReservationConcurrent("CREN-AMPHI-01", "Amphitheatre Al Khawarizmi", 50);
            emInitial.persist(creneau);
            emInitial.getTransaction().commit();
            creneauId = creneau.getId();
            System.out.println("-> Creneau insere avec succes (ID=" + creneauId + ", Version=" + creneau.getVersion() + ")");
        } finally {
            emInitial.close();
        }

        System.out.println("\n[2] Simulation de deux transactions concurrentes (Session A et Session B)");
        EntityManager emUserA = emf.createEntityManager();
        EntityManager emUserB = emf.createEntityManager();

        try {
            // Deux utilisateurs chargent la meme entite avec version=0
            emUserA.getTransaction().begin();
            CreneauReservationConcurrent entityA = emUserA.find(CreneauReservationConcurrent.class, creneauId);
            System.out.println("-> Session A a charge l'entite avec Version=" + entityA.getVersion() + " (Places dispo: " + entityA.getPlacesDisponibles() + ")");

            emUserB.getTransaction().begin();
            CreneauReservationConcurrent entityB = emUserB.find(CreneauReservationConcurrent.class, creneauId);
            System.out.println("-> Session B a charge l'entite avec Version=" + entityB.getVersion() + " (Places dispo: " + entityB.getPlacesDisponibles() + ")");

            // L'utilisateur A reserve 10 places et commit
            System.out.println("\n[3] Session A reserve 10 places et COMMIT");
            entityA.setPlacesDisponibles(entityA.getPlacesDisponibles() - 10);
            emUserA.getTransaction().commit();
            System.out.println("-> Session A COMMITTE avec succes. Version incremente a " + entityA.getVersion());

            // L'utilisateur B tente de reserver 15 places sur sa version obsolete
            System.out.println("\n[4] Session B tente de reserver 15 places et COMMIT (Version obsolete attendue)");
            entityB.setPlacesDisponibles(entityB.getPlacesDisponibles() - 15);

            try {
                emUserB.getTransaction().commit();
                System.out.println("ERREUR : Le verrouillage optimiste n'a pas bloque le conflit !");
            } catch (Exception ex) {
                System.out.println("-> SUCCESS : OptimisticLockException interceptÃ©e par Hibernate !");
                System.out.println("   * Cause : " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
                if (emUserB.getTransaction().isActive()) {
                    emUserB.getTransaction().rollback();
                    System.out.println("-> Transaction B annulee proprement (Rollback)");
                }
            }

        } finally {
            emUserA.close();
            emUserB.close();
        }

        // Verification finale
        EntityManager emVerif = emf.createEntityManager();
        try {
            CreneauReservationConcurrent finalEntity = emVerif.find(CreneauReservationConcurrent.class, creneauId);
            System.out.println("\n[5] Etat final en base de donnees :");
            System.out.println("   * Version finale : " + finalEntity.getVersion());
            System.out.println("   * Places restantes : " + finalEntity.getPlacesDisponibles() + " (seule la reservation de A a ete appliquee)");
        } finally {
            emVerif.close();
            emf.close();
            System.out.println("\n==========================================================");
            System.out.println("  EXECUTION DU TP6 TERMINEE AVEC SUCCES (BUILD SUCCESS)");
            System.out.println("==========================================================");
        }
    }
}