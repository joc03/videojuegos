package com.example.asus.videojuegos4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.videojuegos4.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//ACA CAMBIAS EL COMAPCT ACTIVITY POR ACTIVITY PARA Q FINKE
public class MainActivity extends Activity  {

    //DECLARANDO VARIABLES

    EditText txt_usuario,txt_contraseña;
    Button btn_iniciarSesion;
    TextView txt_crearCuenta;


    ProgressDialog pDialog;
     RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//ESTO ES PARA Q NO ESTE EL TITLE BAR
        setContentView(R.layout.activity_main);//CON ESTO DICES Q ACTIVITY ABRE
        //DECLARANDO VARIABLES
        txt_usuario = (EditText) findViewById(R.id.txt_usuario);
        txt_contraseña = (EditText) findViewById(R.id.txt_contraseña);

        //ESTO ES EL CODIGO PARA CUANDO HAGAS CLICK EN EL BOTON
        btn_iniciarSesion = (Button)findViewById(R.id.id_btn_iniciarSesion);
        btn_iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent int_btn_iniciarSesion = new Intent(MainActivity.this,Main3Activity.class);
               startActivity(int_btn_iniciarSesion);
                //Login();

            }
        });

        //ESTO ES EL CODIGO PARA CUANDO HAGAS CLICK EN EL TEXTO

        txt_crearCuenta = (TextView)findViewById(R.id.id_txt_crearCuenta);
        String texto;
        texto="Crear Cuenta";//SE ESCRIBE EL TEXTO QUE USARAS
        SpannableString ss = new SpannableString(texto);
        //METODO PARA CUANDO QUEIRES Q ENU N TEXTO SOLO CIERTAS PALABRAS SEAN CLICKEABLES
        //SON LOS METODOS DE LAS PALABRAS CLICKEABLES
        final ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Toast.makeText(MainActivity.this,"One",Toast.LENGTH_SHORT).show();

                Intent int_btn_iniciarSesion = new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
                startActivity(int_btn_iniciarSesion);

            }
//ACA SACAS PRESIONANDO CRTL + O Y LEUGO OK  Y AGREGAS LAS DOS LINEAS DE CODIGO CON DS.
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
                ds.setUnderlineText(true);
            }
        };

      /*  ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this,"Two",Toast.LENGTH_SHORT).show();

            }
        };*/

        //ACA ESPECIFICAS DE DONDE A DODNE SON LAS PALABRAS A CLICKEAR
        ss.setSpan(clickableSpan1, 0,12,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  ss.setSpan(clickableSpan2, 6,12,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//LUEGO PONES ESTO Q DEA HI NO FUNKA
        txt_crearCuenta.setText(ss);
        txt_crearCuenta.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private void Login() {
      /*  pDialog=new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Cargando...");
        pDialog.show();*/
/*
        // String ip="http://192.168.1.4:81";
       // String ip=getString(R.string.ip);

        String url="http://192.168.1.4:81/BD_APP/wsJSONLogin2.php?documento="+txt_usuario.getText().toString().trim()+"&nombre="+txt_contraseña.getText().toString().trim();
        requestQueue= Volley.newRequestQueue(this);
        stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  pDialog.hide();
               // String rpta = response.toString().trim();
                if(response.trim().equals("existe")){
                    Toast.makeText(MainActivity.this, "LOGIN", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "LOGIN FAILED"+response.trim()+"A", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // pDialog.hide();
                Toast.makeText(MainActivity.this, "ERROR: "+error.toString(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("documento",txt_usuario.getText().toString().trim());
                params.put("nombre",txt_contraseña.getText().toString().trim());
                return  params;
            }
        };

        requestQueue.add(stringRequest);*/

String url="http://192.168.1.4:81/BD_APP/wsJSONLogin3.php";
RequestQueue requestQueue =Volley.newRequestQueue(this);
StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        String rpta=response.trim();
        if(rpta.equals("a")){
            Toast.makeText(getApplicationContext(),"exito",Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(getApplicationContext(),rpta+"fallo"+rpta,Toast.LENGTH_SHORT).show();


        }

    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"error: "+error,Toast.LENGTH_SHORT).show();

    }
}){
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String,String> params = new HashMap<>();
        params.put("username",txt_usuario.getText().toString().trim());
        params.put("password",txt_contraseña.getText().toString().trim());


        return params;
    }

};


requestQueue.add(stringRequest);









    }


}
