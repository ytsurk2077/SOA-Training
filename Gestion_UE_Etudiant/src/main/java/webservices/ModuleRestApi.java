package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/module")
public class ModuleRestApi {
    ModuleBusiness helper = new ModuleBusiness();
    UniteEnseignementBusiness ueHelper = new UniteEnseignementBusiness();

    // Liste de tous les modules -> GET /api/module/list
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListModules() {
        return Response.status(200).entity(helper.getAllModules()).build();
    }

    // Récupérer un module par matricule -> GET /api/module/M101
    @Path("/{matricule}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModule(@PathParam("matricule") String matricule) {
        Module m = helper.getModuleByMatricule(matricule);
        if (m != null)
            return Response.status(200).entity(m).build();
        else
            return Response.status(404).entity("Module introuvable").build();
    }

    // Ajouter un module -> POST /api/module/add
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addModule(Module module) {
        if (module.getUniteEnseignement() == null)
            return Response.status(400).entity("Il faut préciser l'UE du module").build();

        if (helper.addModule(module))
            return Response.status(201).entity("Created").build();
        else
            return Response.status(400).entity("UE inexistante").build();
    }

    // Modifier un module -> PUT /api/module/update/M101
    @Path("/update/{matricule}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (helper.updateModule(matricule, module))
            return Response.status(200).entity("Module modifié").build();
        else
            return Response.status(404).entity("Module introuvable").build();
    }

    // Supprimer un module -> DELETE /api/module/delete/M101
    @Path("/delete/{matricule}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (helper.deleteModule(matricule))
            return Response.status(200).entity("Module supprimé").build();
        else
            return Response.status(404).entity("Module introuvable").build();
    }

    // Modules d'un type -> GET /api/module/type/PROFESSIONNEL
    @Path("/type/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByType(@PathParam("type") Module.TypeModule type) {
        List<Module> liste = helper.getModulesByType(type);
        return Response.status(200).entity(liste).build();
    }

    // Modules d'une UE -> GET /api/module/ue/1
    @Path("/ue/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@PathParam("code") int code) {
        UniteEnseignement ue = ueHelper.getUEByCode(code);
        if (ue == null)
            return Response.status(404).entity("UE introuvable").build();

        List<Module> liste = helper.getModulesByUE(ue);
        return Response.status(200).entity(liste).build();
    }
}
