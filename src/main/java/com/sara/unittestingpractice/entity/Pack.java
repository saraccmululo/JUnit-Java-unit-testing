package com.sara.unittestingpractice.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pack {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long packId;

  private String status;

  private Long gameId;

  @OneToMany(mappedBy="pack")//points to the pack field in Tickets table which contains the foreign key
  private List<Ticket> tickets;

  @ManyToOne
  @JoinColumn(name="retailer_id")
  private Retailer retailer;

  // Constructors
  public Pack() {}
  public Pack(Long packId, String status, Long gameId) {
    this.packId = packId;
    this.status = status;
    this.gameId = gameId;
  }

  // Getters and setters
  public Long getPackId() {
    return packId;
  }

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public Long getGameId() { return gameId; }

  public List<Ticket> getTickets() { return tickets; }//read all tickets of this pack
  public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }//assign a list of tickets when creating a new pack
}


