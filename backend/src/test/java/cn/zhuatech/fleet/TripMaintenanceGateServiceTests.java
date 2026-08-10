/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.fleet;

import cn.zhuatech.fleet.service.TripMaintenanceGateService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TripMaintenanceGateServiceTests {
    private final TripMaintenanceGateService service = new TripMaintenanceGateService();

    @Test void blocksTripCrossingMaintenanceThreshold() {
        var result = service.evaluate(new TripMaintenanceGateService.Request(
            "沪A10001", 49800, 40000, 10000, 500, 0, 50, 4));
        assertEquals(200, result.distanceToServiceKm());
        assertEquals("BLOCK_DISPATCH", result.decision());
    }

    @Test void allowsHealthyVehicle() {
        var result = service.evaluate(new TripMaintenanceGateService.Request(
            "沪A10002", 42000, 40000, 10000, 300, 0, 30, 5));
        assertEquals("READY", result.decision());
    }
}
