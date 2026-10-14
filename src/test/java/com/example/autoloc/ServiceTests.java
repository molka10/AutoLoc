package com.example.autoloc;

import com.example.autoloc.domain.Agence;
import com.example.autoloc.domain.Client;
import com.example.autoloc.domain.Employe;
import com.example.autoloc.domain.Equipement;
import com.example.autoloc.domain.Maintenance;
import com.example.autoloc.domain.Vehicule;
import com.example.autoloc.domain.enums.RoleEmploye;
import com.example.autoloc.Exception.ResourceNotFoundException;
import com.example.autoloc.service.IAgenceService;
import com.example.autoloc.service.IClientService;
import com.example.autoloc.service.IEmployeService;
import com.example.autoloc.service.IEquipementService;
import com.example.autoloc.service.IMaintenanceService;
import com.example.autoloc.service.IVehiculeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceTests {

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Autowired
    private IEmployeService employeService;

    @Autowired
    private IEquipementService equipementService;

    @Autowired
    private IMaintenanceService maintenanceService;

    // ---------- Partie 2 : AgenceService ----------
    @Test
    void testAgenceService() {
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("71000000");

        Agence saved = agenceService.create(agence);
        System.out.println("Agence enregistrée : id = " + saved.getIdAgence() + ", nom = " + saved.getNom());
        assertNotNull(saved.getIdAgence());

        List<Agence> agences = agenceService.findAll();
        System.out.println("Nombre d'agences (findAll) : " + agences.size());
        assertFalse(agences.isEmpty());

        Agence trouvee = agenceService.findById(saved.getIdAgence());
        System.out.println("Agence trouvée : " + trouvee.getNom() + " - " + trouvee.getVille());
        assertEquals("Agence Tunis Centre", trouvee.getNom());

        // le TP demande count() : le service n'expose pas count, on utilise findAll().size()
        int avantSuppression = agenceService.findAll().size();
        System.out.println("Nombre d'agences : " + avantSuppression);

        // modification (update)
        trouvee.setVille("La Marsa");
        Agence modifiee = agenceService.update(saved.getIdAgence(), trouvee);
        System.out.println("Ville après update : " + modifiee.getVille());
        assertEquals("La Marsa", modifiee.getVille());

        agenceService.deleteById(saved.getIdAgence());
        System.out.println("Agence supprimée : " + saved.getIdAgence());
        assertEquals(avantSuppression - 1, agenceService.findAll().size());
        assertThrows(ResourceNotFoundException.class, () -> agenceService.findById(saved.getIdAgence()));
    }

    // ---------- Partie 3 : ClientService ----------
    @Test
    void testClientService() {
        Client c1 = new Client();
        c1.setNom("Ben Salah");
        c1.setPrenom("Ali");
        c1.setEmail("ali.bensalah@test.com");
        c1.setTelephone("20111111");
        c1.setNumPermis("P-1001");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");
        c2.setEmail("sara.trabelsi@test.com");
        c2.setTelephone("20222222");
        c2.setNumPermis("P-1002");
        c2.setDateInscription(LocalDate.now());

        Client s1 = clientService.create(c1);
        Client s2 = clientService.create(c2);
        System.out.println("Clients enregistrés : " + s1.getIdClient() + " et " + s2.getIdClient());
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        List<Client> clients = clientService.findAll();
        System.out.println("Liste des clients (findAll) :");
        clients.forEach(c -> System.out.println(" - " + c.getIdClient() + " : " + c.getNom() + " " + c.getPrenom()));
        assertTrue(clients.size() >= 2);

        Client trouve = clientService.findById(s1.getIdClient());
        System.out.println("Client trouvé : " + trouve.getNom() + " " + trouve.getPrenom());
        assertEquals("Ali", trouve.getPrenom());

        int avantSuppression = clientService.findAll().size();
        System.out.println("Nombre de clients : " + avantSuppression);

        clientService.deleteById(s1.getIdClient());
        System.out.println("Client supprimé : " + s1.getIdClient());
        assertEquals(avantSuppression - 1, clientService.findAll().size());
        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(s1.getIdClient()));

        clientService.deleteById(s2.getIdClient()); // nettoyage
    }

    // ---------- Partie 4 : VehiculeService ----------
    @Test
    void testVehiculeService() {
        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setTarifJournalier(new BigDecimal("90.00"));

        Vehicule saved = vehiculeService.create(v);
        System.out.println("Véhicule enregistré : id = " + saved.getIdVehicule() + ", " + saved.getMarque() + " " + saved.getModele());
        assertNotNull(saved.getIdVehicule());

        List<Vehicule> vehicules = vehiculeService.findAll();
        System.out.println("Nombre de véhicules (findAll) : " + vehicules.size());
        assertFalse(vehicules.isEmpty());

        Vehicule trouve = vehiculeService.findById(saved.getIdVehicule());
        System.out.println("Véhicule trouvé : " + trouve.getImmatriculation());
        assertEquals("Clio", trouve.getModele());

        int avantSuppression = vehiculeService.findAll().size();
        System.out.println("Nombre de véhicules : " + avantSuppression);

        vehiculeService.deleteById(saved.getIdVehicule());
        System.out.println("Véhicule supprimé : " + saved.getIdVehicule());
        assertEquals(avantSuppression - 1, vehiculeService.findAll().size());
        assertThrows(ResourceNotFoundException.class, () -> vehiculeService.findById(saved.getIdVehicule()));
    }

    // ---------- Partie 5 : exercice autonome (Employe, Equipement, Maintenance) ----------
    @Test
    void testEmployeService() {
        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Omar");
        e.setRole(RoleEmploye.values()[0]); // première valeur de l'énumération

        Employe saved = employeService.create(e);
        System.out.println("Employé enregistré : id = " + saved.getIdEmploye() + ", " + saved.getNom());
        assertNotNull(saved.getIdEmploye());

        assertFalse(employeService.findAll().isEmpty());
        Employe trouve = employeService.findById(saved.getIdEmploye());
        System.out.println("Employé trouvé : " + trouve.getNom() + " " + trouve.getPrenom());
        assertEquals("Omar", trouve.getPrenom());

        employeService.deleteById(saved.getIdEmploye());
        System.out.println("Employé supprimé : " + saved.getIdEmploye());
        assertThrows(ResourceNotFoundException.class, () -> employeService.findById(saved.getIdEmploye()));
    }

    @Test
    void testEquipementService() {
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementService.create(eq);
        System.out.println("Équipement enregistré : id = " + saved.getIdEquipement() + ", " + saved.getLibelle());
        assertNotNull(saved.getIdEquipement());

        assertFalse(equipementService.findAll().isEmpty());
        Equipement trouve = equipementService.findById(saved.getIdEquipement());
        System.out.println("Équipement trouvé : " + trouve.getLibelle());
        assertEquals("GPS", trouve.getLibelle());

        equipementService.deleteById(saved.getIdEquipement());
        System.out.println("Équipement supprimé : " + saved.getIdEquipement());
        assertThrows(ResourceNotFoundException.class, () -> equipementService.findById(saved.getIdEquipement()));
    }

    @Test
    void testMaintenanceService() {
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.of(2026, 10, 1));
        m.setDateFin(LocalDate.of(2026, 10, 3));
        m.setDescription("Vidange et contrôle des freins");

        Maintenance saved = maintenanceService.create(m);
        System.out.println("Maintenance enregistrée : id = " + saved.getIdMaintenance());
        assertNotNull(saved.getIdMaintenance());

        assertFalse(maintenanceService.findAll().isEmpty());
        Maintenance trouvee = maintenanceService.findById(saved.getIdMaintenance());
        System.out.println("Maintenance trouvée : " + trouvee.getDescription());
        assertEquals("Vidange et contrôle des freins", trouvee.getDescription());

        // règle métier : date de fin avant date de début refusée
        Maintenance invalide = new Maintenance();
        invalide.setDateDebut(LocalDate.of(2026, 10, 5));
        invalide.setDateFin(LocalDate.of(2026, 10, 1));
        assertThrows(IllegalArgumentException.class, () -> maintenanceService.create(invalide));
        System.out.println("Maintenance invalide correctement refusée");

        maintenanceService.deleteById(saved.getIdMaintenance());
        System.out.println("Maintenance supprimée : " + saved.getIdMaintenance());
        assertThrows(ResourceNotFoundException.class, () -> maintenanceService.findById(saved.getIdMaintenance()));
    }
}