package com.backendvovoluisa.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.backendvovoluisa.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserAccountRepository  {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public UserAccount save(UserAccount userAccount){
        dynamoDBMapper.save(userAccount);
        return userAccount;
    }

    public UserAccount getUserAccountById (String userId){
        return dynamoDBMapper.load(UserAccount.class, userId);
    }

    public List <UserAccount> getAllUsers(){
        List <UserAccount> allusers = dynamoDBMapper.scan(UserAccount.class, new DynamoDBScanExpression());
            return allusers;
    }

    public UserAccount delete(String userId){
        UserAccount del = dynamoDBMapper.load(UserAccount.class, userId);
        dynamoDBMapper.delete(del);
        return del;
    }

    public UserAccount update(String userId, UserAccount userAccount){
        dynamoDBMapper.save(userAccount,
                new DynamoDBSaveExpression()
                .withExpectedEntry("userId",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(userId)
                        )));
        return userAccount;
    }
    
    public Page<UserAccount> findUserAccountsByDateRange(LocalDate start, LocalDate end, Pageable pageable) {
        List<UserAccount> allUserAccounts = getAllUsers();
        List<UserAccount> filteredUserAccounts = new ArrayList<>();
    
        for (UserAccount userAccount : allUserAccounts) {
            LocalDate userDataCriacao = userAccount.getUserDataCriacao();
            if (userDataCriacao != null && (userDataCriacao.isEqual(start) || userDataCriacao.isEqual(end) ||
                    (userDataCriacao.isAfter(start) && userDataCriacao.isBefore(end)))) {
                filteredUserAccounts.add(userAccount);
            }
        }
    
        int startOffset = (int) pageable.getOffset();
        int endOffset = (startOffset + pageable.getPageSize()) > filteredUserAccounts.size() ? filteredUserAccounts.size() : (startOffset + pageable.getPageSize());
        return new PageImpl<>(filteredUserAccounts.subList(startOffset, endOffset), pageable, filteredUserAccounts.size());
    }
    

    public Page<UserAccount> getUserAccounts(Pageable pageable) {
        List<UserAccount> allUserAccounts = getAllUsers();
        int startOffset = (int) pageable.getOffset();
        int endOffset = (startOffset + pageable.getPageSize()) > allUserAccounts.size() ? allUserAccounts.size() : (startOffset + pageable.getPageSize());
        return new PageImpl<>(allUserAccounts.subList(startOffset, endOffset), pageable, allUserAccounts.size());
    }
    
}
