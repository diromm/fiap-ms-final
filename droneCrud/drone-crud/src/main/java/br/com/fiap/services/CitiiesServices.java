package br.com.fiap.services;

import br.com.fiap.persistence.models.CitiesModel;
import br.com.fiap.persistence.models.repository.CitiesRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Traced

public class CitiiesServices {

    @Inject
    private CitiesRepository citiesRepository;

    public List<CitiesModel> buscarTodasCidades() {
        return citiesRepository.allCities();
    }
}
