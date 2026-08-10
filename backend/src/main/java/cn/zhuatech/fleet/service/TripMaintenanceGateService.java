/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.fleet.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripMaintenanceGateService {
    public Result evaluate(Request request) {
        int nextServiceOdometer = request.lastServiceOdometerKm() + request.serviceIntervalKm();
        int distanceToService = nextServiceOdometer - request.currentOdometerKm();
        boolean serviceDueDuringTrip = distanceToService <= request.plannedTripKm();
        boolean safetyIssue = request.activeFaultCodes() > 0 || request.brakeWearPercent() >= 90
            || request.minimumTireDepthMm() < 2;
        String decision = serviceDueDuringTrip || safetyIssue ? "BLOCK_DISPATCH"
            : distanceToService <= 500 ? "SERVICE_SOON" : "READY";
        List<String> actions = new ArrayList<>();
        if (serviceDueDuringTrip) actions.add("先完成计划保养或更换车辆后再派车");
        if (request.activeFaultCodes() > 0) actions.add("诊断并关闭活动故障码");
        if (request.brakeWearPercent() >= 90) actions.add("检查制动系统并更换磨损件");
        if (request.minimumTireDepthMm() < 2) actions.add("更换低花纹轮胎并复核胎压");
        if (actions.isEmpty()) actions.add("允许派车并记录下次保养里程");
        return new Result(request.vehicleNo(), nextServiceOdometer, distanceToService,
            decision, actions);
    }

    public record Request(@NotBlank String vehicleNo, @Min(0) int currentOdometerKm,
                          @Min(0) int lastServiceOdometerKm, @Min(1) int serviceIntervalKm,
                          @Min(0) int plannedTripKm, @Min(0) int activeFaultCodes,
                          @Min(0) @Max(100) int brakeWearPercent,
                          @DecimalMin("0") double minimumTireDepthMm) {}
    public record Result(String vehicleNo, int nextServiceOdometerKm, int distanceToServiceKm,
                         String decision, List<String> actions) {}
}
