package com.example.asus.videojuegos4;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.Calendar;


public class RegistrarProductosFragment extends Fragment {

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
    ImageView imagen;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

/// SPINNER


Spinner cb_tipo;


//FECHA
    Button btnFecha;
    EditText txt_fecha,txt_tipo;
    private int dia,mes,ano;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_registrar_productos, container, false);

        //INICIO DE CAPTURAR DATOS DE COMBO BOX
        txt_tipo= (EditText)vista.findViewById(R.id.txt_tipo);
        cb_tipo =(Spinner)vista.findViewById(R.id.sp_marca);
        ArrayAdapter <CharSequence> adaptador= ArrayAdapter.createFromResource(getContext(),R.array.combo_dias,android.R.layout.simple_spinner_item);
        cb_tipo.setAdapter(adaptador);
        cb_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // txt_tipo.setText(parent.getItemAtPosition(position).toString());
                //txt_tipo.setText(parent.getItemIdAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //FIN DE COMBO BOX


        //LLENANDO LAS VARIABLES

      /*  txt_nick= (EditText) vista.findViewById(R.id.txt_nick);
        // txt_nombre= (EditText) findViewById(R.id.txt_nombreid);
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


*/
//INICIO DE CAPTURAR DATOS DE FECHA
        btnFecha = (Button) vista.findViewById(R.id.btnFecha);
        txt_fecha = (EditText) vista.findViewById(R.id.txt_fec_venc);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH+1);
                ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txt_fecha.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },dia,mes,ano);
                datePickerDialog.show();
            }
        });

//FIN DE FECHA



        return vista;
    }



}
