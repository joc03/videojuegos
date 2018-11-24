package com.example.asus.finalvideojuegos.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Producto {

    private Integer cod_prod;
    private Integer cod_tipo;
    private  String marca;
    private String fec_venc;
    private Double peso;
    private Double precio;
    private Integer stock;
    private  String dato;
    private Bitmap imagen;
    private String rutaImagen;

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

    public Integer getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(Integer cod_prod) {
        this.cod_prod = cod_prod;
    }

    public Integer getCod_tipo() {
        return cod_tipo;
    }

    public void setCod_tipo(Integer cod_tipo) {
        this.cod_tipo = cod_tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFec_venc() {
        return fec_venc;
    }

    public void setFec_venc(String fec_venc) {
        this.fec_venc = fec_venc;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}


