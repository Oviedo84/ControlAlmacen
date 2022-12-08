package com.example.crud;

public class GetCategories {
    private String reg_id, fecha_ingreso, fecha_egreso, fecha_modificacion, prod_id, usu_id;

    public GetCategories() { }

    public GetCategories(String reg_id, String fecha_ingreso, String fecha_egreso, String fecha_modificacion, String prod_id, String usu_id) {
        this.reg_id = reg_id;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_egreso = fecha_egreso;
        this.fecha_modificacion = fecha_modificacion;
        this.prod_id = prod_id;
        this.usu_id = usu_id;
    }

    public String getRegisterId() {
        return prod_id;
    }

    public String getFechaIngreso() {
        return fecha_ingreso;
    }

    public String getFechaEgreso() { return fecha_egreso;}

    public String getFechaModificacion() {
        return fecha_modificacion;
    }

    public String getProdId() {
        return prod_id;
    }

    public String getUsuId() {
        return usu_id;
    }
}
