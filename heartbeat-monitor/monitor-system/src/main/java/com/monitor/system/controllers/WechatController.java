package com.monitor.system.controllers;

import com.monitor.system.entity.*;
import com.monitor.system.repository.GeneralService;
import com.monitor.system.repository.WrapperService;
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

@Api(tags = {"5. 公众号监控数据"})
@RequestMapping("/wechat")
@RestController
public class WechatController {
    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    @ApiOperation(value = "获取发出上报的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/instruction/data")
    public Object getReportPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, WeChatMsgInfo.class, WeChatSendReport.class, params);
        return pageVO;
    }

    @ApiOperation(value = "获取发出上诉的分页数据", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "isSuccess", value = "是否发送成功", required = false, paramType = "query", dataType = "Boolean")

    })
    @GetMapping("/appeal/data")
    public Object getAppealPageData(Long page, Long pageSize, Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        final PageVO pageVO = generalService.criteriaQuery(page, pageSize, WeChatMsgInfo.class, WeChatSendAppeal.class, params);
        return pageVO;
    }

    //============================================================
    @ApiOperation(value = "获取每种消息类型的条数", tags = {""})
    @GetMapping("/counts")
    public Object getMsgCountOfWechat() {

        Map<String, Long> countMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        final Long reportCount = generalService.getTotalCount(WeChatMsgInfo.class, WeChatSendReport.class, params);
        final Long appealCount = generalService.getTotalCount(WeChatMsgInfo.class, WeChatSendAppeal.class, params);
        countMap.put("reportCount", reportCount);
        countMap.put("appealCount", appealCount);
        return countMap;
    }

    @ApiOperation(value = "图表- 发出上报统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/report/count")
    public Object getReportCountOfWechat(Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(WeChatMsgInfo.class, WeChatSendReport.class, params, "sendTime");
    }
    
    @ApiOperation(value = "图表- 发出上诉统计", tags = {""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSuccess", value = "是否成功", required = false, paramType = "query", dataType = "Boolean")
    })
    @GetMapping("/appeal/count")
    public Object getAppealCountOfWechat(Boolean isSuccess) {
        Map<String, Object> params = new HashMap<>();
        if (isSuccess != null) {
            params.put("isSuccess", isSuccess);
        }
        return wrapperService.getChartDataByCriteria(WeChatMsgInfo.class, WeChatSendAppeal.class, params, "sendTime");
    }
}
