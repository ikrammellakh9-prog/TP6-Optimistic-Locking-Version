# TP 6 : Gestion des AccÃ¨s Concurrents & Verrouillage Optimiste (@Version) avec JPA / Hibernate

ImplÃ©mentation et dÃ©monstration du **verrouillage optimiste** (*Optimistic Locking*) via l'annotation @Version sous **JPA 2.2** et **Hibernate ORM 5.6**, garantissant la cohÃ©rence des transactions concurrentes en Ã©vitant les pertes de mise Ã  jour (*Lost Updates*).

RÃ©alisÃ© par : **Ikram Mellakh**

---

## 1. Concepts ClÃ©s et Architecture

1. **Annotation @Version** : Hibernate injecte automatiquement une vÃ©rification de version dans la clause WHERE des requÃªtes UPDATE (WHERE id=? AND num_version=?).
2. **DÃ©tection de Conflit d'AccÃ¨s Concurrent** : Si une autre transaction a modifiÃ© l'enregistrement entre-temps, le compteur de version est incrÃ©mentÃ©, provoquant un nombre de lignes mises Ã  jour Ã©gal Ã  0.
3. **Interception d'Exception** : Hibernate lÃ¨ve immÃ©diatement une OptimisticLockException (encapsulÃ©e dans une RollbackException), permettant d'effectuer un rollback propre ou une stratÃ©gie de rÃ©essai (retry).

---

## 2. Captures d'Ã‰cran et Preuves d'ExÃ©cution

### EntitÃ© avec Verrouillage Optimiste (@Version)
![Entite avec Version](docs/images/01-entity-version.png)

### Simulation Concurrente et Gestion d'Exception
![Simulation Concurrente](docs/images/02-optimistic-locking-code.png)

### Trace d'ExÃ©cution ComplÃ¨te (PowerShell Terminal)
![Trace Terminal](docs/images/03-execution-cmd.png)

---

## 3. Commandes de Compilation et ExÃ©cution

``bash
mvn clean compile
mvn exec:java "-Dexec.mainClass=ma.enset.ikram.tp6.App"
``