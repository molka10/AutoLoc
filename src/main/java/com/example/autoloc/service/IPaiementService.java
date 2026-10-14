package com.example.autoloc.service;

import com.example.autoloc.domain.Paiement;
import java.util.List;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}