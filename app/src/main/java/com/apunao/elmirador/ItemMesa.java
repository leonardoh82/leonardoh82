package com.apunao.elmirador;

class ItemMesa {
    String capacidad;
    String id;

    public ItemMesa() {
    }

    public ItemMesa(String capacidad, String id) {
        this.capacidad = capacidad;
        this.id = id;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
