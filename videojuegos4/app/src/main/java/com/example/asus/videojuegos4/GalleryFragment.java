package com.example.asus.videojuegos4;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.videojuegos4.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class GalleryFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText documento;
    TextView nombre,profesion;
    Button consultar;
    ImageButton foto;
    ProgressDialog progreso;
    ImageView campoImagen;

    File fileImagen;
    Bitmap bitmap;

    private String path;//almacena la ruta de la imagen
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Toast.makeText(getContext(), "Mensaje: "+getIP(), Toast.LENGTH_SHORT).show();


        View vista=inflater.inflate(R.layout.fragment_gallery, container, false);


        documento = (EditText) vista.findViewById(R.id.id_txt_documento);
        nombre = (TextView) vista.findViewById(R.id.id_txt_nombre);
        profesion = (TextView) vista.findViewById(R.id.id_txt_profesion);
        consultar = (Button) vista.findViewById(R.id.id_btn_consultar);
        campoImagen= (ImageView) vista.findViewById(R.id.imagenId);
        foto = (ImageButton) vista.findViewById(R.id.ibtn_foto);


        request= Volley.newRequestQueue(getContext());

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultaraWebService();
            }
        });
        //campoImagen= (ImageView) vista.findViewById(R.id.imagenId);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria();
            }
        });


        return vista;
    }

    private void AbrirGaleria() {
        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);

    }



    private void ConsultaraWebService() {

        // la vaian d ecargando de toda app
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Consultando lista 3....");
        progreso.show();

        String ip=getString(R.string.ip);

        String url=ip+"/ejemploBDRemota/wsJSONConsultarUsuarioImagen.php?documento="+documento.getText().toString();
        //ESTA LINEA HACE EL LLAMADO AL URL DEL WEB SERVICES Y EN CASO DE Q ENTRE HARA LO Q DICE EL METODO ONRESPONSE Y EN ACSO DE SALGA ERROR IRA ALO DEL ERROR
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progreso.hide();
        Toast.makeText(getContext(),"No Se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        //ERROR EN CONSOLA
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(getContext(), "Mensaje: "+response, Toast.LENGTH_SHORT).show();
        Usuario miusuario = new Usuario();
        JSONArray json=response.optJSONArray("usuario");//nombre de lo q te bota el array en el php
        JSONObject jsonObject=null;//con esto lllenas tu array de usuario con toda la info q te devuelve

        try {
            jsonObject=json.getJSONObject(0);//contie el objeto como tal en al psoc 0
            //ACA LLENAMOS LSOATRIUBTOS DE ELAARRAYDE LA WEB SERVICE  ALA CALSE USUARIO
            miusuario.setNombre(jsonObject.optString("nombre"));
            miusuario.setProfesion(jsonObject.optString("profecion"));
            miusuario.setDato(jsonObject.optString("imagen"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ACA PASAMOS DE LA CALSE USUARIO A LAS VARIABLES DEL ANDROID
        nombre.setText("Nombre: "+miusuario.getNombre());
        profesion.setText("Profesion: "+miusuario.getProfesion());

        if(miusuario.getImagen()!=null) {
            campoImagen.setImageBitmap(miusuario.getImagen());

            foto.setImageBitmap(miusuario.getImagen());
        }else{
            campoImagen.setImageResource(R.drawable.sinimagen);
            foto.setImageResource(R.drawable.sinimagen);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                foto.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    foto.setImageBitmap(bitmap);
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
                foto.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimensionarImagen(bitmap,400,400);
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


    public static String getIP(){
        List<InetAddress> addrs;
        String address = "";
        try{
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface intf : interfaces){
                addrs = Collections.list(intf.getInetAddresses());
                for(InetAddress addr : addrs){
                    if(!addr.isLoopbackAddress() && addr instanceof Inet4Address){
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
                    }
                }
            }
        }catch (Exception e){
            Log.w(TAG, "Ex getting IP value " + e.getMessage());
        }
        return address;
    }



}
