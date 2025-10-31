# 🧩 Product Service – Spring Boot

Service Spring Boot dont le cœur de métier est la **gestion des produits**, développé selon une architecture **MVC** et la méthode **TDD (Test-Driven Development)**.

---

## 🚀 Objectifs

* Mettre en place une base solide pour la gestion des produits.
* Structurer le projet selon le modèle **MVC** :
  **Model** (entités + repository) → **Service** (logique métier) → **Controller** (exposition API).
* Suivre un cycle de développement **TDD** : *Red → Green → Refactor*.

---

## ⚙️ Stack technique

**Langage :** Java 17+
**Framework :** Spring Boot
**Base de données :** MySQL
**Build tool :** Maven

### 🧱 Dépendances principales

* `spring-boot-starter-data-jpa` → gestion ORM / persistance
* `spring-boot-starter-web-services` → exposition des services
* `spring-boot-devtools` → rechargement à chaud
* `mysql-connector-j` → connecteur MySQL
* `spring-boot-starter-test` → outils de test (JUnit, Mockito)

---

## 🧪 Méthodologie TDD

1. **Écrire le test** avant la fonctionnalité (test rouge).
2. **Coder le minimum** pour faire passer le test (test vert).
3. **Refactoriser** le code en conservant tous les tests au vert.

Commandes utiles :

```bash
mvn test          # Exécuter les tests
mvn spring-boot:run   # Lancer l'application
```

---

## 📁 Structure du projet

```
src/
 ├─ main/
 │   └─ java/.../product/ → MVC (model, service, controller)
 └─ test/
     └─ java/.../product/ → tests unitaires et d’intégration
```
