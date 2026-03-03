# Microservices Lab - SE4010
## Spring Boot | Docker | API Gateway

### Project Structure
```
microservices/
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
<img width="1564" height="705" alt="docker-build3" src="https://github.com/user-attachments/assets/0ab5ac30-443d-4889-81ff-7b6a3b675991" />

<img width="1391" height="294" alt="docker-build" src="https://github.com/user-attachments/assets/d0b4096f-c5b9-46a2-a62c-990d0806d161" />

<img width="660" height="595" alt="docker-build2" src="https://github.com/user-attachments/assets/3bae381f-c86f-44b4-b6f5-7079c89e349b" />


### 2. Check Everything is Running
```bash
docker ps
```
You should see 4 containers: `item-service`, `order-service`, `payment-service`, `api-gateway`.

<img width="1480" height="139" alt="docker-ps" src="https://github.com/user-attachments/assets/a98dafa2-27cd-41e5-895b-17456885b961" />


### 3. View Logs
```bash
docker-compose logs item-service
```

<img width="1561" height="572" alt="docker-compose-log-item" src="https://github.com/user-attachments/assets/682cebb4-411d-4b6f-86a4-e79f78512b8e" />


### 4. Stop Everything
```bash
docker-compose down
```
<img width="1400" height="226" alt="docker-compose-down" src="https://github.com/user-attachments/assets/f5c1f5cc-311a-4a9d-b950-ce3bda61b196" />

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
<img width="1439" height="535" alt="postman-post-item" src="https://github.com/user-attachments/assets/1e256949-7867-4cde-9cbc-d294e3185894" />

**GET /All items:**

<img width="1443" height="760" alt="postman-get-items" src="https://github.com/user-attachments/assets/14774de5-62e0-4d49-a3b4-fd843e423409" />

**GET /items by id:**

<img width="1444" height="519" alt="postman-get-id-item" src="https://github.com/user-attachments/assets/cab8fc1a-6fa5-4430-8e30-6161efc974c4" />


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
<img width="1442" height="554" alt="postman-post-order" src="https://github.com/user-attachments/assets/6f8f9915-b2dc-4211-87b4-5a192ac116d0" />

**GET /All orders:**

<img width="1444" height="797" alt="postman-get-orders" src="https://github.com/user-attachments/assets/450cb159-69bf-43b4-a520-b09b35062b58" />


**GET /orders by id:**

<img width="1439" height="609" alt="postman-get-id-order" src="https://github.com/user-attachments/assets/8a8e50e5-7457-49b5-81e0-7f82df4e0a9f" />


### Payment Service
| Method | URL | Description |
|--------|-----|-------------|
| GET | http://localhost:8080/payments | Get all payments |
| POST | http://localhost:8080/payments/process | Process a payment |
| GET | http://localhost:8080/payments/{id} | Get payment by ID |

**POST /payments/process body:**
```json
{ "orderId": 3, "amount": 1299.99, "method": "CARD" }
```
<img width="1444" height="627" alt="postman-post-payment" src="https://github.com/user-attachments/assets/670bf311-761f-4ff3-b5f9-f6bbdc70b046" />

**GET /All payments:**

<img width="1441" height="865" alt="postman-get-payment" src="https://github.com/user-attachments/assets/79d4a534-69a4-44d5-90ed-d385def897d6" />


**GET /payments by id:**

<img width="1448" height="597" alt="postman-get-id-payment" src="https://github.com/user-attachments/assets/3a671840-ae4f-40a9-a147-c726eed48c8a" />

---

## Postman Testing

1. Open Postman and create a new Collection called **"microservices"**
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
