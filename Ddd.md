Agis en tant qu'expert en architecture logicielle (Hexagonale / DDD) et spécialiste en modélisation de bases de données (SQL Server 2022 et PostgreSQL).

Contexte précis de mon architecture :
- Base de données actuelle : SQL Server 2022.
- Structure des schémas : Mon nouveau modèle (Bounded Context "Catalog") vit dans un NOUVEAU schéma nommé 'catalog'. Il cohabite dans la même base de données que le système Legacy dont les tables sont dans le schéma par défaut 'dbo'.
- Outil de migration : Liquibase.
- Évolution future : À moyen terme, l'application migrera vers PostgreSQL, et les tables de référentiel seront déplacées dans un schéma dédié nommé 'referential'.

Le besoin :
Dans mon schéma 'catalog', je dois faire référence à trois tables de référentiel (Legacy) situées actuellement dans 'dbo' :
1. `dbo.RefSegment`
2. `dbo.RefProductType`
3. `dbo.RefEntity`

Je souhaite appliquer la meilleure approche DDD / Hexagonale pour lier mes nouvelles tables (ex: `catalog.Product`) à ces référentiels, sachant qu'en base de données, je veux éviter un couplage fort (pas de clés étrangères physiques inter-schémas afin de faciliter la future migration vers PostgreSQL).

Ce que je te demande :

1. AU NIVEAU DU DOMAINE (DDD) : Montre-moi comment modéliser ces référentiels (probablement sous forme de Value Objects ou d'entités en lecture seule) et définis le "Driven Port" (l'interface) qui permettra au domaine de valider l'existence de ces références sans connaître la structure de la base legacy.

2. AU NIVEAU DE L'INFRASTRUCTURE (Adaptateur) : Fournis-moi l'implémentation de cet adaptateur secondaire pour SQL Server (utilise du pseudo-code propre ou le langage de ton choix, ex: C# ou Java) montrant comment il requête le schéma `dbo` actuel. Explique brièvement comment cet adaptateur changera lors du passage à PostgreSQL.

3. AU NIVEAU LIQUIBASE (Fichiers SQL) :
   - Écris-moi le changeset pour créer la table `catalog.Product` de manière propre et idempotente (format `--liquibase formatted sql`), en utilisant le couplage par ID (Id-Coupling) sans contrainte de clé étrangère physique.
   - Utilise des types de données compatibles ou facilement transposables entre SQL Server (`UNIQUEIDENTIFIER`) et PostgreSQL (`UUID`).

Génère un exemple de code clair, modulaire et structuré selon les répertoires types d'un projet hexagonal.
