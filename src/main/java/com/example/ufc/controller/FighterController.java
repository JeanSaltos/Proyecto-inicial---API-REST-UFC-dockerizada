package com.example.ufc.controller;

import com.example.ufc.model.Fighter;
import com.example.ufc.service.FighterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fighters")
public class FighterController {
    private final FighterService service;

    public FighterController(FighterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fighter> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fighter> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fighter> create(@RequestBody Fighter fighter) {
        Fighter saved = service.save(fighter);
        return ResponseEntity.created(URI.create("/api/fighters/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fighter> update(@PathVariable Long id, @RequestBody Fighter fighter) {
        return service.findById(id).map(existing -> {
            fighter.setId(existing.getId());
            Fighter updated = service.save(fighter);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.findById(id).map(f -> {
            service.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
