package de.muenchen.kobit.backend;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = {MicroServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MicroServiceApplicationTest {}
