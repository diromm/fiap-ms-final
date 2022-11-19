package br.com.fiap.services;


import br.com.fiap.persistence.models.TabelaDrones;
import br.com.fiap.persistence.models.repository.TabelaDroneRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


@RequestScoped
@Traced
public class IdDroneService {

    @Inject
    private TabelaDroneRepository tabelaDroneRepository;

    public TabelaDrones consultaPorIdDrone(Integer idDrone){
        return tabelaDroneRepository.getByIdDrone(idDrone);
    }

    public List<TabelaDrones> buscarTodosDrones () {
        return tabelaDroneRepository.allDrones();
    }
    
}
