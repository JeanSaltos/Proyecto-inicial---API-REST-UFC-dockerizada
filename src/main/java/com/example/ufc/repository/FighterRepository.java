package com.example.ufc.repository;

import com.example.ufc.model.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
    // puedes añadir queries personalizadas aquí si quieres
}
