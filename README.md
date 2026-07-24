# Familia API

Backend REST de **Familia**, une application de gestion de familles, personnes, relations, patrimoine, intérêts et parrainages.

## Technologies

- Java 21
- Spring Boot 4
- Spring Web, Spring Security et JWT
- Spring Data JPA / Hibernate
- MySQL en production
- SMTP pour l’activation des comptes et la réinitialisation des mots de passe
- Maven Wrapper, Docker et Docker Compose
- OpenAPI / Swagger UI

## Fonctionnalités

- Inscription, activation de compte, connexion et réinitialisation du mot de passe
- Authentification stateless par jeton JWT
- Gestion des utilisateurs et des personnes
- Gestion des familles et des relations entre personnes
- Gestion des commentaires, intérêts, patrimoines et parrainages
- Téléversement d’images de personnes
- Documentation interactive de l’API

## Prérequis

- JDK 21
- Docker Desktop et Docker Compose pour l’environnement conteneurisé, ou
- un serveur MySQL accessible et un serveur SMTP pour une exécution hors Docker

## Lancer le projet localement

### Avec Maven

Définissez les variables d’environnement nécessaires, puis démarrez l’application :

```powershell
.\mvnw.cmd spring-boot:run
```

L’API écoute par défaut sur `http://localhost:8080`.

### Avec Docker Compose

Le projet fournit MySQL, Mailpit et l’API :

```powershell
docker-compose up --build
```

Services locaux :

| Service | Adresse |
| --- | --- |
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Mailpit | `http://localhost:8025` |
| MySQL | `localhost:3306` |

Pour arrêter les services :

```powershell
docker-compose down
```

Consultez aussi [DOCKER.md](DOCKER.md) pour les commandes Docker complémentaires.

## Configuration

L’application lit ses secrets et paramètres de connexion depuis les variables d’environnement. Ne mettez jamais de mot de passe dans `application.properties` ni dans Git.

### Base de données MySQL

| Variable | Description |
| --- | --- |
| `SPRING_DATASOURCE_URL` | URL JDBC MySQL, par exemple `jdbc:mysql://db.example.com:3306/familia?sslMode=REQUIRED` |
| `SPRING_DATASOURCE_USERNAME` | Utilisateur MySQL |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe MySQL |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Politique Hibernate, `update` par défaut |

### Sécurité et URLs

| Variable | Description |
| --- | --- |
| `APP_JWT_SECRET` | Secret JWT long, aléatoire et privé |
| `APP_BASE_URL` | URL publique de l’API |
| `APP_FRONTEND_URL` | Origine exacte du frontend autorisée par CORS |
| `PORT` | Port HTTP fourni par l’hébergeur ; Render le définit automatiquement |

### SMTP

| Variable | Description |
| --- | --- |
| `SPRING_MAIL_HOST` | Hôte SMTP |
| `SPRING_MAIL_PORT` | Port SMTP, généralement `587` ou `465` |
| `SPRING_MAIL_USERNAME` | Identifiant SMTP, souvent l’adresse email complète |
| `SPRING_MAIL_PASSWORD` | Mot de passe SMTP |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH` | `true` lorsque l’authentification est requise |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE` | `true` pour SMTP avec STARTTLS, habituellement sur le port `587` |
| `SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE` | `true` pour SMTP SSL implicite, habituellement sur le port `465` |

Pour un serveur SMTP SSL sur le port `465` :

```text
SPRING_MAIL_PORT=465
SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=false
SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE=true
```

Pour un serveur SMTP STARTTLS sur le port `587` :

```text
SPRING_MAIL_PORT=587
SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
```

## Déploiement sur Render

1. Créez un **Web Service** connecté à ce dépôt GitHub.
2. Laissez Render utiliser le `Dockerfile` du projet.
3. Déclarez toutes les variables de la section Configuration dans **Environment**.
4. Utilisez une base MySQL accessible depuis Internet. L’URL doit impérativement commencer par `jdbc:mysql://`.
5. Déployez et vérifiez que les logs contiennent `Started FamiliaApplication`.
6. Ajoutez votre domaine personnalisé dans **Settings > Custom Domains**.

Exemple de variable datasource valide :

```text
SPRING_DATASOURCE_URL=jdbc:mysql://db.example.com:3306/familia?sslMode=REQUIRED
```

Une URL commençant seulement par `mysql://` n’est pas une URL JDBC valide pour Spring Boot.

## CORS

L’API autorise l’origine définie par `APP_FRONTEND_URL`. Cette valeur doit correspondre exactement à l’origine du navigateur, incluant le protocole :

```text
APP_FRONTEND_URL=https://familia.nefertitidadjo.fr
```

Ne mettez pas de chemin ou de `/` final dans cette valeur.

## Tests et build

Compiler, lancer les tests et créer le JAR :

```powershell
.\mvnw.cmd clean package
```

Créer le JAR sans les tests :

```powershell
.\mvnw.cmd -DskipTests package
```

## Documentation API

Après démarrage de l’application, la documentation OpenAPI est disponible à :

```text
/swagger-ui/index.html
```

## Sécurité

- Ne commitez jamais `APP_JWT_SECRET`, mots de passe SMTP ou identifiants de base de données.
- Utilisez les variables sécurisées de Render en production.
- Changez immédiatement tout secret partagé par erreur dans un message, un commit ou une capture d’écran.
- Utilisez HTTPS pour le frontend et l’API en production.
