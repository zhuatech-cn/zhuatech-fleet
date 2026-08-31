/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverVehicleComplianceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.licenseValid()) blockers.add("驾驶证已失效");
        if (!request.requiredClassHeld()) blockers.add("驾驶员准驾车型不匹配");
        if (request.hoursDriven24h() >= request.maxHours24h()) blockers.add("24 小时驾驶时长达到上限");
        if (!request.alcoholCheckPassed()) blockers.add("酒精检测未通过");
        if (!request.inspectionPassed()) blockers.add("车辆出车检查未通过");
        if (!request.insuranceValid()) blockers.add("车辆保险无效");
        if (request.overloadPercent() > 0) blockers.add("车辆存在超载");
        if (!blockers.isEmpty()) {
            actions.add("禁止发车并通知车队安全负责人处理阻断项");
            return new Assessment(Decision.BLOCKED, blockers, actions);
        }
        if (request.routeRiskHigh() && !request.emergencyKitReady()) {
            actions.add("高风险路线需补齐应急物资并完成发车复核");
            return new Assessment(Decision.REVIEW, blockers, actions);
        }
        actions.add("允许发车并记录驾驶员、车辆和检查快照");
        return new Assessment(Decision.CLEARED, blockers, actions);
    }

    public record Request(@NotBlank String dispatchNo, @NotBlank String vehicleNo,
                          @NotBlank String driverId, boolean licenseValid,
                          boolean requiredClassHeld, @Min(0) double hoursDriven24h,
                          @Min(1) double maxHours24h, boolean alcoholCheckPassed,
                          boolean inspectionPassed, boolean insuranceValid,
                          @Min(0) double overloadPercent, boolean routeRiskHigh,
                          boolean emergencyKitReady) {}
    public record Assessment(Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { CLEARED, REVIEW, BLOCKED }
}
