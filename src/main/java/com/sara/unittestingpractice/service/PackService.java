package com.sara.unittestingpractice.service;

import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.entity.PackStatus;
import com.sara.unittestingpractice.repository.PackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PackService {
  private final PackRepository packRepository;
  public PackService(PackRepository packRepository) {
    this.packRepository = packRepository;
  }

  public List<Pack> getPacksByRetailer(Long retailerId) {
    return packRepository.findByRetailerId(retailerId);
  }

  public Pack activatePackById(Long id) {

    Pack pack = packRepository.findById(id)
        .orElseThrow(()-> new NoSuchElementException("Pack not found with id: " + id));

    if (pack.getStatus() == PackStatus.ACTIVATED) { //check if pack was already activated
      throw new IllegalStateException("Pack is already activated");
    }

    if(pack.getStatus() != PackStatus.IN_RETAILER){ //check if status is not "IN_RETAILER"
      throw new IllegalStateException("Pack must be in retailer to be activated");
    }

    pack.setStatus(PackStatus.ACTIVATED); //change status to activated
    return packRepository.save(pack);
  } //saving using the repository and returning pack

}
