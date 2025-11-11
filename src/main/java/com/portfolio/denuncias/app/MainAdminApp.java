package com.portfolio.denuncias.app;

import com.portfolio.denuncias.model.*;
import com.portfolio.denuncias.repository.InMemoryRepository;
import com.portfolio.denuncias.service.AdminService;
import com.portfolio.denuncias.service.AdminServiceImpl;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class MainAdminApp {
    public static void main(String[] args) {
        // Repositórios em memória
        InMemoryRepository<User> userRepo = new InMemoryRepository<>();
        InMemoryRepository<Category> categoryRepo = new InMemoryRepository<>();
        InMemoryRepository<Complaint> complaintRepo = new InMemoryRepository<>();
        InMemoryRepository<ComplaintHistory> historyRepo = new InMemoryRepository<>();
        InMemoryRepository<Comment> commentRepo = new InMemoryRepository<>();

        AdminService admin = new AdminServiceImpl(userRepo, categoryRepo, complaintRepo, historyRepo, commentRepo);

        // Dados iniciais
        User adminUser = admin.createUser("Prefeitura Admin", "admin@prefeitura.gov", "admin123", Role.ADMIN);
        User maria = admin.createUser("Maria Silva", "maria@example.com", "1234", Role.USER);

        Category cat1 = admin.createCategory("Iluminação Pública", "Postes, lâmpadas, fiação");
        Category cat2 = admin.createCategory("Mobilidade Urbana", "Buracos, semáforos, sinalização");
        Category cat3 = admin.createCategory("Saneamento Básico", "Esgoto, limpeza urbana");

        admin.createComplaint("Poste sem luz na Rua A", "Poste apagado há 10 dias", maria, cat1);
        admin.createComplaint("Buraco perigoso na Rua B", "Grande buraco, risco de acidente", maria, cat2);
        Complaint c3 = admin.createComplaint("Vazamento de esgoto na Av C", "Cheiro forte e água suja", maria, cat3);
        admin.changeComplaintStatus(c3.getId(), ComplaintStatus.CLOSED, adminUser, "Equipe de saneamento realizou reparo.");

        runConsole(admin, adminUser);
    }

    private static void runConsole(AdminService admin, User currentAdmin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Sistema de Denúncias (Admin) ===");
        // login demo omitted (we have currentAdmin)
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Listar categorias");
            System.out.println("2) Criar categoria");
            System.out.println("3) Alternar status de categoria");
            System.out.println("4) Listar denúncias");
            System.out.println("5) Ver detalhe denúncia");
            System.out.println("6) Alterar status denúncia");
            System.out.println("7) Relatório mensal (ano atual)");
            System.out.println("8) Distribuição por categoria");
            System.out.println("0) Sair");
            System.out.print("Opção: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    List<Category> cats = admin.listCategories();
                    System.out.println("Categorias:");
                    cats.forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Nome categoria: ");
                    String name = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String desc = scanner.nextLine();
                    Category created = admin.createCategory(name, desc);
                    System.out.println("Criada: " + created);
                    break;
                case "3":
                    System.out.print("ID categoria: ");
                    Long cid = Long.parseLong(scanner.nextLine());
                    boolean toggled = admin.toggleCategoryActive(cid);
                    System.out.println(toggled ? "Status alternado." : "Categoria não encontrada.");
                    break;
                case "4":
                    List<Complaint> complaints = admin.listComplaints();
                    System.out.println("Denúncias:");
                    complaints.forEach(System.out::println);
                    break;
                case "5":
                    System.out.print("ID denúncia: ");
                    Long did = Long.parseLong(scanner.nextLine());
                    Optional<Complaint> opd = admin.findComplaint(did);
                    if (!opd.isPresent()) {
                        System.out.println("Denúncia não encontrada.");
                    } else {
                        Complaint comp = opd.get();
                        System.out.println("Detalhes: " + comp);
                        System.out.println("Descrição: " + comp.getDescription());
                        System.out.println("Categoria: " + (comp.getCategory() != null ? comp.getCategory().getName() : "N/C"));
                        System.out.println("Status: " + comp.getStatus());
                        System.out.println("Criada em: " + comp.getCreatedAt());
                        System.out.println("Histórico:");
                        comp.getHistory().forEach(h -> System.out.println("  " + h));
                        System.out.println("Comentários:");
                        comp.getComments().forEach(cm -> System.out.println("  " + cm));
                    }
                    break;
                case "6":
                    System.out.print("ID denúncia: ");
                    Long dc = Long.parseLong(scanner.nextLine());
                    System.out.print("Novo status (OPEN, IN_PROGRESS, CLOSED): ");
                    String ns = scanner.nextLine().trim().toUpperCase();
                    try {
                        ComplaintStatus st = ComplaintStatus.valueOf(ns);
                        System.out.print("Nota (opcional): ");
                        String note = scanner.nextLine();
                        boolean changed = admin.changeComplaintStatus(dc, st, currentAdmin, note);
                        System.out.println(changed ? "Status alterado." : "Falha ao alterar (id inválido ou sem alteração).");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Status inválido.");
                    }
                    break;
                case "7":
                    int year = LocalDate.now().getYear();
                    Map<Month, Map<String, Long>> report = admin.monthlyOpenClosedCountByYear(year);
                    System.out.println("Relatório " + year + ":");
                    report.forEach((m, counts) -> System.out.printf("  %s -> OPEN=%d CLOSED=%d%n", m, counts.getOrDefault("OPEN", 0L), counts.getOrDefault("CLOSED", 0L)));
                    break;
                case "8":
                    Map<String, Long> byCat = admin.complaintCountByCategory();
                    System.out.println("Denúncias por categoria:");
                    byCat.forEach((k, v) -> System.out.printf("  %s: %d%n", k, v));
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        System.out.println("Encerrando.");
        scanner.close();
    }
}
