# 📘 Lien entre `@Entity`, `@Id`, et JPA dans Spring Boot

*(sans la partie `@GeneratedValue`, qui sera dans la doc suivante)*

---

## 1️⃣ Contexte général : JPA et Spring Boot

Spring Boot intègre **JPA (Java Persistence API)** pour simplifier la gestion de la persistance des données entre tes **objets Java** et une **base de données relationnelle**.

Grâce à cela :

* tu définis des classes comme **entités persistantes** ;
* Spring Boot se charge de la **connexion**, de la **création du schéma** et du **mapping objet–relationnel** via **Hibernate** (implémentation de JPA).

---

## 2️⃣ Dépendances nécessaires (Maven)

```xml
<dependencies>
    <!-- JPA et Hibernate -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Pilote JDBC pour MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
</dependencies>
```

### 🔍 Ce qu’elles apportent :

| Dépendance                       | Rôle                                                  |
| -------------------------------- | ----------------------------------------------------- |
| **spring-boot-starter-data-jpa** | Inclut Spring Data JPA + Hibernate                    |
| **mysql-connector-j**            | Permet la communication JDBC entre Hibernate et MySQL |

💬 Spring Boot configure automatiquement :

* le `DataSource` (connexion à la base),
* le `EntityManagerFactory` (gestion des entités),
* et le `TransactionManager` (gestion des transactions).

---

## 3️⃣ L’annotation `@Entity`

```java
@Entity
public class Product {
    ...
}
```

### 🔹 Rôle

Marque une classe comme **entité JPA**, c’est-à-dire :

* que ses instances seront **persistées en base de données** ;
* et que chaque attribut correspondra à une **colonne SQL**.

### 🔹 Effet dans Spring Boot

Lors du démarrage :

* Hibernate **scanne** le classpath à la recherche des classes annotées `@Entity` ;
* puis **crée la table correspondante** (si `spring.jpa.hibernate.ddl-auto` est activé).

> Sans `@Entity`, Hibernate ignore complètement la classe : elle ne sera pas persistée.

---

## 4️⃣ L’annotation `@Id`

```java
@Id
private Long id;
```

### 🔹 Rôle

`@Id` indique **quelle propriété** sert de **clé primaire** dans la table correspondante.
C’est **obligatoire** dans toute entité JPA.

* Elle identifie **de manière unique** chaque instance de la table.
* JPA s’en sert pour savoir si un objet doit être **inséré**, **mis à jour** ou **supprimé**.

### 🔹 Effet en base

Lors du `save()` :

* Si l’`@Id` est **null**, Hibernate effectue un **INSERT**.
* Si l’`@Id` a une valeur existante, Hibernate effectue un **UPDATE**.

---

## 5️⃣ Circuit de fonctionnement interne dans Spring Boot

```
┌──────────────────────────────┐
│ application.properties        │
│ spring.datasource.url         │
│ spring.jpa.hibernate.ddl-auto │
└──────────────┬───────────────┘
               │
               ▼
   ┌──────────────────────────────┐
   │ Hibernate (ORM / Impl JPA)   │
   │ - Scanne les @Entity         │
   │ - Crée les tables SQL        │
   │ - Mappe colonnes ↔ attributs │
   └──────────────┬───────────────┘
                  │
                  ▼
        ┌────────────────────────────┐
        │ EntityManagerFactory       │ ← créé par Spring Boot
        └────────────┬───────────────┘
                     │
                     ▼
          ┌────────────────────────┐
          │ Repository (interface) │ ← utilise EntityManager
          │ ProductRepository      │
          └────────────┬───────────┘
                       │
                       ▼
           ┌────────────────────────┐
           │ Product Entity          │ ← @Entity + @Id
           └────────────────────────┘
```

---

## 6️⃣ Bonnes pratiques

| Cas                               | Bonne pratique                                 |
| --------------------------------- | ---------------------------------------------- |
| Une classe mappée à une table     | Toujours annoter avec `@Entity`                |
| Une clé primaire unique           | Toujours déclarer `@Id`                        |
| Plusieurs identifiants composites | Utiliser `@EmbeddedId` ou `@IdClass`           |
| Besoin de gérer l’auto-incrément  | Utiliser `@GeneratedValue` *(voir doc dédiée)* |

---

## 7️⃣ Ressources utiles

📚 **Documentation officielle :**

* [Jakarta Persistence – Entities](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#entities)
* [Spring Data JPA Reference – Entity Mapping](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-persistence)
* [Hibernate ORM User Guide – Entity Basics](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#entity)

---

## ✅ TL;DR

| Annotation        | Rôle                                      | Géré par             |
| ----------------- | ----------------------------------------- | -------------------- |
| `@Entity`         | Marque la classe comme entité persistante | JPA / Hibernate      |
| `@Id`             | Définit la clé primaire de la table       | JPA                  |
| `@GeneratedValue` | (Voir doc dédiée)                         | Hibernate ou la base |

---
