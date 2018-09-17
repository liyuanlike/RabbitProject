package com.monitor.system.controllers;

import com.monitor.system.entity.*;
import com.monitor.system.repository.GeneralService;
import com.monitor.system.repository.WrapperService;
import com.monitor.system.vo.ErrorVO;
import com.monitor.system.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote 封装接报的  监控接口
 */

@Api(tags = {"3. 接报的监控数据"})
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    @ApiOperation(value = "接报接收消息的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/msg/data")
    public Object getMsgPageData(Long page, Long pageSize, Boolean isResolved) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, ReportMsgInfo.class, ReportReceiveMsg.class, params);
        return pageVO;
    }

    @ApiOperation(value = "接报发出事件的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/event/data")
    public Object getEventPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, ReportMsgInfo.class, ReportSendEvent.class, params);
        return pageVO;
    }

    //==================================================================
    @ApiOperation(value = "获取每种消息类型的条数", tags = {""})
    @GetMapping("/report/counts")
    public Object getMsgCountOfReport() {

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        final Long msgCount = generalService.getTotalCount(ReportMsgInfo.class, ReportReceiveMsg.class, params);
        final Long eventCount = generalService.getTotalCount(ReportMsgInfo.class, ReportSendEvent.class, params);
        countMap.put("msgCount", msgCount);
        countMap.put("eventCount", eventCount);
        return countMap;
    }


    @ApiOperation(value = "图表- 接收消息统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/msg/count")
    public Object getMsgCountOfMsg(Boolean isResolved) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        return wrapperService.getChartDataByCriteria(ReportMsgInfo.class, ReportReceiveMsg.class, params, "receiveTime");
    }

    @ApiOperation(value = "图表- 发出事件统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/event/count")
    public Object getEventCountOfReport(Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(ReportMsgInfo.class, ReportSendEvent.class, params, "sendTime");
    }

}
