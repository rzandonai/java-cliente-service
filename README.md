# Projeto: java-client-service

<img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>

<img alt="Github Actions" src="https://github.com/rzandonai/java-cliente-service/actions/workflows/gradle.yml/badge.svg"/>
<img alt="Github Actions" src="https://github.com/rzandonai/java-cliente-service/actions/workflows/codeql-analysis.yml/badge.svg"/>

##Escopo

###Endpoint para criar(HTTP_POST):

    Permite a criação de um cliente tento como campos obrigatorios o nome e o cpf do cliente
    No campo de cpf existe uma validação do padrão brasileiro
    Somente é possivel criar um item sem enviar um id fixo

###Endpoint para listar(HTTP_GET):

    Disponibiliza uma consulta de um registro individual mandando somente o id exemplo:
    https://java-client-service.herokuapp.com/clientes/1
    é possivel consultar uma lista paginada de registros enviado os parametros pagina e quantidade de registros exemplo: 
    https://java-client-service.herokuapp.com/clientes?pageNumber=0&pageSize=0
    alem da paginação é possivel fazer consultas utilizando o parametro search exemplo: 
    https://java-client-service.herokuapp.com/clientes?search=nome:ricardo&pageNumber=0&pageSize=0
    a documentação do parametro search esta em https://github.com/sipios/spring-search
    
###Endpoint para editar(HTTP_PUT):

    Deve ser enviado o id na url enviando  exemplo: https://java-client-service.herokuapp.com/clientes/1
    Permite a edição de um cliente tento como campos obrigatorios o nome e o cpf do cliente
    No campo de cpf existe uma validação do padrão brasileiro
    Somente é possivel ediar um item sem enviar um id fixo

###Endpoint para deletar(HTTP_DELETE):

    Deve ser enviado o id do registro a ser apagado na url exexmplo: https://java-client-service.herokuapp.com/clientes/1

# Inicialização do projeto

https://start.spring.io


# Disponibilização do projeto
<img alt="Heroku" src="https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white"/>

https://java-client-service.herokuapp.com

## Endpoint

https://java-client-service.herokuapp.com/clientes

# Documentação 

https://java-client-service.herokuapp.com/v3/api-docs/

https://java-client-service.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config