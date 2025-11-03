package com.canse.product;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.repos.CategoryRepository;
import com.canse.product.repos.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class CategoryApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    private void flushAndClear() {
        em.flush();
        em.clear();
    }

    @Test
    @Rollback
    void create_shouldPersistAndAssignId() {
        Category category = Category.builder()
                .name("cat-1")
                .description("desc-1")
                .build();

        Category saved = categoryRepository.save(category);

        assertNotNull(saved.getId(), "L'ID doit être généré");
        assertTrue(categoryRepository.findById(saved.getId()).isPresent(), "La catégorie doit exister en DB");
    }

    @Test
    @Rollback
    void findById_shouldReturnEntity() {
        Category seed = categoryRepository.save(
                Category.builder()
                        .name("cat-2")
                        .description("desc-2")
                        .build()
        );
        Long id = seed.getId();

        Category found = categoryRepository.findById(id).orElseThrow();
        assertEquals("cat-2", found.getName());
        assertEquals("desc-2", found.getDescription());
    }

    @Test
    @Rollback
    void update_shouldChangeName() {
        Category seed = categoryRepository.save(
                Category.builder()
                        .name("old-name")
                        .description("d")
                        .build()
        );
        Long id = seed.getId();

        Category toUpdate = categoryRepository.findById(id).orElseThrow();
        toUpdate.setName("new-name");
        categoryRepository.save(toUpdate);

        flushAndClear();

        Category reloaded = categoryRepository.findById(id).orElseThrow();
        assertEquals("new-name", reloaded.getName());
    }

    @Test
    @Rollback
    void delete_shouldRemoveEntity() {
        Category seed = categoryRepository.save(
                Category.builder()
                        .name("to-delete")
                        .description("d")
                        .build()
        );
        Long id = seed.getId();

        categoryRepository.deleteById(id);
        flushAndClear();

        assertTrue(categoryRepository.findById(id).isEmpty(), "La catégorie doit être supprimée");
    }

    @Test
    @Rollback
    void findAll_shouldReturnAddedItems() {
        long before = categoryRepository.count();

        Category c1 = categoryRepository.save(
                Category.builder().name("A").description("a").build()
        );
        Category c2 = categoryRepository.save(
                Category.builder().name("B").description("b").build()
        );

        flushAndClear();

        long after = categoryRepository.count();
        assertEquals(before + 2, after, "Le total doit avoir augmenté de 2");

        List<Category> all = categoryRepository.findAll();
        assertTrue(all.stream().anyMatch(c -> c.getId().equals(c1.getId())));
        assertTrue(all.stream().anyMatch(c -> c.getId().equals(c2.getId())));
    }

    @Test
    @Rollback
    void relation_findProductsByCategory_shouldReturnOnlyForThatCategory() {
        // Catégories
        Category cat1 = categoryRepository.save(Category.builder().name("cat-1").build());
        Category cat2 = categoryRepository.save(Category.builder().name("cat-2").build());

        // Produits rattachés (on SET bien le côté propriétaire)
        productRepository.save(
                Product.builder()
                        .nameProduct("MacBook")
                        .priceProduct(777.00)
                        .category(cat1)
                        .dateCreated(new Date())
                        .build()
        );
        productRepository.save(
                Product.builder()
                        .nameProduct("MacBook")
                        .priceProduct(666.00)
                        .category(cat2)
                        .dateCreated(new Date())
                        .build()
        );

        flushAndClear();

        List<Product> found = productRepository.findByCategory(cat1);
        assertEquals(1, found.size());
        assertEquals(777.00, found.get(0).getPriceProduct());
        assertEquals("cat-1", found.get(0).getCategory().getName());
    }
}
