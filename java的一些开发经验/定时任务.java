package com.fiberhome.iwork.service.timingtask;

import com.alibaba.fastjson.JSON;
import com.fiberhome.iwork.common.constants.SysConfigConstant;
import com.fiberhome.iwork.service.dashboard.DashboardService;
import com.fiberhome.iwork.utils.RedisUtil;
import com.fiberhome.iwork.vo.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 看板模块定时去统计数据
 */
@Component
public class TaskJob {

    private static final Logger LOGGER = LogManager.getLogger(TaskJob.class);
    /**
     * redis 缓存时间24小时
     */
    private static final Long EXPIRETIME = 24 * 60 * 60L;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private RedisUtil redisutil;


    /**
     * 0 0 0 * * ?每天晚上12点执行
     * "0 0 0 * * ?"
     * "0 0/5 * * * ?"
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void job() {
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.info("DASHBOARD CRON JOB BEGIN TIME:" + startTime);
            dashboardDataCache();

            LOGGER.info("DASHBOARD CRON JOB END TIME:" + new Date(System.currentTimeMillis()) + " , COST TIME: " + (System.currentTimeMillis() - startTime) + " MS,END!");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void dashboardDataCache() {
        try {
            long startTime = System.currentTimeMillis();

            //获取菲律宾的仓库及地区
            Map<Object, Object> areaWareHouse = dashboardService.getWareHouse();
            redisutil.set(SysConfigConstant.REDIS_AREA_WAREHOUSE_KEY_NAME, JSON.toJSONString(areaWareHouse), EXPIRETIME);

            //国家层面
            Map<String, String> map = new HashMap<>();
            map.put("countryId", (String) areaWareHouse.get("countryId"));
            map.put("areaId", "0");
            map.put("actualWarehouseCode", null);
            List<MaterialListVo> countryConsumeList = dashboardService.getMaterialConsumptionList(map);
            redisutil.setList(SysConfigConstant.REDIS_MATERIAL_CONSUMPTION_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryConsumeList, EXPIRETIME);

            List<MaterialListVo> countrySuggestionList = dashboardService.getPurchaseSuggestionList(map);
            redisutil.setList(SysConfigConstant.REDIS_MATERIAL_SUGGESTION_KEY_NAME + "_" + areaWareHouse.get("countryId"), countrySuggestionList, EXPIRETIME);

            List<RoofInfoVo> countryRoofInfoVos = dashboardService.getRoofInfo(map);
            redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_ROOFINFO_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryRoofInfoVos, EXPIRETIME);

            List<CostPerOrderVo> countryCost = dashboardService.getMaterialConsumCostPerOrder(map);
            redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_CONSUMCOST_PERORDER_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryCost, EXPIRETIME);

            List<MaterialListVo> countryInventory = dashboardService.getWareHouseMaterielInventoryList(map);
            redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_INVENTORY_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryInventory, EXPIRETIME);

            List<MaterialListVo> countryMiss = dashboardService.getMissMaterielList(map);
            redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_LOSS_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryMiss, EXPIRETIME);

            List<ResponseTimeAndWarningVo> countryTime = dashboardService.getDemandResponseTimeList(map);
            redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_DEMAND_RESPONSE_TIME_KEY_NAME + "_" + areaWareHouse.get("countryId"), countryTime, EXPIRETIME);


            //中心仓库层面
            List<MaterialListVo> wareHouseList = (List<MaterialListVo>) areaWareHouse.get("warehouse");
            for (MaterialListVo materiel : wareHouseList) {
                map.put("actualWarehouseCode", materiel.getActualWarehouseCode());
                List<MaterialListVo> warehouseConsumeList = dashboardService.getMaterialConsumptionList(map);
                redisutil.setList(SysConfigConstant.REDIS_MATERIAL_CONSUMPTION_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseConsumeList, EXPIRETIME);

                List<MaterialListVo> warehouseSuggestionist = dashboardService.getPurchaseSuggestionList(map);
                redisutil.setList(SysConfigConstant.REDIS_MATERIAL_SUGGESTION_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseSuggestionist, EXPIRETIME);

                List<RoofInfoVo> wareHouseRoofInfoVos = dashboardService.getRoofInfo(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_ROOFINFO_KEY_NAME + "_" + materiel.getActualWarehouseCode(), wareHouseRoofInfoVos, EXPIRETIME);

                List<MaterielInfo> warehouseSMRS = dashboardService.getSMRS(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_SMRS_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseSMRS, EXPIRETIME);

                List<CostPerOrderVo> warehouseCost = dashboardService.getMaterialConsumCostPerOrder(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_CONSUMCOST_PERORDER_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseCost, EXPIRETIME);

                List<MaterialListVo> warehouseInventory = dashboardService.getWareHouseMaterielInventoryList(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_INVENTORY_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseInventory, EXPIRETIME);

                List<MaterialListVo> warehouseMiss = dashboardService.getMissMaterielList(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_LOSS_KEY_NAME + "_" + materiel.getActualWarehouseCode(), warehouseMiss, EXPIRETIME);

                List<ResponseTimeAndWarningVo> provinceTime = dashboardService.getDemandResponseTimeList(map);
                redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_DEMAND_RESPONSE_TIME_KEY_NAME + "_" + materiel.getActualWarehouseCode(), provinceTime, EXPIRETIME);

                //省份层面
                List<MaterialListVo> province_list = materiel.getList();
                for (MaterialListVo mater : province_list) {
                    map.put("areaId", mater.getProvinceId().toString());
                    List<MaterialListVo> provinceConsumeist = dashboardService.getMaterialConsumptionList(map);
                    redisutil.setList(SysConfigConstant.REDIS_MATERIAL_CONSUMPTION_KEY_NAME + "_" + mater.getProvinceId(), provinceConsumeist, EXPIRETIME);

                    List<MaterialListVo> provinceSuggestionist = dashboardService.getPurchaseSuggestionList(map);
                    redisutil.setList(SysConfigConstant.REDIS_MATERIAL_SUGGESTION_KEY_NAME + "_" + mater.getProvinceId(), provinceSuggestionist, EXPIRETIME);

                    List<RoofInfoVo> provinceRoofInfoVos = dashboardService.getRoofInfo(map);
                    redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_ROOFINFO_KEY_NAME + "_" + mater.getProvinceId(), provinceRoofInfoVos, EXPIRETIME);

                    List<MaterielInfo> provinceSMRS = dashboardService.getSMRS(map);
                    redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_SMRS_KEY_NAME + "_" + materiel.getProvinceId(), provinceSMRS, EXPIRETIME);

                    List<CostPerOrderVo> provinceCost = dashboardService.getMaterialConsumCostPerOrder(map);
                    redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_CONSUMCOST_PERORDER_KEY_NAME + "_" + mater.getProvinceId(), provinceCost, EXPIRETIME);

                    List<MaterialListVo> provinceInventory = dashboardService.getWareHouseMaterielInventoryList(map);
                    redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_INVENTORY_KEY_NAME + "_" + mater.getProvinceId(), provinceInventory, EXPIRETIME);

                    List<MaterialListVo> provinceMiss = dashboardService.getMissMaterielList(map);
                    redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_MATERIEL_LOSS_KEY_NAME + "_" + mater.getProvinceId(), provinceMiss, EXPIRETIME);

                    //地市层面
                    List<MaterialListVo> org_list = mater.getList();
                    for (MaterialListVo org : org_list) {
                        map.put("orgId", org.getOrgId().toString());
                        List<MaterielInfo> orgSMRS = dashboardService.getSMRS(map);
                        redisutil.setList(SysConfigConstant.REDIS_DASHBOARD_SMRS_KEY_NAME + "_" + org.getOrgId(), orgSMRS, EXPIRETIME);
                    }

                }


            }

            //历史采购清单
            LocalDate today = LocalDate.now();
            for (long i = 0L; i <= 12L; i++) {
                String localDate = today.minusMonths(i).toString();
                String ss = localDate.toString().substring(0, 7);
                map.put("date", localDate);
                List<MaterialListVo> historyList = dashboardService.getMaterialHistoryList(map);
                redisutil.setList(SysConfigConstant.REDIS_MATERIAL_HISTORY_KEY_NAME + "_" + ss, historyList, EXPIRETIME);
            }

            long E_time = System.currentTimeMillis() - startTime;
            LOGGER.info("CRON JOB DASHBOARD MODULE DATA COUNT COST TIME：" + E_time + "MS");
        } catch (Exception ex) {
            LOGGER.error("DASHBOARD DATA CACHE FAIL!");
            LOGGER.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

}
