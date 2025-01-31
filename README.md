# ToDo List API

## 📌 Sobre o Projeto

O **ToDo List API** é uma aplicação backend desenvolvida em **Java com Spring Boot** que permite a gestão de tarefas e usuários. O sistema inclui funcionalidades como:

- Cadastro de usuários com criptografia de senha (BCrypt);
- Autenticação básica via **Basic Auth** para acesso às tarefas;
- Criação, listagem e atualização de tarefas vinculadas a usuários;
- Controle de permissões, garantindo que apenas o usuário dono da tarefa possa modificá-la;
- Tratamento global de exceções para respostas mais informativas.

## 🛠 Tecnologias Utilizadas

O projeto foi desenvolvido com as seguintes tecnologias:

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database (Banco em Memória para Testes)**
- **Jakarta Persistence API (JPA)**
- **Lombok**
- **BCrypt (para hashing de senhas)**
- **Maven** (gerenciamento de dependências)
- **Docker** (opcional, para conteinerização da aplicação)

## 📌 Estrutura do Projeto

```
todolist
│── src
│   ├── main/java/br/com/VHAPorfiro/todolist
│   │   ├── errors (tratamento de erros)
│   │   ├── filter (autenticação de usuários)
│   │   ├── task (repositório, controlador e modelo de tarefas)
│   │   ├── user (repositório, controlador e modelo de usuários)
│   │   ├── utils (funções auxiliares)
│   │   ├── TodolistApplication.java (classe principal)
│── pom.xml (dependências do projeto)
│── Dockerfile (para conteinerização da aplicação)
```

## 📌 Funcionalidades

### 🔹 Usuários
#### Criar Usuário:
**Endpoint:**
```http
POST /users/
```
**Payload:**
```json
{
  "username": "usuario123",
  "name": "Nome do Usuário",
  "password": "senha123"
}
```
**Resposta de Sucesso (201 Created):**
```json
{
  "id": "uuid",
  "username": "usuario123",
  "name": "Nome do Usuário",
  "createAt": "2025-01-31T10:00:00"
}
```

### 🔹 Tarefas
#### Criar Tarefa:
**Endpoint:**
```http
POST /tasks/
```
**Headers:**
```http
Authorization: Basic usuario:senha
```
**Payload:**
```json
{
  "title": "Estudar Spring Boot",
  "description": "Assistir aulas sobre Spring Boot",
  "startAt": "2025-02-01T10:00:00",
  "endAt": "2025-02-01T12:00:00",
  "priority": "Alta"
}
```
**Restrições:**
- A data de início deve ser maior que a atual.
- A data de término deve ser posterior à data de início.

#### Listar Tarefas do Usuário:
**Endpoint:**
```http
GET /tasks/
```
**Headers:**
```http
Authorization: Basic usuario:senha
```
**Resposta:**
```json
[
  {
    "id": "uuid",
    "title": "Estudar Spring Boot",
    "description": "Assistir aulas sobre Spring Boot",
    "startAt": "2025-02-01T10:00:00",
    "endAt": "2025-02-01T12:00:00",
    "priority": "Alta"
  }
]
```

#### Atualizar Tarefa:
**Endpoint:**
```http
PUT /tasks/{id}
```
**Headers:**
```http
Authorization: Basic usuario:senha
```
**Payload:**
```json
{
  "title": "Estudar Spring Security"
}
```

## 📌 Segurança e Autenticação
A API usa **Basic Auth** para proteger os endpoints de tarefas. O filtro `FilterTaskAuth.java`:
- Lê o cabeçalho `Authorization`.
- Decodifica as credenciais do usuário.
- Verifica o hash da senha armazenada no banco.
- Se as credenciais estiverem corretas, a requisição prossegue.

## 📌 Tratamento de Exceções
A classe `ExceptionHandlerController.java` captura exceções, garantindo respostas mais descritivas:
- **HttpMessageNotReadableException** → Retorna um erro `400 Bad Request` com detalhes sobre a falha no JSON enviado.

📌 **Desenvolvido por Victor Hugo Aguiar Porfiro**

