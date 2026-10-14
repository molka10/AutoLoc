package com.example.autoloc.service.impl;

import com.example.autoloc.domain.Paiement;
import com.example.autoloc.Exception.ResourceNotFoundException;
import com.example.autoloc.repository.IPaiementRepository;
import com.example.autoloc.service.IPaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}