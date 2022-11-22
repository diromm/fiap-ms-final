package br.com.fiap.persistence.models.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.com.fiap.persistence.models.CitiesModel;

@RequestScoped
public class CitiesRepository implements PanacheRepository<CitiesModel> {

    public List<CitiesModel> allCities() {
        return listAll();
    }
    
}
