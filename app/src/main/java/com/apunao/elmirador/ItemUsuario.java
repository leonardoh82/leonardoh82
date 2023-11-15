package com.apunao.elmirador;

class ItemUsuario {
    String id;
    String correo;
    String telefono;
    String nombre;

    public ItemUsuario() {
    }

    public ItemUsuario(String id, String correo, String telefono, String nombre) {
        this.id = id;
        this.correo = correo;
        this.telefono = telefono;
        this.nombre = nombre;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
