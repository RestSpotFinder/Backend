package com.restspotfinder.notice.service;

import com.restspotfinder.notice.entity.Notice;
import com.restspotfinder.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Notice create(String title, String content) {
        Notice notice = Notice.of(title, content);

        return noticeRepository.save(notice);
    }

    public List<Notice> getNoticeList() {
        return noticeRepository.findListByOrderByCreatedAtDesc();
    }
}