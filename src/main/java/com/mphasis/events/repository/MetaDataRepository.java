
package com.mphasis.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mphasis.events.entity.MetadataEntity;

@Repository
public interface MetaDataRepository extends JpaRepository<MetadataEntity, String> {

}
