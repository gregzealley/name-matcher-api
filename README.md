[![Build Status](https://travis-ci.com/gregzealley/name-matcher-api.svg?branch=master)](https://travis-ci.com/gregzealley/name-matcher-api)

# Name Matcher API
API for matching two CSV files containing lists of names. Each name in the "primary" file will be compared to all names
in the "secondary" file using "matchers" (described below). Each name will be assigned a match type result, or an "unmatched"
result. On matching, the "id" field from the secondary file will be added to the name in the "primary".

The API will return a file of names, match types (including unmatched) and secondary file IDs. 

# Description

## Input File Description

### The Primary File

* Minimum fields - forename, surname
* Optional fields - known-as name, id, description fields (e.g. job title, department)  
* All names in this file will be assigned a match result
* Names will be assigned a secondary file id when a match is made

### The Secondary File

* Minimum fields - forename, surname, id
* Optional fields - known-as name, description fields (e.g. job title, department)
* Names are used for matching only - they are not assigned any results or ids from the primary file

### Notes on file content

* The forename value in the secondary file can be an initial
* The forename value in the secondary file could be a shortened version of the name (e.g. Tim and Timothy, Tony and Anthony)

## Matching Types

| Type | Priority | Description |
| :--- | :--- | :--- |
| Surname and Forename identical | 1 | Both names match exactly and there are no duplicates |
| Surname identical with matching first initial | 2 | The surname matches, and the first initial of the forename matches when the secondary file forename is just an initial |
| Surname and shortened name match | 3 | The surname matches and the forename matches a shortened version in the secondary file |
| Duplicate name but description fields match | 4 | There are duplicate surname/forename matches but the description information is unique |
| Duplicates | x | Multiple matches exist in the secondary file |
| No match | x | No match can be made |

# Technical Notes

* To run the integration tests use `mvn verify failsafe:integration-tests`
