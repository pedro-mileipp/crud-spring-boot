# Projeto CRUD API

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.4-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-orange?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.8.1-yellow?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)


# 🚀 Objetivo

O objetivo desse projeto é desenvolver um CRUD (Create, Update, Read, Delete) por meio da API projetada

# 🖥️ Preparação do ambiente e tecnologias aplicadas

## Requisitos necessários:
- JDK 21
- Maven
- Postman ou Insomnia (para fazer requisições HTTP)
- PgAdmin (PostgreSQL)
- IDE (IntelliJ, STS, Eclipse, VSCode, etc)

## Como será desenvolvido
- Uso do Spring Initalizr para criar o repositório do projeto
- PostgreSQL
- Flyway e conceito de Migrations

## Spring Initializr
Vá no [Spring Initializr](https://start.spring.io/) e siga as seguintes instruções:
- Adicione as dependências:
    - Spring Web;
    - Spring Data JPA;
    - Flyway Migration;
    - PostgreSQL Driver;
- Selecione a linguagem Java;
- Packaging sendo `jar`;
- Selecione a versão do seu JDK;

<img src="./images/initializr.png" width=70%>

# Camadas de um projeto Spring

## 🏗️ Arquitetura do Projeto

Este projeto segue o padrão de **arquitetura em camadas** utilizando o Spring Boot, conforme ilustrado abaixo:

<img src="./images/layersSpring.png" width=70%>

### 1. Controller (Camada de Apresentação)
- Recebe requisições HTTP e envia respostas.
- Converte dados de entrada (JSON, XML, etc.) para objetos Java.
- Encaminha as requisições para a camada **Service**.
- **Anotação principal:** `@RestController` ou `@Controller`.

### 2. Service (Camada de Negócio)
- Contém as regras de negócio da aplicação.
- Faz a ponte entre o **Controller** e o **Repository**.
- Aplica validações, cálculos e orquestra chamadas de métodos.
- **Anotação principal:** `@Service`.

### 3. Repository (Camada de Acesso a Dados)
- Responsável pela interação com o banco de dados.
- Executa operações de CRUD.
- Utiliza JPA/Hibernate ou SQL nativo.
- **Anotação principal:** `@Repository` (no Spring Data, pode ser implícita).

### 4. Model (Camada de Modelo)
- Representa os dados da aplicação.
- Inclui:
  - **Entities**: Classes anotadas com `@Entity` que mapeiam tabelas do banco.
  - **DTOs**: Objetos para transferência de dados entre camadas.
- Usada por todas as camadas para representar informações.

---

### **Fluxo de execução**:
1. O cliente envia uma requisição → **Controller**
2. O Controller chama o **Service**
3. O Service consulta o **Repository**
4. O Repository interage com o **Model** e o banco
5. A resposta sobe de volta ao cliente

<br>

# 👨‍💻 SHOW ME THE CODE!!!

## Estrutura do projeto
```
C:.
└───crud
    │   .gitattributes
    │   .gitignore
    │   HELP.md
    │   mvnw
    │   mvnw.cmd
    │   pom.xml
    │   README.MD
    │
    ├───.mvn
    │   └───wrapper
    │           maven-wrapper.properties
    │
    ├───.vscode
    │       settings.json
    │
    ├───images
    │       initializr.png
    │       layersSpring.png
    │
    ├───src
    │   ├───main
    │   │   ├───java
    │   │   │   └───com
    │   │   │       └───pedro
    │   │   │           └───crud
    │   │   │               │   CrudApplication.java
    │   │   │               │
    │   │   │               ├───controller
    │   │   │               │       ProductController.java
    │   │   │               │
    │   │   │               ├───dtos
    │   │   │               │       ProductDto.java
    │   │   │               │
    │   │   │               ├───model
    │   │   │               │       Product.java
    │   │   │               │
    │   │   │               └───repository
    │   │   │                       ProductRepository.java
    │   │   │
    │   │   └───resources
    │   │       │   application.properties
    │   │       │
    │   │       ├───db
    │   │       │   └───migration
    │   │       │           V1__create-table-product.sql
    │   │       │
    │   │       ├───static
    │   │       └───templates
    │   └───test
    │       └───java
    │           └───com
    │               └───pedro
    │                   └───crud
    │                           CrudApplicationTests.java
    │
    └───target
        ├───classes
        │   │   application.properties
        │   │
        │   ├───com
        │   │   └───pedro
        │   │       └───crud
        │   │           │   CrudApplication.class
        │   │           │
        │   │           ├───controller
        │   │           │       ProductController.class
        │   │           │
        │   │           ├───dtos
        │   │           │       ProductDto.class
        │   │           │
        │   │           ├───model
        │   │           │       Product.class
        │   │           │
        │   │           └───repository
        │   │                   ProductRepository.class
        │   │
        │   └───db
        │       └───migration
        │               V1__create-table-product.sql
        │
        └───test-classes
            └───com
                └───pedro
                    └───crud
                            CrudApplicationTests.class
```

## Camadas do Projeto

### Model (Entidade)

- Classe responsável por mapear os dados da aplicação para o banco de dados.
```java
@Entity(name = "product")
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private BigDecimal price;
}
```
- Explicando as anotações:
  - `@Entity`: Marca a classe como uma entidade JPA (tabela lógica no modelo de persistência).
  - `name="product"`: Nome usado em consultas JPQL (não no banco).
  - `@Table(name="product")`: Nome da tabela no banco.
  - `@Id`: Define o atributo id como chave primária.
  - `@GeneratedValue(strategy = GenerationType.AUTO)`: O valor do id será gerado automaticamente pelo provedor JPA.
  - UUID: Tipo usado para gerar identificadores únicos universais.

### DTO (Data Transfer Object)

- Camada intermediária usada para transferir dados sem expor diretamente a entidade.
```java
public record ProductDto(String name, BigDecimal price) {}
```

### Repository

- Interface que fornece métodos prontos para manipulação do banco via Spring Data JPA.
```java
public interface ProductRepository extends JpaRepository<Product, UUID> {}
```

- JpaRepository<Product, UUID>: Gera métodos CRUD automaticamente (findAll, save, deleteById, etc.).
- Não é necessário implementar nada manualmente para operações básicas.

### Controller

- O ProductController é a camada responsável por expor a API REST para gerenciar produtos no sistema, recebendo requisições HTTP, processando-as e retornando respostas adequadas. Ele utiliza o ProductRepository para interagir com o banco de dados.
- Todos os endpoints estão mapeados sob a rota base /products.
- O controller interage diretamente com o ProductRepository para acessar e manipular os dados, e utiliza ProductDto como objeto de transferência para criação e atualização.

```java
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    ProductRepository repository; 

    @GetMapping
    public ResponseEntity getAll(){
        List<Product> listProducts = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") UUID id){
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        
        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductDto dto){
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") UUID id) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        String productName = product.get().getName();
        repository.delete(product.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product: '" + productName + "' deleted successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id") UUID id, @RequestBody ProductDto dto) {
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(dto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));
    }
    
}
```
- Explicando as anotações:
  - `@RestController`: Define que a classe é um controlador REST, retornando os dados diretamente no corpo da resposta (JSON por padrão).
  - `@RequestMapping("/products")`: Define o caminho base `/products` para todos os endpoints da classe.
  - `@Autowired`: Injeta automaticamente a instância de uma dependência (no caso, `ProductRepository`).
  - `@GetMapping`: Mapeia requisições HTTP GET para um método.
  - `@PostMapping`: Mapeia requisições HTTP POST para um método.
  - `@PutMapping`: Mapeia requisições HTTP PUT para um método.
  - `@DeleteMapping`: Mapeia requisições HTTP DELETE para um método.
  - `@PathVariable`: Liga o valor de uma variável na URL a um parâmetro do método.
  - `@RequestBody`: Faz o binding do corpo da requisição (JSON) para um objeto Java.

## Endpoints do Controller
### 1. GET /products
- Descrição: Retorna a lista de todos os produtos cadastrados.
- Retorno:
  - `200 OK` + lista de produtos (JSON).

### 2. GET /products/{id}
- Descrição: Retorna um produto específico pelo seu ID (UUID).
- Parâmetro:
  - id (UUID) – Identificador único do produto.
- Retorno:
  - `302 FOUND` + dados do produto, se encontrado.
  - `404 NOT_FOUND` se não existir.

### 3. POST /products

- Descrição: Cria um novo produto no banco de dados.
  - Corpo da requisição:
  - Objeto JSON seguindo o formato de ProductDto.
  - Retorno:
    - `201 CREATED` + dados do produto criado.

### 4. DELETE /products/{id}
- Descrição: Remove um produto existente pelo ID.
- Parâmetro:
  - id (UUID) – Identificador único do produto.
- Retorno:
  - `200 OK`+ mensagem de confirmação.
  - `404 NOT_FOUND` se não existir.

5. PUT /products/{id}
- Descrição: Atualiza os dados de um produto existente.
- Parâmetro:
  - id (UUID) – Identificador único do produto.
- Corpo da requisição:
  - Objeto JSON seguindo o formato de ProductDto.
- Retorno:
  - `200 OK` + dados do produto atualizado.
  - `404 NOT_FOUND` se não existir.


# ✅ Conclusão

Este projeto demonstrou como construir uma API REST completa utilizando **Spring Boot**, com operações de **CRUD** sobre produtos, integrando **PostgreSQL** como banco de dados e utilizando boas práticas como **DTOs**, **camadas de arquitetura** e **migrations com Flyway**.

Com este projeto você aprendeu a:  
- Estruturar uma aplicação Spring Boot em camadas (Controller, Service, Repository, Model).  
- Criar endpoints REST completos para criar, ler, atualizar e deletar recursos.  
- Conectar a aplicação a um banco de dados PostgreSQL.  
- Utilizar DTOs para transferir dados entre camadas sem expor entidades diretamente.  
- Aplicar migrations com Flyway para versionamento do banco de dados.  
- Gerar identificadores únicos com **UUID** para os registros.  

---

# ⚡ Como testar a aplicação

1. Certifique-se de que o **PostgreSQL** está rodando e a configuração no `application.properties` está correta.  
2. Execute a aplicação via IDE ou `./mvnw spring-boot:run`.  
3. Utilize ferramentas como **Postman** ou **Insomnia** para fazer requisições HTTP aos endpoints:  

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET    | /products | Retorna todos os produtos |
| GET    | /products/{id} | Retorna um produto específico |
| POST   | /products | Cria um novo produto |
| PUT    | /products/{id} | Atualiza um produto existente |
| DELETE | /products/{id} | Deleta um produto existente |

---

# 📖 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)  
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)  
- [Flyway Migration](https://flywaydb.org/documentation/)  
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)  

---

# 🌟 Próximos passos / Melhorias

- Adicionar camada **Service** para separar lógica de negócios do Controller.  
- Implementar **validações** (Bean Validation) para dados de entrada.  
- Criar **tratamento global de exceções** com `@ControllerAdvice`.  
- Adicionar **autenticação e autorização** (Spring Security).  
- Criar **testes unitários e de integração** para os endpoints.
