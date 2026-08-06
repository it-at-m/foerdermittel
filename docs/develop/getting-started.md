# Getting Started

*This page describes the  steps required to set up and run the project locally.*

## Prerequisites

*Install the required software before starting development.*

- Java
- Node.js
- Maven
- Podman or Docker
- Git
- 
## Clone the Repository

```bash
git clone https://github.com/it-at-m/foerdermittel.git
cd foerdermittel
```

## Install Dependencies

*Install all required project dependencies.*

### Backend

```bash
mvn clean install
```

### Frontend

```bash
npm install
```

## Start the local development stack

**The local development stack consists of.**

- Keycloak
- PostgreSQL
- pgAdmin
- API Gateway

**Start the stack from the `stack` directory:**

```bash
podman compose up -d
```

## Run the Application

*Describe how to start the applications for local development.*

### Backend

**.......**

### Frontend

**.......**

### Local services and ports

| Service               | Default port |
| --------------------- | :----------: |
| \<Frontend(Link)\>    |  \<Port-1\>  |
| \<API Gateway(Link)\> |  \<Port-2\>  |
| \<Backend (Link)\>    |  \<Port-3\>  |
| \<Keykloak (Link)\>   |  \<Port-4\>  |
| \<pgAdmin (Link)\>    |  \<Port-5\>  |

## Application Profiles

| Profilename | Description          |
| ----------- | -------------------- |
| local       | _<\Description-1 \>_ |

## Default User

| Profilename | Password      | Description         |
| ----------- | ------------- | ------------------- |
| fmad        | foerdermittel | _<\Description-1\>_ |
| fmsb        | foerdermittel | _<\Description-2\>_ |

