package com.example.cacau2.ecovoicetest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class AddTree_Step2 extends AppCompatActivity {

    public static Activity segundoPasso;
    Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree__step2);

        //O ComboBox da lista de Nomes Cientificos das Arvores precisa ser inicializado
        //Uma lista de nomes esta localizada em R.values.lista_nomes_cientificos.xml
        //Futuramente substituir o Adaptador por uma lista recebida pelo Servidor

        Spinner spinner = (Spinner) findViewById(R.id.ScientificNameEdit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lista_nomes_cientificos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Recebe dados de Latitude e Longitude da Tela Anterior
        Intent it = getIntent();
        params = it.getExtras();

        //É utilizado para fechar essa activity remotamente apos a conclusao do processo
        segundoPasso = this;

    }

    public void nextStep(View view) {

        Spinner comboBox = findViewById(R.id.ScientificNameEdit);
        String nomeCientifico = comboBox.getSelectedItem().toString();
        EditText editNomeComum = findViewById(R.id.commonNameEdit);
        String nomeComum = editNomeComum.getText().toString();

        CheckBox naoSabeNome = findViewById(R.id.checkBox);
        Intent it = new Intent(this, AddTree_Step3.class);

        //Se souber o nome da arvore envia os dados dos campos
        if(!naoSabeNome.isChecked()){
            params.putString("scientificName",nomeCientifico);
            params.putString("commonName",nomeComum);
        }
        else{
            params.putString("scientificName","");
            params.putString("commonName","");
        }

        it.putExtras(params);
        startActivity(it);
    }
}
