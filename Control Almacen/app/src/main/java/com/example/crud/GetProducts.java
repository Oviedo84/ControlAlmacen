package com.example.crud;

import java.io.Serializable;

public class GetProducts {
    private String prod_id, nombre, tipo, cantidad;

    public GetProducts() { }

    public GetProducts(String prod_id, String nombre, String tipo, String cantidad) {
        this.prod_id = prod_id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public String getProducto_id() {
        return prod_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCantidad() {
        return cantidad;
    }

}
