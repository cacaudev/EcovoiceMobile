package com.example.cacau2.ecovoicetest;

import android.app.ProgressDialog;
import android.content.ClipData;
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
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Adapters.AdapterAddImageTree;
import Base.Data.DataImageTree;
import Base.EmptyRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.cacau2.ecovoicetest.LoadTrees.mProgressDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddTree_Step3 extends Fragment implements AdapterAddImageTree.ItemClickListener {
    public static final int PICK_IMAGE = 1;
    public static final int TAKE_PICTURE = 10;
    public static final int PICK_IMAGE_MULTIPLE = 1;

    View view;
    @BindView(R.id.recycler_grid_images)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty_recycler_add_tree)
    ConstraintLayout mEmptyView;

    @BindView(R.id.btn_add_tree_images)
    Button mBtnAddImage;

    private AdapterAddImageTree adapter = null;

    Unbinder unbinder;

    List<DataImageTree> imagesEncodedList;
    DataImageTree imageEncoded;

    public ArrayList<DataImageTree> getmArrayUri() {
        if (mArrayUri != null)
            return mArrayUri;
        return null;
    }

    public ArrayList<DataImageTree> mArrayUri;
    public ArrayList<DataImageTree> aArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            aArrayList = (ArrayList<DataImageTree>) savedInstanceState.getSerializable("arrayImages");
        }

        View rootView = inflater.inflate(R.layout.activity_add_tree__step3, container, false);
        view = rootView;
        unbinder = ButterKnife.bind(this, view);

        //DataImageTree dataImageTree = new DataImageTree(Uri.parse(new File("/storage/emulated/0/DCIM/Facebook/FB_IMG_1540470878449.jpg").toString()));
       // dataImageTree = new DataImageTree();
        ArrayList<DataImageTree> data = new ArrayList<DataImageTree>();

        /*for (int i = 0; i < 15; i++)
            data.add(dataImageTree);*/
        Button finish = view.findViewById(R.id.finish);
        // Button add_photo =  view.findViewById(R.id.id_choose_pic);

        mRecyclerView.setEmptyView(mEmptyView);
        // set up the RecyclerView
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        registerForContextMenu(mBtnAddImage);
        mBtnAddImage.setOnCreateContextMenuListener(this);
        mBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insereArvore();
            }
        });


        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this,"chamei onActivityResult",Toast.LENGTH_LONG).show();

        //VERIFICA SE O CODIGO DE RETORNO É DE FOTO DA CAMERA OU DA GALERIA
        if (requestCode == TAKE_PICTURE) {
            if (data != null) {
                Log.i("TesteFoto", "Tem data");

                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Log.i("TesteFoto", "Tem bundle");
                    //recupera o bitmap retornado da camera
                    Bitmap foto = (Bitmap) bundle.get("data");

                    //atualiza imagem na tela para testar
                    //ImageView tela = (ImageView) getActivity().findViewById(R.id.photo);
                    // tela.setImageBitmap(foto);
                }
            }
        } else if (requestCode == PICK_IMAGE_MULTIPLE) {

            try {
                // When an Image is picked
                if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    imagesEncodedList = new ArrayList<DataImageTree>();
                    if (data.getData() != null) {

                        Uri mImageUri = data.getData();

                        // Get the cursor
                        Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = new DataImageTree(cursor.getString(columnIndex));
                        cursor.close();

                        mArrayUri = new ArrayList<DataImageTree>();
                        mArrayUri.add(imageEncoded);

                        //galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                        //gvGallery.setAdapter(galleryAdapter);
                        //gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                        /*ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                .getLayoutParams();
                        mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*/

                    } else {
                        if (data.getClipData() != null) {


                            ClipData mClipData = data.getClipData();
                            mArrayUri = new ArrayList<DataImageTree>();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {


                                ClipData.Item item = mClipData.getItemAt(i);

                                mArrayUri.add(new DataImageTree(item.getUri().toString()));


                                imagesEncodedList.add(imageEncoded);


                            }

                        }

                    }
                } else {
                    //Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                /*Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();*/
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

        if (mArrayUri != null) {
            if (adapter == null) {
                if (aArrayList != null) {
                    mArrayUri.addAll(aArrayList);
                }
                adapter = new AdapterAddImageTree(getActivity(), mArrayUri);
                mRecyclerView.setAdapter(adapter);
                adapter.setClickListener(this);
            } else {

                mArrayUri.addAll(aArrayList);
                adapter.insertData(mArrayUri);

            }


        }

    }

    public void tirarFoto() {
        // cria arquivo JPG dentro da pasta IntentCamera.
        // Nome do arquivo contem a hora do sistema no nome.
        String file = "IMG_" + System.currentTimeMillis() + ".jpg";

        File newfile = new File(dir + file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
        }

        Uri outputFileUri = Uri.fromFile(newfile);
        Log.i("EcoVoiceFoto", "URI: " + outputFileUri);

        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Configuração de qualidade da imagem
        it.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 640 * 640);

        //Configuração de persistencia em memoria externa
        //COMENTAR PARA onActivityResult receber uma intent não nula
        //it.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(it, TAKE_PICTURE);
    }

    public void abreGaleria() {

        Intent intent = new Intent();


        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("arrayImages", getmArrayUri());
    }


    public void insereArvore() {
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_tree_pic:

                tirarFoto();
                return true;
            case R.id.add_tree_pic_galery:

                abreGaleria();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.header_menu_add_tree);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_add_tree_pic, menu);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
