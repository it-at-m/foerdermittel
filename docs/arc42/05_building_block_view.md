# 05. Building Block View

## Whitebox Overall System

***<Overview Diagram>***

### Motivation

<Beschreibung der modularen Struktur der Anwendung.  
Darstellung der Komponenten, Module und deren Beziehungen.  
Besonders relevante Bausteine für den Life Cycle (z. B. Update-Module) werden hervorgehoben.>

### Contained Building Blocks

| Building Block | Description                                          |
|----------------|------------------------------------------------------|
| Auth           | Authentication and authorization                     |
| Master Data    | Management, maintenance and validation of master data |
| Projects       | Management of project data                           |
| Reports        | Generation and processing of reports                 |

### Important Interfaces

<Beschreibung der wichtigsten Schnittstellen zwischen den Bausteinen.>

---

## Level 2

### White Box <Module Name>

***<Module Overview Diagram>***

<Beschreibung des Moduls, Verantwortlichkeiten und Schnittstellen.>

### Contained Building Blocks

| Building Block | Description     |
|---------------|-----------------|
| <Controller>  | REST interface  |
| <Service>     | Business logic  |
| <Repository>  | Data access     |
| <Database>    | Data storage    |

### Important Interfaces

<Beschreibung der Schnittstellen des Moduls.>

---

## Level 3

### White Box <Technical Component>

***<Component Diagram>***

<Beschreibung der technischen Umsetzung und Abhängigkeiten.>

Example:

```text
<Module>Controller
        |
        v
<Module>Service
        |
        v
<Module>Repository
        |
        v
<Module>DB