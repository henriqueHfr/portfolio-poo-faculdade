package com.portfolio.denuncias.service;

import com.portfolio.denuncias.model.*;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface AdminService {
    Optional<User> authenticate(String email, String password);
    User createUser(String name, String email, String password, Role role);

    Category createCategory(String name, String description);
    List<Category> listCategories();
    Optional<Category> findCategory(Long id);
    boolean toggleCategoryActive(Long id);

    Complaint createComplaint(String title, String description, User reporter, Category category);
    List<Complaint> listComplaints();
    Optional<Complaint> findComplaint(Long id);
    boolean changeComplaintStatus(Long complaintId, ComplaintStatus newStatus, User changedBy, String note);

    Map<Month, Map<String, Long>> monthlyOpenClosedCountByYear(int year);
    Map<String, Long> complaintCountByCategory();
}