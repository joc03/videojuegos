package com.example.asus.finalvideojuegos.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.finalvideojuegos.R;
import com.example.asus.finalvideojuegos.entidades.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgregarProductosFragment extends Fragment {
  //EDIT TEXT
   EditText txt_marca,txt_precic,txt_stock,txt_peso;
//  SPINNER
    Spinner cb_tipo;
    String[] items;


//  FECHA
    Button btnFecha,btnRegistrar;
    EditText txt_fecha,txt_tipo;
    private int dia,mes,ano;

    ProgressDialog pDialog;
    StringRequest stringRequest;//SE MODIFICA

//IMAGEN
    ImageView img_producto;



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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_agregar_productos, container, false);
        txt_marca= (EditText) vista.findViewById(R.id.txt_marca);
        txt_precic  = (EditText) vista.findViewById(R.id.txt_precio);
        txt_stock  = (EditText) vista.findViewById(R.id.txt_stock);
        txt_peso  = (EditText) vista.findViewById(R.id.txt_peso);
        //INICIO DE CAPTURAR DATOS DE COMBO BOX
        txt_tipo= (EditText)vista.findViewById(R.id.txt_tipo);
        cb_tipo =(Spinner)vista.findViewById(R.id.sp_marca);
        ArrayAdapter<CharSequence> adaptador= ArrayAdapter.createFromResource(getContext(),R.array.combo_dias,android.R.layout.simple_spinner_item);
        cb_tipo.setAdapter(adaptador);
        cb_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 txt_tipo.setText(parent.getItemAtPosition(position).toString());
                // txt_tipo.setText(parent.getSelectedItemPosition());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //FIN DE COMBO BOX


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
                        txt_fecha.setText(year+"/"+month+"/"+dayOfMonth);
                    }
                },dia,mes,ano);
                datePickerDialog.show();
            }
        });

//FIN DE FECHA
        btnRegistrar= (Button) vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrarProductos();


            }
        });

        //COMIENZO DE IMAGEN
        img_producto= (ImageView)vista.findViewById(R.id.id_imagen) ;
        img_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarFoto();
            }
        });

//FIN DE IMAGEN



        return vista;
    }

    // INICIO DE REGISTRAR PRODUCTOS
    private void RegistrarProductos() {


        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();

        // String ip=getString(R.string.ip);
        String ip=getString(R.string.ip);

        String url=ip+"/BD_APP/wsJSONRegistrarProductos.php";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();

                if (response.trim().equalsIgnoreCase("actualiza")){
                    // etiNombre.setText("");
                    //  txtDocumento.setText("");
                    //   etiProfesion.setText("");
                    Toast.makeText(getContext(),"Se ha Registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha Registrado ",Toast.LENGTH_SHORT).show();
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

                String cod_tipo=Integer.toString(cb_tipo.getSelectedItemPosition()+1);
                String marca=txt_marca.getText().toString();
                String precio=txt_precic.getText().toString();
                String peso=txt_peso.getText().toString();
                String stock=txt_stock.getText().toString();
                String fecha_venc =txt_fecha.getText().toString();

                String imagen=convertirImgString(bitmap);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("cod_tipo",cod_tipo);
                parametros.put("marca",marca);
                parametros.put("precio",precio);
                parametros.put("peso",peso);
                parametros.put("stock",stock);
                parametros.put("fec_venc",fecha_venc);
                parametros.put("imagen",imagen);

                return parametros;
            }
        };
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);



    }
    // FIN DE REGISTRAR PRODUCTOS
    private void CargarFoto() {

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
                img_producto.setImageURI(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                    img_producto.setImageBitmap(bitmap);
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
                img_producto.setImageBitmap(bitmap);

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

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;


    }




}
