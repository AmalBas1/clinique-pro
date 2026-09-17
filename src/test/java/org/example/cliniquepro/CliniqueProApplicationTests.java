package org.example.cliniquepro;

import org.example.cliniquepro.config.TestMailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestMailConfig.class)
class CliniqueProApplicationTests {

    @Test
    void contextLoads() {
    }

}
