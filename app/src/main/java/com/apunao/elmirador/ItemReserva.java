package com.apunao.elmirador;

class ItemReserva {
    String cantidad;
    String cliente;
    String fecha;
    String hora;
    String mesa;
    String nombre;
    String id;

    public ItemReserva() {
    }

    public ItemReserva(String cantidad, String cliente, String fecha, String hora, String mesa, String nombre, String id) {
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.mesa = mesa;
        this.nombre = nombre;
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
