package com.shepherdmoney.interviewproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InterviewProjectApplicationTests {

    @Autowired
    private UserController userController;
    
    @Test
    void contextLoads() {
        // Verify that the user controller is not null
        assertThat(userController).isNotNull();
    }

}
