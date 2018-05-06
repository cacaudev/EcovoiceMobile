package com.example.cacau2.ecovoicetest;


import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Cacau2 on 05/05/2018.
 *
 * Classe responsável pela conexão com banco de dados do Heroku Postgres Database de forma assícrona
 * As variáveis são geradas pelo heroku, e podem ser conferidas na aba "Database Credentials" de "Settings"
 *
 * Lembretes:
 *  A biblioteca utilizada para conexão foi JDBC Postgres versão 42.2.2 (JRE 7) que pode ser baixada
 *  no site https://jdbc.postgresql.org/download.html
 *  JDBC Convention: jdbc:postgresql://<host>:<port>/<dbname>?user=<username>&password=<password>
 *  JDBC Convention - with SSL: jdbc:postgresql://<host>:<port>/<dbname>?sslmode=require&user=<username>&password=<password>
 *  Formato da Url: jdbc:postgresql://host:port/database?sslmode=require&user=user&password=password
 *
 *  AsyncTask<Params, Progress, Result>
 */

public class ConexaoDB extends AsyncTask<String, Integer, Connection> {

        private Connection connection = null;
        private String host = "ec2-54-204-41-74.compute-1.amazonaws.com";
        private String db = "d2ppauescms70t";
        private int port = 5432;
        private String user = "ckznlmuufuldwm";
        private String password = "0_-duk6luF2vg8o0YRZFJxI2kA";
        private String url = "jdbc:postgresql://%s:%d/%s?sslmode=require&user=%s&password=%s";

        // Constructor
        public ConexaoDB() {
            super();
            this.url = String.format(this.url, this.host, this.port, this.db, this.user, this.password);
        }

        public Connection getConnection() {
            return this.connection;
        }


        public void disconecta() {
            if (this.connection != null) {
                try {
                    this.connection.close();
                    Log.v("BancoDeDados", "Está desconectado do banco conexaoDB");
                } catch (Exception e) {
                    Log.v("BancoDeDados", "Erro ao desconectar do banco conexaoDB " + e.getMessage());
                } finally {
                    this.connection = null;
                }
            }
        }

        protected Connection doInBackground(String... params) {
            Log.v("BancoDeDados", "Entrou no doInBackground de conexaoDB.class");

            try {
                Class.forName("org.postgresql.Driver");
                this.connection = DriverManager.getConnection(this.url, this.user, this.password);
                Log.v("BancoDeDados", "Conectado ao banco Heroku conexaoDB");
            } catch (Exception e) {
                Log.v("BancoDeDados", "Erro ao conectar banco Heroku conexaoDB" + e.getMessage());
            }

            return this.connection;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.v("BancoDeDados", "Conexao está em processamento conexaoDB.class");
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Connection result) {
            Log.v("BancoDeDados", "Terefa de conexao está terminada conexaoDB.class");
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            Log.v("BancoDeDados", "Conexao está comecando conexaoDB.class");
            super.onPreExecute();
        }
}

