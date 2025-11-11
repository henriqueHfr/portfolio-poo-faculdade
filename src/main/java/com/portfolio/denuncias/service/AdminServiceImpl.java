package com.portfolio.denuncias.service;

import com.portfolio.denuncias.model.*;
import com.portfolio.denuncias.repository.InMemoryRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementação do serviço administrativo.
 */
public class AdminServiceImpl implements AdminService {
    private final InMemoryRepository<User> userRepo;
    private final InMemoryRepository<Category> categoryRepo;
    private final InMemoryRepository<Complaint> complaintRepo;
    private final InMemoryRepository<ComplaintHistory> historyRepo;
    private final InMemoryRepository<Comment> commentRepo;

    public AdminServiceImpl(InMemoryRepository<User> userRepo,
                            InMemoryRepository<Category> categoryRepo,
                            InMemoryRepository<Complaint> complaintRepo,
                            InMemoryRepository<ComplaintHistory> historyRepo,
                            InMemoryRepository<Comment> commentRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.complaintRepo = complaintRepo;
        this.historyRepo = historyRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        return userRepo.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public User createUser(String name, String email, String password, Role role) {
        User u = new User(null, name, email, password, role);
        return userRepo.save(u);
    }

    @Override
    public Category createCategory(String name, String description) {
        Category c = new Category(name, description);
        return categoryRepo.save(c);
    }

    @Override
    public List<Category> listCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findCategory(Long id) {
        return categoryRepo.findById(id);
    }

    @Override
    public boolean toggleCategoryActive(Long id) {
        Optional<Category> opt = categoryRepo.findById(id);
        if (!opt.isPresent()) return false;
        Category c = opt.get();
        c.setActive(!c.isActive());
        categoryRepo.save(c);
        return true;
    }

    @Override
    public Complaint createComplaint(String title, String description, User reporter, Category category) {
        Complaint c = new Complaint(null, title, description, reporter, category);
        return complaintRepo.save(c);
    }

    @Override
    public List<Complaint> listComplaints() {
        return complaintRepo.findAll();
    }

    @Override
    public Optional<Complaint> findComplaint(Long id) {
        return complaintRepo.findById(id);
    }

    @Override
    public boolean changeComplaintStatus(Long complaintId, ComplaintStatus newStatus, User changedBy, String note) {
        Optional<Complaint> opt = complaintRepo.findById(complaintId);
        if (!opt.isPresent()) return false;
        Complaint c = opt.get();
        ComplaintStatus old = c.getStatus();
        if (old == newStatus) return false;
        c.setStatus(newStatus);
        complaintRepo.save(c);
        ComplaintHistory h = new ComplaintHistory(null, old, newStatus, changedBy, note);
        historyRepo.save(h);
        c.getHistory().add(h);
        return true;
    }

    @Override
    public Map<Month, Map<String, Long>> monthlyOpenClosedCountByYear(int year) {
        Map<Month, Map<String, Long>> report = new TreeMap<>();
        for (Complaint c : complaintRepo.findAll()) {
            LocalDate d = c.getCreatedAt().toLocalDate();
            if (d.getYear() != year) continue;
            Month m = d.getMonth();
            report.putIfAbsent(m, new HashMap<>());
            Map<String, Long> counts = report.get(m);
            counts.putIfAbsent("OPEN", 0L);
            counts.putIfAbsent("CLOSED", 0L);
            if (c.getStatus() == ComplaintStatus.CLOSED) counts.put("CLOSED", counts.get("CLOSED") + 1);
            else counts.put("OPEN", counts.get("OPEN") + 1);
        }
        return report;
    }

    @Override
    public Map<String, Long> complaintCountByCategory() {
        return complaintRepo.findAll().stream()
                .collect(Collectors.groupingBy(c -> c.getCategory() != null ? c.getCategory().getName() : "N/C", Collectors.counting()));
    }
}
