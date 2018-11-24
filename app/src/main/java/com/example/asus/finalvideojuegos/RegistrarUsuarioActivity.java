package com.example.asus.finalvideojuegos;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.finalvideojuegos.entidades.VolleySingleton;

import java.util.HashMap;
import java.util.Map;


public class RegistrarUsuarioActivity extends AppCompatActivity {
    EditText txt_nick,txt_password,txt_celular,txt_nombre,txt_apellidos,txt_direccion,txt_creditcard,txt_correo;
    Button btnRegistrar;
    ProgressDialog pDialog;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        txt_nick=(EditText)findViewById(R.id.txt_nick);
        txt_password=(EditText)findViewById(R.id.txt_password);
        txt_celular=(EditText)findViewById(R.id.txt_num_celular);


        txt_nombre=(EditText)findViewById(R.id.txt_nombre);
        txt_apellidos=(EditText)findViewById(R.id.txt_apellidos);
        txt_direccion=(EditText)findViewById(R.id.txt_direccion);
        txt_creditcard=(EditText)findViewById(R.id.txt_credit_card);
        txt_correo=(EditText)findViewById(R.id.txt_correo);

        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
            }
        });
    }

    private void RegistrarUsuario() {


        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        // String ip=getString(R.string.ip);
        String ip=getString(R.string.ip);

        String url=ip+"/BD_APP/wsJSONRegistrarUsuarios.php";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){

                    txt_nick.setText("");
                    txt_password.setText("");
                    txt_celular.setText("");
                    txt_nombre.setText("");
                    txt_apellidos.setText("");
                    txt_direccion.setText("");
                    txt_creditcard.setText("");
                    txt_correo.setText("");


                    Toast.makeText(RegistrarUsuarioActivity.this,"Se ha Registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarUsuarioActivity.this,"No se ha Registrado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarUsuarioActivity.this,"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nick=txt_nick.getText().toString();
                String password=txt_password.getText().toString();
               String celular=txt_celular.getText().toString();


                String nombre=txt_nick.getText().toString();
                String apellidos=txt_apellidos.getText().toString();
                String direccion=txt_direccion.getText().toString();
                String credit_card=txt_creditcard.getText().toString();
                //String correoaa="joc_rl03@hotmial.ocm";//este en caso de q falle
               String correo=txt_correo.getText().toString();
               String cod_tipo="2";

               // String profesion="122546";

               // String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("nick",nick);
                parametros.put("password",password);
                parametros.put("celular",celular);

                parametros.put("nombre",nombre);
                parametros.put("apellidos",apellidos);
                parametros.put("direccion",direccion);
                parametros.put("credit_card",credit_card);
                parametros.put("correo",correo);
                parametros.put("cod_tipo",cod_tipo);


               // parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(RegistrarUsuarioActivity.this).addToRequestQueue(stringRequest);



    }
}
