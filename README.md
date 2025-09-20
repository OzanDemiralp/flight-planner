# Flight Planner API

A Spring Boot REST API for planning round-trip flights, considering departure, return dates, vacation length, and non-working days.

---

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Example Requests](#example-requests)
- [Notes](#notes)
- [License](#license)

---

## Features
- Plan round-trip flights with optimized routes and prices.
- Filter trips by non-working days (weekends + configured Turkish national holidays in `application.properties`).
- Retrieve all cities or search a city by code.
- Sort trips based on non-working days and total price.
- Simple and clean REST endpoints for easy integration.

---

## Technologies
- Java 17
- Spring Boot 3
- Spring Data JPA
- Lombok
- Maven
- PostgreSQL
- Flyway
- MapStruct
- Springdoc OpenAPI

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3+
- Database (configured via `application.properties`)

### Installation
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/flight-planner.git
   ```
2. Navigate to the project folder:
   ```bash
   cd flight-planner
   ```
3. Start the application with Docker Compose (this will run both PostgreSQL and the Spring Boot):
   ```bash
   docker-compose up --build
   ```

The API will be available at http://localhost:8080/api/flightplan.

### API Endpoints
- `GET /hello` - Test endpoint to check API availability.
- `POST /planflight` - Plan a round-trip flight (request body required).
- `GET /cities` - Retrieve all cities.
- `GET /cities/{code}` - Retrieve a city by its code.

---

## Example Request

### POST /planflight
```bash
curl -X POST http://localhost:8080/api/flightplan/planflight \
-H "Content-Type: application/json" \
-d '{
  "departureFrom": "IST",
  "departureTo": "SJJ",
  "returnFrom": "BEL",
  "returnTo": "IST",
  "startDate": "2026-05-01",
  "endDate": "2026-05-30",
  "vacationLength": 5,
  "minNonWorkingDays": 3
}'
```

## Notes
- This API currently works with dummy data for demonstration purposes.
- Real world flights are not available. Searching for flights in months/between cities besides the suggested request may not return results.
- The frontend for this project is available in a separate repository: [flight-planner-frontend](https://github.com/OzanDemiralp/flight-planner-frontend)
