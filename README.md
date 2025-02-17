# FilterAPI
Microsserviço para filtro e geração de recomendação e relatórios de compras.

## Descrição
Microsserviço para filtro e geração de recomendação e relatórios de compras.

Premissas

## Tecnologias Utilizadas
- **Java 11**
- **Maven** (gerenciador de dependências)
- **FeignClient**

## Funcionalidades
- **Busca compras**
- **Buscar maior comra do ano**
- **Busca Clientes fiéis**
- **Recomendação de vinhos**

## Pré-requisitos
- **Java 11**: Certifique-se de ter o Java 11 instalado em sua máquina.
- **Maven**: Certifique-se de ter o Maven instalado e configurado.
- **Docker**: *Caso queira utilizar essa opção para rodar a aplicação. Baixe o Docker Desktop ou outras soluções de gerenciamento de contêineres como Rancher, etc.
- **Docker Compose**: *Caso queira utilizar essa opção para rodar a aplicação. Baixando o Docker Desktop o Docker Compose já vem instalado. O Rancher vem com Rancher Compose que suporta arquivos docker-compose.yml do tipo v1 e v2.

## Executando a Aplicação

Disponibilizo duas formas de executar a aplicação:

- 1- Baixando e instalando ferramentas como JDK, Maven e executando comandos
- 2- Rodando um arquivo docker-compose

### 1- Baixando e instalando ferramentas como JDK, Maven e executando comandos

#### Clone o repositório:

```
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_DIRETORIO>
```

#### Compile e execute a aplicação usando Maven:


```
mvn clean install
mvn spring-boot:run
```

Acesse a aplicação em http://localhost:8080.


### 2- Rodando um arquivo docker-compose

#### Após iniciar o seu gerenciador de contêineres, no terminal, navegue até a raiz do seu projeto e execute o comando:
```
docker-compose up --build
```
Para usar o Swagger no container será necessário usar trocar localhost pelo ipv4 da sua máquina. Ex: xxx.xxx.xx.x/vehicle-registration-doc"

## Documentação e Testes de Endpoints

Para verificar os testes integrados disponíveis execute:
```
mvn test
```

### TO DO:
- Testes unitários do controlador de pessoa e da integração com api externa ViaCEP.
