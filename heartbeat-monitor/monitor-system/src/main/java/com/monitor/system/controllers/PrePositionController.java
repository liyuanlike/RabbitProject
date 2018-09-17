package com.monitor.system.controllers;

import com.monitor.system.entity.PreMsgInfo;
import com.monitor.system.entity.PreReceiveInstruction;
import com.monitor.system.entity.PreSendExecution;
import com.monitor.system.entity.PreSendReport;
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
 * @apiNote 封装部委前置的  监控接口
 */

@Api(tags = {"2. 部委前置监控数据"})
@RequestMapping("/pre")
@RestController
public class PrePositionController {

    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    @ApiOperation(value = "获取接收指令的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("/instruction/data")
    public Object getInstructionPageData(Long page, Long pageSize, Boolean isResolved, String prepositionID) {
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, PreMsgInfo.class, PreReceiveInstruction.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出反馈的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String")

    })
    @GetMapping("/execution/data")
    public Object getExecutionPageData(Long page, Long pageSize, Boolean isSuccess, String prepositionID) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, PreMsgInfo.class, PreSendExecution.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出上报的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String")

    })
    @GetMapping("/report/data")
    public Object getReportPageData(Long page, Long pageSize, Boolean isSuccess, String prepositionID) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, PreMsgInfo.class, PreSendReport.class, params);
        return pageVO;
    }


    // ===============================================================
    @ApiOperation(value = "获取每种消息类型的条数", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String")

    })
    @GetMapping("/preposition/counts")
    public Object getMsgCountOfPreposition(String prepositionID) {

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("prepositionID", prepositionID);
        final Long instructionCount = generalService.getTotalCount(PreMsgInfo.class, PreReceiveInstruction.class, params);
        final Long executionCount = generalService.getTotalCount(PreMsgInfo.class, PreSendExecution.class, params);
        final Long reportCount = generalService.getTotalCount(PreMsgInfo.class, PreSendReport.class, params);
        countMap.put("instructionCount", instructionCount);
        countMap.put("executionCount", executionCount);
        countMap.put("reportCount", reportCount);
        return countMap;
    }

    @ApiOperation(value = "图表- 接收指令统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isResolved", value = "是否已处理", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/instruction/count")
    public Object getMsgCountOfInstruction(String prepositionID,Boolean isResolved) {

        if (prepositionID == null ){
            return new ErrorVO("prepositionID不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        if (isResolved != null) {
            params.put("isResolved", isResolved);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }

        return wrapperService.getChartDataByCriteria(PreMsgInfo.class,PreReceiveInstruction.class,params,"receiveTime");
    }

    @ApiOperation(value = "图表- 发出反馈统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/execution/count")
    public Object getMsgCountOfExecution(String prepositionID,Boolean isSuccess) {

        if (prepositionID == null ){
            return new ErrorVO("prepositionID不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }
        return wrapperService.getChartDataByCriteria(PreMsgInfo.class,PreSendExecution.class,params,"sendTime");
    }

    @ApiOperation(value = "图表- 发出上报统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prepositionID", value = "部委前置的ID标识", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/report/count")
    public Object getMsgCountOfReport(String prepositionID,Boolean isSuccess) {

        if (prepositionID == null ){
            return new ErrorVO("prepositionID不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        if (prepositionID != null) {
            params.put("prepositionId", prepositionID);
        }
        return wrapperService.getChartDataByCriteria(PreMsgInfo.class,PreSendReport.class,params,"sendTime");
    }

}
