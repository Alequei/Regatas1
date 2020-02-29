package com.hdelacruz.regatasasistencia.models;

public class Entrada {

    private Long id_asist;

    private String codigo_e;

    private String dni_e;

    private String fecha_e;

    private String hora_e;

    private String imagen;

    public Long getId_asist() {
        return id_asist;
    }

    public void setId_asist(Long id_asist) {
        this.id_asist = id_asist;
    }

    public String getCodigo_e() {
        return codigo_e;
    }

    public void setCodigo_e(String codigo_e) {
        this.codigo_e = codigo_e;
    }

    public String getDni_e() {
        return dni_e;
    }

    public void setDni_e(String dni_e) {
        this.dni_e = dni_e;
    }

    public String getFecha_e() {
        return fecha_e;
    }

    public void setFecha_e(String fecha_e) {
        this.fecha_e = fecha_e;
    }

    public String getHora_e() {
        return hora_e;
    }

    public void setHora_e(String hora_e) {
        this.hora_e = hora_e;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "id_asist=" + id_asist +
                ", codigo_e='" + codigo_e + '\'' +
                ", dni_e='" + dni_e + '\'' +
                ", fecha_e='" + fecha_e + '\'' +
                ", hora_e='" + hora_e + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
