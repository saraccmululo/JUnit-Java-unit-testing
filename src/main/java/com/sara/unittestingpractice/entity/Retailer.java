package com.sara.unittestingpractice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Retailer {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long retailerId;

  private String name;

  @OneToMany(mappedBy = "retailer")//points to the retailer field in Packs table which contains the foreign key
  private List<Pack> packs;

  // Constructors
  public Retailer() {}
  public Retailer(Long retailerId, String name) {
    this.retailerId = retailerId;
    this.name = name;
  }

  // Getters and setters
  public Long getRetailerId() {
    return retailerId;
  }

  public String getName() {return name;}
  public void setName(String name) {
    this.name = name;
  }

  public List<Pack> getPacks() { return packs; }//read all packs from a retailer
  public void setPacks(List<Pack> packs) { this.packs = packs; }//assign a list of packs when creating a new retailer
}
