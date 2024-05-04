# **Book Storage API**

## Introduction

This project is an API for storing books. It provides endpoints for retrieving books, searching for books by author, getting authors by symbol, and adding new books.

## Usage

### Endpoints

- **GET /api/v1/books**: Get all books.
- **GET /api/v1/books/books-by-author?author={author}**: Get books by a specific author.
- **GET /api/v1/books/authors-by-symbol?symbol={symbol}&limit={limit}**: Get authors by a specific symbol with a limit.
- **POST /api/v1/books**: Add a new book.

### Request Examples

#### Get all books in descending order

```http
GET /api/v1/books-desc
```

#### Get books by author

```http
GET /api/v1/books/books-by-author?author=AuthorName
```

#### Get authors by symbol

```http
GET /api/v1/books/authors-by-symbol?symbol=a&limit=10
```

#### Add a book

```http
POST /api/v1/books
Content-Type: application/json

{
    "title": "Crime and Punishment",
    "author": "F. Dostoevsky",
    "description": null
}
```

## Technologies Used

- Spring Boot
- PostgreSQL
- Java Stream API
- Maven
