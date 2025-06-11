package com.restspotfinder.fuel.scheduler;

import com.restspotfinder.fuel.service.FuelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FuelScheduler {
    private final FuelService fuelService;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void updateFuelStationDaliy() {
        log.info("⛽ 연료 정보 스케줄러 실행 시작");
        fuelService.updateFuelStationsFromApi();
        log.info("✅ 연료 정보 스케줄러 실행 완료");
    }
}
