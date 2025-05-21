package model;


/** Método público */
public class Medico {

    /** Atributo privado */
    private String id;

    /** Atributo privado */
    private String nome;

    /** Atributo privado */
    private String crm;

    /** Atributo privado */
    private String especialidade;


    /** Método público */
    public Medico(String id, String nome, String crm, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }


    /** Método público */
    public boolean autenticar(String inputCrm) {
        return this.crm.equals(inputCrm);
    }


    /** Método público */
    public String getId() { return id; }

    /** Método público */
    public String getNome() { return nome; }

    /** Método público */
    public String getCrm() { return crm; }

    /** Método público */
    public String getEspecialidade() { return especialidade; }
}
