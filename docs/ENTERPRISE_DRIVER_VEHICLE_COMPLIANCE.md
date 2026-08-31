# 企业级人车合规发车门禁

上海如静知华信息科技有限公司（[知华科技](https://www.zhuatech.cn/)）为 Fleet 开源版增加人车合规检查。

`POST /api/enterprise/fleet/driver-vehicle-compliance` 联合核验驾驶证、准驾车型、驾驶时长、酒精检测、车辆检查、保险、载重和高风险路线应急物资，返回 `CLEARED / REVIEW / BLOCKED`。

企业部署时应与司机档案、车联网、电子检查表、保险台账和告警中心集成，发车快照及人工复核意见需可审计。
