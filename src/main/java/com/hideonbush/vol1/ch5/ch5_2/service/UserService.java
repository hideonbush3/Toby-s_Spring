package com.hideonbush.vol1.ch5.ch5_2.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    // 스프링의 트랜잭션 추상화 기술은 앞서 적용해봤던 트랜잭션 동기화를 사용한다
    // PlatformTransactionManager로 시작한 트랜잭션은 트랜잭션 동기화 저장소에 저장된다
    // PlatformTransactionManager를 구현한 DataSourceTransactionManager는
    // JdbcTemplate에서 사용될 수 있는 방식으로 트랜잭션을 관리해준다
    // 따라서 PlatformTransactionManager를 통해 시작한 트랜잭션은
    // UserDao의 JdbcTemplate 안에서 사용된다
    public void upgradeLevels() throws Exception {
        // 스프링이 제공하는 트랜잭션 경계설정을 위한 추상 인터페이스 PlatformTransactionManager
        // JDBC의 로컬 트랜잭션을 사용한다면 PlatformTransactionManager을 구현한
        // DataSourceTransactionManager를 사용한다
        // 생성자의 파라미터로 사용할 DB의 DataSource를 전달한다
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        // JDBC를 이용하는 경우엔 먼저 Connection을 생성하고 트랜잭션을 시작했었다
        // PlatformTransactionManager에서는 트랜잭션을 가져오는 요청인
        // getTransaction() 메서드를 호출하기만 하면 된다
        // 필요에 따라서 트랜잭션 매니저가 DB 커넥션을 가져오는 작업도 같이 수행해주기 때문임
        // DefaultTransactionDefinition 객체는 트랜잭션에 대한 속성을 담고있음
        // 이렇게 시작된 트랜잭션은 TransactionStatus 타입의 변수에 저장된다
        // TransactionStatus은 트랜잭션에 대한 조작(커밋, 롤백 등..)이 필요할때
        // PlatformTransactionManager 메서드의 파라미터로 전달하면 된다
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
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
