# Sistema de Análise Anatômica com Reconhecimento de Voz

Este projeto simula um sistema voltado para a análise de peças anatômicas em ambientes médicos ou periciais. Utiliza conceitos da Programação Orientada a Objetos com integração a um sistema de reconhecimento de voz.

## 📚 Tecnologias Utilizadas

- Java 11+
- JUnit 5 (para testes)
- Oracle SQL Data Modeler (para modelagem de dados)
- Whisper API (para transcrição de voz - externo)
- IDE recomendada: IntelliJ IDEA ou Eclipse

## 📦 Estrutura de Pacotes

```
src/
├── controller/
│   └── RelatorioAnalise.java
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

## 🧠 Funcionalidades

- Criação e manipulação de objetos `Medico`, `PecaAnatomica`, `FormularioPadronizado` e `RelatorioAnalise`.
- Associação entre médicos e relatórios.
- Geração de relatórios com observações clínicas.
- Simulação de transcrição automática de voz.
- Utilização de herança (`Braco`, `Perna`) e polimorfismo (`identificar()`).
- Testes unitários para verificar validações básicas.


## 🗃️ Banco de Dados

O modelo relacional foi projetado usando Oracle SQL Data Modeler com as seguintes tabelas:
- `medico`
- `peca_anatomica`
- `formulario_padronizado`
- `relatorio_analise`
- `reconhecimento_vozwhisper`

## 📌 Observações

- Este projeto é acadêmico, voltado para integração entre disciplinas de DDD-Java e Banco de Dados.
- O reconhecimento de voz foi simulado com base textual.

## 📄 Licença

Projeto desenvolvido para fins educacionais. Uso livre com atribuição.
