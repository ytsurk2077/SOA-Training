package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ue")
public class UniteEnsRestApi {
    UniteEnseignementBusiness helper = new UniteEnseignementBusiness();
    //getlistUE
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(200)
                .entity(helper.getListeUE())
                .build();
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)

    public Response addUe(UniteEnseignement ue) {
        if (helper.addUniteEnseignement(ue))
            return Response.status(201).entity("Created").build();
        else
            return Response.status(400).entity("erreur").build();
    }

    // Récupérer une UE par son code -> GET /api/ue/3
    @Path("/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByCode(@PathParam("code") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue != null)
            return Response.status(200).entity(ue).build();
        else
            return Response.status(404).entity("UE introuvable").build();
    }

    // Supprimer une UE -> DELETE /api/ue/delete/3
    @Path("/delete/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        if (helper.deleteUniteEnseignement(code))
            return Response.status(200).entity("UE supprimée").build();
        else
            return Response.status(404).entity("UE introuvable").build();
    }

    // Modifier une UE -> PUT /api/ue/update/3
    @Path("/update/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUe(@PathParam("code") int code, UniteEnseignement ue) {
        if (helper.updateUniteEnseignement(code, ue))
            return Response.status(200).entity("UE modifiée").build();
        else
            return Response.status(404).entity("UE introuvable").build();
    }

    // Chercher les UE d'un domaine -> GET /api/ue/domaine?nom=Informatique
    @Path("/domaine")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByDomaine(@QueryParam("nom") String domaine) {
        List<UniteEnseignement> liste = helper.getUEByDomaine(domaine);
        return Response.status(200).entity(liste).build();
    }

    // Chercher les UE d'un semestre -> GET /api/ue/semestre/1
    @Path("/semestre/{num}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeBySemestre(@PathParam("num") int semestre) {
        List<UniteEnseignement> liste = helper.getUEBySemestre(semestre);
        return Response.status(200).entity(liste).build();
    }

    // Total des crédits d'un semestre -> GET /api/ue/credits/1
    @Path("/credits/{num}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTotalCredits(@PathParam("num") int semestre) {
        int total = helper.getTotalCreditsBySemestre(semestre);
        return Response.status(200).entity("Total crédits semestre " + semestre + " : " + total).build();
    }
}
