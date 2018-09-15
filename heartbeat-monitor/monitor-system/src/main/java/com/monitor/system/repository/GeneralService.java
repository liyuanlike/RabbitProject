package com.monitor.system.repository;

import com.monitor.system.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
@Transactional
public class GeneralService {
    private Logger logger = LoggerFactory.getLogger(GeneralService.class);

    @PersistenceContext
    private EntityManager em;

    public PageVO criteriaQuery(Long page, Long pageSize, Class<?> parent, Class<?> target, Map<String, Object> params) {
        String parentName = parent.getSimpleName();
        String targetName = target.getSimpleName();
        Long index = (page - 1) * pageSize;
        StringBuffer resultSqlBuffer = new StringBuffer();
        StringBuffer countSqlBuffer = new StringBuffer();
        String token = (params == null || params.keySet().size() == 0) ? "" : " AND ";
        countSqlBuffer.append("SELECT count(*) FROM " + parentName + " a WHERE type(a)=" + targetName + token);
        resultSqlBuffer.append("SELECT a FROM " + parentName + " a WHERE a.class=" + targetName + token);

        int entryCount = 0;
        for (Entry<String, Object> entry : params.entrySet()) {
            entryCount++;
            final String attribute = entry.getKey();
            final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
            String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
            resultSqlBuffer.append(sqlMiddle);
            countSqlBuffer.append(sqlMiddle);
        }
        logger.info("resultSql= {}", resultSqlBuffer.toString());
        logger.info("countSql= {}", countSqlBuffer.toString());
        final long totalSize = em.createQuery(countSqlBuffer.toString(), Long.class).getSingleResult().longValue();
        Long totalPage = (totalSize + pageSize - 1) / pageSize;
        logger.info("总条数: {}",totalSize);
        final List<?> resultList = em.createQuery(resultSqlBuffer.toString(), parent)
                .setFirstResult(index.intValue()).setMaxResults(pageSize.intValue())
                .getResultList();
        PageVO pageVO = new PageVO(resultList, totalPage, page, pageSize);
        return pageVO;
    }
}
