
# 📗 Comprendre `@GeneratedValue` et les stratégies d’ID en JPA

---

## 1️⃣ Rôle général

`@GeneratedValue` indique à JPA **comment générer automatiquement** la valeur de la clé primaire marquée avec `@Id`.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

> Sans cette annotation, tu dois toi-même fournir la valeur du champ `id` avant tout `save()`.

---

## 2️⃣ Les quatre stratégies principales (`GenerationType`)

### 1️⃣ `IDENTITY`

* Utilise le mécanisme **auto-incrément** de la base de données (ex: MySQL `AUTO_INCREMENT`).
* L’ID est généré **par la base**, **après insertion**.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

💡 **Utilisation** :
→ MySQL, MariaDB, SQL Server.
Spring exécute un `INSERT`, puis récupère la clé générée.

---

### 2️⃣ `SEQUENCE`

* Utilise une **séquence SQL dédiée**.
* Hibernate appelle une séquence avant chaque `INSERT`.

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
@SequenceGenerator(name = "prod_seq", sequenceName = "product_seq", allocationSize = 1)
private Long id;
```

💡 **Utilisation** :
→ PostgreSQL, Oracle.
Rapide et efficace (pas besoin d’attendre l’insert pour connaître l’ID).

---

### 3️⃣ `TABLE`

* Crée une **table spéciale** qui stocke les prochains IDs à attribuer.

```java
@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "table_gen")
@TableGenerator(
    name = "table_gen",
    table = "id_gen",
    pkColumnName = "entity",
    valueColumnName = "next_val",
    allocationSize = 1
)
private Long id;
```

💡 **Utilisation** :
→ Cas multi-SGBD (portable), mais **moins performant** car nécessite des requêtes supplémentaires.

---

### 4️⃣ `AUTO` *(valeur par défaut)*

* Hibernate choisit automatiquement la meilleure stratégie selon le dialecte de la base.

```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
```

💡 **Utilisation** :
→ Pratique pour le développement, mais à **éviter en production** car le comportement dépend de la base.

---

## 3️⃣ Comparatif des stratégies

| Stratégie  | Géré par       | SGBD typique        | Performance      | Commentaire               |
| ---------- | -------------- | ------------------- | ---------------- | ------------------------- |
| `IDENTITY` | Base           | MySQL               | ⚡️ Rapide        | Simple et direct          |
| `SEQUENCE` | Hibernate + DB | PostgreSQL / Oracle | ⚡️⚡️ Très rapide | Optimisé pour séquences   |
| `TABLE`    | Hibernate      | Tous                | 🐢 Lent          | Portable mais peu utilisé |
| `AUTO`     | Hibernate      | Tous                | ⚙️ Variable      | Dépend du dialecte        |

---

## 4️⃣ Bonnes pratiques

| Cas                           | Recommandation            |
| ----------------------------- | ------------------------- |
| Projet MySQL / MariaDB        | `GenerationType.IDENTITY` |
| Projet PostgreSQL / Oracle    | `GenerationType.SEQUENCE` |
| Application portable multi-DB | `GenerationType.TABLE`    |
| En phase de test ou PoC       | `GenerationType.AUTO`     |

---

## 5️⃣ Attention aux erreurs fréquentes

❌ **Ne jamais définir manuellement l’ID** si `@GeneratedValue` est présent.
➡️ Cela peut provoquer une erreur du type :

> `org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction`

✅ Toujours laisser Hibernate / la base générer la clé.

---

## 6️⃣ Ressources officielles

📚 **Documentation officielle :**

* [Jakarta Persistence 3.1 – Generated Values](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#generatedvalue)
* [Hibernate ORM – Identifier Generation](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#identifiers)
* [Baeldung – Hibernate Identifier Strategies](https://www.baeldung.com/hibernate-identifiers)

---

## ✅ TL;DR

| Stratégie  | Description         | Cas d’usage          |
| ---------- | ------------------- | -------------------- |
| `IDENTITY` | Auto-incrément SQL  | MySQL                |
| `SEQUENCE` | Séquence dédiée     | PostgreSQL, Oracle   |
| `TABLE`    | Table spéciale d’ID | Multi-DB             |
| `AUTO`     | Choix automatique   | Tests, développement |

