package com.karas.wftoolkit.Mail;

import android.util.Log;

import com.sun.mail.smtp.SMTPMessage;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Hongliang Luo on 2019/8/7.
 **/
public class MailUtils {
    private static final String TAG="MailUtils";
    private static MailUtils instance;

    public static MailUtils getInstance(){
        if(instance==null){
            synchronized (MailUtils.class){
                if(instance==null){
                    instance=new MailUtils();
                }
            }
        }
        Log.i(TAG,"instance="+instance);
        return instance;
    }

    public boolean verifyAccount(final String email, final String password){
        String server=email.split("@")[1];
        Log.i(TAG,"server="+server);
        Properties props=new Properties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.host","smtp."+server);
        props.put("mail.smtp.port",EmailConstants.getValue(server));
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.timeout","6000");
        Session session=Session.getDefaultInstance(props);
        try {
            Transport transport = session.getTransport();
            transport.connect(email,password);
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendEmail(final String email, final String password, List<String>friends,
                          String title, String content) throws Exception{
        if(friends.size()<=0){
            return;
        }
        String server=email.split("@")[1];
        Log.i(TAG,"server="+server);
        Properties props=new Properties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.host","smtp."+server);
        props.put("mail.smtp.port",EmailConstants.getValue(server));
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.timeout","6000");
        Session session=Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email,password);
                    }
                });
        MimeMessage mimeMessage=new MimeMessage(session);
        //发送方
        mimeMessage.setFrom(new InternetAddress(email));
        //接收方
        int size=friends.size();
        Address[]addresses=new Address[size];
        for (int i=0;i<size;i++){
            addresses[i]=new InternetAddress(friends.get(i));
        }
        mimeMessage.setRecipients(Message.RecipientType.TO,addresses);
        mimeMessage.setSubject(title);
        mimeMessage.setText(content);
        //发送
        Transport.send(mimeMessage);
    }
}
