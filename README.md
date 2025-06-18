# 🛒 E-Commerce Case - Sistema de Microserviços

Um sistema de e-commerce moderno construído com arquitetura de microserviços, utilizando Spring Boot, Next.js, RabbitMQ e MongoDB. O sistema implementa um padrão de mensageria assíncrona para processamento de pedidos com separação clara entre produção e consumo de dados.

## ✨ Características Principais

- **Arquitetura de Microserviços** com comunicação assíncrona
- **GraphQL APIs** para operações flexíveis de dados
- **Message Queue** com RabbitMQ para desacoplamento
- **Banco NoSQL** MongoDB para persistência
- **Interface Moderna** em Next.js 13 com TypeScript
- **Containerização Completa** com Docker
- **Monitoramento** com health checks e métricas

## 🚀 Execução Rápida

**Pré-requisitos**: Docker e Docker Compose instalados

```bash
# 1. Clonar o repositório
git clone <repository-url>
cd e-commerce-case

# 2. Subir toda a aplicação
docker-compose up --build

# 3. Aguardar inicialização (2-3 minutos)
# 4. Acessar: http://localhost:3000
```

## 🏗️ Arquitetura do Sistema

```mermaid
graph TD
    A[Cliente Externo] -->|GraphQL| B[Producer Service]
    B -->|Publica Mensagem| C[RabbitMQ]
    C -->|Consome Mensagem| D[Consumer Service]
    D -->|Persiste| E[MongoDB]
    F[Frontend UI] -->|GraphQL Query| D
    D -->|Retorna Dados| F
```

## 📋 Serviços e Tecnologias

| Serviço | Tecnologia | URL | Porta | Função |
|---------|------------|-----|-------|--------|
| **Frontend** | Next.js 13 + TypeScript | http://localhost:3000 | 3000 | Interface web para visualização de pedidos |
| **Consumer API** | Spring Boot 3.5 + Java 17 | http://localhost:8080 | 8080 | Consome mensagens, persiste no MongoDB e serve dados via GraphQL |
| **Producer API** | Spring Boot 3.5 + Java 17 | http://localhost:8085 | 8085 | API GraphQL que recebe chamadas e publica mensagens no RabbitMQ |
| **RabbitMQ** | Message Broker + Management UI | http://localhost:15672 | 15672 | Interface de gerenciamento do message broker |
| **MongoDB** | NoSQL Database | mongodb://localhost:27017 | 27017 | Banco de dados para persistência das orders |

### 🛠️ Stack Tecnológica

**Backend (Spring Boot)**
- Java 17
- Spring Boot 3.5
- Spring Data MongoDB
- Spring GraphQL
- RabbitMQ AMQP
- Maven

**Frontend (Next.js)**
- Next.js 13 (App Router)
- TypeScript
- Apollo Client (GraphQL)
- TailwindCSS
- React Icons

**Infraestrutura**
- Docker & Docker Compose
- RabbitMQ (Message Broker)
- MongoDB (Database)
- Health Checks & Actuator

## 🐳 Comandos Docker

### Execução Completa
```bash
# Subir todos os serviços
docker-compose up --build

# Subir em background (modo daemon)
docker-compose up -d --build

# Parar todos os serviços
docker-compose down

# Parar e remover volumes (reset completo)
docker-compose down -v
```

### Rebuild Seletivo
```bash
# Rebuild apenas frontend
docker-compose up --build orders-ui

# Rebuild apenas consumer
docker-compose up --build consumer-service

# Rebuild apenas producer
docker-compose up --build producer-service

# Rebuild apenas dependências
docker-compose up rabbitmq mongodb
```

### Logs e Monitoramento
```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs de serviços específicos
docker-compose logs -f orders-ui
docker-compose logs -f consumer-service
docker-compose logs -f producer-service
docker-compose logs -f rabbitmq
docker-compose logs -f mongodb

# Ver status dos containers
docker-compose ps

# Estatísticas de uso de recursos
docker stats
```

### Manutenção
```bash
# Limpeza geral do Docker
docker system prune -f

# Remover imagens não utilizadas
docker image prune -f

# Remover volumes órfãos
docker volume prune -f

# Reset completo do projeto
docker-compose down -v && docker system prune -f && docker-compose up --build
```

## 🔐 Credenciais e Acesso

