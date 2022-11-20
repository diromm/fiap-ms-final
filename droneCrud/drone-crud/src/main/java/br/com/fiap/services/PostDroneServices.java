package br.com.fiap.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.fiap.persistence.models.TabelaDrones;
import br.com.fiap.persistence.models.repository.TabelaDroneRepository;

@RequestScoped
public class PostDroneServices {

    @Inject
    private TabelaDroneRepository tabelaDroneRepository;

    @Transactional

    public TabelaDrones criarNovoDrone(
            String nomeDrone,
            Float latitudeDrone,
            Float longitudeDrone,
            Float temperatura,
            Float umidade,
            Boolean rastreando) {

        TabelaDrones novoDrone = new TabelaDrones(
                nomeDrone,
                latitudeDrone,
                longitudeDrone,
                temperatura,
                umidade,
                rastreando);

        tabelaDroneRepository.persist(novoDrone);

        return novoDrone;

    }

}
