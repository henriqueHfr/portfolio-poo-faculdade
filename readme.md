```markdown
# Denúncias — Lado Administrador (Java)

Este repositório contém a implementação em Java do lado administrativo de um sistema de denúncias públicas. O foco aqui é puramente educacional: apresentar um projeto bem organizado, orientado a objetos e pronto para ser estendido — ideal para estudos, portfólio ou como base para uma entrega acadêmica.

Por que este projeto?
- Porque transformar requisitos reais (relatar problemas urbanos) em um design simples e robusto é um excelente exercício de POO.
- Porque ele demonstra como separar responsabilidades: modelos, repositórios, serviços e a camada de aplicação (console) trabalham desacoplados e fáceis de substituir.
- Porque serve como ponto de partida para evoluções: API REST, persistência real, front-end web ou testes automatizados.

O que há aqui (resumo)
- Modelos: User, Category, Complaint, Comment, ComplaintHistory e enums auxiliares.
- Repositórios: interfaces genéricas e implementação em memória (InMemoryRepository) para facilitar experimentos sem banco.
- Serviços: interface e implementação do AdminService que encapsula regras administrativas (criar categorias, listar denúncias, alterar status, gerar relatórios).
- Aplicação: um app console simples que demonstra o fluxo administrativo e permite testar as funcionalidades.
- Build: projeto Maven com JAR executável para rodar localmente.

Principais decisões de design
- Orientação a objetos: entidades pequenas e coesas, métodos com responsabilidades claras e nomes autoexplicativos.
- Abstração da persistência: trocando a implementação do repositório por uma que use JDBC/JPA, a aplicação evolui sem mudanças na lógica de negócio.
- Histórico e auditoria: cada alteração de status é registrada em uma entidade de histórico — essencial para rastreabilidade em sistemas públicos.
- Simplicidade para ensino: senha em texto e repositório em memória são escolhas deliberadas para manter o foco em arquitetura e POO (não é produção).

Como executar
1. Requisitos: Java 11+ e Maven.
2. Compilar e empacotar:
   mvn clean package
3. Executar:
   java -jar target/denuncias-admin-1.0.0.jar

O que você pode fazer a seguir
- Substituir InMemoryRepository por JPA/Hibernate com um banco (H2 para testes locais).
- Adicionar autenticação segura e controle de permissões (RBAC).
- Criar uma API REST (Spring Boot) que exponha os serviços administrativos.
- Implementar testes unitários (JUnit) para cobrir regras de negócio.
- Integrar o front-end (HTML/CSS/React) usando os wireframes como referência.

Um recado rápido
Este projeto não é uma solução pronta para produção, mas sim uma base didática e bem estruturada para aprender e demonstrar conceitos de POO em um contexto real. Se quiser, posso ajudar a transformar isso em uma API completa, adicionar persistência, criar testes ou preparar um repositório público no GitHub com todo o código.

Boa leitura e bom desenvolvimento!
```