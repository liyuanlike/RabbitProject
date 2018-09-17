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
 * @apiNote 封装指挥的  监控接口
 */

@Api(tags = {"4. 指挥监控数据"})
@RequestMapping("/direct")
@RestController
public class DirectController {

    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    @ApiOperation(value = "获取接收事件的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/event/data")
    public Object getEventPageData(Long page, Long pageSize, Boolean isResolved) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, DirectMsgInfo.class, DirectReceiveEvent.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出指令的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/instruction/data")
    public Object getInstructionPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, DirectMsgInfo.class, DirectSendInstruction.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取接受反馈的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long")

    })
    @GetMapping("/execution/data")
    public Object getExecutionPageData(Long page, Long pageSize) {
        Map<String, Object> params = new HashMap<>();
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, DirectMsgInfo.class, DirectReceiveExecution.class, params);
        return pageVO;
    }


    // ===============================================================
    @ApiOperation(value = "获取每种消息类型的条数", tags = {""})
    @GetMapping("/counts")
    public Object getMsgCountOfDirect() {

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        final Long eventCount = generalService.getTotalCount(DirectMsgInfo.class, DirectReceiveEvent.class, params);
        final Long instructionCount = generalService.getTotalCount(DirectMsgInfo.class, DirectSendInstruction.class, params);
        final Long executionCount = generalService.getTotalCount(DirectMsgInfo.class, DirectReceiveExecution.class, params);
        countMap.put("eventCount", eventCount);
        countMap.put("instructionCount", instructionCount);
        countMap.put("executionCount", executionCount);
        return countMap;
    }

    @ApiOperation(value = "图表- 接收事件统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/event/count")
    public Object getEventCountOfDirect(Boolean isResolved) {

        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        return wrapperService.getChartDataByCriteria(DirectMsgInfo.class, DirectReceiveEvent.class, params, "receiveTime");
    }

    @ApiOperation(value = "图表- 发出指令统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/instruction/count")
    public Object getInstructionCountOfDirect(Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(DirectMsgInfo.class, DirectSendInstruction.class, params, "sendTime");
    }

    @ApiOperation(value = "图表- 接收反馈统计", tags = {""})
    @GetMapping("/report/count")
    public Object getExecutionCountOfDirect() {

        Map<String, Object> params = new HashMap<>();
        return wrapperService.getChartDataByCriteria(DirectMsgInfo.class, DirectReceiveExecution.class, params, "receiveTime");
    }

}
