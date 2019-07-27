package com.ala.repository;

import com.ala.model.User;

public interface UserRepository {

    User getUser(int id);

    void storeUser(User user);
}
