package com.restspotfinder.notice.repository;

import com.restspotfinder.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findListByOrderByCreatedAtDesc();
}