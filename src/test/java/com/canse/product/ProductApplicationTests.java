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
        Product product =
                Product.builder()
                        .nameProduct("Pc Dell")
                        .priceProduct(2250.00)
                        .dateCreated(new Date()).build();
        Product saved = productRepository.save(product);

        assertNotNull(saved.getId(), "L'ID doit être généré");
        assertTrue(productRepository.findById(saved.getId()).isPresent(), "Le produit doit exister en DB");
    }

    @Test
    @Rollback
    void findById_shouldReturnEntity() {
        Product seed = productRepository.save(
                Product.builder()
                        .nameProduct("MacBook")
                        .priceProduct(2999.00)
                        .dateCreated(new Date()).build()
        );
        Long id = seed.getId();

        Product found = productRepository.findById(id).orElseThrow();
        assertEquals("MacBook", found.getNameProduct());
        assertEquals(2999.00, found.getPriceProduct());
    }

    @Test
    @Rollback
    void update_shouldChangePrice() {
        Product seed = productRepository.save(
                Product.builder()
                        .nameProduct("ThinkPad")
                        .priceProduct(1900.00)
                        .dateCreated(new Date()).build()
        );
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
        Product seed = productRepository.save(
                Product.builder()
                        .nameProduct("PC Gamer")
                        .priceProduct(1500.00)
                        .dateCreated(new Date()).build()
        );
        Long id = seed.getId();

        productRepository.deleteById(id);
        flushAndClear();

        assertTrue(productRepository.findById(id).isEmpty(), "Le produit doit être supprimé");
    }

    @Test
    @Rollback
    void findAll_shouldReturnAddedItems() {
        long before = productRepository.count();

        Product p1 = productRepository.save(
                Product.builder()
                        .nameProduct("HP Pro")
                        .priceProduct(999.00)
                        .dateCreated(new Date()).build()
        );
        Product p2 = productRepository.save(
                Product.builder()
                        .nameProduct("Asus ROG")
                        .priceProduct(1899.00)
                        .dateCreated(new Date()).build()
        );

        flushAndClear();

        long after = productRepository.count();
        assertEquals(before + 2, after, "Le total doit avoir augmenté de 2");

        List<Product> all = productRepository.findAll();
        assertTrue(all.stream().anyMatch(p -> p.getId().equals(p1.getId())));
        assertTrue(all.stream().anyMatch(p -> p.getId().equals(p2.getId())));
    }

    @Test
    @Rollback
    void findByName_shouldReturnEntity() {
        Product seed = productRepository.save(
                Product.builder()
                        .nameProduct("MacBook")
                        .priceProduct(2999.00)
                        .dateCreated(new Date()).build()
        );

        List<Product> found = productRepository.findByNameProduct(seed.getNameProduct());
        assertEquals("MacBook", found.get(0).getNameProduct());
        assertEquals(2999.00, found.get(0).getPriceProduct());
    }

    @Test
    @Rollback
    void findByNameCpntains_shouldReturnEntity() {
        productRepository.save(
                Product.builder()
                        .nameProduct("MacBook Pro M4")
                        .priceProduct(777.00)
                        .dateCreated(new Date()).build()
        );
        productRepository.save(
                Product.builder()
                        .nameProduct("MacBook Pro M5")
                        .priceProduct(666.00)
                        .dateCreated(new Date()).build()
        );

        List<Product> found = productRepository.findByNameProductContains("Pro");
        assertEquals(2, found.toArray().length);
        assertEquals(777.00, found.get(0).getPriceProduct());
        assertEquals(666.00, found.get(1).getPriceProduct());
    }
}
