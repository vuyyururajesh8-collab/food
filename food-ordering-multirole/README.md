# Food Ordering Multi-Portal MVP

## Apps
- Main portal: http://localhost:4173
- Customer interface: http://localhost:5173
- Hotel partner interface: http://localhost:5174
- One Spring Boot API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui/index.html

## Option 1: Docker
Install Docker Desktop and run from this folder:
```bash
docker compose up --build
```
Open the three app URLs above. Stop with Ctrl+C, then `docker compose down`.

## Option 2: Run locally
Requirements: Java 21, Maven 3.9+, Node.js 20+, npm and PostgreSQL.

Create database and user in PostgreSQL:
```sql
CREATE USER fooduser WITH PASSWORD 'foodpass';
CREATE DATABASE food_ordering OWNER fooduser;
```
If you already have a database, set DB_URL, DB_USERNAME and DB_PASSWORD environment variables for backend.

Terminal 1:
```bash
cd backend
mvn spring-boot:run
```
Terminal 2:
```bash
cd portal
npm install
npm run dev -- --port 4173
```
Terminal 3:
```bash
cd customer-frontend
npm install
npm run dev -- --port 5173
```
Terminal 4:
```bash
cd hotel-frontend
npm install
npm run dev -- --port 5174
```

## Test flow
1. Open hotel portal on port 5174 and register a hotel.
2. Open customer portal on port 5173; that registered hotel should appear.
3. Return to hotel portal and use Manage menu to add items.
4. Customer opens hotel, adds food, and places a demo order.
5. Customer registration form is near the bottom of customer portal.

## API endpoints
- POST /api/v1/hotels/register
- GET /api/v1/hotels
- GET /api/v1/hotels/{id}/menu
- POST /api/v1/hotel-admin/{id}/menu
- GET /api/v1/hotel-admin/{id}/menu
- POST /api/v1/customers/register
- POST /api/v1/orders
- GET /api/v1/orders

## MVP security note
This is a local demo, not production-ready. It has no real login/session authorization yet; hotel admin APIs are not protected. Customer password handling is a placeholder and must be replaced with BCrypt + Spring Security before real use. Hotel registration stores address in the existing description field. No payment is collected. Do not use real passwords or sensitive customer data.
