/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.controller;

import cn.zhuatech.fleet.common.ApiResponse;
import cn.zhuatech.fleet.service.DispatchReadinessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dispatch-readiness")
public class DispatchReadinessController {
    private final DispatchReadinessService service;
    public DispatchReadinessController(DispatchReadinessService service) { this.service = service; }
    @PostMapping
    ApiResponse<DispatchReadinessService.DispatchDecision> evaluate(
        @Valid @RequestBody DispatchReadinessService.DispatchRequest request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
