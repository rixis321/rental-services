package com.example.rentalservices.utils;

import com.example.rentalservices.model.Role;
import com.example.rentalservices.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if(!roleRepository.existsByName("ADMIN")){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setUuid(UUID.randomUUID());
            roleRepository.save(adminRole);
        }
        if(!roleRepository.existsByName("EDITOR")){
            Role editorRole = new Role();
            editorRole.setName("EDITOR");
            editorRole.setUuid(UUID.randomUUID());
            roleRepository.save(editorRole);
        }
        if(!roleRepository.existsByName("CLIENT")){
            Role clientRole = new Role();
            clientRole.setName("CLIENT");
            clientRole.setUuid(UUID.randomUUID());
            roleRepository.save(clientRole);
        }
    }


}