| Serviço | Usuário | Senha | URL de Acesso |
|---------|---------|-------|---------------|
| **RabbitMQ Management** | `guest` | `guest` | http://localhost:15672 |
| **MongoDB** | `admin` | `password` | mongodb://localhost:27017/marketplace |

### URLs dos Serviços
- **Frontend**: http://localhost:3000
- **Consumer GraphQL**: http://localhost:8080/graphql
- **Producer GraphQL**: http://localhost:8085/graphql
- **RabbitMQ Management**: http://localhost:15672
- **MongoDB**: mongodb://localhost:27017

## ⚡ Health Checks e Monitoramento

Verificar se todos os serviços estão funcionando:

```bash
# Frontend (Next.js)
curl http://localhost:3000/api/health
# Resposta esperada: {"status": "ok", "timestamp": "..."}

# Consumer Service (Spring Boot)
curl http://localhost:8080/actuator/health
# Resposta esperada: {"status": "UP"}

# Producer Service (Spring Boot)
curl http://localhost:8085/actuator/health
# Resposta esperada: {"status": "UP"}

# Verificar métricas (Spring Boot Actuator)
curl http://localhost:8080/actuator/metrics
curl http://localhost:8085/actuator/metrics

# RabbitMQ (via Management API)
curl -u guest:guest http://localhost:15672/api/overview
```

### Status de Saúde Esperado
✅ **Tudo funcionando**: Todos os health checks retornam status "UP" ou "ok"  
⚠️ **Problema parcial**: Algum serviço retorna erro ou não responde  
❌ **Sistema offline**: Múltiplos serviços inacessíveis

## 🔌 APIs e Endpoints

### Producer Service (http://localhost:8085)
**Função**: Recebe pedidos externos e publica no RabbitMQ

- `POST /graphql` - Endpoint GraphQL para criar pedidos
- `GET /actuator/health` - Health check do serviço
- `GET /actuator/metrics` - Métricas e estatísticas
- `GET /actuator/info` - Informações da aplicação

### Consumer Service (http://localhost:8080)
**Função**: Consome mensagens, persiste dados e serve consultas

- `POST /graphql` - Endpoint GraphQL para consultas de pedidos
- `GET /actuator/health` - Health check do serviço  
- `GET /actuator/metrics` - Métricas e estatísticas
- `GET /actuator/info` - Informações da aplicação

### Frontend Service (http://localhost:3000)
**Função**: Interface web para visualização

- `GET /` - Página principal com lista de pedidos
- `GET /api/health` - Health check do frontend
- `POST /api/graphql` - Proxy para Consumer Service

## 📊 Schemas GraphQL

### Producer Service - Criação de Pedidos
```graphql
# Mutation para criar novos pedidos
type Mutation {
  publishPlacedOrderMessage(order: OrderInput!): MessageResponse!
}

# Tipo de resposta da mutation
type MessageResponse {
  success: Boolean!         # Status do processamento (true/false)
  timestamp: String!        # Timestamp da operação (ISO 8601)
  message: String          # Mensagem descritiva do resultado
}

# Input para criar um pedido
input OrderInput {
  order: String!           # ID único do pedido
  origin: String!          # Origem do pedido (web, mobile, api)
  total: Float!            # Valor total do pedido (BigDecimal no backend)
  createdAt: String!       # Data/hora de criação (ISO 8601)
  items: [ItemInput!]!     # Lista de itens do pedido
}

# Input para itens do pedido
input ItemInput {
  name: String!            # Nome do produto
  image: String!           # URL da imagem do produto
  qty: Int!                # Quantidade (Integer)
  cost: Float!             # Preço unitário (BigDecimal no backend)
  currency: String!        # Moeda (BRL, USD, etc.)
}

# Exemplo de uso e resposta:
# REQUISIÇÃO:
mutation {
  publishPlacedOrderMessage(order: {
    order: "ORD-2024-001",
    origin: "web",
    total: 299.90,
    createdAt: "2024-06-18T10:30:00Z",
    items: [{
      name: "Smartphone",
      image: "https://example.com/phone.jpg",
      qty: 1,
      cost: 299.90,
      currency: "BRL"
    }]
  }) {
    success
    timestamp
    message
  }
}

# RESPOSTA DE SUCESSO:
{
  "data": {
    "publishPlacedOrderMessage": {
      "success": true,
      "timestamp": "2024-06-18T10:30:45.123Z",
      "message": "Pedido ORD-2024-001 recebido e enviado para processamento!"
    }
  }
}

# RESPOSTA DE ERRO (ex: falha RabbitMQ):
{
  "data": {
    "publishPlacedOrderMessage": {
      "success": false,
      "timestamp": "2024-06-18T10:30:45.456Z",
      "message": "Erro ao processar pedido: Connection refused"
    }
  }
}
```

