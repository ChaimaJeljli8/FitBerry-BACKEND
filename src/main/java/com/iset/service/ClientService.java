package com.iset.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iset.DTO.ClientProfileDTO;
import com.iset.DTO.UpdateClientProfileRequest;
import com.iset.entity.Client;
import com.iset.entity.User;
import com.iset.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

public class ClientService {
    
    private final UserRepository userRepository;
    
    public ClientService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    @Transactional
    public ClientProfileDTO updateClientProfile(Long clientId, UpdateClientProfileRequest request) {
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        
        if (user.getRole() != User.Role.CLIENT) {
            throw new RuntimeException("Cet utilisateur n'est pas un client");
        }
        
        if (!(user instanceof Client)) {
            throw new RuntimeException("Erreur: l'utilisateur n'est pas une instance de Client");
        }
        
        Client client = (Client) user;
        
        // Mettre à jour les informations
        client.setAge(request.getAge());
        client.setSexe(request.getSexe());
        client.setPoids(request.getPoids());
        client.setTaille(request.getTaille());
        client.setObjectifs(request.getObjectifs());
        client.setAllergies(request.getAllergies());
        client.setMaladiesChroniques(request.getMaladiesChroniques());
        client.setNiveauActivite(request.getNiveauActivite());
        
        Client updatedClient = (Client) userRepository.save(client);
        return convertToDTO(updatedClient);
    }
    
    // Récupérer le profil du client
    public ClientProfileDTO getClientProfile(Long clientId) {
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        
        if (!(user instanceof Client)) {
            throw new RuntimeException("Cet utilisateur n'est pas un client");
        }
        
        Client client = (Client) user;
        return convertToDTO(client);
    }
    
    // Convertir Client en ClientProfileDTO
    private ClientProfileDTO convertToDTO(Client client) {
        ClientProfileDTO dto = new ClientProfileDTO();
        dto.setId(client.getId());
        dto.setEmail(client.getEmail());
        dto.setAge(client.getAge());
        dto.setSexe(client.getSexe());
        dto.setPoids(client.getPoids());
        dto.setTaille(client.getTaille());
        dto.setObjectifs(client.getObjectifs());
        dto.setAllergies(client.getAllergies());
        dto.setMaladiesChroniques(client.getMaladiesChroniques());
        dto.setNiveauActivite(client.getNiveauActivite());
        
        // Calculer l'IMC si poids et taille sont disponibles
        if (client.getPoids() != null && client.getTaille() != null && client.getTaille() > 0) {
            double tailleEnMetres = client.getTaille() / 100.0;
            double imc = client.getPoids() / (tailleEnMetres * tailleEnMetres);
            dto.setImc(Math.round(imc * 100.0) / 100.0); // Arrondir à 2 décimales
            dto.setCategorieImc(getCategorieImc(imc));
        }
        
        // Vérifier si le profil est complet
        dto.setProfilComplet(isProfilComplet(client));
        
        return dto;
    }
    
    // Déterminer la catégorie IMC
    private String getCategorieImc(double imc) {
        if (imc < 18.5) {
            return "Insuffisance pondérale";
        } else if (imc < 25) {
            return "Poids normal";
        } else if (imc < 30) {
            return "Surpoids";
        } else {
            return "Obésité";
        }
    }
    
    // Vérifier si le profil est complet
    private boolean isProfilComplet(Client client) {
        return client.getAge() != null 
            && client.getSexe() != null && !client.getSexe().isEmpty()
            && client.getPoids() != null 
            && client.getTaille() != null;
    }
}

