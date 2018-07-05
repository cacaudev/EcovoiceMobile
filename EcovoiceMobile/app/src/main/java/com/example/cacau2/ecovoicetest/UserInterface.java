package com.example.cacau2.ecovoicetest;

import java.util.List;

/**
 * Created by Cacau2 on 06/05/2018.
 */

public interface UserInterface {

    Boolean checkPassword(String user_email, String user_password_plain);
    int insert(User newUser);
    int updateById(User user);
    int deleteById(Integer user_id);
    User selectById(Integer user_id);
    List<User> selectMany();

}
