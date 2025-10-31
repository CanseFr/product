# 💡 Program to Interface — Bonne pratique dans Spring

---

## 1️⃣ Que signifie « Program to Interface » ?

**Program to Interface** signifie que vos classes **dépendent d’abstractions**, et non d’implémentations concrètes.

Autrement dit, au lieu de lier votre code à *la manière dont une action est réalisée*, vous le liez seulement à *ce qu’il est censé faire*.

```java
// ❌ MAUVAIS — dépend directement d’une classe concrète
ProductServiceImpl service = new ProductServiceImpl();
service.save(product);

// ✅ BON — dépend d’une interface
ProductService service = new ProductServiceImpl();
service.save(product);
```

➡️ Vous codez désormais en vous basant sur le **contrat** (`ProductService`), et non sur **l’implémentation concrète** (`ProductServiceImpl`).

---

## 2️⃣ Pourquoi c’est important (et pourquoi Spring adore ce principe)

Ce principe fait partie des **fondements de l’architecture propre** (*clean architecture*) et de la conception **SOLID**, plus précisément du **principe d’inversion de dépendance** (le “D” de SOLID) :

> « Les modules de haut niveau ne doivent pas dépendre des modules de bas niveau. Les deux doivent dépendre d’abstractions. »

Dans Spring :

* Le **conteneur IoC** (*ApplicationContext*) gère vos dépendances.
* Les **interfaces** permettent à Spring d’injecter *n’importe quelle implémentation* correspondant au contrat.
* Vos composants deviennent **faiblement couplés**, **plus faciles à tester** et **plus simples à remplacer**.

---

## 3️⃣ Exemple sans et avec interface

### ❌ Sans interface (fort couplage)

```java
@Service
public class OrderController {
    private final OrderServiceImpl orderService = new OrderServiceImpl();

    public void createOrder(Order order) {
        orderService.save(order);
    }
}
```

* Le contrôleur dépend directement d’une implémentation (`OrderServiceImpl`).
* Si vous changez la classe de service (par exemple pour une version mockée ou une autre base de données), vous devez **modifier le code du contrôleur**.

---

### ✅ Avec interface (faible couplage)

```java
public interface OrderService {
    void save(Order order);
}

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void save(Order order) {
        System.out.println("Commande sauvegardée : " + order);
    }
}

@RestController
public class OrderController {

    private final OrderService orderService;

    // Spring injecte automatiquement l’implémentation
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public void createOrder(@RequestBody Order order) {
        orderService.save(order);
    }
}
```

🧩 Ici :

* Le contrôleur ne connaît que **l’interface**, pas la classe concrète.
* Spring injecte **l’implémentation appropriée** au moment de l’exécution.
* Vous pouvez ensuite ajouter une nouvelle implémentation (`OrderServiceMock`, `OrderServiceV2`, etc.) sans modifier le contrôleur.

---

## 4️⃣ Comment Spring résout la dépendance

1️⃣ Au démarrage, Spring scanne les beans (classes annotées avec `@Service`, `@Repository`, `@Component`, `@Controller`).
2️⃣ Il détecte que `OrderServiceImpl` implémente `OrderService`.
3️⃣ Lorsqu’une autre classe déclare une dépendance sur `OrderService`, Spring sait **quelle implémentation injecter**.

S’il existe plusieurs implémentations possibles, vous pouvez préciser laquelle utiliser :

```java
@Autowired
@Qualifier("orderServiceImpl")
private OrderService orderService;
```

---

## 5️⃣ Les avantages de « programmer sur une interface »

| Avantage               | Description                                                                  |
| ---------------------- | ---------------------------------------------------------------------------- |
| 🧩 **Faible couplage** | Les composants ne dépendent pas de classes concrètes, seulement de contrats. |
| 🧪 **Tests facilités** | Vous pouvez injecter des mocks ou des stubs pour les tests.                  |
| 🔄 **Flexibilité**     | Changez d’implémentation sans modifier le code principal.                    |
| 🧱 **Évolutivité**     | Ajoutez de nouvelles fonctionnalités sans impacter les couches supérieures.  |
| 🧠 **Lisibilité**      | Les frontières de votre API sont plus claires et cohérentes.                 |

---

## 6️⃣ Exemple : l’avantage pour les tests

```java
// Implémentation fictive pour les tests
public class MockOrderService implements OrderService {
    @Override
    public void save(Order order) {
        System.out.println("Commande mockée sauvegardée !");
    }
}

@SpringBootTest
class OrderControllerTest {

    @Autowired
    private OrderController controller;

    @MockBean
    private OrderService orderService; // injecté automatiquement par Spring

    @Test
    void createOrder_shouldCallService() {
        Order order = new Order("Pizza");
        controller.createOrder(order);

        verify(orderService).save(order);
    }
}
```

✅ Le code du contrôleur **ne change pas du tout**, même lorsqu’on remplace le service réel par un mock.
C’est la **véritable puissance** du principe *Program to Interface*.

---

## 7️⃣ Points clés à retenir

| Principe                 | Signification                                              |
|--------------------------| ---------------------------------------------------------- |
| Program to Interface     | Dépendre du comportement (contrat) et non du code concret  |
| IoC & DI                 | Laisser Spring fournir l’implémentation adéquate           |
| Testabilité              | Pouvoir injecter facilement des mocks ou stubs             |
| Flexibilité              | Remplacer les implémentations sans modifier le code client |

---

## 8️⃣ Ressources recommandées

📚 **Sources officielles et articles de référence :**

* [Spring Framework — Concepts de l’injection de dépendances](https://docs.spring.io/spring-framework/reference/core/beans/dependencies.html)
* [Spring Guide — Comprendre l’injection de dépendances](https://spring.io/guides/gs/handling-form-submission/)
* [Baeldung — Program to an Interface en Java](https://www.baeldung.com/java-program-to-interface)
* [Wikipedia — Principes SOLID](https://fr.wikipedia.org/wiki/SOLID_%28informatique%29)

---

## ✅ En résumé (TL;DR)

* Injectez toujours des **interfaces**, jamais des **classes concrètes**.
* Laissez **Spring** choisir et injecter la bonne implémentation.
* Votre code devient **modulaire**, **testable** et **pérenne**.
