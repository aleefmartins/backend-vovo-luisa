package com.backendvovoluisa.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backendvovoluisa.entity.UserAccount;

@SpringBootTest
public class PDFGeneratorTest {

    private PDFGenerator pdfGenerator;

    @BeforeEach
    void setUp() {
        pdfGenerator = new PDFGenerator();
    }

    @Test
    void testGeneratePDF() {
        List<UserAccount> userAccounts = new ArrayList<>();
        userAccounts.add(new UserAccount("1", null, null, null, "Rua Motorista", null, "alef.mrh@gmail.com", LocalDate.now()));
        userAccounts.add(new UserAccount("2", null, null, null, "Rua Motorista", null, "sabrina.rocp@gmail.com", LocalDate.now()));

        assertDoesNotThrow(() -> {
            byte[] pdfBytes = pdfGenerator.generatePDF(userAccounts);
            assertNotNull(pdfBytes);
            assertTrue(pdfBytes.length > 0);
        });
    }
}
