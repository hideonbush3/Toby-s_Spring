package com.hideonbush.vol1.ch5.ch5_4.service;

import java.util.List;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hideonbush.vol1.ch5.ch5_4.dao.UserDao;
import com.hideonbush.vol1.ch5.ch5_4.domain.Level;
import com.hideonbush.vol1.ch5.ch5_4.domain.User;

public class UserService {
    UserDao userDao;
    private PlatformTransactionManager transactionManager;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 스프링의 트랜잭션 추상화 기술은 앞서 적용해봤던 트랜잭션 동기화를 사용한다
    // PlatformTransactionManager로 시작한 트랜잭션은 트랜잭션 동기화 저장소에 저장된다
    public void upgradeLevels() throws Exception {
        // JDBC를 이용하는 경우엔 먼저 Connection을 생성하고 트랜잭션을 시작했었다
        // PlatformTransactionManager에서는 트랜잭션을 가져오는 요청인
        // getTransaction() 메서드를 호출하기만 하면 된다
        // 필요에 따라서 트랜잭션 매니저가 DB 커넥션을 가져오는 작업도 같이 수행해주기 때문임
        // DefaultTransactionDefinition 객체는 트랜잭션에 대한 속성을 담고있음
        // 이렇게 시작된 트랜잭션은 TransactionStatus 타입의 변수에 저장된다
        // TransactionStatus은 트랜잭션에 대한 조작(커밋, 롤백 등..)이 필요할때
        // PlatformTransactionManager 메서드의 파라미터로 전달하면 된다
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        String smtpHost = "smtp.gmail.com";

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        String adminEmailAddress = "이메일";
        String adminEmailPassword = "비밀번호";

        // MailSender의 구현 클래스인 JavaMailSenderImpl
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setJavaMailProperties(javaMailProperties);
        mailSender.setProtocol("smtp");
        mailSender.setPort(587);
        mailSender.setUsername(adminEmailAddress);
        mailSender.setPassword(adminEmailPassword);

        // 간단한 텍스트가 전부이므로 MailMessage를 구현한
        // SimpleMailMessage를 사용해 메시지 생성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(adminEmailAddress);
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

        mailSender.send(mailMessage);
    }
}
