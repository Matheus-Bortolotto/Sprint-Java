package model;

/**
 * Representa um médico responsável por análises anatômicas.
 */
public class Medico {
    private String nome;
    private String crm;
    private String especialidade;

    public Medico(String nome, String crm, String especialidade) {
        if (crm == null || crm.isEmpty()) throw new IllegalArgumentException("CRM não pode ser vazio");
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public String getEspecialidade() { return especialidade; }
}