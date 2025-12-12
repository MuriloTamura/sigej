
# Sigej

Sistema de Gestão de Jardinagem e Manutenção do Campus Maracanaú (SIGEJ)

**Projeto desenvolvido para a cadeira de Banco de Dados**.  
Este repositório contém um sistema acadêmico completo para gerenciamento de pessoas, funcionários, produtos, estoques, equipes de manutenção, ordens de serviço e relatórios. O foco do trabalho foi a prática com **persistência manual (JDBC/DAO)** e modelagem SQL — por exigência da disciplina, **não** foi utilizado ORM (JPA/Hibernate).

---
## 📄 Documentação do projeto

Toda a modelagem, requisitos e decisões de projeto estão no documento oficial:

https://docs.google.com/document/d/1zWjaSQcNkMTsnDLllJMLpMbtO_wUeHVEGqSziFaUji0/edit?tab=t.0

---

## 🎯 Objetivos

- Implementar um sistema realista de gestão de jardinagem e manutenção para o campus.  
- Exercitar modelagem relacional e consultas SQL avançadas.  
- Implementar persistência com **DAO + JDBC** (PreparedStatement, ResultSet, transações quando necessário).  
- Fornecer telas funcionais em Thymeleaf/Bootstrap para testes e demonstração.  
- Produzir relatórios SQL que facilitem a operação e a análise (estoque crítico, consumo por equipe, timeline de OS, etc.).

---
## 🏗 Arquitetura do projeto
### Camadas
- **Controller**: rota, validação leve, escolha entre Mock/DAO.  
- **DAO**: responsabilidade sobre SQL, ResultSet -> Model mapping.  
- **Model**: POJOs simples.  
- **Database**: ConnectionFactory centraliza acesso ao PostgreSQL.  
- **Mock**: RelatoriosMockDAO / DataLoader para desenvolvimento sem banco.
---

## 🧰 Tecnologias

### **Back-end**
- Java 21+
- Spring Boot 3
- Spring MVC
- Spring JDBC / PostgreSQL
- DAO Pattern

### **Front-end**
- Thymeleaf
- HTML5, CSS3
- JS

### **Banco de Dados**
- PostgreSQL  
- Migrações com scripts SQL
---
## ✅ Funcionalidades principais

- CRUD de Pessoas, Funcionários, Setores, Produtos, Fornecedores, Marcas.  
- Gestão de Estoque por variação de produto (produto_variacao).  
- Movimentações de estoque (entrada/saída) vinculadas a ordens de serviço.  
- Ordens de serviço com timeline (andamentos), status e prioridades.  
- Equipes de manutenção e membros.  
- Relatórios SQL prontos - **Pedidos pelo professor**:  
  - OS em aberto por prioridade e área;  
  - Materiais abaixo do ponto de reposição;  
  - Timeline de uma OS;  
  - Consumo por equipe em período;  
  - OS concluídas por tipo no ano.
---
## 🔧 Como rodar (instruções rápidas)

1. Clone o repositório:
```bash
git clone https://github.com/MuriloTamura/sigej
cd <repositorio>
```
2. Crie o banco PostgreSQL e execute o script de criação das tabelas:

Arquivo de exemplo: src/main/resources/sql/tabelas.sql 

Ajuste application.properties (src/main/resources):

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/sigej
spring.datasource.username=postgres
spring.datasource.password=SEU_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
```
3. Rodar via Maven:
```bash
mvn spring-boot:run
```
ou usar a sua IDE para executar SigejApplication
