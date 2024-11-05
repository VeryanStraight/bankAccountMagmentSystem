package com.veryan.springbootapi;

import com.veryan.springbootapi.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsername() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("tojhonson");
        assertNotNull(userDetails);
        assertEquals("tojhonson", userDetails.getUsername());
    }
}
