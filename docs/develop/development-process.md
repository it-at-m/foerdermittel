# Development Process

## Getting started

*Text*

## Technologies

**This project follows the standard development tooling and conventions defined in the Reference Architecture [RefArch – Develop](https://refarch.oss.muenchen.de/templates/develop.html).
See the Reference Architecture for details about:**

- Local container stack (Podman/Docker)
- Vite and Vitest
- Maven
- Code formatting and linting
- Vue DevTools

**Only project-specific deviations are documented below.**

## Local stack

**The local development stack consists of:**

- Keycloak
- PostgreSQL
- pgAdmin
- API Gateway
- Appswitcher

**Start the stack from the `stack` directory:**

```bash
podman compose up -d
```

### Local services and ports

| Service               | Default port |
| --------------------- | :----------: |
| \<Frontend(Link)\>    |  \<Port-1\>  |
| \<API Gateway(Link)\> |  \<Port-2\>  |
| \<Backend (Link)\>    |  \<Port-3\>  |
| \<Keykloak (Link)\>   |  \<Port-4\>  |
| \<pgAdmin (Link)\>    |  \<Port-5\>  |

## Starting the Backend

**<\Table\>**

## Starting the Frontend

**<\Table\>**

## Formatter configuration

**Project-specific formatter configuration (e.g. Spotless, Checkstyle).**

## Testability and Quality Assurance

**Description of approaches to ensure quality and maintainability.**

- Code reviews
- Sprint reviews
- Automated tests
