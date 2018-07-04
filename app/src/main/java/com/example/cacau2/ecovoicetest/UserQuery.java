package com.example.cacau2.ecovoicetest;

import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Cacau2 on 06/05/2018.
 *
 * Classe que implementa a interface com queries do objeto User
 */

public class UserQuery {


    private executeQuery tarefaQuery = null;
    private Connection conexaoUsada = null;

    // Constructor
    public UserQuery(Connection connection){
        this.conexaoUsada = connection;
    }


    public Boolean checkPassword(String user_email, String user_password_plain) {
        Boolean password_match = false;
        String password_hashed = "";
        String query = String.format("SELECT encrypted_password FROM users WHERE email='%s';", user_email);
        ResultSet result = null;

        try {
            result = new executeQuery(conexaoUsada, query, 1).execute().get();
            Log.v("BancoDeDados", "Fez query de checkPassword com sucesso ao banco - userQuerys.class");

            if (result != null) {
                while (result.next()) {
                    password_hashed = result.getString("encrypted_password");
                }
            } else
                Log.v("BancoDeDados", "Resultado do resultSet da query deu null - userQuerys.class");

        } catch (Exception e) {
            Log.v("BancoDeDados", "Erro ao conectar com banco -  userQuerys.class " + e.getMessage());
        }

        if (BCrypt.checkpw(user_password_plain, password_hashed)) {
            Log.v("BancoDeDados", "The password matches.");
            password_match = true;
        } else {
            Log.v("BancoDeDados", "The password does not match.");
            password_match = false;
        }

        tarefaQuery = null;
        return password_match;
    }

    //TODO SelectMany
    public List<User> selectMany(){
        List<User> usuariosLista = null;
        return usuariosLista;
    }

    //TODO selectById
    public User selectById(Integer user_id) {
        User userResult = null;
        return userResult;
    }

    //TODO salvar
    public int insert(User user){
        int linhas_alteradas = 0;
        return linhas_alteradas;
    }

    //TODO editar
    public int updateById(User user) {
        int linhas_alteradas = 0;
        return linhas_alteradas;
    }

    //TODO apagar
    public int deleteById(Integer user_id) {
        int linhas_deletadas = 0;
        return linhas_deletadas;
    }
}
