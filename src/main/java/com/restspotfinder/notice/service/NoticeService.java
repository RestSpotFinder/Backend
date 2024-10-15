package com.restspotfinder.notice.service;

import com.restspotfinder.notice.entity.Notice;
import com.restspotfinder.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Notice getNotice() {
        return noticeRepository.findAll().get(0);
    }
}