package com.sara.unittestingpractice.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pack {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long packId;

  @Enumerated(EnumType.STRING)
  private PackStatus status;

  private Long gameId;

  @OneToMany(mappedBy="pack")//points to the pack field in Tickets table which contains the foreign key
  private List<Ticket> tickets;

  @ManyToOne
  @JoinColumn(name="retailer_id")
  private Retailer retailer;

  // Constructors
  public Pack() {}
  public Pack(Long packId, PackStatus status, Long gameId) {
    this.packId = packId;
    this.status = status;
    this.gameId = gameId;
  }

  // Getters and setters
  public Long getPackId() {
    return packId;
  }

  public PackStatus getStatus() {
    return status;
  }
  public void setStatus(PackStatus status) {
    this.status = status;
  }

  public Long getGameId() { return gameId; }

  public List<Ticket> getTickets() { return tickets; }//read all tickets of this pack
  public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }//assign a list of tickets when creating a new pack

  public Retailer getRetailer() { return retailer; }
  public void setRetailer(Retailer retailer) { this.retailer = retailer;}
}