### Consumer Service - Consulta de Pedidos
```graphql
# Query para buscar pedidos
type Query {
  placedOrders: [Order!]!   # Lista todos os pedidos
}

# Tipo do pedido
type Order {
  id: ID!                   # ID único no banco
  order: String!            # ID do pedido
  origin: String!           # Origem
  total: Float!             # Valor total
  createdAt: String!        # Data de criação
  items: [Item!]!           # Lista de itens
}

# Tipo do item
type Item {
  name: String!             # Nome do produto
  image: String!            # URL da imagem
  qty: Int!                 # Quantidade
  cost: Float!              # Preço unitário
  currency: String!         # Moeda
}

# Exemplo de uso:
# query {
#   placedOrders {
#     id
#     order
#     origin
#     total
#     createdAt
#     items {
#       name
#       image
#       qty
#       cost
#       currency
#     }
#   }
# }
```

## 📁 Estrutura Detalhada do Projeto

```
e-commerce-case/
├── 📄 docker-compose.yaml           # 🐳 Orquestração completa dos serviços
├── 📄 README.md                     # 📖 Documentação do projeto
│
├── 🏭 producer-service/             # Serviço Produtor (Spring Boot)
│   ├── 📄 Dockerfile               # Container configuration
│   ├── 📄 pom.xml                  # Maven dependencies & build config
│   ├── 📄 mvnw, mvnw.cmd          # Maven wrapper scripts
│   └── 📁 src/
│       ├── 📁 main/java/com/teste/ziyou/producerservice/
│       │   ├── 📄 ProducerServiceApplication.java    # Main Spring Boot app
│       │   ├── 📁 config/
│       │   │   └── 📄 RabbitMQConfig.java           # RabbitMQ configuration
│       │   ├── 📁 controller/                        # REST/GraphQL controllers
│       │   ├── 📁 model/                            # Domain models
│       │   └── 📁 service/                          # Business logic
│       ├── 📁 main/resources/
│       │   ├── 📄 application.yml                   # Local config
│       │   ├── 📄 application-docker.yml            # Docker config
│       │   └── 📁 graphql/
│       │       └── 📄 schema.graphqls               # GraphQL schema
│       └── 📁 test/                                 # Unit & integration tests
│
├── 📦 consumer-service/             # Serviço Consumidor (Spring Boot)
│   ├── 📄 Dockerfile               # Container configuration
│   ├── 📄 pom.xml                  # Maven dependencies & build config
│   ├── 📄 mvnw, mvnw.cmd          # Maven wrapper scripts
│   └── 📁 src/
│       ├── 📁 main/java/com/teste/ziyou/consumerservice/
│       │   ├── 📄 ConsumerServiceApplication.java   # Main Spring Boot app
│       │   ├── 📁 config/
│       │   │   └── 📄 RabbitMQConfig.java           # RabbitMQ configuration
│       │   ├── 📁 controller/
│       │   │   └── 📄 OrderController.java          # GraphQL controller
│       │   ├── 📁 messaging/
│       │   │   └── 📄 OrderListener.java            # RabbitMQ message listener
│       │   ├── 📁 model/
│       │   │   ├── 📄 Order.java                    # Order entity
│       │   │   └── 📄 Item.java                     # Item entity
│       │   ├── 📁 repository/
│       │   │   └── 📄 OrderRepository.java          # MongoDB repository
│       │   └── 📁 service/
│       │       ├── 📄 OrderService.java             # Service interface
│       │       └── 📄 OrderServiceImpl.java         # Service implementation
│       ├── 📁 main/resources/
│       │   ├── 📄 application.yml                   # Local config
│       │   ├── 📄 application-docker.yml            # Docker config
│       │   └── 📁 graphql/
│       │       └── 📄 schema.graphqls               # GraphQL schema
│       └── 📁 test/                                 # Unit & integration tests
│
└── 🎨 orders-ui/                   # Interface Web (Next.js)
    ├── 📄 Dockerfile               # Container configuration
    ├── 📄 package.json             # NPM dependencies & scripts
    ├── 📄 next.config.js           # Next.js configuration
    ├── 📄 tailwind.config.js       # TailwindCSS configuration
    ├── 📄 tsconfig.json            # TypeScript configuration
    ├── 📄 postcss.config.cjs       # PostCSS configuration
    ├── 📁 public/                  # Static assets
    └── 📁 src/
        ├── 📁 app/                 # Next.js 13 App Router
        │   ├── 📄 globals.css      # Global styles
        │   ├── 📄 layout.tsx       # Root layout component
        │   ├── 📄 page.tsx         # Home page component
        │   └── 📁 api/
        │       └── 📁 health/
        │           └── 📄 route.ts # Health check API route
        ├── 📁 components/          # React components
        │   ├── 📄 ApolloWrapper.tsx      # Apollo Client wrapper
        │   ├── 📄 AsyncWrapper.tsx       # Async operations wrapper
        │   ├── 📄 Button.tsx             # Reusable button component
        │   ├── 📄 ErrorBoundary.tsx      # Error boundary component
        │   ├── 📄 ErrorState.tsx         # Error state component
        │   ├── 📄 ItemCard.tsx           # Item display component
        │   ├── 📄 LoadingSpinner.tsx     # Loading spinner component
        │   ├── 📄 OrderDetails.tsx       # Order details component
        │   ├── 📄 OrderList.tsx          # Orders list component
        │   ├── 📄 OrdersHeader.tsx       # Header component
        │   └── 📄 OrderSummary.tsx       # Order summary component
        ├── 📁 constants/
        │   └── 📄 index.ts         # Application constants
        ├── 📁 graphql/
        │   └── 📄 queries.ts       # GraphQL queries
        ├── 📁 hooks/
        │   └── 📄 useOrders.ts     # Custom React hook for orders
        ├── 📁 lib/
        │   └── 📄 apolloClient.ts  # Apollo Client configuration
        ├── 📁 types/
        │   └── 📄 types.ts         # TypeScript type definitions
        └── 📁 utils/
            └── 📄 index.ts         # Utility functions
```

