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

| Serviço | Tecnologia | URL | Porta | Status |
|---------|------------|-----|-------|--------|
| **Frontend** | Next.js 13 | http://localhost:3000 | 3000 | ✅ Pronto |
| **Consumer API** | Spring Boot | http://localhost:8080 | 8080 | ✅ Pronto |
| **Producer API** | Spring Boot | http://localhost:8085 | 8085 | ✅ Pronto |
| **RabbitMQ Web** | Management UI | http://localhost:15672 | 15672 | ✅ Pronto |
| **MongoDB** | Database | mongodb://localhost:27017 | 27017 | ✅ Pronto |

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

## 📁 Estrutura do Projeto

```
e-commerce-case/
├── docker-compose.yaml           # 🐳 Orquestração completa
├── producer-service/             # 🏭 Serviço Produtor
│   ├── Dockerfile               #    Container Spring Boot
│   ├── pom.xml                  #    Dependências Maven
│   └── src/                     #    Código Java
├── consumer-service/             # 📦 Serviço Consumidor  
│   ├── Dockerfile               #    Container Spring Boot
│   ├── pom.xml                  #    Dependências Maven
│   └── src/                     #    Código Java + GraphQL
└── orders-ui/                   # 🎨 Interface Web
    ├── Dockerfile               #    Container Next.js
    ├── package.json             #    Dependências NPM
    └── src/                     #    Código React/TypeScript
```

## 🔄 Fluxo de Dados

1. **Frontend** → Envia pedidos via GraphQL → **Consumer Service**
2. **Consumer** → Salva no **MongoDB** → Publica evento → **RabbitMQ**  
3. **Producer** → Consome eventos → **RabbitMQ** → Processa pedidos
4. **Frontend** → Consulta dados → **Consumer Service** → **MongoDB**

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
