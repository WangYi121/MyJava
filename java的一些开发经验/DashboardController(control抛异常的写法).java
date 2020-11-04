package com.fiberhome.iwork.controller.dashboard;


import com.fiberhome.iwork.client.annotation.IgnoreClientToken;
import com.fiberhome.iwork.client.annotation.IgnoreUserToken;
import com.fiberhome.iwork.common.constants.ErrorCode;
import com.fiberhome.iwork.common.utils.ResultUtil;
import com.fiberhome.iwork.common.vo.Result;
import com.fiberhome.iwork.service.dashboard.DashboardService;
import com.fiberhome.iwork.vo.CostPerOrderVo;
import com.fiberhome.iwork.vo.MaterialListVo;
import com.fiberhome.iwork.vo.MaterielInfo;
import com.fiberhome.iwork.vo.ResponseTimeAndWarningVo;
import com.fiberhome.iwork.vo.RoofInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ：ymh&wy
 * @date ：2019/02/27
 * @desc : 大区模块
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获取看板置顶信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getRoofInfo", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public Result getRoofInfo(@RequestBody Map<String, String> map) {
        try {
            List<RoofInfoVo> result = dashboardService.getRoofInfo(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取物料可供应时间详情
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getSMRS", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public Result getSMRS(@RequestBody Map<String, String> map) {
        try {
            List<MaterielInfo> result = dashboardService.getSMRS(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取平均每单成本
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getMaterielConsumCostPerOrder", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public Result getMaterialConsumCostPerOrder(@RequestBody Map<String, String> map) {
        try {
            List<CostPerOrderVo> result = dashboardService.getMaterialConsumCostPerOrder(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取物料库存列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getWareHouseMaterielList", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public Result getWareHouseMaterielList(@RequestBody Map<String, String> map) {
        try {
            List<MaterialListVo> result = dashboardService.getWareHouseMaterielInventory(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取丢失物料列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getMissMaterielList", method = RequestMethod.POST)
    @IgnoreClientToken
    @IgnoreUserToken
    public Result getMissMaterielList(@RequestBody Map<String, String> map) {
        try {
            List<MaterialListVo> result = dashboardService.getMissMateriel(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取菲律宾的中心仓库及地区
     */
    @RequestMapping(value = "/getAreaWarehouse", method = RequestMethod.GET)
    public Result getWareHouse() {
        try {
            Map<Object, Object> result = dashboardService.getAreaWareHouse();
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取物料消耗情况
     *  @param map
     * @return
     */
    @RequestMapping(value = "/getMaterialConsumptionList", method = RequestMethod.POST)
    public Result getMaterialConsumptionList(@RequestBody Map<String, String> map) {
        try {
            List<MaterialListVo> result = dashboardService.getMaterialConsumption(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取历史采购清单
     * @param map
     * @return
     */
    @RequestMapping(value = "/getMaterialHistoryList", method = RequestMethod.POST)
    public Result getMaterialHistoryList(@RequestBody Map<String, String> map) {
        try {
            List<MaterialListVo> result = dashboardService.getMaterialHistoryListByDate(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取采购建议表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPurchaseSuggestionList", method = RequestMethod.POST)
    public Result getPurchaseSuggestionList(@RequestBody Map<String, String> map) {
        try {
            List<MaterialListVo> result = dashboardService.getPurchaseSuggestion(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取需求响应时间
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDemandResponseTime", method = RequestMethod.POST)
    public Result getDemandResponseTime(@RequestBody Map<String, String> map) {
        try {
            List<ResponseTimeAndWarningVo> result = dashboardService.getDemandResponseTime(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }

    /**
     * 获取库存告警
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInventoryWarning", method = RequestMethod.POST)
    public Result getInventoryWarning(@RequestBody Map<String, String> map) {
        try {
            List<ResponseTimeAndWarningVo> result = dashboardService.getInventoryWarning(map);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.failed(ErrorCode.NETWORK_EXCPTION);
        }
    }


}

