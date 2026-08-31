/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DriverVehicleComplianceServiceTest {
    private final DriverVehicleComplianceService service = new DriverVehicleComplianceService();

    @Test void clearsCompliantDispatch() {
        var result = service.assess(new DriverVehicleComplianceService.Request(
            "DSP-001", "沪A00001", "DRV-01", true, true, 4, 8, true, true, true, 0, false, true));
        assertThat(result.decision()).isEqualTo(DriverVehicleComplianceService.Decision.CLEARED);
    }

    @Test void blocksDriverAndVehicleViolations() {
        var result = service.assess(new DriverVehicleComplianceService.Request(
            "DSP-002", "沪A00002", "DRV-02", false, false, 10, 8, false, false, false, 20, false, false));
        assertThat(result.decision()).isEqualTo(DriverVehicleComplianceService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(7);
    }

    @Test void reviewsHighRiskRouteWithoutEmergencyKit() {
        var result = service.assess(new DriverVehicleComplianceService.Request(
            "DSP-003", "沪A00003", "DRV-03", true, true, 2, 8, true, true, true, 0, true, false));
        assertThat(result.decision()).isEqualTo(DriverVehicleComplianceService.Decision.REVIEW);
    }
}
