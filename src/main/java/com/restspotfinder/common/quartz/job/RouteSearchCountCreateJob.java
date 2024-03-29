package com.restspotfinder.common.quartz.job;

import com.restspotfinder.apicount.domain.PlaceSearchCount;
import com.restspotfinder.apicount.repository.PlaceSearchCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class RouteSearchCountCreateJob extends QuartzJobBean {
    private final PlaceSearchCountRepository placeSearchCountRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        placeSearchCountRepository.save(PlaceSearchCount.init(tomorrow));
    }
}