package com.canse.product;

import com.canse.product.entities.Product;
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
class ProductApplicationTests {

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
        Product product = new Product("Pc Dell", 2250.00, new Date());
        Product saved = productRepository.save(product);

        assertNotNull(saved.getId(), "L'ID doit être généré");
        assertTrue(productRepository.findById(saved.getId()).isPresent(), "Le produit doit exister en DB");
    }

    @Test
    @Rollback
    void findById_shouldReturnEntity() {
        Product seed = productRepository.save(new Product("MacBook", 2999.00, new Date()));
        Long id = seed.getId();

        Product found = productRepository.findById(id).orElseThrow();
        assertEquals("MacBook", found.getNameProduct());
        assertEquals(2999.00, found.getPriceProduct());
    }

    @Test
    @Rollback
    void update_shouldChangePrice() {
        Product seed = productRepository.save(new Product("ThinkPad", 1900.00, new Date()));
        Long id = seed.getId();

        Product toUpdate = productRepository.findById(id).orElseThrow();
        toUpdate.setPriceProduct(2000.00);
        productRepository.save(toUpdate);

        flushAndClear();
        Product reloaded = productRepository.findById(id).orElseThrow();
        assertEquals(2000.00, reloaded.getPriceProduct(), 0.0001);
    }

    @Test
    @Rollback
    void delete_shouldRemoveEntity() {
        Product seed = productRepository.save(new Product("PC Gamer", 1500.00, new Date()));
        Long id = seed.getId();

        productRepository.deleteById(id);
        flushAndClear();

        assertTrue(productRepository.findById(id).isEmpty(), "Le produit doit être supprimé");
    }

    @Test
    @Rollback
    void findAll_shouldReturnAddedItems() {
        long before = productRepository.count();

        Product p1 = productRepository.save(new Product("HP Pro", 999.00, new Date()));
        Product p2 = productRepository.save(new Product("Asus ROG", 1899.00, new Date()));

        flushAndClear();

        long after = productRepository.count();
        assertEquals(before + 2, after, "Le total doit avoir augmenté de 2");

        List<Product> all = productRepository.findAll();
        assertTrue(all.stream().anyMatch(p -> p.getId().equals(p1.getId())));
        assertTrue(all.stream().anyMatch(p -> p.getId().equals(p2.getId())));
    }
}
