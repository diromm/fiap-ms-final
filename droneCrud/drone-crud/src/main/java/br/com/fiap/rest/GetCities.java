package br.com.fiap.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;

import br.com.fiap.persistence.models.CitiesModel;
import br.com.fiap.services.CitiiesServices;


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/getCities")
@Traced
@RequestScoped

public class GetCities {

    @Inject
    private CitiiesServices citiiesServices;


    @GET
    // Anotações para documentação no Swagger
    @Operation(summary = "CitiesModel", description = "Descricao da sua operação: Retorna Todas as Cidades Onde podem ter Drones")
    @APIResponse(responseCode = "200", description = "CitiesModel", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CitiesModel.class)) })
    public List<CitiesModel> listar() {
        return tratarRequisicao();
    }

    private List<CitiesModel> tratarRequisicao() {
        return citiiesServices.buscarTodasCidades();

    }
    
}
