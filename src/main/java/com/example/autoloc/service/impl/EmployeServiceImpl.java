package com.example.autoloc.service.impl;

import com.example.autoloc.domain.Employe;
import com.example.autoloc.Exception.ResourceNotFoundException;
import com.example.autoloc.repository.IEmployeRepository;
import com.example.autoloc.service.IEmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        verifier(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existant = findById(id);
        verifier(employe);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        existant.setAgence(employe.getAgence());
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void verifier(Employe e) {
        if (e.getNom() == null || e.getNom().isBlank()
                || e.getPrenom() == null || e.getPrenom().isBlank()
                || e.getRole() == null) {
            throw new IllegalArgumentException("Le nom, le prénom et le rôle de l'employé sont obligatoires");
        }
    }
}