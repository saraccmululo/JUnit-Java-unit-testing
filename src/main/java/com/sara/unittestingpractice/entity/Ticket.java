package com.sara.unittestingpractice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {
  @Id
  private String ticketId;

  @ManyToOne //many tickets belong to one pack
  @JoinColumn(name="pack_id") //Use the column pack_id in the tickets table as the foreign key to join both tables.
  private Pack pack;//creates a reference to another entity Pack which is the foreign key.

  private String status;

  // Constructors
  public Ticket() {}
  public Ticket(String ticketId, Pack pack, String status) {
    this.ticketId = ticketId;
    this.pack = pack;
    this.status = status;
  }

  // Getters and setters
  public String getTicketId() {
    return ticketId;
  }

  public Pack getPack() { //gets the pack of a ticket
    return pack;
  }
  public void setPack(Pack pack) {//assigns a pack to a ticket when creating or updating
    this.pack = pack;
  }

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}
