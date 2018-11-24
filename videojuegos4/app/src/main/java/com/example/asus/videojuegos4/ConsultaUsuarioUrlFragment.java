package com.example.asus.videojuegos4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import static android.Manifest.permission.CAMERA;

import com.android.volley.toolbox.Volley;
import com.example.asus.videojuegos4.entidades.Usuario;
import com.example.asus.videojuegos4.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ConsultaUsuarioUrlFragment extends Fragment {


        EditText txtDocumento;
        EditText etiNombre;//SE MODIFICA
        EditText etiProfesion;//SE MODIFICA
        ProgressDialog pDialog;
        ImageButton btnConsultar;//SE MODIFICA
        Button btnActualizar, btnEliminar;//SE MODIFICA
        ImageView campoImagen;
        //Button btnCargar;



        //SE MODIFICA
        private final int MIS_PERMISOS = 100;
        private static final int COD_SELECCIONA = 10;
        private static final int COD_FOTO = 20;

        private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
        private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
        private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
        private String path;//almacena la ruta de la imagen
        File fileImagen;
        Bitmap bitmap;

        //

        JsonObjectRequest jsonObjectRequest;
        StringRequest stringRequest;//SE MODIFICA

   // RequestQueue request;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_consulta_usuario_url, container, false);

        txtDocumento = (EditText) vista.findViewById(R.id.campoDocumento);
        etiNombre = (EditText) vista.findViewById(R.id.txtNombre);
        etiProfesion = (EditText) vista.findViewById(R.id.txtProfesion);
        btnConsultar = (ImageButton) vista.findViewById(R.id.btnConsultaURL);//SE MODIFICA
       btnActualizar = (Button) vista.findViewById(R.id.btnActualizar);//SE MODIFICA
        btnEliminar = (Button) vista.findViewById(R.id.btnEliminar);//SE MODIFICA


        //btnCargar= (ImageButton) vista.findViewById(R.id.btnConsultaURL);
        campoImagen = (ImageView) vista.findViewById(R.id.imagenId);


       // request = Volley.newRequestQueue(getContext());

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });


        campoImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceActualizar();

            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceEliminar();

            }
        });


        return vista;
    }

    private void webServiceEliminar() {

        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

       // String ip="http://192.168.1.4:81";
        String ip=getString(R.string.ip);

        String url=ip+"/ejemploBDRemota/wsJSONDeleteMovil.php?documento="+txtDocumento.getText().toString();

        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("elimina")){
                    etiNombre.setText("");
                    txtDocumento.setText("");
                    etiProfesion.setText("");
                    campoImagen.setImageResource(R.drawable.sinimagen);
                    Toast.makeText(getContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    if (response.trim().equalsIgnoreCase("noExiste")){
                        Toast.makeText(getContext(),"No se encuentra la persona ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }else{
                        Toast.makeText(getContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ",""+response);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);


    }

    private void webServiceActualizar() {

        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

       // String ip=getString(R.string.ip);
        String ip=getString(R.string.ip);

        String url=ip+"/ejemploBDRemota/wsJSONUpdateMovil.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String documento=txtDocumento.getText().toString();
                String nombre=etiNombre.getText().toString();
                String profesion=etiProfesion.getText().toString();

                String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("documento",documento);
                parametros.put("nombre",nombre);
                parametros.put("profecion",profesion);
                parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);


    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;


    }

    private void mostrarDialogOpciones() {

                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                campoImagen.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    campoImagen.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                campoImagen.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimensionarImagen(bitmap,600,800);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }



    private void cargarWebService() {
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

         final String ip=getString(R.string.ip);

        String url=ip+"/ejemploBDRemota/wsJSONConsultarUsuarioUrl.php?documento="+txtDocumento.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Usuario miUsuario=new Usuario();

                JSONArray json=response.optJSONArray("usuario");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    miUsuario.setNombre(jsonObject.optString("nombre"));
                    miUsuario.setProfesion(jsonObject.optString("profesion"));
                    miUsuario.setRutaImagen(jsonObject.optString("ruta_imagen"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                etiNombre.setText(miUsuario.getNombre());//SE MODIFICA
                etiProfesion.setText(miUsuario.getProfesion());//SE MODIFICA

                String urlImagen=ip+"/ejemploBDRemota/"+miUsuario.getRutaImagen();
                urlImagen=urlImagen.replace(" ","%20");
                Toast.makeText(getContext(), "url "+urlImagen, Toast.LENGTH_LONG).show();
                cargarWebServiceImagen(urlImagen);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                pDialog.hide();
                Log.d("ERROR: ", error.toString());

            }
        });

        //request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }


    private void cargarWebServiceImagen(String urlImagen) {
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bitmap=response;//SE MODIFICA
                campoImagen.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
                Log.i("ERROR IMAGEN","Response -> "+error);
                campoImagen.setImageResource(R.drawable.sinimagen);
            }
        });
         //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);
    }





}