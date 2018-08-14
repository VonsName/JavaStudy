package com.example.thriftservice;

public class NetAuthServiceImpl implements NetAuthService {
    @Override
    public boolean login(String userAccount, String password) {
        System.out.println(userAccount+"=="+password);
        return false;
    }
}
