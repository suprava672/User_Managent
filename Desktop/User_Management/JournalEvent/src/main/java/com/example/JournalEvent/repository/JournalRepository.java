package com.example.JournalEvent.repository;

import com.example.JournalEvent.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal,Long> {
}
