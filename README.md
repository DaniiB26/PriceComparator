# Price Comparator - Market

A backend application for comparing products prices from different stores, tracking discounts, optimizing shopping baskets, and monitoring price changes over time.

## Project Structure

- `controller/` - Providing API endpoints for products, discounts, and alerts
- `service/` - Contains business logic (filtering, sorting, alerts, etc.) 
- `model/` – Defines data models (Product, Discount, PriceAlert)  
- `util/` – CSV readers for loading data from `resources/data`
- `resources/data/` – Sample CSV files with product and discount data 

## Build & Run

### Prerequisites
- Java 17+
- Maven

### Run with Maven:
```bash
mvn spring-boot:run
```

The server will start at: `http://localhost:8080`

## Implemented Features

### Products:

- `GET /products` – All available products

- `POST /products/basket/check` – Optimized basket grouped by store

- `GET /products/{productId}/history` – Price history of a product (filterable)

- `GET /products/best-value?category=&unit=` – Products sorted by value per unit

### Discounts:

- `GET /discounts` – All discounts

- `GET /discounts/top` – Top 10 highest percentage discounts

- `GET /discounts/new` – Discounts added in the last 24 hours

### Price Alert:

- `POST /alerts` – Add a price alert

Example body:

```json
{
  "productName": "lapte zuzu",
  "targetPrice": 8.50
}
```

- `GET /alerts` – View all saved alerts

- `GET /alerts/triggered` – Products that match active alerts

## Example CSV Files

Located in `src/main/resources/data/`. File naming convention:

- Products: `store_yyyy-MM-dd.csv`

- Discounts: `store_discounts_yyyy-MM-dd.csv`

## Assumptions and Simplifications
- No user authentication; alerts are in-memory only (not per user)

- Product name matching is case-insensitive

- The application does not use a database; all data is parsed from CSV

- Value per unit is calculated as price / quantity