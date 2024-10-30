package com.example.JournalEvent.controller;

import com.example.JournalEvent.entity.Journal;
import com.example.JournalEvent.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/journals")
public class JournalController {
    @Autowired
    private JournalService journalService;
    @GetMapping("/getAlJournals")
    public List<Journal> getAllJournal(){
        return journalService.getAllJournal();

    }
}
