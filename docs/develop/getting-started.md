# Getting Started

_This page describes the steps required to set up and run the project locally._

## Prerequisites

_Install the required software before starting development._

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

_Install all required project dependencies._

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

_Describe how to start the applications for local development._

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
