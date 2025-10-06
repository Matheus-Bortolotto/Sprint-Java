package api;

import controller.RelatorioAnalise;
import dto.RelatorioDTO;
import io.javalin.http.Context;
import model.Medico;
import model.PecaSimples;
import service.RelatorioService;

import java.util.List;

public class RelatorioController {
    private final RelatorioService service = new RelatorioService();

    public void list(Context ctx) {
        List<RelatorioAnalise> all = service.findAll();
        ctx.json(all);
    }
    public void get(Context ctx) {
        Long id = Long.valueOf(ctx.pathParam("id"));
        var opt = service.findById(id);
        if (opt.isPresent()) ctx.json(opt.get());
        else ctx.status(404).json(new ApiError("Relatório não encontrado"));
    }
    public void create(Context ctx) {
        RelatorioDTO dto = ctx.bodyAsClass(RelatorioDTO.class);
        // Monta entidades mínimas
        Medico m = new Medico(); m.setId(dto.medicoId);
        PecaSimples p = new PecaSimples(dto.pecaDescricao);
        RelatorioAnalise r = new RelatorioAnalise(m, p, dto.observacoes);
        Long id = service.create(r);
        r.setId(id);
        ctx.status(201).json(r);
    }
    public void update(Context ctx) {
        Long id = Long.valueOf(ctx.pathParam("id"));
        RelatorioDTO dto = ctx.bodyAsClass(RelatorioDTO.class);
        Medico m = new Medico(); m.setId(dto.medicoId);
        PecaSimples p = new PecaSimples(dto.pecaDescricao);
        RelatorioAnalise r = new RelatorioAnalise(m, p, dto.observacoes);
        boolean ok = service.update(id, r);
        if (ok) { r.setId(id); ctx.json(r); }
        else ctx.status(404).json(new ApiError("Relatório não encontrado"));
    }
    public void delete(Context ctx) {
        Long id = Long.valueOf(ctx.pathParam("id"));
        boolean ok = service.delete(id);
        if (ok) ctx.status(204);
        else ctx.status(404).json(new ApiError("Relatório não encontrado"));
    }
}
