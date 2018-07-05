package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AddTree_Step3 extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PICTURE = 10;
    String dir;
    Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree__step3);

        //Recupera dados dos ultimos passos
        Intent it = getIntent();
        params = it.getExtras();

        //Cria pasta EcoVoice dentro da pasta de fotos do sistema
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EcoVoice/";
        File newdir = new File(dir);
        newdir.mkdirs();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this,"chamei onActivityResult",Toast.LENGTH_LONG).show();

        //VERIFICA SE O CODIGO DE RETORNO É DE FOTO DA CAMERA OU DA GALERIA
        if(requestCode == TAKE_PICTURE){
            if(data != null){
                Log.i("TesteFoto","Tem data");

                Bundle bundle = data.getExtras();
                if(bundle != null){
                    Log.i("TesteFoto","Tem bundle");
                    //recupera o bitmap retornado da camera
                    Bitmap foto = (Bitmap) bundle.get("data");

                    //atualiza imagem na tela para testar
                    ImageView tela = (ImageView) findViewById(R.id.photo);
                    tela.setImageBitmap(foto);
                }
            }
        }

        if(requestCode == PICK_IMAGE){
            if(data != null){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap foto = BitmapFactory.decodeFile(picturePath);

                ImageView tela = findViewById(R.id.photo);
                tela.setImageBitmap(foto);
            }
        }
    }

    public void tirarFoto(View v){
        // cria arquivo JPG dentro da pasta IntentCamera.
        // Nome do arquivo contem a hora do sistema no nome.
        String file = "IMG_" + System.currentTimeMillis() + ".jpg";

        File newfile = new File(dir+file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {}

        Uri outputFileUri = Uri.fromFile(newfile);
        Log.i("EcoVoiceFoto","URI: "+ outputFileUri);

        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Configuração de qualidade da imagem
        it.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 640*640);

        //Configuração de persistencia em memoria externa
        //COMENTAR PARA onActivityResult receber uma intent não nula
        //it.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(it, TAKE_PICTURE);
    }

    public void abreGaleria(View view){
        //Pega conteudo do tipo Imagem pelo visualizador de Documentos embutido
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        //Pega conteudo do tipo Imagem pelo aplicativo da Galeria Preferido
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        //Cria a escolha para o usuario
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    public void insereArvore(View view){
        //
        // Cadastra arvore no banco
        //
        Double latitude = params.getDouble("lat");
        Double longitude = params.getDouble("lon");
        String nomeCientifico = params.getString("scientificName");
        String nomeComum = params.getString("commonName");

        EditText obs = findViewById(R.id.obsEdit);
        String observacao = obs.getText().toString();

        Toast.makeText(getBaseContext(), "Inserido no Banco"
                +"\nLocal: "+latitude+", "+longitude
                +"\nNome Cientifico "+nomeCientifico
                +"\nNome Comum: "+nomeComum
                +"\nObservação: "+observacao, Toast.LENGTH_LONG).show();


        //Fecha as activities apos a conclusão do processo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AddTree_Step1.primeiroPasso.finish();
                AddTree_Step2.segundoPasso.finish();
                finish();
            }
        },1000);
    }

}
