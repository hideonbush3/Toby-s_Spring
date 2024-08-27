package com.hideonbush.vol1.ch5.ch5_2.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.hideonbush.vol1.ch5.ch5_2.dao.UserDao;
import com.hideonbush.vol1.ch5.ch5_2.domain.Level;
import com.hideonbush.vol1.ch5.ch5_2.domain.User;

public class UserService {
    UserDao userDao;
    private DataSource dataSource;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 트랜잭션 동기화가 돼있는 상태에서 JdbcTemplate을 사용하면
    // JdbcTemplate은 동기화 돼있는 DB 커넥션을 사용한다
    // 결국 DAO를 통해 진행되는 모든 데이터 액세스 작업은
    // upgradeLevels() 메서드에서 만든 똑같은 Connection을 사용한다
    public void upgradeLevels() throws Exception {
        // 스프링이 제공하는 동기화 관리 클래스
        // 트랜잭션 동기화 작업 초기화 요청
        TransactionSynchronizationManager.initSynchronization();

        // 스프링이 제공하는 유틸 클래스 DataSourceUtils
        // DataSource에서 커넥션을 가져오지 않고 DataSourceUtils에서 가져온다
        // DataSourceUtils에서 커넥션을 가져오면 해당 커넥션을
        // 트랜잭션 동기화에 사용하도록 저장소에 자동으로 바인딩 해준다
        Connection c = DataSourceUtils.getConnection(dataSource);

        // 트랜잭션 시작
        c.setAutoCommit(false);
        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }

            // 작업 정상종료시 트랜잭션 커밋
            c.commit();
        } catch (Exception e) {
            // 이상 발생시 원상복구
            c.rollback();
            throw e;
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource); // DB 커넥션 닫기

            // 동기화 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("unknown Level: " + currentLevel);
        }

    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
