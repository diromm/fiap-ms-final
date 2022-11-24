package br.com.fiap.schema;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.model.SetBodyDefinition;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DroneSchema {

    private Integer id_drone;
    private String nome_drone;
    private Float lat_drone;
    private Float lng_drone;
    private Float temperatura;
    private Float umidade;
    private Boolean rastreando;

    public Integer getId_drone() {
        return id_drone;
    }

    public void setId_drone(Integer id_drone) {
        this.id_drone = id_drone;
    }

    public String getNome_drone() {
        return nome_drone;
    }

    public void setNome_drone(String nome_drone) {
        this.nome_drone = nome_drone;
    }

    public Float getLat_drone() {
        return lat_drone;
    }

    public void setLat_drone(Float lat_drone) {
        this.lat_drone = lat_drone;
    }

    public Float getLng_drone() {
        return lng_drone;
    }

    public void setLng_drone(Float lng_drone) {
        this.lng_drone = lng_drone;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getUmidade() {
        return umidade;
    }

    public void setUmidade(Float umidade) {
        this.umidade = umidade;
    }

    public Boolean getRastreando() {
        return rastreando;
    }

    public void setRastreando(Boolean rastreando) {
        this.rastreando = rastreando;
    }

    @Override
    public String toString() {
        return "Olá! tenho uma atualização para o drone: " + "\n"
                + id_drone + "-" + nome_drone + "." + "\n" +
                "\n" +
                "Atualmente está na latitude: " + lat_drone + "." + "\n" +
                "Longitude: " + lng_drone + "." + "\n" +
                "\n" +
                "O drone possui os seguintes dados: \n" +
                "temperatura: " + temperatura + "°C, umidade:" + umidade + "%." +
                "\n" +
                "\n" +
                "Obrigado or usar nosso serviço de push de Drones!";
    }

}
