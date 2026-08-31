/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.controller;

import cn.zhuatech.fleet.common.ApiResponse;
import cn.zhuatech.fleet.service.DriverVehicleComplianceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/fleet")
public class DriverVehicleComplianceController {
    private final DriverVehicleComplianceService service;
    public DriverVehicleComplianceController(DriverVehicleComplianceService service) { this.service = service; }

    @PostMapping("/driver-vehicle-compliance")
    public ApiResponse<DriverVehicleComplianceService.Assessment> assess(
        @Valid @RequestBody DriverVehicleComplianceService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
