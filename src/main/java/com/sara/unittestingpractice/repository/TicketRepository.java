package com.sara.unittestingpractice.repository;

import com.sara.unittestingpractice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
  List<Ticket> findByPackId(Long packId);
}
