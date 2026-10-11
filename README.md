# TP 6 : Gestion des Conflits Concurrents & Verrouillage Optimiste @Version (Ikram Mellakh)

DÃ©monstration du verrouillage optimiste avec `@Version` :
- PrÃ©vention des pertes de mise Ã  jour (*Lost Updates*)
- DÃ©tection automatique de conflit de version par Hibernate
- Interception de l'exception `OptimisticLockException` et rollback propre

---

## Captures d'Ã‰cran IntelliJ IDEA & Terminal CMD

### 1. EntitÃ© avec Attribut @Version (IntelliJ IDEA)
![EntitÃ© Version](docs/images/01-intellij-entity-version.png)

### 2. Simulation de Conflit Multi-Session (IntelliJ IDEA)
![Simulation Concurrente](docs/images/02-intellij-concurrency-code.png)

### 3. RÃ©sultat des Tests et ExÃ©cution (CMD / PowerShell)
![RÃ©sultat ExÃ©cution CMD](docs/images/03-cmd-test-result.png)

---

## Lancement
```bash
mvn clean compile exec:java -Dexec.mainClass=ma.enset.ikram.tp6.App
```