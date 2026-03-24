package com.sara.unittestingpractice.repository;

import com.sara.unittestingpractice.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, Long> {
  List<Pack> findByRetailerId(Long retailerId);

}