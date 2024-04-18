package com.backendvovoluisa.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.backendvovoluisa.entity.UserAccount;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public class UserAccountRepositoryTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UserAccountRepository userAccountRepository;

    @Mock
    private PaginatedScanList<UserAccount> paginatedScanList;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testSave() {
        UserAccount userAccount = new UserAccount();
        doNothing().when(dynamoDBMapper).save(any(UserAccount.class));
        UserAccount returnedUserAccount = userAccountRepository.save(userAccount);
        assertNotNull(returnedUserAccount);
        verify(dynamoDBMapper, times(1)).save(userAccount);
    }

    @Test
    public void testGetUserAccountById() {
        String userId = "123";
        UserAccount userAccount = new UserAccount();
        when(dynamoDBMapper.load(UserAccount.class, userId)).thenReturn(userAccount);

        UserAccount result = userAccountRepository.getUserAccountById(userId);
        assertEquals(userAccount, result);
    }
    
    @Test
    public void testGetAllUsers() {
        PaginatedScanList<UserAccount> mockedList = Mockito.mock(PaginatedScanList.class);
        
        UserAccount user1 = new UserAccount();
        UserAccount user2 = new UserAccount();
        List<UserAccount> userAccounts = Arrays.asList(user1, user2);
        
        when(mockedList.size()).thenReturn(userAccounts.size());
        when(mockedList.get(0)).thenReturn(user1);
        when(mockedList.get(1)).thenReturn(user2);
    
        when(dynamoDBMapper.scan(eq(UserAccount.class), any())).thenReturn(mockedList);
        
        List<UserAccount> result = userAccountRepository.getAllUsers();
        assertEquals(2, result.size());
        assertSame(user1, result.get(0));
        assertSame(user2, result.get(1));
    }

    @Test
    public void testDelete() {
        String userId = "123";
        UserAccount userAccount = new UserAccount();
        when(dynamoDBMapper.load(UserAccount.class, userId)).thenReturn(userAccount);
        doNothing().when(dynamoDBMapper).delete(userAccount);

        UserAccount result = userAccountRepository.delete(userId);
        verify(dynamoDBMapper, times(1)).delete(userAccount);
        assertEquals(userAccount, result);
    }

    @Test
    public void testUpdate() {
        String userId = "123";
        UserAccount userAccount = new UserAccount();
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
        .withExpectedEntry("userId", new ExpectedAttributeValue(new AttributeValue().withS(userId)));
        dynamoDBMapper.save(userAccount, saveExpression);
        verify(dynamoDBMapper).save(userAccount, saveExpression);
    }


}