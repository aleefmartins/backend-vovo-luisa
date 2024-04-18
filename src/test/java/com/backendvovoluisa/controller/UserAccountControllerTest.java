package com.backendvovoluisa.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.backendvovoluisa.entity.UserAccount;
import com.backendvovoluisa.repository.UserAccountRepository;
import com.itextpdf.text.DocumentException;

@SpringBootTest
public class UserAccountControllerTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PDFGenerator pdfGenerator;

    @InjectMocks
    private UserAccountController userAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveUserAccount() {
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.save(userAccount)).thenReturn(userAccount);

        UserAccount savedUserAccount = userAccountController.saveUserAccount(userAccount);

        assertNotNull(savedUserAccount);
        assertEquals(userAccount, savedUserAccount);
    }

    @Test
    void testGetUserAccount() {
        String userId = "exampleId";
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.getUserAccountById(userId)).thenReturn(userAccount);

        UserAccount retrievedUserAccount = userAccountController.getUserAccount(userId);

        assertNotNull(retrievedUserAccount);
        assertEquals(userAccount, retrievedUserAccount);
    }

    @Test
    void testDeleteUserAccount() {
        String userId = "exampleId";
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.delete(userId)).thenReturn(userAccount);

        UserAccount deletedUserAccount = userAccountController.deleteUserAccount(userId);

        assertNotNull(deletedUserAccount);
        assertEquals(userAccount, deletedUserAccount);
    }

    @Test
    void testGetUserAccountsByDateRange() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        Page<UserAccount> mockPage = mock(Page.class);
        when(userAccountRepository.findUserAccountsByDateRange(start, end, PageRequest.of(0, 10))).thenReturn(mockPage);

        ResponseEntity<Page<UserAccount>> responseEntity = userAccountController.getUserAccountsByDateRange(start, end, 0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPage, responseEntity.getBody());
    }

    @Test
    void testUpdateUserAccount() {
        String userId = "exampleId";
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.update(userId, userAccount)).thenReturn(userAccount);

        UserAccount updatedUserAccount = userAccountController.updateUserAccount(userId, userAccount);

        assertNotNull(updatedUserAccount);
        assertEquals(userAccount, updatedUserAccount);
    }

    @Test
    void testDownloadPDF() throws IOException, DocumentException {
        Page<UserAccount> mockPage = mock(Page.class);
        byte[] pdfBytes = {1, 2, 3};
        when(userAccountRepository.findUserAccountsByDateRange(any(LocalDate.class), any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(mockPage);
        when(mockPage.getContent()).thenReturn(new ArrayList<>());
        when(pdfGenerator.generatePDF(anyList())).thenReturn(pdfBytes);

        ResponseEntity<byte[]> responseEntity = userAccountController.downloadPDF(LocalDate.now(), LocalDate.now(), 0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pdfBytes.length, responseEntity.getBody().length);
    }

    @Test
    void testGetUserAccounts() {
        Page<UserAccount> mockPage = mock(Page.class);
        when(userAccountRepository.getUserAccounts(PageRequest.of(0, 10))).thenReturn(mockPage);

        ResponseEntity<Page<UserAccount>> responseEntity = userAccountController.getUserAccounts(0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPage, responseEntity.getBody());
    }
}

