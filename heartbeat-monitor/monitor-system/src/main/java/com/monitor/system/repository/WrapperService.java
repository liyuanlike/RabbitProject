package com.monitor.system.repository;

import com.monitor.system.vo.ChartVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author liuxun
 * @apiNote 包装Service
 */
@Service
@Transactional
public class WrapperService {
    private static final Logger logger = LoggerFactory.getLogger(WrapperService.class);

    @Autowired
    private GeneralService generalService;

    public ChartVO getChartDataByCriteria(Class<?> parent, Class<?> target, Map<String, Object> params, String timeField) {
        // 将昨天分成12个段  每个时间段是两个小时
        final ChartVO chartVO = new ChartVO();
        chartVO.setCountList(new ArrayList<>());
        chartVO.setTimesList(new ArrayList<>());
        for (Long i = 0L; i <= 24; i += 2) {
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, i == 0 ? i.intValue() : i.intValue() - 2);
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date beginDate = c.getTime();
            Date endDate = null;
            if (i == 0) {
                endDate = beginDate;
            } else if (i == 24) {
                c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
                c.set(Calendar.HOUR_OF_DAY,0);
                endDate = c.getTime();
            } else {
                c.set(Calendar.HOUR_OF_DAY, (i.intValue() + 2));
                endDate = c.getTime();
            }
            final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger.info("===={}  begin={}  end={}", i, s.format(beginDate), s.format(endDate));
            final Long count = generalService.getCountBetweenDate(parent, target, params, beginDate, endDate, timeField);
            chartVO.getTimesList().add(i);
            chartVO.getCountList().add(count);
        }

        return chartVO;
    }

    public static void main(String[] args) {
        new WrapperService().getChartDataByCriteria(null, null, null, null);
    }
}
