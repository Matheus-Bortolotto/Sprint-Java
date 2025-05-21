package model;

public class Medico {
    private String id;
    private String nome;
    private String crm;
    private String especialidade;

    public Medico(String id, String nome, String crm, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public boolean autenticar(String inputCrm) {
        return this.crm.equals(inputCrm);
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public String getEspecialidade() { return especialidade; }
}
