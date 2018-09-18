package com.monitor.system.repository;

import com.monitor.system.entity.WeChatMsgInfo;
import com.monitor.system.entity.WeChatSendAppeal;
import com.monitor.system.entity.WeChatSendReport;
import com.monitor.system.vo.ChartVO;
import com.monitor.system.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TestService {
    private final Logger log = LoggerFactory.getLogger(TestService.class);
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GeneralService generalService;

    @Autowired
    private WrapperService wrapperService;

    public void testInsert() {
        for (int i = 0; i < 84; i++) {
            WeChatSendAppeal appeal = new WeChatSendAppeal();
            appeal.setAppealId("AppelId1");
            appeal.setReceiveIp("192.168.1.111");
            appeal.setSuccess(true);
            WeChatSendAppeal appeal2 = new WeChatSendAppeal();
            appeal2.setAppealId("AppelId2");
            appeal2.setReceiveIp("192.168.1.111");
            appeal2.setSuccess(false);
            WeChatSendAppeal appeal3 = new WeChatSendAppeal();
            appeal3.setAppealId("AppelId1");
            appeal3.setReceiveIp("192.168.1.112");
            appeal3.setSuccess(true);
            WeChatSendReport report = new WeChatSendReport();
            report.setTaskId("ReportId2");
            report.setReceiveIp("192.168.1.222");
            appeal.setSendTime(new Date(new Date().getTime() - 24 * 60 * 60 + 60 * i));
            em.persist(appeal);
            em.persist(appeal2);
            em.persist(appeal3);
            em.persist(report);
        }
    }

    /*
    public List<WeChatMsgInfo> testQuery() {
        final TypedQuery<WeChatMsgInfo> query =
                em.createQuery("SELECT we FROM WeChatMsgInfo we WHERE we.class='WeChatSendAppeal' and we.receiveIp = :ip and we.appealId =?1 ", WeChatMsgInfo.class);
        int page = 1;
        int pageSize = 10;
        int firstIndex = (page - 1) * 10;
        query.setFirstResult(firstIndex).setMaxResults(pageSize);
        query.setParameter("ip","192.168.1.111").setParameter(1,"AppelId1");
         List<WeChatMsgInfo> resultList = query.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i) instanceof WeChatSendAppeal) {
                WeChatSendAppeal appeal = (WeChatSendAppeal) resultList.get(i);
                log.info("======== 类型= WeChatSendAppeal {}", appeal.getAppealId());
            }
        }
        return resultList;
    }
    */

    public PageVO testQuery() {
        Map<String, Object> params = new HashMap<>();
//        params.put("appealId","AppelId1");
//        params.put("receiveIp","192.168.1.112");
//        params.put("isSuccess",true);
        return generalService.criteriaQuery(1L, 10L, WeChatMsgInfo.class, WeChatSendAppeal.class, params);
    }

    public Long testGetCount() {
        Map<String, Object> params = new HashMap<>();
        params.put("receiveIp", "192.168.1.111");
        return generalService.getTotalCount(WeChatMsgInfo.class, WeChatSendAppeal.class, params);
    }

    public ChartVO testChart() {
        return wrapperService.getChartDataByCriteria(WeChatMsgInfo.class, WeChatSendAppeal.class, null, "sendTime");
    }

    public void testSaveOrUpdate() {
        WeChatSendAppeal appeal = new WeChatSendAppeal();
        appeal.setAppealId("XXXXXXXX");
        appeal.setReceiveIp("192.168.1.115");
        appeal.setSuccess(true);
        appeal.setSendTime(new Date());
        generalService.saveOrUpdateByField(WeChatMsgInfo.class, WeChatSendAppeal.class, "appealId", "AppelId1", appeal);
    }

    public static void main(String[] args){
        System.out.println("aaa_"+null);
    }
}
