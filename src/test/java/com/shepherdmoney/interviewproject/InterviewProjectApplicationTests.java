package com.shepherdmoney.interviewproject;
import org.junit.jupiter.api.Assertions;

import com.shepherdmoney.interviewproject.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class InterviewProjectApplicationTests {

    @Autowired
    private UserController userController;
    
    // @Test
    // void contextLoads() {
    //     // Verify that the user controller is not null
    //     assertThat(1==1, true);
    // }


}
