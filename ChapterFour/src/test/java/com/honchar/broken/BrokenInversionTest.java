package com.honchar.broken;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.honchar.hibernate.util.SessionUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class BrokenInversionTest {
    @Test
    public void testBrokenInversionCode() {
        Long emailId;
        Long messageId;
        Email email;
        Message message;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            email = new Email("Broken");
            message = new Message("Broken");
            email.setMessage(message);
            session.persist(email);
            session.persist(message);
            emailId = email.getId();
            messageId = message.getId();
            tx.commit();
        }
        assertNotNull(email.getMessage());
        assertNull(message.getEmail());
        try (Session session = SessionUtil.getSession()) {
            email = session.get(Email.class, emailId);
            System.out.println(email);
            message = session.get(Message.class, messageId);
            System.out.println(message);
        }
        assertNotNull(email.getMessage());
        assertNull(message.getEmail());
    }
}