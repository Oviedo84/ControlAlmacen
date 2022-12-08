package com.example.crud;

public class GetUsers {
    private String usuario_id, puesto, password, nombre, apellidoPaterno, apellidoMaterno;

    public GetUsers() { }

    public GetUsers(String usuario_id, String puesto, String password, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.usuario_id = usuario_id;
        this.puesto = puesto;
        this.password = password;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
}
