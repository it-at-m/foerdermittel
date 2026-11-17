<!-- General project links -->
[documentation]: https://it-at-m.github.io/foerdermittel/
[itm-opensource]: https://opensource.muenchen.de/
[license]: ../LICENSE
[code-of-conduct]: ./CODE_OF_CONDUCT.md
[refarch-templates]: https://refarch.oss.muenchen.de/templates/

<!-- Project specific links -->
[spring-documentation]: https://spring.io/
[vuejs-documentation]: https://vuejs.org/

<!-- Shields.io links -->
[documentation-shield]: https://img.shields.io/badge/documentation-blue?style=for-the-badge
[made-with-love-shield]: https://img.shields.io/badge/made%20with%20%E2%9D%A4%20by-it%40M-yellow?style=for-the-badge
[license-shield]: https://img.shields.io/github/license/it-at-m/foerdermittel?style=for-the-badge

[![Documentation][documentation-shield]][documentation]
[![Made with love by it@M][made-with-love-shield]][itm-opensource]
[![GitHub license][license-shield]][license]

# Fördermittel Verwaltung

The **Fördermittel Verwaltung** application is a web-based solution developed for the City Treasury Department of the City of Munich (Landeshauptstadt München). It is designed to centrally manage and document all municipal construction projects for which funding is requested from the State of Bavaria, the German federal government, the European Union, or other organizations and public authorities.

The application supports the entire funding process, from the initial funding application through approval and disbursement of funds to the final proof of use. The stored data can be queried and evaluated at any time using reports, either by organizational area and/or by project.

The application consists of a backend and a frontend and is built using Java, Spring Boot, Vue.js, JasperReports and Vuetify. PostgreSQL is used as the database management system.

The application is built using [Spring][spring-documentation], [Vue.js][vuejs-documentation], Vuetify, JasperReports and PostgreSQL. It is based on the [refarch-templates] provided by it@M.
## Features

The application provides the following core functional areas:

- **Funding Applications:** Management and documentation of funding applications for municipal construction projects.
- **Funding Approvals:** Recording and management of approved funding.
- **Fund Disbursements:** Management and documentation of funding disbursements.
- **Proof of Use:** Recording and management of documentation demonstrating how allocated funds have been used.
- **Reporting and Analytics:** Reports for evaluating stored data by organizational area and/or project.

## Project Structure

The repository is organized into several main directories:

| Directory | Description |
|---|---|
| `backend/` | Application backend |
| `frontend/` | Application frontend |
| `docs/` | Project documentation |
| `stack/` | Infrastructure and stack configuration |
| `.github/` | GitHub configuration and workflows |

## Requirements

The application requires the following technical components:

- Java Runtime Environment
- PostgreSQL database
- Node.js and npm for frontend development

The specific required versions and additional dependencies are defined in the respective project configuration files.

## Installation and Setup

Installation and deployment instructions will be added.

## Configuration

Information about application configuration and setup will be added.

## Documentation

Detailed project documentation is available at:

[Fördermittel Verwaltung – Project Documentation][documentation]

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

## License

Distributed under the MIT License. See the [LICENSE][license] file for more information.

## Contact

it@M – opensource@muenchen.de

---

# Deutsch

Die **Fördermittel Verwaltung** ist eine Webanwendung der Landeshauptstadt München, Stadtkämmerei. Sie dient der zentralen Verwaltung und Dokumentation aller baulichen Maßnahmen der Stadt München, für die Fördermittel bei Land, Bund, Europäischer Union sowie weiteren Organisationen und Behörden beantragt werden.

Die Anwendung unterstützt den gesamten Fördermittelprozess – von der Antragstellung über die Bewilligung und den Mittelabruf bis hin zum Verwendungsnachweis. Die zugrunde liegenden Daten können jederzeit projekt- und/oder bereichsbezogen über Berichte ausgewertet werden.

Die Anwendung besteht aus einem Backend und einem Frontend und basiert auf Java, Spring Boot, Vue.js, JasperReports und Vuetify. Als Datenbankmanagementsystem wird PostgreSQL eingesetzt.

Die Anwendung basiert auf [Spring][spring-documentation], [Vue.js][vuejs-documentation], Vuetify, JasperReports und PostgreSQL. Als Grundlage dienen die von it@M bereitgestellten [refarch-templates].
## Funktionsumfang

Die Anwendung umfasst folgende zentrale Funktionsbereiche:

- **Förderanträge:** Verwaltung und Dokumentation von Förderanträgen für bauliche Maßnahmen.
- **Bewilligungen:** Erfassung und Verwaltung der bewilligten Fördermittel.
- **Mittelabrufe:** Verwaltung und Dokumentation der Mittelabrufe.
- **Verwendungsnachweise:** Erfassung und Verwaltung der Verwendungsnachweise.
- **Reporting und Auswertungen:** Bereichs- und projektbezogene Auswertung der erfassten Daten mittels Berichten.

## Projektstruktur

Das Repository ist in mehrere Bereiche unterteilt:

| Verzeichnis | Beschreibung |
|---|---|
| `backend/` | Backend der Anwendung |
| `frontend/` | Frontend der Anwendung |
| `docs/` | Projektdokumentation |
| `stack/` | Infrastruktur- und Stack-Konfiguration |
| `.github/` | GitHub-Konfigurationen und Workflows |

## Voraussetzungen

Die Anwendung benötigt folgende technische Komponenten:

- Java-Laufzeitumgebung
- PostgreSQL-Datenbank
- Node.js und npm für die Frontend-Entwicklung

Die konkret erforderlichen Versionsstände und weiteren Abhängigkeiten sind den jeweiligen Projektkonfigurationen zu entnehmen.

## Installation und Inbetriebnahme

Die Installations- und Bereitstellungsanleitung wird ergänzt.

## Konfiguration

Informationen zur Konfiguration und Einrichtung der Anwendung werden ergänzt.

## Dokumentation

Die ausführliche Projektdokumentation ist unter folgendem Link verfügbar:

[Fördermittel Verwaltung – Projektdokumentation][documentation]

## Mitwirken

Beiträge zur Weiterentwicklung des Projekts sind herzlich willkommen. Wir freuen uns über jede Unterstützung aus der Open-Source-Community.

## Lizenz

Dieses Projekt wird unter der MIT-Lizenz veröffentlicht. Weitere Informationen findest du in der Datei [LICENSE][license].

## Kontakt

it@M – opensource@muenchen.de