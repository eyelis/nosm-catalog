# language: fr
Fonctionnalité: Gestion de la portée (Scope) du catalogue
  En tant que gestionnaire de catalogue
  Je veux associer des entités juridiques et des segments de clientèle à mon catalogue
  Afin de restreindre l'application des tarifs

  Scénario: Ajout d'entités au périmètre du catalogue
    Étant donné un catalogue au statut "DRAFT"
    Quand j'ajoute les entités suivantes au catalogue :
      | Entity Code | Entity Name      |
      | 111111      | XX-XXX (LONDON)  |
      | 22222       | XX-XXX (INDIA)   |
    Alors l'onglet "ENTITIES" affiche un compteur égal à 2

  Scénario: Configuration d'un segment avec liste d'exception
    Étant donné un catalogue au statut "DRAFT"
    Quand j'accède à l'onglet "SEGMENTS"
    Et que j'ajoute le segment "External Financial Institution"
    Et que je sélectionne "No" à l'option "Exceptional List"
    Alors le segment est enregistré sans exemption de la commission XXX/YYY
    Et l'onglet "SEGMENTS" affiche un compteur égal à 1