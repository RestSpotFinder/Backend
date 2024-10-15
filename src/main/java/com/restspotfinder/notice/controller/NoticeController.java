package com.restspotfinder.notice.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.notice.entity.Notice;
import com.restspotfinder.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "공지사항[Notice] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController extends CommonController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> getNotice() {
        Notice notice = noticeService.getNotice();

        return SuccessReturn(notice);
    }
}