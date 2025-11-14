package com.canse.product.services;

import com.canse.product.entities.Image;
import com.canse.product.entities.Product;
import com.canse.product.repos.ImageRepository;
import com.canse.product.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Image uploadImage(MultipartFile file, Long idProduct) throws IOException {
        Product product = new Product();
        product.setId(idProduct);
        return imageRepository.save(
                Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .image(file.getBytes())
                        .product(product)
                        .build()
        );
    }

    @Override
    public List<Image> getImageDetails(Long id) throws IOException {
        Product product = productRepository.findById(id).get();
        return product.getImages();
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> image = imageRepository.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.get().getType()))
                .body(image.get().getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
