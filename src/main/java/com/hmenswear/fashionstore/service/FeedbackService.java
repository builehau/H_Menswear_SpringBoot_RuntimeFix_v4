package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbacks; private final NotificationRepository notifications; private final AuditService audit;
    public FeedbackService(FeedbackRepository feedbacks,NotificationRepository notifications,AuditService audit){this.feedbacks=feedbacks;this.notifications=notifications;this.audit=audit;}
    @Transactional public void submit(Customer customer,String name,String email,String subject,String content){Feedback f=new Feedback();f.setCustomer(customer);f.setTenKh(name);f.setEmail(email);f.setChuDe(subject);f.setNoiDung(content);f.setNgayGui(LocalDateTime.now());f.setTrangThai("Chờ xử lý");feedbacks.save(f);}
    @Transactional public Map<String,Object> reply(Long id,String content){Feedback f=feedbacks.findById(id).orElseThrow();f.setTrangThai("Đã xử lý");feedbacks.save(f);if(f.getCustomer()!=null){Notification n=new Notification();n.setCustomer(f.getCustomer());n.setTitle("Phản hồi về: "+f.getChuDe());n.setContent(content==null||content.isBlank()?"Quản trị viên đã tiếp nhận, kiểm tra và xử lý xong báo cáo của bạn. Cảm ơn bạn đã đóng góp ý kiến!":content);n.setRead(false);n.setCreatedAt(LocalDateTime.now());notifications.save(n);}audit.log("Phản hồi báo cáo","Đã trả lời Report ID: "+id);return Map.of("status","success","message","Đã phản hồi và gửi thông báo cho khách!");}
}
