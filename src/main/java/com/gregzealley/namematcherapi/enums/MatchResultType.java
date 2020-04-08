package com.gregzealley.namematcherapi.enums;

public enum MatchResultType {
    SURNAME_FORENAME,
    SURNAME_FIRST_INITIAL,
    SURNAME_SHORTENED_FORENAME,
    DUPLICATE_WITH_UNIQUE_DESCRIPTORS,
    DUPLICATE,
    NO_MATCH
}
