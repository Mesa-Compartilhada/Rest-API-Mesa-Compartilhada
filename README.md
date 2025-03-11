# Mesa Compartilhada

## 📌 Descrição
Este projeto é uma API REST desenvolvida com Spring Boot, projetada para expor endpoints referente a empresas doadoras e instituições recebedoras, além de suas respectivas doações.

## 🚀 Tecnologias Utilizadas
- Java 21
- Spring Boot 3.3.2
- Spring Starter Data MongoDB
- Banco de Dados MongoDB

## 📂 Estrutura do Projeto
```
/src
  |-- main
      |-- java
          |-- com.pi.mesacompartilhada
              |-- controllers
              |-- enums
              |-- exception
              |-- models  
              |-- records   
              |-- repositories
              |-- services
              |-- states # Contém as classes dos estados das doações (concluída, cancelada, solicitada, cancelada)
      |-- resources
          |-- application.properties
  |-- test
```

## 🛠️ Configuração do Ambiente
1. Certifique-se de ter o Java 21 e o Maven instalados.
2. Clonagem do repositório:
   ```sh
   git clone https://github.com/Mesa-Compartilhada/Rest-API-Mesa-Compartilhada.git
   ```
3. Configuração do banco de dados no `application.properties`.
4. Instalação das dependências e executando a aplicação:
   ```sh
   cd Rest-API-Mesa-Compartilhada
   mvn spring-boot:run
   ```

## 📖 Documentação da API
Acesse a documentação via Swagger:
```
http://localhost:8080/swagger-ui.html
```

---

