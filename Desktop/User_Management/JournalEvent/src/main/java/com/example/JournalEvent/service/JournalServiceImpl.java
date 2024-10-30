package com.example.JournalEvent.service;

import com.example.JournalEvent.entity.Journal;
import com.example.JournalEvent.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService{
    @Autowired
    private JournalRepository journalRepository;
    @Override
    public List<Journal> getAllJournal() {
       return journalRepository.findAll();
    }
}