## 🔄 Fluxo de Dados e Arquitetura

### 📈 Fluxo de Criação de Pedidos
```
[Cliente/API Externa] 
    ↓ GraphQL Mutation
[Producer Service] 
    ↓ Publica mensagem (AMQP)
[RabbitMQ Queue] 
    ↓ Consome mensagem
[Consumer Service] 
    ↓ Persiste dados
[MongoDB]
```

### 📊 Fluxo de Consulta de Pedidos
```
[Frontend UI] 
    ↓ GraphQL Query
[Consumer Service] 
    ↓ Busca dados
[MongoDB] 
    ↓ Retorna resultados
[Frontend UI]
```

### 🏗️ Componentes da Arquitetura

1. **Producer Service** (Spring Boot)
   - Recebe chamadas externas via GraphQL
   - Valida e processa dados de entrada
   - Publica mensagens no RabbitMQ
   - Não persiste dados localmente

2. **Consumer Service** (Spring Boot)
   - Escuta mensagens do RabbitMQ
   - Processa e persiste dados no MongoDB
   - Serve dados via GraphQL para consultas
   - Implementa repositório MongoDB

3. **Frontend (Next.js)**
   - Interface web moderna e responsiva
   - Consome dados do Consumer Service
   - **Apenas visualização** (não cria pedidos)
   - Apollo Client para GraphQL

4. **RabbitMQ (Message Broker)**
   - Comunicação assíncrona entre serviços
   - Garantia de entrega de mensagens
   - Desacoplamento entre Producer e Consumer
   - Interface de monitoramento incluída

5. **MongoDB (Database)**
   - Armazenamento NoSQL
   - Persistência de pedidos e itens
   - Consultas flexíveis e performance

### 💡 Vantagens da Arquitetura

- **Desacoplamento**: Serviços independentes
- **Escalabilidade**: Cada serviço pode escalar separadamente
- **Resiliência**: Falhas isoladas não afetam todo o sistema
- **Performance**: Processamento assíncrono de mensagens
- **Flexibilidade**: GraphQL permite consultas customizadas

