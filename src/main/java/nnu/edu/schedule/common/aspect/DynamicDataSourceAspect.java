package nnu.edu.schedule.common.aspect;

import lombok.extern.slf4j.Slf4j;
import nnu.edu.schedule.common.config.DataSourceContextHolder;
import nnu.edu.schedule.common.config.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:02
 * @Description:
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {
//    @Value("${databasePath}")
//    String databasePath;



//    @Autowired
//    @Qualifier("dynamicDataSource")
//    private DynamicDataSource dynamicDataSource;

    /**
     * 切换数据源
     */
    @Before("execution(* nnu.edu.schedule.dao.pg.*.*(..))")
    public void switchPgDataSource() {
        if (!DataSourceContextHolder.getDataSourceKey().equals("pg")) {
            DataSourceContextHolder.setDataSourceKey("pg");
        }
    }
    /**
     * 重置数据源
     */
    @After("execution(* nnu.edu.schedule.dao.sqlite.*.*(..))")
    public void restoreDataSource() {
        // 将数据源置为默认数据源
        DataSourceContextHolder.setDataSourceKey("default");
    }
}
