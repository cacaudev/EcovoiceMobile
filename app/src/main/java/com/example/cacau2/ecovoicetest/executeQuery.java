package com.example.cacau2.ecovoicetest;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cacau2 on 06/05/2018.
 *
 * Classe responsável por fazer querys ao banco de forma assícrona
 *
 * Para diferenciar quando for executar a query:
 * Método ExecuteDB com parametro 1 usado para select
 * Método ExecuteDB com parametro 2 usado para insert, update, delete
 */

public class executeQuery  extends AsyncTask<Void, Integer, ResultSet> {

    private String query;
    private Integer tipoQuery;
    private Connection connection;

    public executeQuery(Connection connection,String query, Integer tipoQuery) {
        this.connection = connection;
        this.query = query;
        this.tipoQuery = tipoQuery;
    }

    @Override
    protected ResultSet doInBackground(Void... params) {
        Log.v("BancoDeDados", "Entrou no doInBackground executeQueryDB");
        ResultSet resultSet = null;

        try {
            if(connection == null) Log.v("DEBUG", "CONEXAO ESTÁ NULL executeQueryDB");
            if(tipoQuery == 1) {
                resultSet = connection.prepareStatement(query).executeQuery();
                Log.v("BancoDeDados", "Fez executeQuery executeQueryDB ");
            } else if(tipoQuery == 2) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                Log.v("BancoDeDados", "Fez executeUpdate executeQueryDB");
            }

            if(resultSet != null)
                Log.v("BancoDeDados", "Voltou alguma coisa no resultset executeQueryDB");

        } catch (SQLException e) {
            Log.v("BancoDeDados", "Erro ao tentar fazer connection.prepareStatement(query) executeQueryDB");
        }
        return resultSet;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.v("BancoDeDados", "Query está em processamento executeQueryDB");
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(ResultSet result) {
        Log.v("BancoDeDados", "Query terminou executeQueryDB");
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        Log.v("BancoDeDados", "Query comecou executeQueryDB");
        super.onPreExecute();
    }
}
