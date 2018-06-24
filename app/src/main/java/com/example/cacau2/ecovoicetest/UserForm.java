package com.example.cacau2.ecovoicetest;

import com.google.gson.annotations.Expose;

/**
 * Created by Cacau2 on 23/06/2018.
 */

public class UserForm {

    @Expose
    private String email;

    @Expose
    private String first_name;

    @Expose
    private String last_name;

    @Expose
    private String password;

    @Expose
    private String password_confirmation;

    @Expose
    private String locale;

    public UserForm(String email, String first_name, String last_name, String password, String password_confirmation){
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.password_confirmation = password_confirmation;
        //TODO MUDAR CONFORME A LOCALIZAÇÃO DO USUARIO (USAR GPS)
        this.locale = "pt-BR";
    }
}
