package model;

/** Entidade de domínio: Médico */
public class Medico {
    private String id;
    private String nome;
    private String crm;            // usamos string para manter zeros à esquerda
    private String especialidade;

    public Medico() { }

    public Medico(String id, String nome, String crm, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    /** Regras simples de autenticação: CRM precisa bater e não pode ser vazio */
    public boolean autenticar(String crmInformado) {
        if (crmInformado == null || crmInformado.isBlank()) return false;
        return crmInformado.trim().equals(this.crm);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
