package api;

import dto.MedicoDTO;
import io.javalin.http.Context;
import model.Medico;
import service.MedicoService;

import java.util.List;

public class MedicoController {
    private final MedicoService service = new MedicoService();

    public void list(Context ctx) {
        List<Medico> all = service.findAll();
        ctx.json(all);
    }
    public void get(Context ctx) {
        String id = ctx.pathParam("id");
        var opt = service.findById(id);
        if (opt.isPresent()) ctx.json(opt.get());
        else ctx.status(404).json(new ApiError("Médico não encontrado"));
    }
    public void create(Context ctx) {
        MedicoDTO dto = ctx.bodyAsClass(MedicoDTO.class);
        Medico m = new Medico(dto.id, dto.nome, dto.crm, dto.especialidade);
        String id = service.create(m);
        ctx.status(201).json(new Medico(id, dto.nome, dto.crm, dto.especialidade));
    }
    public void update(Context ctx) {
        String id = ctx.pathParam("id");
        MedicoDTO dto = ctx.bodyAsClass(MedicoDTO.class);
        Medico m = new Medico(id, dto.nome, dto.crm, dto.especialidade);
        boolean ok = service.update(id, m);
        if (ok) ctx.json(m); else ctx.status(404).json(new ApiError("Médico não encontrado"));
    }
    public void delete(Context ctx) {
        String id = ctx.pathParam("id");
        boolean ok = service.delete(id);
        if (ok) ctx.status(204);
        else ctx.status(404).json(new ApiError("Médico não encontrado"));
    }
}
