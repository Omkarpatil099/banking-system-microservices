package com.omkar.account_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    classes = AccountServiceApplication.class,
    properties = {
        "spring.datasource.url=jdbc:h2:mem:accountdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "eureka.client.enabled=false"
    }
)
class AccountServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
