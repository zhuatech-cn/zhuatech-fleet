/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.fleet.controller;

import cn.zhuatech.fleet.common.ApiResponse;
import cn.zhuatech.fleet.service.TripMaintenanceGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fleet/insights")
public class TripMaintenanceGateController {
    private final TripMaintenanceGateService service;
    public TripMaintenanceGateController(TripMaintenanceGateService service) { this.service = service; }
    @PostMapping("/trip-maintenance-gate")
    public ApiResponse<TripMaintenanceGateService.Result> evaluate(
        @Valid @RequestBody TripMaintenanceGateService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
