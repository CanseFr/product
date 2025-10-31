# 1) `@SpringBootTest`

**Ce que c’est**
Charge **tout** le contexte Spring Boot (config, web, JPA, etc.).
**Pourquoi ici**
Tu veux rester sur ta stack telle quelle (MySQL local, vraies propriétés). Pas de base embarquée ni de slice minimaliste.
**Utilité**

* Teste l’intégration “réelle” de ta couche JPA avec le reste de l’app.
* Détecte des soucis de configuration Spring, pas seulement du code métier.

> ⚠️ Plus lent que `@DataJpaTest`, mais fidèle à “ce qui tourne chez toi”.

---

# 2) `@AutoConfigureTestDatabase(replace = Replace.NONE)`

**Ce que c’est**
Demande à Spring **de ne pas remplacer** la DataSource par une DB embarquée pendant les tests.
**Pourquoi ici**
Par défaut, Spring essaye d’injecter une DB embarquée (H2). Chez toi, on garde **MySQL local**.
**Utilité**

* Évite l’erreur “Failed to replace DataSource with an embedded database”.
* Garantit que les tests utilisent **exactement** ta config MySQL (URL, user, mot de passe, etc.).

---

# 3) `@ActiveProfiles("test")`

**Ce que c’est**
Active le **profil Spring** `test`.
**Pourquoi ici**
Permet d’avoir un fichier `src/test/resources/application-test.properties` avec **tes propriétés MySQL** (ou des variantes spécifiques aux tests) sans toucher `application.properties` de prod/dev.
**Utilité**

* Sépare proprement la config de test du reste.
* Tu peux y mettre, par exemple :

  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/spring_DB?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
  spring.datasource.username=root
  spring.datasource.password=Rootoorn
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.sql.init.mode=never
  spring.test.database.replace=none
  ```

---

# 4) `@Transactional` (au niveau de la classe de test)

**Ce que c’est**
Chaque test s’exécute **dans une transaction** démarrée avant le test et **close** après.
**Comportement par défaut en test**
Avec Spring Test, une méthode de test **transactionnelle** est **rollbackée par défaut** à la fin, sauf si on met `@Commit`.
**Pourquoi ici**

* On veut **isoler** chaque test : pas de pollution de la base d’un test à l’autre.
  **Utilité**
* Tu peux créer/modifier/supprimer librement → à la fin du test, **retour à l’état initial**.
* Plus de tests dépendants entre eux.

> ℹ️ J’ai aussi ajouté `@Rollback` sur les méthodes pour **rendre l’intention explicite** (même si `@Transactional` en test rollback déjà par défaut).

---

# 5) `@Rollback`

**Ce que c’est**
Force (ou documente) le **rollback** de la transaction de test.
**Pourquoi ici**
Rendre le comportement **visible** et éviter les surprises si quelqu’un change la politique de transaction plus tard.
**Utilité**

* Zéro état résiduel en base après chaque test, même si tu utilises MySQL “pour de vrai”.

---

# 6) `@PersistenceContext` + `EntityManager` + `flushAndClear()`

**Ce que c’est**

* `EntityManager` = API JPA bas niveau.
* `em.flush()` = force l’écriture SQL **immédiate** des changements pendants.
* `em.clear()` = vide le **contexte de persistance** (cache de premier niveau).
  **Pourquoi ici**
  Après un `save`, si tu relis le même objet dans le même contexte, tu peux **lire depuis le cache** (et non la DB).
  **Utilité**
* Avec `flushAndClear()`, quand tu refais un `findById`, tu relis **depuis la base** → tu valides la **persistance réelle**.
* Indispensable pour des tests d’**update** fiables.

Exemple utilisé :

```java
private void flushAndClear() {
  em.flush();  // force les SQL
  em.clear();  // évite le cache de 1er niveau
}
```

---

# 7) **Semer des données** et **ne jamais coder `id=1L`**

**Ce que c’est**
Chaque test **crée ses propres données** (un “seed”), récupère l’**id généré**, puis travaille avec.
**Pourquoi ici**

* `@GeneratedValue` rend l’ID **inconnu** à l’avance.
* Coder `1L` rend le test **fragile** (il dépend de l’état de la base).
  **Utilité**
* Tests **indépendants** et **déterministes**.
* Pas d’effet de bord entre tests.

---

# 8) Assertions JUnit5 / AssertJ

**Ce que c’est**

* `assertNotNull`, `assertEquals`, `assertTrue`, etc.
* Finis les `System.out.println()` (bruit) : on **vérifie** un résultat attendu.
  **Pourquoi ici**
  Un test qui “n’explose pas” n’est pas forcément **juste**. On veut confirmer un **comportement précis**.
  **Utilité**
* Tu sais **ce que tu valides** (création OK, update bien persisté, suppression effective…).
* Les régressions seront détectées **immédiatement**.

> Pour les `double`, j’ai mis une **tolérance** : `assertEquals(2000.00, value, 0.0001)` pour éviter les surprises d’arrondi.

---

# 9) `count()` et vérifs sur des listes

**Ce que c’est**

* `count()` pour comparer le nombre d’éléments avant/après.
* Tests sur `findAll()` avec `anyMatch` pour vérifier que les entités créées **sont bien là**.
  **Pourquoi ici**
  Valider non seulement qu’on a **persisté**, mais que la **requête standard** (findAll) les **retrouve**.
  **Utilité**
* Couverture un peu plus large de la couche repository.

---

# 10) Structure des tests (pattern AAA)

**Ce que c’est**

* **Arrange** (préparer) → **Act** (agir) → **Assert** (vérifier).
  **Pourquoi ici**
  Rend le test **lisible**, **prévisible**, et limite les surprises.
  **Utilité**
* Tu sais exactement **où** chercher si quelque chose casse.
* Facilite l’évolution des tests.

---

# 11) Choix “MySQL local” vs alternatives (pour plus tard)

**Ton choix actuel**

* Rester **100% local** sur MySQL → OK, fiable dans ton contexte.
  **Alternatives futures**
* `@DataJpaTest` + H2/embedded : **rapide**, isolé, mais différent d’un MySQL réel.
* **Testcontainers** (MySQL/MariaDB) : DB jetable **réelle**, reproductible, CI-friendly.

---

# 12) Petites bonnes pratiques autour de l’entité

* `@GeneratedValue(strategy = IDENTITY)` + **ne jamais setter l’`id`** toi-même.
* Remplacer `Date` par `LocalDateTime` (Java Time) quand tu pourras.
* Si tu veux un timestamp auto :

  ```java
  @CreationTimestamp
  private LocalDateTime dateCreated;
  ```
* Si un jour tu fais de l’optimistic locking :

  ```java
  @Version
  private Long version;
  ```

---

# 13) Résumé ultra-court

* **`@SpringBootTest`** : teste avec **ta vraie app**.
* **`@AutoConfigureTestDatabase(replace = NONE)`** : garde **MySQL**.
* **`@ActiveProfiles("test")`** : config dédiée aux tests.
* **`@Transactional` + `@Rollback`** : **isolation**, DB propre après chaque test.
* **`EntityManager.flush/clear`** : vérifie l’état **réel** en DB (pas le cache).
* **Semer les données / pas d’ID en dur** : tests **fiables**.
* **Assertions** : on **prouve** le résultat attendu (pas de `println`).
