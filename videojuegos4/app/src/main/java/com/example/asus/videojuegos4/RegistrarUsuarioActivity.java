package com.example.asus.videojuegos4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.videojuegos4.entidades.VolleySingleton;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    //DECLARANDO VARIABLES

    EditText txt_nick,txt_nombre,txt_apellido,txt_direccion,txt_credit_card,txt_correo,txt_num_celular,txt_pass_usu;
    Button btnRegistrar;
    ProgressDialog progreso;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//ESTO ES PARA Q NO ESTE EL TITLE BAR
        setContentView(R.layout.activity_registrar_usuario);

        //LLENANDO LAS VARIABLES

        txt_nick= (EditText) findViewById(R.id.txt_nick);
        txt_nombre= (EditText) findViewById(R.id.txt_nombre);
        txt_apellido= (EditText) findViewById(R.id.txt_apellidos);
        txt_direccion= (EditText) findViewById(R.id.txt_direccion);
        txt_credit_card= (EditText) findViewById(R.id.txt_credit_card);
        txt_correo= (EditText) findViewById(R.id.txt_correo);
        txt_num_celular= (EditText) findViewById(R.id.txt_num_celular);
        txt_pass_usu= (EditText) findViewById(R.id.txt_password);


        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });




    }

    private void Registrar() {
       // Toast.makeText(RegistrarUsuarioActivity.this, "DATOS: "+txt_nick.getText().toString()+txt_apellido.getText().toString()+txt_direccion.getText().toString()+txt_credit_card.getText().toString()+txt_correo.getText().toString()+txt_num_celular.getText().toString()+txt_pass_usu.getText().toString(), Toast.LENGTH_SHORT).show();
      //  +txt_nick.getText().toString()+txt_apellido.getText().toString()+txt_direccion.getText().toString()+txt_credit_card.getText().toString()+txt_correo.getText().toString()+txt_num_celular.getText().toString()+txt_pass_usu.getText().toString()

        progreso=new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();



        String ip=getString(R.string.ip);

        String url=ip+"/BD_APP/wsJSONRegistroMovil.php";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")){

                    txt_nick.setText("");
                    txt_nombre.setText("");
                    txt_apellido.setText("");
                    txt_direccion.setText("");
                    txt_credit_card.setText("");
                    txt_correo.setText("");
                    txt_num_celular.setText("");
                    txt_pass_usu.setText("");

                    Toast.makeText(RegistrarUsuarioActivity.this,"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarUsuarioActivity.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                    Log.i("RPTA 2 :",txt_nick.getText().toString()+txt_pass_usu.getText().toString()+txt_nombre.getText().toString()+txt_apellido.getText().toString()+txt_direccion.getText().toString()+txt_credit_card.getText().toString()+txt_correo.getText().toString()+txt_num_celular.getText().toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarUsuarioActivity.this,"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

              /*  String cod_tipo=txt_nick.getText().toString();
                String descripcion=txt_pass_usu.getText().toString();*/


                String nick=txt_nick.getText().toString().trim();
                String pass_usu=txt_pass_usu.getText().toString().trim();
                String nombre=txt_nombre.getText().toString().trim();
                String apellido=txt_apellido.getText().toString().trim();
                String direccion=txt_direccion.getText().toString().trim();
                String credit_card=txt_credit_card.getText().toString().trim();
                String correo=txt_correo.getText().toString().trim();
                String num_celular=txt_num_celular.getText().toString().trim();
                String cod_tipo="1";





                Map<String,String> parametros=new HashMap<>();

                parametros.put("nick",nick);
                parametros.put("pass_usu",pass_usu);
                parametros.put("nombre",nombre);
                parametros.put("apellido",apellido);
                parametros.put("direccion",direccion);
                parametros.put("credit_card",credit_card);
                parametros.put("correo",correo);
                parametros.put("num_celular",num_celular);
                parametros.put("cod_tipo",cod_tipo);

             /*  parametros.put("cod_tipo",cod_tipo);
                parametros.put("descripcion",descripcion);*/


                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(RegistrarUsuarioActivity.this).addToRequestQueue(stringRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);




    }
}
