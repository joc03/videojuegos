package com.example.asus.videojuegos4.entidades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Usuario {

    private Integer documento;
    private  String nombre;
    private String profesion;
    private  String dato;
    private Bitmap imagen;
    private String rutaImagen;

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
        try {
            //codigficar apra recibir bien la iamgen
            byte [] byteCode= Base64.decode(dato,Base64.DEFAULT);
            //una ves decodificado pueda leerlo como tipo imagen
           // this.imagen=BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
            int alto=100;//alto en pixeles
            int ancho=150;//ancho en pixeles

            Bitmap foto =BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
            this.imagen=Bitmap.createScaledBitmap(foto,alto,ancho,true);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
}
