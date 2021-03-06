package com.example.asus.videojuegos4;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.videojuegos4.adapter.UsuariosAdapter;
import com.example.asus.videojuegos4.entidades.Usuario;
import com.example.asus.videojuegos4.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ConsultarListaUsuariosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerUsuarios;
    ArrayList<Usuario> listaUsuarios;

    ProgressDialog progress;

   //  RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    public ConsultarListaUsuariosFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_consultar_lista_usuarios, container, false);

        listaUsuarios=new ArrayList<>();

        recyclerUsuarios= (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);

       // request= Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;

    }

    private void cargarWebService() {

        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando Lista1...");
        progress.show();



        String ip=getString(R.string.ip);

        String url=ip+"/ejemploBDRemota/wsJSONConsultarLista.php";

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
         //request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario usuario=null;

        JSONArray json=response.optJSONArray("usuario");

        try {

            for (int i=0;i<json.length();i++){
                usuario=new Usuario();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                usuario.setDocumento(jsonObject.optInt("documento"));
                usuario.setNombre(jsonObject.optString("nombre"));
                usuario.setProfesion(jsonObject.optString("profecion"));
                listaUsuarios.add(usuario);
            }
            progress.hide();
            UsuariosAdapter adapter=new UsuariosAdapter(listaUsuarios);
            recyclerUsuarios.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }

}
