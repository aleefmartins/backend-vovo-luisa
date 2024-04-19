# Projeto Spring Boot - Cadastro de Clientes Delicias da Vovó Luisa.

Este é um projeto backend desenvolvido em Spring Boot para gerenciar o cadastro de clientes de uma pequena lanchonete chamada Delicias da Vovó Luisa.

## Objetivo

O objetivo deste projeto é fornecer uma solução simples e eficiente para o cadastro e gerenciamento de clientes de uma lanchonete. Ele oferece endpoints para criar, atualizar, listar e excluir clientes, facilitando a gestão das informações dos clientes da lanchonete.

## Rodando o Projeto Localmente

### Pré-requisitos

- Java 11 instalado
- Maven instalado

### Passos

1. Clone o repositório para o seu ambiente local:

```
git clone https://github.com/aleefmartins/backend-vovo-luisa.git
```
2. Acesse o diretório do projeto.

```
cd backend-vovo-luisa
```

2. Compile o projeto utilizando o Maven.

```
mvn clean install
```
3. Execute o projeto 

```
mvn spring-boot:run
```

O projeto ficará acessível em: http://localhost:8083

## Parametros e Endpoints

Abaixo, segue os parametros utilizados dentro do projeto, os endpoints e como atuam dentro da aplicação.

### Parametros
O projeto utiliza os seguintes parametros:

- userId: - String (gerado automaticamente na função POST)
- userName: - String
- userEmail: - String
- userPhone: - Long
- userCpf: - Long
- userEndereco: - Long
- userDataNascimento:  - Date
- userDataCriacao: Date (gerado automaticamente na função POST)

Segue abaixo um exemplo de parametro para aplicar utilizar via Postaman:
OBS: lembrar de não enviar o userId, e o userDataCriacao na função POST.
```
"userId": "3f948691-b7b4-4224-a8b3-f4946a7912d8",
"userName": "Alef Yan Martins da Rocha",
"userPhone": 687468465,
"userCpf": 12345678901,
"userEndereco": "Rua Motorista luis de abreu 145",
"userDataNascimento": "1994-09-20",
"userEmail": "alef.mrh@gmail.com"
"userDataCriacao": "2024-04-14"
```

### Endpoints

Abaixo segue os endpoints e suas funções.

- POST - /userAccount - Este endpoint tem a função de realizar o cadastro do cliente.
- GET  - /userAccount/all - Este endpoint tem como função resgatar todos os registros cadastrados.
- GET  - /userAccount/{id} - Este endpoint tem como função resgatar um registro através do seu ID.
- PUT  - /userAccount/{id} - Este endpoint tem como função atualizar um registro a partir do seu ID.- 
- DELETE - userAccount/{id} - Este endpoint tem como função deletar um registro a partir do seu ID.
- GET  - /userAccount/byDateRange - Este endpoint tem como função trazer registros filtrados por data.
- GET  - /userAccount/downloadPDF - Este endpoint tem como função criar um arquivo PDF com os registros filtrados.




