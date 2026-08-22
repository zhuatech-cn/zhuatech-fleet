/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DispatchReadinessService {
    public DispatchDecision evaluate(DispatchRequest request) {
        double loadRate = Math.round(request.payloadKg() * 1000.0 / request.vehicleCapacityKg()) / 10.0;
        int riskScore = (loadRate > 100 ? 45 : loadRate > 90 ? 15 : 0)
            + (request.driverHoursToday() > 8 ? 35 : request.driverHoursToday() > 7 ? 15 : 0)
            + Math.max(0, 80 - request.vehicleHealthScore())
            + (request.maintenanceDue() ? 25 : 0)
            + (request.coldChainRequired() && !request.coldChainReady() ? 40 : 0);
        riskScore = Math.min(100, riskScore);
        List<String> blockers = new ArrayList<>();
        if (loadRate > 100) blockers.add("计划载重超过车辆核定容量");
        if (request.driverHoursToday() > 8) blockers.add("驾驶员工时超过单日派车阈值");
        if (request.vehicleHealthScore() < 80) blockers.add("车辆健康评分低于派车基线");
        if (request.maintenanceDue()) blockers.add("车辆存在到期未完成的维保任务");
        if (request.coldChainRequired() && !request.coldChainReady()) blockers.add("冷链任务缺少有效温控保障");
        String decision = riskScore >= 50 ? "BLOCK" : riskScore >= 20 ? "REVIEW" : "READY";
        if (blockers.isEmpty()) blockers.add("车辆、司机与任务约束满足派车条件");
        return new DispatchDecision(loadRate, riskScore, decision, blockers);
    }

    public record DispatchRequest(@NotNull @Positive Integer routeDistanceKm,
        @NotNull @Positive Integer payloadKg, @NotNull @Positive Integer vehicleCapacityKg,
        @NotNull @Min(0) @Max(24) Integer driverHoursToday,
        @NotNull @Min(0) @Max(100) Integer vehicleHealthScore,
        @NotNull Boolean maintenanceDue, @NotNull Boolean coldChainRequired,
        @NotNull Boolean coldChainReady) {}
    public record DispatchDecision(double loadRate, int riskScore, String decision, List<String> blockers) {}
}
