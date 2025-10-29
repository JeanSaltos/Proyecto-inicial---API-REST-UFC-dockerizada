package com.example.ufc.service;

import com.example.ufc.model.Fighter;
import com.example.ufc.repository.FighterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FighterService {
    private final FighterRepository repo;

    public FighterService(FighterRepository repo) {
        this.repo = repo;
    }

    public List<Fighter> findAll() { return repo.findAll(); }
    public Optional<Fighter> findById(Long id) { return repo.findById(id); }
    public Fighter save(Fighter f) { return repo.save(f); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
