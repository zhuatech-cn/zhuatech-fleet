/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fleet.domain;
import org.springframework.stereotype.Component;
import java.util.List;
@Component public class DomainCatalog {
    public String systemName(){return "知华 Fleet 车队运营管理平台";}
    public String sceneName(){return "车辆、司机、任务、油耗与维保";}
    public List<SeedItem> seedItems(){return List.of(
        new SeedItem("FLEET-20260801-001","沪A12K8车辆故障救援","处理中","调度中心","紧急"),
        new SeedItem("FLEET-20260801-002","冷链车辆温控复核","待处理","安全管理组","高"),
        new SeedItem("FLEET-20260801-003","七月异常油耗复盘","已完成","成本管理组","中"),
        new SeedItem("FLEET-20260801-004","下周预防性维保排程","处理中","维修保障组","高"));}
    public List<String> recommendedActions(){return List.of("优先处置道路故障与安全事件","重新平衡司机工时和车辆利用率","核查油耗、温控与维保数据异常");}
    public record SeedItem(String recordNo,String title,String status,String owner,String priority){}
}