## 🛠️ Desenvolvimento Local

### Desenvolvimento Completo com Docker
```bash
# Ambiente completo (recomendado)
docker-compose up --build

# Modo daemon (background)
docker-compose up -d --build
```

### Desenvolvimento Híbrido (Serviços Locais + Dependências Docker)
```bash
# 1. Subir apenas as dependências
docker-compose up rabbitmq mongodb

# 2. Em terminais separados, executar cada serviço:

# Terminal 1 - Consumer Service
cd consumer-service
./mvnw spring-boot:run

# Terminal 2 - Producer Service  
cd producer-service
./mvnw spring-boot:run

# Terminal 3 - Frontend
cd orders-ui
npm install
npm run dev
```

### Configurações de Desenvolvimento

**Application Properties (Local)**
```yaml
# consumer-service/src/main/resources/application.yml
spring:
  data:
    mongodb:
      uri: mongodb://admin:password@localhost:27017/marketplace?authSource=admin
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

# producer-service/src/main/resources/application.yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

**Environment Variables (Frontend)**
```bash
# orders-ui/.env.local
NEXT_PUBLIC_GRAPHQL_URL=http://localhost:8080/graphql
NODE_ENV=development
```

### Scripts Úteis

```bash
# Build sem cache
docker-compose build --no-cache

# Rebuild service específico
docker-compose up --build consumer-service

# Logs em tempo real
docker-compose logs -f consumer-service

# Acessar container para debug
docker exec -it consumer-service bash
docker exec -it mongodb mongosh
```

## � Testes

### Executar Testes Unitários

**Consumer Service**
```bash
cd consumer-service
./mvnw test

# Com relatório de cobertura
./mvnw test jacoco:report
```

**Producer Service**
```bash
cd producer-service
./mvnw test

# Com relatório de cobertura
./mvnw test jacoco:report
```

**Frontend**
```bash
cd orders-ui
npm test

# Com cobertura
npm run test:coverage

# Testes E2E (se configurado)
npm run test:e2e
```

### Testes de Integração

**Testcontainers (Java)**
Os serviços Spring Boot incluem testes de integração com:
- RabbitMQ Testcontainer
- MongoDB Testcontainer
- GraphQL integration tests

**API Testing**
```bash
# Testar Producer Service
curl -X POST http://localhost:8085/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { publishPlacedOrderMessage(order: { order: \"TEST-001\", origin: \"api\", total: 100.0, createdAt: \"2024-06-18T10:00:00Z\", items: [{ name: \"Test Item\", image: \"test.jpg\", qty: 1, cost: 100.0, currency: \"BRL\" }] }) }"
  }'

# Testar Consumer Service
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{ "query": "{ placedOrders { id order origin total } }" }'
```

## 🚨 Troubleshooting

### Problemas Comuns

**🔴 Porta já está em uso**
```bash
# Verificar processos usando as portas
netstat -tulpn | grep :3000
netstat -tulpn | grep :8080
netstat -tulpn | grep :8085

# Alterar portas no docker-compose.yaml se necessário
```

**🔴 Build falha**
```bash
# Limpeza completa do Docker
docker system prune -a -f
docker volume prune -f

# Rebuild do zero
docker-compose down -v
docker-compose up --build
```

**🔴 Serviço não conecta ao RabbitMQ**
```bash
# Verificar logs do RabbitMQ
docker-compose logs rabbitmq

# Verificar conectividade
docker exec -it producer-service curl rabbitmq:15672
```

**🔴 Consumer não persiste no MongoDB**
```bash
# Verificar logs do MongoDB
docker-compose logs mongodb

# Conectar ao MongoDB para debug
docker exec -it mongodb mongosh -u admin -p password
> use marketplace
> db.orders.find()
```

**🔴 Frontend não carrega dados**
```bash
# Verificar configuração do Apollo Client
# Verificar se Consumer Service está respondendo
curl http://localhost:8080/graphql

# Verificar logs do frontend
docker-compose logs orders-ui
```

### Reset Completo do Sistema
```bash
# Parar tudo
docker-compose down -v

# Limpar Docker completamente
docker system prune -a -f
docker volume prune -f

# Rebuild completo
docker-compose up --build

