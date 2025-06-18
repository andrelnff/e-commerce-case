# 🐳 Docker Setup - E-commerce Project

## 📋 Arquivos de Configuração

### Dockerfile (orders-ui)
```dockerfile
# Multi-stage build otimizado para Next.js 13
# Inclui todas as dependências para build
# Runtime seguro com usuário não-root
```

### docker-compose.yaml
```yaml
# Orquestração completa dos serviços:
# - MongoDB (banco de dados)
# - RabbitMQ (message broker)  
# - Producer Service (Spring Boot)
# - Consumer Service (Spring Boot)
# - Orders UI (Next.js)
```

## 🚀 Como Executar

### Opção 1: Stack Completa (Recomendado)
```bash
# Build e execução de todos os serviços
docker-compose up --build

# Em background
docker-compose up --build -d

# Ver logs
docker-compose logs -f
```

### Opção 2: Apenas Frontend
```bash
# Build da imagem
docker build -t orders-ui ./orders-ui

# Executar o frontend
docker run -p 3000:3000 orders-ui
```

### Opção 3: Usando Makefile
```bash
# Ver comandos disponíveis
make help

# Build de todos os serviços
make build

# Iniciar stack completa
make up

# Ver logs apenas do frontend
make logs-ui

# Health check dos serviços
make health
```

## 🔧 Scripts de Automação

### Para Windows (PowerShell)
```powershell
# Executar script de build e teste
.\build-and-test.ps1
```

### Para Linux/Mac (Bash)
```bash
# Executar script de build e teste
chmod +x build-and-test.sh
./build-and-test.sh
```

## 📊 Serviços e Portas

| Serviço | Porta | URL |
|---------|-------|-----|
| Orders UI | 3000 | http://localhost:3000 |
| Consumer Service | 8080 | http://localhost:8080 |
| Producer Service | 8085 | http://localhost:8085 |
| MongoDB | 27017 | localhost:27017 |
| RabbitMQ Management | 15672 | http://localhost:15672 |
| RabbitMQ AMQP | 5672 | localhost:5672 |

## 🛡️ Health Checks

### Endpoints de Saúde
- **Frontend**: `GET /api/health`
- **Consumer**: `GET /actuator/health`
- **Producer**: `GET /actuator/health`

### Monitoramento Docker
```bash
# Status dos containers
docker-compose ps

# Health checks automáticos
docker-compose logs orders-ui | grep health
```

## 🔄 Comandos Úteis

### Desenvolvimento
```bash
# Logs em tempo real
docker-compose logs -f orders-ui

# Reconstruir apenas um serviço
docker-compose up --build orders-ui

# Parar todos os serviços
docker-compose down

# Limpar volumes e redes
docker-compose down -v --remove-orphans
```

### Produção
```bash
# Deploy completo
make deploy

# Backup do banco
make backup-db

# Restart de emergência
make restart
```

## ⚙️ Variáveis de Ambiente

### Orders UI
```env
NODE_ENV=production
NEXT_TELEMETRY_DISABLED=1
PORT=3000
HOSTNAME=0.0.0.0
```

### Consumer Service
```env
SPRING_DATA_MONGODB_URI=mongodb://admin:password@mongodb:27017/marketplace?authSource=admin
SPRING_RABBITMQ_HOST=rabbitmq
```

### Producer Service
```env
SPRING_RABBITMQ_HOST=rabbitmq
```

## 🐛 Troubleshooting

### Frontend não carrega
```bash
# Verificar logs
docker logs orders-ui

# Verificar se build foi bem-sucedido
docker-compose logs orders-ui | grep "ready"

# Reconstruir se necessário
docker-compose up --build orders-ui
```

### Problemas de Conectividade
```bash
# Verificar rede Docker
docker network ls
docker network inspect e-commerce-case_app-network

# Verificar se todos containers estão na mesma rede
docker-compose ps
```

### Problemas de Performance
```bash
# Verificar uso de recursos
docker stats

# Limpar cache Docker
docker system prune -a
```

## 📈 Monitoramento

### Logs Estruturados
```bash
# Frontend logs
docker-compose logs orders-ui

# Backend logs
docker-compose logs consumer-service

# Database logs
docker-compose logs mongodb
```

### Métricas de Performance
```bash
# Uso de recursos por container
docker stats --format "table {{.Container}}\t{{.CPUPerc}}\t{{.MemUsage}}"

# Tempo de resposta dos health checks
curl -w "%{time_total}" http://localhost:3000/api/health
```

## 🚀 Deployment em Produção

### Requisitos Mínimos
- **RAM**: 4GB
- **CPU**: 2 cores
- **Disk**: 10GB
- **Docker**: 20.10+
- **Docker Compose**: 2.0+

### Configurações Recomendadas
```yaml
# docker-compose.prod.yaml
services:
  orders-ui:
    restart: always
    deploy:
      resources:
        limits:
          memory: 512M
        reservations:
          memory: 256M
```

### Backup e Restore
```bash
# Backup automático
make backup-db

# Restore
docker exec -i mongodb mongorestore --archive < backup.archive
```

---

**✨ Projeto dockerizado com sucesso!**

*Stack completa rodando com React 18, Next.js 13, Spring Boot e todas as estilizações funcionando perfeitamente.*
