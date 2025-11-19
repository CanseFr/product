package com.canse.product.services;

import com.canse.product.dto.ProductDto;
import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.repos.ImageRepository;
import com.canse.product.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public ProductDto saveProduct(ProductDto product) {
        return convertEntityDto(productRepository.save(convertEntity(product)));
    }

    @Override
    public ProductDto updateProduct(ProductDto product) {
//        Long oldProdImgId = this.getProductById(product.getId()).getImage().getId();
//        Long newProdImgId = product.getImage().getId();
//                  Product prodUpdate = productRepository.save(product);
//        if(!Objects.equals(oldProdImgId, newProdImgId)){
//            imageRepository.deleteById(oldProdImgId);
//        }
//                    return prodUpdate;
        return convertEntityDto(productRepository.save(convertEntity(product)));
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(Long id) {
        return convertEntityDto(productRepository.findById(id).get());
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<ProductDto> getAllProducts() {
// Without stream
//        List<Product> products = productRepository.findAll();
//        List<ProductDto> productDtoList = new ArrayList<>(products.size());
//        for(Product product : products){
//            productDtoList.add(convertEntityDto(product));
//        }
//        return productDtoList;

// With stream
        return productRepository.findAll()
                .stream().map(this::convertEntityDto)
                .collect(Collectors.toList());
    }

//    ---

    @Override
    public List<Product> findByNameProduct(String nameProduct) {
        return productRepository.findByNameProduct(nameProduct);
    }

    @Override
    public List<Product> findByNameProductContains(String part) {
        return productRepository.findByNameProductContains(part);
    }

    @Override
    public List<Product> findByNameAndPrice(String name, Double price) {
        return productRepository.findByNameAndPrice(name, price);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public List<Product> findByOrderByNameProductAsc() {
        return productRepository.findByOrderByNameProductAsc();
    }

    @Override
    public List<Product> orderProductNameAndPrice() {
        return productRepository.filterProductByNameAndPrice();
    }

//    --

    @Override
    public ProductDto convertEntityDto(Product product) {
//        Without builder
//        ProductDto productDto = new ProductDto();
//        productDto.setId(product.getId());
//        productDto.setNameProduct(product.getNameProduct());
//        productDto.setPriceProduct(product.getPriceProduct());
//        productDto.setCategory(product.getCategory());
//        productDto.setImages(product.getImages());
//        return productDto;
//        With builder
        return ProductDto.builder()
                .id(product.getId())
                .priceProduct(product.getPriceProduct())
                .nameProduct(product.getNameProduct())
                .dateCreated(product.getDateCreated())
                .category(product.getCategory())
                .images(product.getImages())
                .build();
    }

    @Override
    public Product convertEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setNameProduct(productDto.getNameProduct());
        product.setPriceProduct(productDto.getPriceProduct());
        product.setCategory(productDto.getCategory());
        product.setImages(productDto.getImages());
        return product;
    }


}
