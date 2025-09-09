package app;

import application.AnaliseService;
import infra.dao.MedicoDAO;
import model.Medico;
import model.PecaAnatomica;
import model.PecaSimples; // <- use a classe concreta

import java.util.UUID;

public class MainSalvarRelatorio {
    public static void main(String[] args) {
        // 1) Salvar MÉDICO (usa o construtor com 4 argumentos)
        Medico medico = new Medico(
                UUID.randomUUID().toString(),
                "Dra. Maria DAO",
                "CRM-" + (System.currentTimeMillis() % 100000), // evita UNIQUE
                "Patologia"
        );
        new MedicoDAO().insert(medico);
        System.out.println("MEDICO salvo -> ID: " + medico.getId());

        // 2) Criar PEÇA em memória (use PecaSimples, não PecaAnatomica)
        PecaAnatomica peca = new PecaSimples("Biópsia de pele");

        // 3) Gerar + SALVAR RELATÓRIO
        AnaliseService service = new AnaliseService();
        Long idRel = service.gerarESalvarRelatorio(medico, peca, "Relatório salvo via Main.");
        System.out.println("RELATORIO salvo -> ID: " + idRel);

        System.out.println("\nConfirme no Oracle:");
        System.out.println("SELECT * FROM MEDICO WHERE ID = '" + medico.getId() + "';");
        System.out.println("SELECT * FROM RELATORIO WHERE ID = " + idRel + ";");
    }
}
