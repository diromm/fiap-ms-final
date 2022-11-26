package br.com.fiap.persistence.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_drones", schema = "public")
@NamedNativeQueries({
        @NamedNativeQuery(name = TabelaDrones.BUSCAR_DRONE_POR_ID, query = "SELECT \n" +
                "    id_drone  \n " +
                "    nome_drone  \n" +
                "    lat_drone  \n " +
                "    lng_drone  \n " +
                "    temperatura  \n " +
                "    umidade  \n " +
                "    rastreando  \n " +
                "    FROM public.tb_drones WHERE id_drone = :idDrone")
})

public class TabelaDrones implements Serializable {
    public static final String BUSCAR_DRONE_POR_ID = "BUSCAR_DRONE_POR_ID";

    public TabelaDrones() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_drone",unique=true, nullable = false)
    private Integer idDrone;

    @Column(name = "nome_drone")
    private String nomeDrone;

    @Column(name = "lat_drone")
    private Float latitudeDrone;

    @Column(name = "lng_drone")
    private Float longitudeDrone;

    @Column(name = "temperatura")
    private Float temperatura;

    @Column(name = "umidade")
    private Float umidade;

    @Column(name = "rastreando")
    private Boolean rastreando;

    public TabelaDrones(String nomeDrone, Float latitudeDrone, Float longitudeDrone, Float temperatura, Float umidade,
            Boolean rastreando) {
        this.nomeDrone = nomeDrone;
        this.latitudeDrone = latitudeDrone;
        this.longitudeDrone = longitudeDrone;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.rastreando = rastreando;
    }

    public static String getBuscarDronePorId() {
        return BUSCAR_DRONE_POR_ID;
    }

    public Integer getIdDrone() {
        return idDrone;
    }

    public void setIdDrone(Integer idDrone) {
        this.idDrone = idDrone;
    }

    public String getNomeDrone() {
        return nomeDrone;
    }

    public void setNomeDrone(String nomeDrone) {
        this.nomeDrone = nomeDrone;
    }

    public Float getLatitudeDrone() {
        return latitudeDrone;
    }

    public void setLatitudeDrone(Float latitudeDrone) {
        this.latitudeDrone = latitudeDrone;
    }

    public Float getLongitudeDrone() {
        return longitudeDrone;
    }

    public void setLongitudeDrone(Float longitudeDrone) {
        this.longitudeDrone = longitudeDrone;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idDrone == null) ? 0 : idDrone.hashCode());
        result = prime * result + ((nomeDrone == null) ? 0 : nomeDrone.hashCode());
        result = prime * result + ((latitudeDrone == null) ? 0 : latitudeDrone.hashCode());
        result = prime * result + ((longitudeDrone == null) ? 0 : longitudeDrone.hashCode());
        result = prime * result + ((temperatura == null) ? 0 : temperatura.hashCode());
        result = prime * result + ((umidade == null) ? 0 : umidade.hashCode());
        result = prime * result + ((rastreando == null) ? 0 : rastreando.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TabelaDrones other = (TabelaDrones) obj;
        if (idDrone == null) {
            if (other.idDrone != null)
                return false;
        } else if (!idDrone.equals(other.idDrone))
            return false;
        if (nomeDrone == null) {
            if (other.nomeDrone != null)
                return false;
        } else if (!nomeDrone.equals(other.nomeDrone))
            return false;
        if (latitudeDrone == null) {
            if (other.latitudeDrone != null)
                return false;
        } else if (!latitudeDrone.equals(other.latitudeDrone))
            return false;
        if (longitudeDrone == null) {
            if (other.longitudeDrone != null)
                return false;
        } else if (!longitudeDrone.equals(other.longitudeDrone))
            return false;
        if (temperatura == null) {
            if (other.temperatura != null)
                return false;
        } else if (!temperatura.equals(other.temperatura))
            return false;
        if (umidade == null) {
            if (other.umidade != null)
                return false;
        } else if (!umidade.equals(other.umidade))
            return false;
        if (rastreando == null) {
            if (other.rastreando != null)
                return false;
        } else if (!rastreando.equals(other.rastreando))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "tabelaDrone [idDrone=" + idDrone + ", nomeDrone=" + nomeDrone + ", latitudeDrone=" + latitudeDrone
                + ", longitudeDrone=" + longitudeDrone + ", temperatura=" + temperatura + ", umidade=" + umidade
                + ", rastreando=" + rastreando + "]";
    }
}
