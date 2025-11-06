package com.iset.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iset.DTO.ClientProfileDTO;
import com.iset.DTO.UpdateClientProfileRequest;
import com.iset.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")

@CrossOrigin(origins = "*")
public class ClientController {
    
    private final ClientService clientService;
    
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    // Remplir/Mettre à jour le profil du client
    @PutMapping("/{clientId}/profile")
    public ResponseEntity<ClientProfileDTO> updateProfile(
            @PathVariable Long clientId,
            @Valid @RequestBody UpdateClientProfileRequest request) {
        try {
            ClientProfileDTO profile = clientService.updateClientProfile(clientId, request);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Récupérer le profil du client
    @GetMapping("/{clientId}/profile")
    public ResponseEntity<ClientProfileDTO> getProfile(@PathVariable Long clientId) {
        try {
            ClientProfileDTO profile = clientService.getClientProfile(clientId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
