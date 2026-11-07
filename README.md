<h1 align="center">🎓 Sistema Acadêmico</h1>

<p align="center">
  Aplicação desktop para gestão acadêmica de alunos, desenvolvida em <b>Java (Swing)</b> com persistência em <b>MySQL</b> e organizada no padrão <b>DAO</b>.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-0D1117?style=for-the-badge&logo=openjdk&logoColor=ED8B00" alt="Java 21" />
  <img src="https://img.shields.io/badge/Swing-Desktop-0D1117?style=for-the-badge&logo=java&logoColor=E6EDF3" alt="Swing" />
  <img src="https://img.shields.io/badge/MySQL-0D1117?style=for-the-badge&logo=mysql&logoColor=4479A1" alt="MySQL" />
  <img src="https://img.shields.io/badge/IDE-Eclipse-0D1117?style=for-the-badge&logo=eclipseide&logoColor=A78BFA" alt="Eclipse" />
</p>

---

## ✨ Funcionalidades

A interface é dividida em quatro abas:

| Aba | O que faz |
| --- | --- |
| **Dados Pessoais** | Cadastro do aluno identificado pelo RGM: nome, mãe, nascimento, CPF, e-mail, endereço completo e celular |
| **Curso** | Vincula o aluno a curso, campus e período |
| **Notas e Faltas** | Lança nota e faltas por disciplina e semestre, exibidas em tabela |
| **Boletim** | Consolida o desempenho do aluno com a situação calculada automaticamente |

- **CRUD completo** de alunos pelo menu *Aluno*: salvar, alterar, consultar e excluir
- **Situação automática** de cada disciplina: *Aprovado* (nota ≥ 6), *Reprovado por Nota* ou *Reprovado por Falta* (mais de 15 faltas)
- **Busca** por RGM e por nome
- **Integridade referencial**: ao excluir um aluno, curso e notas são removidos em cascata (`ON DELETE CASCADE`)

## 🏗️ Arquitetura

```text
SistemaAcademico/
├── SistemaAcademico.sql          # script de criação do banco
└── src/br/SistemaAcademico/
    ├── modeel/                   # entidades: Aluno, CursoAluno, NotaFalta
    ├── DAO/                      # acesso a dados (JDBC): AlunoDAO, CursoAlunoDAO, NotaFaltaDAO, Conexao
    └── view/                     # interface Swing: TelaPrincipal
```

O projeto separa **modelo**, **acesso a dados** e **interface**: a tela nunca executa SQL diretamente — ela conversa com as classes DAO, que usam `PreparedStatement` via JDBC.

### Modelo de dados

```text
aluno (rgm PK) ──┬──< curso_aluno  (id, rgm FK, curso, campus, periodo)
                 └──< notas_faltas (id, rgm FK, disciplina, semestre, nota, faltas, status)
```

## 🚀 Como executar

**Pré-requisitos:** JDK 21, MySQL e o driver [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

1. Crie o banco executando o script:
   ```bash
   mysql -u SEU_USUARIO -p < SistemaAcademico/SistemaAcademico.sql
   ```
2. Importe a pasta `SistemaAcademico/` no Eclipse (*File → Import → Existing Projects into Workspace*).
3. Adicione o `mysql-connector-j-*.jar` ao *Build Path* do projeto.
4. Ajuste URL, usuário e senha do banco em `src/br/SistemaAcademico/DAO/Conexao.java`.
5. Execute a classe `view/TelaPrincipal.java`.

## 🧠 Aprendizados

- Modelagem relacional com chaves estrangeiras e índices
- Padrão DAO e separação de responsabilidades
- Construção de interfaces desktop com Swing (abas, menus, tabelas)
- Regras de negócio aplicadas ao modelo (cálculo da situação do aluno)

---

<p align="center">Desenvolvido por <a href="https://github.com/Beatrizxx06">Beatriz Xavier</a></p>
