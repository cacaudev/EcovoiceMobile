package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;

import java.io.File;
import java.io.IOException;
import java.util.zip.Inflater;

public class Activity_edit_profile extends AppCompatActivity {
    String dir;
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PICTURE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN  |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        Button btn = (Button) findViewById(R.id.btn_edit_pic);
        registerForContextMenu(btn);
        btn.setOnCreateContextMenuListener(this);


    }
    public void change_image(View view) {
        this.openContextMenu(view);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.header_menu_pic);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile,menu);
    }
    public void tirarFoto(){
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

    public void abreGaleria(){
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

    @Override
    public boolean onContextItemSelected (MenuItem item) {

        switch (item.getItemId()) {
            case R.id.take_pic:

                tirarFoto();
                return true;
            case R.id.open_galerie:

                abreGaleria();
                return true;
            case R.id.delete_pic:
                //delete(item);
                return true;
            default:
                return false;
        }
    }




}
