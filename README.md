# Microservices Lab - SE4010
## Spring Boot | Docker | API Gateway

### Project Structure
```
microservices-lab/
├── docker-compose.yml
├── item-service/        → Port 8081
├── order-service/       → Port 8082
├── payment-service/     → Port 8083
└── api-gateway/         → Port 8080  ← Single entry point
```

---

## How to Run

### 1. Build & Start All Services
```bash
docker-compose build
docker-compose up
```
Or in one command (detached):
```bash
docker-compose up --build -d
```

### 2. Check Everything is Running
```bash
docker ps
```
You should see 4 containers: `item-service`, `order-service`, `payment-service`, `api-gateway`.

### 3. View Logs
```bash
docker-compose logs api-gateway
docker-compose logs item-service
```

### 4. Stop Everything
```bash
docker-compose down
```

---

## API Endpoints (via Gateway on port 8080)

### Item Service
| Method | URL | Description |
|--------|-----|-------------|
| GET | http://localhost:8080/items | Get all items |
| POST | http://localhost:8080/items | Add a new item |
| GET | http://localhost:8080/items/{id} | Get item by ID |

**POST /items body:**
```json
{ "name": "Headphones" }
```

### Order Service
| Method | URL | Description |
|--------|-----|-------------|
| GET | http://localhost:8080/orders | Get all orders |
| POST | http://localhost:8080/orders | Place a new order |
| GET | http://localhost:8080/orders/{id} | Get order by ID |

**POST /orders body:**
```json
{ "item": "Laptop", "quantity": 2, "customerId": "C001" }
```

### Payment Service
| Method | URL | Description |
|--------|-----|-------------|
| GET | http://localhost:8080/payments | Get all payments |
| POST | http://localhost:8080/payments/process | Process a payment |
| GET | http://localhost:8080/payments/{id} | Get payment by ID |

**POST /payments/process body:**
```json
{ "orderId": 1, "amount": 1299.99, "method": "CARD" }
```

---

## Postman Testing

1. Open Postman and create a new Collection called **"Microservices Lab"**
2. Add a request for each endpoint above
3. For POST requests, set **Body → raw → JSON** and paste the sample bodies above
4. All requests go through `localhost:8080` (the API Gateway)

---

## Architecture

```
Client (Postman)
      |
      v
[API Gateway :8080]
   /items/**  ──────>  [Item Service    :8081]
   /orders/** ──────>  [Order Service   :8082]
   /payments/**─────>  [Payment Service :8083]
```

All services communicate via the `microservices-net` Docker bridge network.
Inside Docker, services use container names as hostnames (e.g., `http://item-service:8081`).
