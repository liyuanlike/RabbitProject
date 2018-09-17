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

@Api(tags = {"6. (综治通)网格员监控数据"})
@RequestMapping("/gridman")
@RestController
public class GridManController {
    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    @ApiOperation(value = "获取接收指令的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/instruction/data")
    public Object getInstructionPageData(Long page, Long pageSize, Boolean isResolved) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, GridManMsgInfo.class, GridManReceiveInstruction.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出反馈的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/execution/data")
    public Object getAppealPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, GridManMsgInfo.class, GridManSendExecution.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取接收任务的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/receivetask/data")
    public Object getReceiveTaskPageData(Long page, Long pageSize, Boolean isResolved) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, GridManMsgInfo.class, GridManReceiveTask.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出任务的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })

    @GetMapping("/sendtask/data")
    public Object getSendTaskPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, GridManMsgInfo.class, GridManSendTask.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出上报的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/sendreport/data")
    public Object getSendReportPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, GridManMsgInfo.class, GridManSendReport.class, params);
        return pageVO;
    }

    // ===================================================
    @ApiOperation(value = "获取每种消息类型的条数", tags = {""})
    @GetMapping("/gridman/counts")
    public Object getMsgCountOfGridMan(String prepositionID) {
        Map<String, Long> countMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("prepositionID", prepositionID);
        final Long instructionCount = generalService.getTotalCount(GridManMsgInfo.class, GridManReceiveInstruction.class, params);
        final Long executionCount = generalService.getTotalCount(GridManMsgInfo.class, GridManSendExecution.class, params);
        final Long receiveTaskCount = generalService.getTotalCount(GridManMsgInfo.class, GridManReceiveTask.class, params);
        final Long sendTaskCount = generalService.getTotalCount(GridManMsgInfo.class, GridManSendTask.class, params);
        final Long reportCount = generalService.getTotalCount(GridManMsgInfo.class, GridManSendReport.class, params);
        countMap.put("instructionCount", instructionCount);
        countMap.put("executionCount", executionCount);
        countMap.put("receiveTaskCount", receiveTaskCount);
        countMap.put("sendTaskCount", sendTaskCount);
        countMap.put("reportCount", reportCount);
        return countMap;
    }

    @ApiOperation(value = "图表- 接收指令统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/instruction/count")
    public Object getMsgCountOfInstruction(Boolean isResolved) {

        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        return wrapperService.getChartDataByCriteria(GridManMsgInfo.class, GridManReceiveInstruction.class, params, "receiveTime");
    }

    @ApiOperation(value = "图表- 发出反馈统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/execution/count")
    public Object getExecutionsOfGridMan(Boolean isSuccess) {

        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(GridManMsgInfo.class, GridManSendExecution.class, params, "sendTime");
    }

    @ApiOperation(value = "图表- 接收任务统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/receivetask/count")
    public Object getReceiveTaskOfGridMan(Boolean isResolved) {

        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        return wrapperService.getChartDataByCriteria(GridManMsgInfo.class, GridManReceiveTask.class, params, "receiveTime");
    }

    @ApiOperation(value = "图表- 发出任务统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")

    })

    @GetMapping("/sendtask/count")
    public Object getSendTaskOfGridMan(Boolean isSuccess) {

        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(GridManMsgInfo.class, GridManSendTask.class, params, "sendTime");
    }

    @ApiOperation(value = "图表- 发出上报统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")

    })

    @GetMapping("/sendreport/count")
    public Object getSendReportOfGridMan(Boolean isSuccess) {

        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(GridManMsgInfo.class, GridManSendReport.class, params, "sendTime");
    }


}
