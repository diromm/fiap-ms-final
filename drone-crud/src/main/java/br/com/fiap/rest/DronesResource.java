package br.com.fiap.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;

import br.com.fiap.persistence.models.TabelaDrones;
import br.com.fiap.services.IdDroneService;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/getDrone")
@Traced
@RequestScoped
public class DronesResource {

    @Inject
    private IdDroneService idDroneService;

    @GET
    @Path("/{idDrone}")
    // Anotações para documentação no Swagger
    @Operation(summary = "TabelaDrones", description = "Descricao da sua operação: Retorna Drones por ID.")
    @APIResponse(responseCode = "200", description = "TabelaDrones", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TabelaDrones.class)) })
    public TabelaDrones servir(@PathParam("idDrone") Integer idDrone) {
        return tratarRequisicao(idDrone);
    }

    private TabelaDrones tratarRequisicao(Integer idDrone) {
        return idDroneService.consultaPorIdDrone(idDrone);

    }

    @GET
    @Path("/drones")
    // Anotações para documentação no Swagger
    @Operation(summary = "TabelaDrones", description = "Descricao da sua operação: Retorna Todos os Drones.")
    @APIResponse(responseCode = "200", description = "TabelaDrones", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TabelaDrones.class)) })
    public List<TabelaDrones> listar() {
        return tratarRequisicao();
    }

    private List<TabelaDrones> tratarRequisicao() {
        return idDroneService.buscarTodosDrones();

    }

}
