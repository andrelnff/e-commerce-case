# � E-commerce Case - Sistema de Microserviços

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

## 📋 Serviços e Portas

| Serviço | Tecnologia | URL | Porta | Função |
|---------|------------|-----|-------|--------|
| **Frontend** | Next.js 13 | http://localhost:3000 | 3000 | Interface web para visualização de pedidos |
| **Consumer API** | Spring Boot | http://localhost:8080 | 8080 | Consome mensagens, persiste no MongoDB e serve dados via GraphQL |
| **Producer API** | Spring Boot | http://localhost:8085 | 8085 | API GraphQL que recebe chamadas e publica mensagens no RabbitMQ |
| **RabbitMQ Web** | Management UI | http://localhost:15672 | 15672 | Interface de gerenciamento do message broker |
| **MongoDB** | Database | mongodb://localhost:27017 | 27017 | Banco de dados para persistência das orders |

## � Comandos Docker

### Execução
```bash
# Subir todos os serviços
docker-compose up --build

# Subir em background
docker-compose up -d --build

# Parar todos os serviços
docker-compose down

# Parar e remover volumes
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
```

### Logs e Debug
```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs específicos
docker-compose logs -f orders-ui
docker-compose logs -f consumer-service
docker-compose logs -f producer-service

# Ver status dos containers
docker-compose ps
```

## � Credenciais de Acesso

| Serviço | Usuário | Senha | URL |
|---------|---------|-------|-----|
| **RabbitMQ** | `guest` | `guest` | http://localhost:15672 |
| **MongoDB** | `admin` | `password` | mongodb://localhost:27017 |

## ⚡ Health Checks

Verificar se todos os serviços estão funcionando:

```bash
# Frontend
curl http://localhost:3000/api/health

# Consumer Service  
curl http://localhost:8080/actuator/health

# Producer Service
curl http://localhost:8085/actuator/health
```

## 🔌 Endpoints das APIs

### Producer Service (http://localhost:8085)
- `POST /graphql` - Endpoint GraphQL para criar pedidos
- `GET /actuator/health` - Health check
- `GET /actuator/metrics` - Métricas do serviço

### Consumer Service (http://localhost:8080)
- `POST /graphql` - Endpoint GraphQL para consultas de pedidos
- `GET /actuator/health` - Health check  
- `GET /actuator/metrics` - Métricas do serviço

### GraphQL Schemas

#### Producer Service (Criação)
```graphql
type Mutation {
  createOrder(input: OrderInput!): Order
}

input OrderInput {
  customerId: String!
  items: [OrderItemInput!]!
}
```

#### Consumer Service (Consultas)
```graphql
type Query {
  orders: [Order]
  order(id: ID!): Order
}

type Order {
  id: ID!
  customerId: String!
  items: [OrderItem!]!
  totalAmount: Float!
  status: String!
  createdAt: String!
}
```

## 📁 Estrutura do Projeto

```
e-commerce-case/
├── docker-compose.yaml           # 🐳 Orquestração completa
├── producer-service/             # 🏭 Serviço Produtor (API GraphQL para criação)
│   ├── Dockerfile               #    Container Spring Boot
│   ├── pom.xml                  #    Dependências Maven
│   └── src/                     #    Código Java + GraphQL + RabbitMQ Publisher
├── consumer-service/             # 📦 Serviço Consumidor (Processa mensagens)
│   ├── Dockerfile               #    Container Spring Boot
│   ├── pom.xml                  #    Dependências Maven
│   └── src/                     #    Código Java + GraphQL + MongoDB + RabbitMQ Consumer
└── orders-ui/                   # 🎨 Interface Web (Visualização apenas)
    ├── Dockerfile               #    Container Next.js
    ├── package.json             #    Dependências NPM
    └── src/                     #    Código React/TypeScript + Apollo GraphQL
```

## 🔄 Fluxo de Dados

### Criação de Pedidos
1. **Chamadas externas** → **Producer Service** (GraphQL)
2. **Producer** → Publica mensagem → **RabbitMQ**
3. **Consumer** → Lê mensagem → **RabbitMQ** → Persiste no **MongoDB**

### Consulta de Pedidos  
1. **Frontend** → Consulta via GraphQL → **Consumer Service**
2. **Consumer** → Busca dados → **MongoDB** → Retorna lista de orders

### Arquitetura
- **Producer Service**: API GraphQL que recebe chamadas externas e publica eventos no RabbitMQ
- **Consumer Service**: Consome mensagens do RabbitMQ, persiste no MongoDB e serve dados via GraphQL
- **Frontend**: Interface web apenas para visualização das orders (não cria pedidos)
- **RabbitMQ**: Message broker para comunicação assíncrona entre serviços
- **MongoDB**: Banco de dados NoSQL para persistência das orders

## 🛠️ Desenvolvimento Local

```bash
# Para desenvolvimento sem Docker:

# 1. Subir apenas dependências
docker-compose up rabbitmq mongodb

# 2. Executar serviços localmente
cd consumer-service && mvn spring-boot:run
cd producer-service && mvn spring-boot:run  
cd orders-ui && npm run dev
```

## 🚨 Troubleshooting

### Problemas Comuns
- **Porta ocupada**: Altere as portas no `docker-compose.yaml`
- **Build falha**: Execute `docker system prune -f` para limpar cache
- **Serviço não inicia**: Verifique logs com `docker-compose logs -f <serviço>`

### Reset Completo
```bash
# Parar tudo e limpar
docker-compose down -v
docker system prune -f
docker-compose up --build
```
