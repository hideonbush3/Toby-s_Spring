package com.hideonbush.vol1.ch6.ch6_1.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hideonbush.vol1.ch6.ch6_1.dao.UserDao;
import com.hideonbush.vol1.ch6.ch6_1.domain.Level;
import com.hideonbush.vol1.ch6.ch6_1.domain.User;

public class UserService {
    UserDao userDao;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;
    private String adminEmailAddress;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setAdminEmailAddress(String adminEmailAddress) {
        this.adminEmailAddress = adminEmailAddress;
    }

    // PlatformTransactionManager의 getTransaction() 메서드를 호출하면 트랜잭션이 시작된다
    // PlatformTransactionManager로 시작한 트랜잭션은 트랜잭션 동기화 저장소에 저장된다
    public void upgradeLevels() throws Exception {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            upgradeLevelsInternal();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void upgradeLevelsInternal() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        // 간단한 텍스트가 전부이므로 MailMessage를 구현한
        // SimpleMailMessage를 사용해 메시지 생성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(adminEmailAddress);
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

        this.mailSender.send(mailMessage);
    }
}
