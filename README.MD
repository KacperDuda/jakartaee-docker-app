# ZTI Project 2

This project contains all source code and configuration needed to build and run a multi-service application using Docker Compose.

## Overview

- **Invoice Generator:** Python application using `pdflatex` to generate invoices in PDF format.
- **Application Server:** JakartaEE application running on TomEE.
- **Database:** Persistent storage with a Docker volume to ensure data safety across restarts.

## Prerequisites

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

## Running the Project

1. **Clone the repository:**
    ```bash
    git clone git@github.com:KacperDuda/jakartaee-docker-app.git
    cd ZTI_project_2
    ```

2. **Build and start all services:**
    ```bash
    docker compose up --build
    ```

    This command will:
    - Build Docker images for the Python invoice generator and JakartaEE+TomEE app server from the provided source code.
    - Start all containers as defined in `docker-compose.yml`.
    - Create a Docker volume to persist database data, keeping it safe even after container restarts.

3. **Access the application:**
    - By default, the project will be hosted at: [http://localhost:8080/app](http://localhost:8080/app)
    - Refer to the logs or documentation for additional service URLs and ports.

## Project Structure

- `src/invoice-generator/` - Python+pdflatex invoice generator source code.
- `src/app-server/` - JakartaEE+TomEE application server source code.
- `Dockerfile` - Docker build instructions for each service.
- `docker-compose.yml` - Multi-service orchestration and volume configuration.

## Notes

- All images are built locally from the source code included in this repository.
- No external images are required except for official base images (e.g., Python, TomEE, database).
- Database data is stored in a Docker volume for persistence.
