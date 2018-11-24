package com.example.asus.finalvideojuegos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
Button btnLogin;
EditText txt_nombre;
TextView txt_crearcuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.btnlogin);
        txt_nombre=(EditText)findViewById(R.id.txt_nombre);
        txt_crearcuenta=(TextView)findViewById(R.id.txt_crearcuenta);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_btn_iniciarSesion = new Intent(LoginActivity.this,MenuPrincipalActivity.class);
                startActivity(int_btn_iniciarSesion);


            }

        });

        txt_crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_btn_iniciarSesion = new Intent(LoginActivity.this,RegistrarUsuarioActivity.class);
                startActivity(int_btn_iniciarSesion);


            }
        });


    }
}
