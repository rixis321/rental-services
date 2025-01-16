package com.example.rentalservices.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProjectDataLoader implements CommandLineRunner {

    private final RoleLoader roleLoader;
    private final AdminInitialization adminInitialization;
    private final SampleCarsLoader sampleCarsLoader;

    public ProjectDataLoader(RoleLoader roleLoader, AdminInitialization adminInitialization, SampleCarsLoader sampleCarsLoader) {
        this.roleLoader = roleLoader;
        this.adminInitialization = adminInitialization;
        this.sampleCarsLoader = sampleCarsLoader;
    }

    @Override
    public void run(String... args) {
        roleLoader.run(args);
        adminInitialization.run(args);
        sampleCarsLoader.run(args);
    }
}
