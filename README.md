# Sistema de Análise Anatômica com Reconhecimento de Voz

Este projeto simula um sistema voltado para a análise de peças anatômicas em ambientes médicos ou periciais.  
Aplica **Domain Driven Design (DDD)** em Java, integra com **Oracle DB** via JDBC e usa um módulo externo de **reconhecimento de voz** (Whisper).

---

## 📚 Tecnologias Utilizadas

- **Java 11+**
- **JUnit 5** (para testes unitários e de integração)
- **Oracle Database** (com script `schema_oracle.sql`)
- **Whisper API** (para transcrição de voz — externo via Python)
- **Oracle SQL Data Modeler** (para modelagem de dados)
- IDE recomendada: **IntelliJ IDEA** ou **Eclipse**

---

## 📦 Estrutura de Pacotes

```
src/
├── application/
│   └── AnaliseService.java
├── controller/
│   └── RelatorioAnalise.java
├── infra/
│   ├── db/
│   │   └── OracleConnectionFactory.java
│   └── dao/
│       ├── DAO.java
│       ├── MedicoDAO.java
│       └── RelatorioDAO.java
├── model/
│   ├── Medico.java
│   ├── PecaAnatomica.java
│   ├── PecaSimples.java
│   ├── Braco.java
│   ├── Perna.java
│   └── FormularioPadronizado.java
├── service/
│   └── ReconhecimentoVozWhisper.java
└── view/
    └── Principal.java
```

---

## 🧠 Funcionalidades

- **Autenticação de médico** com validação de CRM.
- **Cadastro/seleção de peça anatômica** (ex.: rim, fígado, braço, perna).
- **Checklist padronizado** com marcações estruturadas.
- **Transcrição de voz** usando Whisper, integrada ao relatório.
- **Geração de relatórios** com observações clínicas e salvamento em arquivo `.txt`.
- **Persistência em banco Oracle** via DAOs (`MedicoDAO`, `RelatorioDAO`).
- **Herança e Polimorfismo** (ex.: `PecaSimples` herda `PecaAnatomica`, `identificar()` sobrescrito).
- **Testes unitários e de integração** para validar regras de negócio e CRUD no banco.

---

## 🗃️ Banco de Dados

O modelo relacional está no arquivo **`db/schema_oracle.sql`**.  
Principais tabelas:

- `medico (id, nome, crm, especialidade)`
- `peca (id, descricao)`
- `relatorio (id, medico_id, peca_id, observacoes, data_analise)`

> Observação: As tabelas `peca_anatomica`, `formulario_padronizado` e `reconhecimento_vozwhisper` foram abstraídas para simplificar a persistência.  
As informações dessas entidades são tratadas na camada de aplicação, mas centralizadas no **relatório**.

---

## ⚙️ Configuração da Conexão

Classe: **`infra.db.OracleConnectionFactory`**

```java
String url  = "Sua_url";
String user = "seu_usuario";
String pass = "sua_senha";
Connection conn = OracleConnectionFactory.getConnection();
```

> Pode sobrescrever `URL/USER/PASS` via variáveis de ambiente `ORACLE_URL`, `ORACLE_USER`, `ORACLE_PASSWORD`.

---

## 📌 Observações

- Projeto acadêmico, integrando **DDD-Java** + **Banco de Dados Oracle** + **Integração com Python/Whisper**.
- O reconhecimento de voz é demonstrado via integração externa (não exige microfone em tempo real para testes).
- Código segue boas práticas de **POO**: encapsulamento, herança, polimorfismo e separação por camadas.

---

## 📄 Licença

Projeto desenvolvido para fins educacionais.  
Uso livre com atribuição.
