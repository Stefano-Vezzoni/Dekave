## 💻 Projeto

Proposta do projeto:
A proposta do site da Dekave é apresentar serviços que foram realizados em alguns clientes, mostrando como uma galeria os serviços divididos por seus tipos.
Também contendo os parceiros e feedbacks coletados pelos clientes.

Descrição da API:
Implementação de uma API para atender a demanda do projeto que se encontrava na sua versão estática, com conteúdos fixos dependendo de alteração manual 
exigindo conhecimentos técnicos para manutenção, assim tendo a necessidade de um ambiente configurável.
Com essa API permitiu que o usuário gerencie:
- Repartições existentes e seus subníveis.
- Conteúdo textual e imagens, permitindo o usuário realizar o Upload/Download.
- Gerenciamento a visibilidade desses conteúdos, permitindo desativa-los se necessário.

Essa versão disponibilizada é o protótipo que foi apresentado para o responsável pelo projeto, a fím de repassar ao desenvolvedor front-end para poder trabalhar em suas pendências, permitido por ele a disponibilização sob licença MIT aqui presente.

## 🧪 Tecnologias

 - Java
 - Spring Boot
 - Spring Data Jpa
 - Rest Api
 - WebService
 - Upload/Download
 - Validation
 - Lombok
 - PostgreSQL
 - Springdoc OpenAPI

## 🚀 Como executar

Instalações necessárias

- IDE de sua escolha
- JDK 17.0.2
- Apache Maven 3.8.5
- PostgreSQL

Clone o projeto e acesse a pasta do mesmo.

```bash
$ git clone https://github.com/Stefano-Vezzoni/dekave.git

$ cd dekave
```

Para iniciá-lo <strong>possuindo</strong> Docker, siga os passos abaixo:
```bash
#Altere no docker-compose as ENVs seguindo de modelo o .env.example.

# Feito isso, salve o arquivo e rode na raiz do projeto o seguinte comando pelo terminal
$ docker-compose up -d

# Ele deverá criar o container da API e do Banco de dados.
```

Para iniciá-lo <strong>sem</strong> Docker, siga os passos abaixo:
```bash
#Renomeie o arquivo env.example para .env e preencha de acordo.

# Feito isso, salve o arquivo e rode o projeto de acordo com sua IDE.
```

- A API estará disponível na porta 8080, tendo disponível o [Swagger-ui](http://localhost:8080/swagger-ui/index.html#/) e para raw JSON o [Api-Docs](http://localhost:8080/v3/api-docs).

- Lembrando que nesse repositório só se encontra o Back-end do projeto,  para rodar o Front-end acesse esse repositório: [Front-end](https://github.com/Jonathan-Rios/dekave.git)

## 🔖 Modelagem de dados

A modelagem utilizada nesse protótipo foi:
![cover](.github/Dekave-DataModeling.png)

## 📝 License

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.MD)
para mais detalhes.

💠 NeverStopLearning 💠

[![Linkedin Badge](https://img.shields.io/badge/-Stefano-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/jonathan-rios-sousa-19b3431b6/)](https://www.linkedin.com/in/stefano-vezzoni-b18806186/) 
[![Gmail Badge](https://img.shields.io/badge/-stefanov.santos@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:stefanov.santos@gmail.com)](mailto:stefanov.santos@gmail.com)
