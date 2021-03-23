package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.ProductPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoRepository extends CrudRepository<ProductPhoto, Long> {
}
