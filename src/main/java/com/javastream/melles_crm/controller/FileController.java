package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductPhoto;
import com.javastream.melles_crm.repo.ProductPhotoRepository;
import com.javastream.melles_crm.service.ProductService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
    Товары сортируются по id
 */

@RestController
@RequestMapping("/endpoint/image")
public class FileController {

    @Autowired
    private ProductPhotoRepository productPhotoRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value= "/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] index(@PathVariable("id") String photoId) throws IOException {

        Product product = productService.findById(photoId);
        ProductPhoto photo = product.getPhoto();

        File file = new File("D:" + File.separator + "me_photo" + File.separator + photo.getFileName());
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }
}