# Aguardar 2-3 minutos para inicialização completa
```

### Verificação de Saúde do Sistema
```bash
# Script para verificar todos os serviços
#!/bin/bash
echo "Verificando saúde dos serviços..."

echo "Frontend: $(curl -s http://localhost:3000/api/health | jq -r '.status')"
echo "Consumer: $(curl -s http://localhost:8080/actuator/health | jq -r '.status')"
echo "Producer: $(curl -s http://localhost:8085/actuator/health | jq -r '.status')"
echo "RabbitMQ: $(curl -s -u guest:guest http://localhost:15672/api/overview | jq -r '.product_name')"
```

## 📚 Recursos Adicionais

### Documentação das Tecnologias
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring GraphQL](https://spring.io/projects/spring-graphql)
- [Next.js Documentation](https://nextjs.org/docs)
- [Apollo Client](https://www.apollographql.com/docs/react/)
- [RabbitMQ Documentation](https://www.rabbitmq.com/documentation.html)
- [MongoDB Documentation](https://docs.mongodb.com/)

### Monitoramento e Observabilidade
- **Spring Boot Actuator**: Métricas e health checks
- **RabbitMQ Management**: Interface web de monitoramento
- **Docker Stats**: Monitoramento de recursos dos containers

### Possíveis Melhorias Futuras
- [ ] Implementar autenticação JWT
- [ ] Adicionar cache com Redis
- [ ] Implementar circuit breaker pattern
- [ ] Adicionar logging estruturado (ELK Stack)
- [ ] Implementar tracing distribuído (Jaeger)
- [ ] Adicionar testes E2E automatizados
- [ ] Implementar deployment com Kubernetes
- [ ] Adicionar pipeline CI/CD

---

## 👥 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

**Desenvolvido com ❤️ para demonstrar uma arquitetura de microserviços moderna e escalável.**

# }
```

## ⚙️ Comportamento Real das APIs

### Producer Service - Como Funciona

**🔄 Fluxo de Processamento:**
1. Recebe a mutation `publishPlacedOrderMessage`
2. Valida os dados de entrada (Spring Boot validations)
3. Tenta enviar para RabbitMQ usando `RabbitTemplate.convertAndSend()`
4. Retorna `MessageResponse` com status de sucesso/erro

**✅ Cenário de Sucesso:**
- Pedido é enviado com sucesso para a fila RabbitMQ
- `success: true`
- `message: "Pedido {ID} recebido e enviado para processamento!"`
- Log: "Pedido {ID} enviado para processamento com sucesso"

**❌ Cenário de Erro:**
- Falha na conexão com RabbitMQ ou erro de serialização
- `success: false` 
- `message: "Erro ao processar pedido: {erro detalhado}"`
- Log: "Erro ao publicar pedido: {erro}"

**🔍 Detalhes Técnicos:**
- Usa `@MutationMapping` do Spring GraphQL
- `BigDecimal` para valores monetários (total, cost)
- `Integer` para quantidades
- Timestamp sempre em UTC (ISO 8601)
- Exception handling com try/catch

### Consumer Service - Como Funciona

**🔄 Fluxo de Processamento:**
1. Escuta mensagens da fila RabbitMQ (background)
2. Deserializa objeto `Order` recebido
3. Persiste no MongoDB usando Spring Data
4. Serve dados via GraphQL Query `placedOrders`

**📊 Consulta de Dados:**
- Retorna **todos** os pedidos persistidos no MongoDB
- Sem paginação ou filtros (implementação atual)
- Ordem cronológica de inserção no banco

### Teste Real da API

**Comando CURL para Producer:**
```bash
curl -X POST http://localhost:8085/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { publishPlacedOrderMessage(order: { order: \"TEST-001\", origin: \"api\", total: 100.0, createdAt: \"2024-06-18T10:00:00Z\", items: [{ name: \"Test Item\", image: \"test.jpg\", qty: 1, cost: 100.0, currency: \"BRL\" }] }) { success timestamp message } }"
  }'
```

**Resposta Real Esperada:**
```json
{
  "data": {
    "publishPlacedOrderMessage": {
      "success": true,
      "timestamp": "2024-06-18T10:30:45.123456Z",
      "message": "Pedido TEST-001 recebido e enviado para processamento!"
    }
  }
}
```
