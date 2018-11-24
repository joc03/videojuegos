package com.example.asus.finalvideojuegos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.asus.finalvideojuegos.R;
import com.example.asus.finalvideojuegos.entidades.Producto;
import com.example.asus.finalvideojuegos.entidades.VolleySingleton;

import java.util.List;

public class ProductosImagenUrlAdapter extends RecyclerView.Adapter<ProductosImagenUrlAdapter.UsuariosHolder> {

    List<Producto> listProducto;
    // RequestQueue request;
    Context context;


    public ProductosImagenUrlAdapter(List<Producto> listProducto, Context context) {
        this.listProducto = listProducto;
        this.context=context;
        //request=Volley.newRequestQueue(context);
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.listaproductos,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {


        holder.txtMarca.setText(listProducto.get(position).getMarca().toString());
        holder.txtPrecio.setText(listProducto.get(position).getPrecio().toString());
        holder.txtPeso.setText(listProducto.get(position).getPeso().toString());
        holder.txtStock.setText(listProducto.get(position).getStock().toString());

        if (listProducto.get(position).getRutaImagen()!=null){
            // holder.imagen.setImageBitmap(listaUsuarios.get(position).getImagen());
            cargarImagenWebService(listProducto.get(position).getRutaImagen(),holder);
        }else {
            holder.imagen.setImageResource(R.drawable.sinimagen);


        }
    }

    private void cargarImagenWebService(String rutaImagen, final UsuariosHolder holder) {
        String ip =context.getString(R.string.ip);
        String urlImagen=ip+"/BD_APP/"+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                holder.imagen.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No se ha podido establecer conexión con el servidor" , Toast.LENGTH_LONG).show();

            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtMarca,txtPrecio,txtPeso,txtStock;
        ImageView imagen;

        public UsuariosHolder(View itemView) {
            super(itemView);
            txtMarca= (TextView) itemView.findViewById(R.id.txt_marca);
            txtPrecio= (TextView) itemView.findViewById(R.id.txt_precio);
            txtPeso= (TextView) itemView.findViewById(R.id.txt_peso);
            txtStock= (TextView) itemView.findViewById(R.id.txt_stock);

            imagen=(ImageView) itemView.findViewById(R.id.idImagen);
        }
    }

}
