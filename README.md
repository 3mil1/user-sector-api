# User Sector API

## Overview

User Sector API is a Spring Boot application that allows users to create a profile by selecting sectors and accepting terms and conditions, with automatic session creation.

## Key Characteristics

- User profile creation with sector selection
- Mandatory terms and conditions acceptance
- Automatic session creation upon profile creation

## Technical Stack

- **Backend**: Spring Boot 3.4.4
- **Language**: Java 17
- **Database**: PostgreSQL

## Prerequisites

- Java 17
- Docker
- Docker Compose

## Related Frontend

The frontend for this application can be found at: 
https://github.com/3mil1/user-sector-ui

## Setup and Running

1. Create a `.env` file with database configuration
   ```
   POSTGRES_USER=myuser
   POSTGRES_PASSWORD=mypassword
   POSTGRES_DB=usersector_db
   ```

2. Run the application
   ```bash
   ./run.sh
   ```
   
   Alternatively, you can manually start the database and run the application:
   ```bash
   # Start the database
   docker-compose up -d db
   
   # Run the application
   ./gradlew bootRun
   ```

## Profile Creation Workflow

When a user creates a profile via the `POST /api/users` endpoint:
- User provides name and selected sectors
- User must accept terms and conditions
- A new user profile is created in the database
- A session is automatically established
- The user is authenticated

## API Endpoints

### Sectors
- `GET /api/sectors`: Retrieve all sectors with their hierarchical structure

### Users
- `POST /api/users`: Create a user profile
  - Automatically creates a session
  - Requires name, sector selection, and terms acceptance
- `GET /api/users/current`: Get current user profile details (requires active session)
- `PUT /api/users`: Update user profile (requires active session)

## Validation Requirements

- Name is required (max 100 characters)
- At least one sector must be selected
- Terms and conditions must be accepted