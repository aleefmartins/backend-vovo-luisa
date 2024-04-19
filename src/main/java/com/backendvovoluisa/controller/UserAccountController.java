package com.backendvovoluisa.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.backendvovoluisa.entity.UserAccount;
import com.backendvovoluisa.repository.UserAccountRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@CrossOrigin(origins="*")

public class UserAccountController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PDFGenerator pdfGenerator;

    @PostMapping("/userAccount")
    public UserAccount saveUserAccount(@RequestBody UserAccount userAccount){
        userAccount.setUserDataCriacao(LocalDate.now());
        return userAccountRepository.save(userAccount);
    }

    @GetMapping("/userAccount/all")
    public List <UserAccount> getUserAccount (){
        return userAccountRepository.getAllUsers();
    }

    @GetMapping("/userAccount/{id}")
    public UserAccount getUserAccount (@PathVariable("id") String userId){
        return userAccountRepository.getUserAccountById(userId);
    }

    @DeleteMapping ("/userAccount/{id}")
    public UserAccount deleteUserAccount(@PathVariable("id") String userId){
        return userAccountRepository.delete(userId);
    }

    @GetMapping("/userAccount/byDateRange")
    public ResponseEntity<Page<UserAccount>> getUserAccountsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<UserAccount> userAccounts = userAccountRepository.findUserAccountsByDateRange(start, end, pageable);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }
    

    @PutMapping ("/userAccount/{id}")
    public UserAccount updateUserAccount(@PathVariable("id") String userId, @RequestBody UserAccount userAccount){
        return userAccountRepository.update(userId, userAccount);
    }

    @GetMapping("/userAccount/downloadPDF")
    public ResponseEntity<byte[]> downloadPDF(
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<UserAccount> userAccountsPage;
    
        if (start != null && end != null) {
            userAccountsPage = userAccountRepository.findUserAccountsByDateRange(start, end, pageable);
        } else {
            userAccountsPage = userAccountRepository.getUserAccounts(pageable);
        }
    
        List<UserAccount> filteredUserAccounts = userAccountsPage.getContent();
    
        try {
            byte[] pdfBytes = pdfGenerator.generatePDF(filteredUserAccounts);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "user_accounts.pdf");
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    @GetMapping("/userAccount")
    public ResponseEntity<Page<UserAccount>> getUserAccounts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<UserAccount> userAccounts = userAccountRepository.getUserAccounts(pageable);
    return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }
}
