package com.canse.product.controllers;

import com.canse.product.entities.Image;
import com.canse.product.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class ImageRestController {

    @Autowired
    ImageService imageService;

    @PostMapping("/upload/{idProduct}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Image uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("idProduct") Long idProduct) throws IOException {
        return imageService.uploadImage(file, idProduct);
    }

    @GetMapping("/{idProduct}")
    public List<Image> getImageDetails(@PathVariable("idProduct") Long idProduct) throws IOException {
        return imageService.getImageDetails(idProduct);
    }


    @DeleteMapping("/{idProduct}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteImage(@PathVariable("idProduct") Long idProduct) throws IOException {
        imageService.deleteImage(idProduct);
    }

}
