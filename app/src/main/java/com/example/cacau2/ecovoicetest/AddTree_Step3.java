package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
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

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cacau2.ecovoicetest.LoadTrees.mProgressDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddTree_Step3 extends Fragment {
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PICTURE = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_tree__step3, container, false);
    }




    ProgressDialog mProgressDialog;
    EditText latitude, longitude;
    SessionManagement session;
    String dir;
    Bundle params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recupera dados dos ultimos passos
        Intent it = getActivity().getIntent();
        params = it.getExtras();

        //Cria pasta EcoVoice dentro da pasta de fotos do sistema
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/EcoVoice/";
        File newdir = new File(dir);
        newdir.mkdirs();
        session = new SessionManagement(getActivity().getApplicationContext());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this,"chamei onActivityResult",Toast.LENGTH_LONG).show();

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
                    ImageView tela = (ImageView) getActivity().findViewById(R.id.photo);
                    tela.setImageBitmap(foto);
                }
            }
        }

        if(requestCode == PICK_IMAGE){
            if(data != null){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap foto = BitmapFactory.decodeFile(picturePath);

                ImageView tela = getActivity().findViewById(R.id.photo);
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

        //EditText obs = findViewById(R.id.obsEdit);
      // String observacao = obs.getText().toString();

        /*Toast.makeText(getActivity().getBaseContext(), "Inserido no Banco"
                +"\nLocal: "+latitude+", "+longitude
                +"\nNome Cientifico "+nomeCientifico
                +"\nNome Comum: "+nomeComum);*/





        createSingleTree(latitude, longitude, session.getUserID());

    }


    public void createSingleTree(Double latitude, Double longitude, int user_id) {
        mProgressDialog = new ProgressDialog(getActivity().getBaseContext());
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Cadastrando....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        //Prepara o objeto tree como formulário
        TreeForm treeToSend = new TreeForm(latitude, longitude, user_id);
        TreePOST requestObject = new TreePOST(treeToSend);

        TreeEndpointsAPI apiService = ApiClient.getClient().
                create(TreeEndpointsAPI.class);

        Call<ResponseBody> createTask = apiService.createTree(session.getToken(), requestObject);

        createTask.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    try {
                        JSONObject apiResponse = new JSONObject(response.body().string());
                        Log.v("API", "Código: " + response.code() +
                                " Status: " + apiResponse.getString("status") +
                                " Message: " + apiResponse.getString("message"));

                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Árvore criada com sucesso", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                }
                if (response.code() == 422) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.v("API", "Código: "
                                + response.code() + " STATUS: "
                                + jsonObject.getString("status")
                                + " Message: " + jsonObject.getString("message"));
                        Toast.makeText(getActivity().getApplicationContext(), "Email já em uso", Toast.LENGTH_SHORT).show();
                        //TODO
                    } catch (Exception e) {
                        Log.v("API", "Código: " + response.code() + " Erro de Exceção: " + e.getMessage());
                    }
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("API", "Api Failure: " + t.toString());
                mProgressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                //TODO
            }
        });
    }


    public String getAddress(double latitude, double longitude) {
        String address = "Not found";
        try {
            Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).toString();
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return address;
    }

}
