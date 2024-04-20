package com.aspiresys.rsqlparserdemo.repository;

import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode, Long>, JpaSpecificationExecutor<ZipCode> {
}
