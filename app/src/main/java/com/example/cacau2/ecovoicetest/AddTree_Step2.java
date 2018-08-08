package com.example.cacau2.ecovoicetest;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddTree_Step2 extends Fragment {

    public static Activity segundoPasso;
    Bundle params;
    View view;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewPager = getActivity().findViewById(R.id.pager);
        View rootView = inflater.inflate(R.layout.activity_add_tree__step2, container, false);
        view = rootView;
        Button moreInfo =  view.findViewById(R.id.btn_more_info);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreInfo(v);


            }
        });

        Button nextStep =  view.findViewById(R.id.next);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextStep(v);

            }
        });


        //O ComboBox da lista de Nomes Cientificos das Arvores precisa ser inicializado
        //Uma lista de nomes esta localizada em R.values.lista_nomes_cientificos.xml
        //Futuramente substituir o Adaptador por uma lista recebida pelo Servidor

        Spinner spinner = (Spinner) view.findViewById(R.id.ScientificNameEdit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.lista_nomes_cientificos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Recebe dados de Latitude e Longitude da Tela Anterior

        //Intent it = getIntent();
        // params = it.getExtras();

        //É utilizado para fechar essa activity remotamente apos a conclusao do processo
        // segundoPasso = this;
        return rootView;
    }

    private void moreInfo(View v) {
        v = getActivity().findViewById(R.id.more_info_layout);
        if(v.getVisibility() == View.GONE){
            v.setVisibility(View.VISIBLE);
        }else{
            v.setVisibility(View.GONE);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN  |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );




    }

    public void nextStep(View view) {

        Spinner comboBox = getActivity().findViewById(R.id.ScientificNameEdit);
        String nomeCientifico = comboBox.getSelectedItem().toString();
        EditText editNomeComum = getActivity().findViewById(R.id.commonNameEdit);
        String nomeComum = editNomeComum.getText().toString();


        //Intent it = new Intent(this, AddTree_Step3.class);

        //Se souber o nome da arvore envia os dados dos campos

        //params.putString("scientificName",nomeCientifico);
        //params.putString("commonName",nomeComum);

        viewPager.setCurrentItem(2);
       // it.putExtras(params);
       // startActivity(it);
    }

}
