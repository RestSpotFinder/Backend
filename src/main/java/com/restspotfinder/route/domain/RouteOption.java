package com.restspotfinder.route.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(enumAsRef = true, description = """
        경로 탐색 옵션:
        * `fast` - 실시간 빠른길
        * `optimal` - 실시간 최적
        * `avoidtoll` - 무료 우선
        * `comfort` - 실시간 편한길
        * `avoidcaronly` - 자동차 전용 도로 회피 우선
        """)
public enum RouteOption {
    fast("trafast", "실시간 빠른길"),
    optimal("traoptimal", "실시간 최적"),
    avoidtoll("traavoidtoll", "무료 우선"),
    comfort("tracomfort", "실시간 편한길"),
    avoidcaronly("traavoidcaronly", "자동차 전용 도로 회피 우선");

    private final String value;
    private final String desc;

    RouteOption(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    // @RequestParam ENUM Parsing
    @JsonCreator
    public static RouteOption from(String s) {
        return RouteOption.valueOf(s);
    }
}
