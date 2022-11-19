package br.com.fiap.persistence.models.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.com.fiap.persistence.models.TabelaDrones;;

@RequestScoped
public class TabelaDroneRepository implements PanacheRepository<TabelaDrones> {

    public TabelaDrones getByIdDrone( Integer idDrone) {
        return find("id_drone", idDrone).firstResult();
    }

    public List<TabelaDrones> allDrones() {
        return listAll();
    }
    
}
