package com.restspotfinder.search.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
@Schema(enumAsRef = true, description = """
        검색 타입:
        * `place` - 장소 검색
        * `route` - 경로 검색
        """)
public enum SearchType {
    place("place", "장소 검색"),
    route("route", "경로 검색");

    private final String value;
    private final String desc;

    SearchType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}