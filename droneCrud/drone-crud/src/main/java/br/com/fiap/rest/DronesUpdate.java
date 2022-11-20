package br.com.fiap.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;

import br.com.fiap.persistence.models.TabelaDrones;
import br.com.fiap.services.PostDroneServices;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/putDrones")
@Traced
@RequestScoped
public class DronesUpdate {

    @Inject
    private PostDroneServices postDroneServices;

    @POST
    // Anotações para documentação no Swagger
    @Operation(summary = "TabelaDrones", description = "Descricao da sua operação: Insere Novo Drone na Tabela.")
    @APIResponse(responseCode = "200", description = "TabelaDrones", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TabelaDrones.class)) })
    public Response inserir(TabelaDrones requisicao) {
        postDroneServices.criarNovoDrone(requisicao.getNomeDrone(),
                requisicao.getLatitudeDrone(),
                requisicao.getLongitudeDrone(),
                requisicao.getTemperatura(),
                requisicao.getUmidade(),
                requisicao.getRastreando());
        return Response.status(Response.Status.OK).entity(requisicao.getIdDrone()).build();
    }

}
