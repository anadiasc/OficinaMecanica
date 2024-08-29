import DAO.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        CarroDAO carroDAO = new CarroDAO();
        MecanicoDAO mecanicoDAO = new MecanicoDAO();
        ServicosDAO servicosDAO = new ServicosDAO();
        OrdemDeServicoDAO ordemDeServicoDAO = new OrdemDeServicoDAO();

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("=================================================");
            System.out.println("     Sistema Gerenciador de Ordem de Serviço   ");
            System.out.println("=================================================");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Carros");
            System.out.println("3. Gerenciar Mecânicos");
            System.out.println("4. Gerenciar Serviços");
            System.out.println("5. Gerenciar Ordens de Serviço");
            System.out.println("0. Sair");
            System.out.println("=======================================");
            System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuClientes(scanner);
                    break;
                case 2:
                    menuCarros(scanner);
                    break;
                case 3:
                    menuMecanicos(scanner);
                    break;
                case 4:
                    menuServicos(scanner);
                    break;
                case 5:
                    menuOrdensDeServico(scanner);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void menuClientes(Scanner scanner) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Scanner input = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("===== Gerenciar Clientes =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Editar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Cliente cliente = new Cliente();
                    String nome;
                    String cpf;
                    Long telefone;

                    System.out.print("Informe o nome do cliente: ");
                    nome =  input.nextLine();
                    cliente.setNome(nome);

                    System.out.println("Informe o cpf do cliente: ");
                    cpf =  input.next();
                    cliente.setCpf(cpf);

                    System.out.println("Informe o telefone do cliente: ");
                    telefone =  input.nextLong();
                    cliente.setTelefone(telefone);

                    System.out.println("Cadastrando cliente...");
                    clienteDAO.inserirCliente(cliente);

                    break;
                case 2:
                    List<Cliente> listarClientes = clienteDAO.listarClientes();

                    System.out.println();
                    System.out.println("======================= Lista de Clientes =====================");
                    System.out.println("================================================================");
                    System.out.printf("%-5s %-20s %-18s %-15s\n", "ID", "Nome", "CPF", "Telefone");
                    System.out.println("================================================================");
                    for (Cliente c : listarClientes) {
                        System.out.printf("%-5d %-20s %-18s %-15s\n", c.getId(), c.getNome(), c.getCpf(), c.getTelefone());
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Informe o ID do cliente a ser editado: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    System.out.print("Informe o novo nome do cliente: ");
                    nome = scanner.nextLine();

                    System.out.print("Informe o novo CPF do cliente: ");
                    cpf = scanner.nextLine();

                    System.out.print("Informe o novo telefone do cliente: ");
                    telefone = scanner.nextLong();

                   cliente = new Cliente(id, nome, cpf, telefone);

                    if (clienteDAO.alterarCliente(cliente)) {
                        System.out.println("Cliente atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar cliente.");
                    }

                    break;
                case 4:

                    System.out.print("Informe o ID do cliente: ");
                    int delId = input.nextInt();

                    System.out.println("Removendo cliente...");
                    if(clienteDAO.delCliente(delId)){
                        System.out.println("Cliente removido com sucesso!");
                    }else{
                        System.out.println("Erro ao remover cliente ID "+delId);
                    }

                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuCarros(Scanner scanner) {
        CarroDAO carroDAO = new CarroDAO();
        Scanner input = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("===== Gerenciar Carros =====");
            System.out.println("1. Cadastrar Carro");
            System.out.println("2. Listar Carros");
            System.out.println("3. Editar Carro");
            System.out.println("4. Remover Carro");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Carro carro = new Carro();
                    String placa;
                    int idProprietario;

                    System.out.print("Informe a placa do carro: ");
                    placa =  input.nextLine();
                    carro.setPlaca(placa);

                    System.out.println("Informe id do proprietario: ");
                    idProprietario = input.nextInt();
                    carro.setIdProprietario(idProprietario);

                    System.out.println("Cadastrando carro...");
                    carroDAO.inserirCarro(carro);

                    break;
                case 2:
                    List<Carro> listarCarros = carroDAO.listarCarros();

                    System.out.println();
                    System.out.println("======================= Lista de Carros =====================");
                    System.out.println("=============================================================");
                    System.out.printf("%-5s %-20s %-18s\n", "ID", "Placa", "ID do Proprietário");
                    System.out.println("=============================================================");
                    for (Carro crr : listarCarros) {
                        System.out.printf("%-5d %-20s %-18s\n", crr.getId(), crr.getPlaca(),crr.getIdProprietario());
                    }
                    System.out.println();

                    break;
                case 3:
                    System.out.print("Informe o ID do carro a ser editado: ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Informe a nova placa do carro: ");
                    placa = input.nextLine();

                    System.out.print("Informe o novo ID do proprietário: ");
                    idProprietario = input.nextInt();

                    carro = new Carro(id, placa, idProprietario);

                    System.out.println("Editando carro...");
                    if (carroDAO.alterarCarro(carro)) {
                        System.out.println("Carro atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar carro.");
                    }
                    break;
                case 4:

                    System.out.print("Informe o ID do carro: ");
                    int delId = input.nextInt();

                    System.out.println("Removendo carro...");

                    if(carroDAO.delCarro(delId)){
                        System.out.println("Carro removido com sucesso!");
                    }else{
                        System.out.println("Erro ao remover carro ID "+delId);
                    }

                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuMecanicos(Scanner scanner) {
        MecanicoDAO mecanicoDAO = new MecanicoDAO();
        Scanner input = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("===== Gerenciar Mecânicos =====");
            System.out.println("1. Cadastrar Mecânico");
            System.out.println("2. Listar Mecânicos");
            System.out.println("3. Editar Mecânico");
            System.out.println("4. Remover Mecânico");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Mecanico mecanico = new Mecanico();

                    String nome;
                    String cpf;
                    Long telefone;

                    System.out.print("Informe o nome do mecânico: ");
                    nome =  input.nextLine();
                    mecanico.setNome(nome);

                    System.out.println("Informe o cpf do mecânico: ");
                    cpf =  input.next();
                    mecanico.setCpf(cpf);

                    System.out.println("Informe o telefone do mecânico: ");
                    telefone =  input.nextLong();
                    mecanico.setTelefone(telefone);

                    System.out.println("Cadastrando mecânico...");
                    mecanicoDAO.inserirMecanico(mecanico);
                    break;
                case 2:
                    List<Mecanico> listarMecanico= mecanicoDAO.listarMecanicos();

                    System.out.println();
                    System.out.println("======================= Lista de Mecânicos =====================");
                    System.out.println("================================================================");
                    System.out.printf("%-5s %-20s %-18s %-15s\n", "ID", "Nome", "CPF", "Telefone");
                    System.out.println("================================================================");
                    for (Mecanico m : listarMecanico) {
                        System.out.printf("%-5d %-20s %-18s %-15s\n", m.getId(), m.getNome(), m.getCpf(), m.getTelefone());
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Informe o ID do mecânico a ser editado: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    System.out.print("Informe o novo nome do mecânico: ");
                    nome = scanner.nextLine();

                    System.out.print("Informe o novo CPF do mecânico: ");
                    cpf = scanner.nextLine();

                    System.out.print("Informe o novo telefone do mecânico: ");
                    telefone = scanner.nextLong();

                    mecanico = new Mecanico(id, nome, cpf, telefone);

                    if (mecanicoDAO.alterarMecanico(mecanico)) {
                        System.out.println("Mecânico atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar mecânico.");
                    }

                    System.out.println("Editando mecânico...");
                    break;
                case 4:
                    System.out.print("Informe o ID do mecânico: ");
                    int delId = input.nextInt();

                    System.out.println("Removendo mecânico...");
                    if(mecanicoDAO.delMecanico(delId)){
                        System.out.println("Mecânico removido com sucesso!");
                    }else{
                        System.out.println("Erro ao remover mecânico ID "+delId);
                    }

                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuServicos(Scanner scanner) {
        ServicosDAO servicosDAO = new ServicosDAO();
        Scanner input = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("===== Gerenciar Serviços =====");
            System.out.println("1. Cadastrar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Editar Serviço");
            System.out.println("4. Remover Serviço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    Servicos servicos = new Servicos();

                    String nome;
                    Double preco;

                    System.out.print("Informe o nome do servico: ");
                    nome =  input.nextLine();
                    servicos.setName(nome);

                    System.out.println("Informe o preço do serviço: ");
                    preco = input.nextDouble();
                    servicos.setPreco(preco);

                    System.out.println("Cadastrando serviço...");
                    servicosDAO.inserirServicos(servicos);

                    break;
                case 2:
                    List<Servicos> listarSevicos= servicosDAO.listarServicos();

                    System.out.println();
                    System.out.println("======================= Lista de Serviços =====================");
                    System.out.println("================================================================");
                    System.out.printf("%-5s %-35s %-18s\n", "ID", "Nome", "Preço");
                    System.out.println("================================================================");
                    for (Servicos s : listarSevicos) {
                        System.out.printf("%-5d %-35s %-18s\n", s.getId(), s.getName(), s.getPreco());
                    }
                    System.out.println();

                    break;
                case 3:
                    System.out.print("Informe o ID do serviço a ser editado: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    System.out.print("Informe o novo nome do servico: ");
                    nome = scanner.nextLine();

                    System.out.print("Informe o novo preço do servico: ");
                    preco = scanner.nextDouble();

                    servicos = new Servicos(id, nome, preco);

                    System.out.println("Editando serviço...");
                    if (servicosDAO.alterarServico(servicos)) {
                        System.out.println("serviço atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar mecânico.");
                    }

                    break;
                case 4:
                    System.out.print("Informe o ID do serviço: ");
                    int delId = input.nextInt();

                    System.out.println("Removendo serviço...");
                    if(servicosDAO.delServico(delId)){
                        System.out.println("Serviço removido com sucesso!");
                    }else{
                        System.out.println("Erro ao remover serviço ID "+delId);
                    }

                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuOrdensDeServico(Scanner scanner) {
        OrdemDeServicoDAO ordemDeServicoDAO = new OrdemDeServicoDAO();
        Scanner input = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("===== Gerenciar Ordens de Serviço =====");
            System.out.println("1. Cadastrar Ordem de Serviço");
            System.out.println("2. Listar Ordens de Serviço");
            System.out.println("3. Listar Ordem de Serviço por ID");
            System.out.println("4. Editar Ordem de Serviço");
            System.out.println("5. Remover Ordem de Serviço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    ordemDeServico ordem = new ordemDeServico();
                    CarroDAO carroDAO = new CarroDAO();
                    Carro carro = new Carro();

                    List<Integer> idServico = new ArrayList<>();
                    int idMecanico;
                    int idCarro;
                    int sair = 0;

                    do{
                        System.out.print("Informe o id do servico: ");
                        idServico.add(input.nextInt());

                        System.out.print("\nDeseja continuar? [sim: 1 | não: 0] ");
                        sair = input.nextInt();
                    }while (sair!=0);
                    ordem.setIdServicos(idServico);

                    System.out.println("Informe o id do mecânico: ");
                    idMecanico =  input.nextInt();
                    ordem.setIdMecanico(idMecanico);

                    System.out.println("Informe a placa do carro: ");//implementar busca por placa que retorne o id
                    carro = carroDAO.buscarPorPlaca(input.next());
                    idCarro = carro.getId();

                    ordem.setIdCarro(idCarro);

                    System.out.println("Cadastrando serviço...");
                    ordemDeServicoDAO.inserirOrdem(ordem);
                    break;
                case 2:

                    System.out.println("Listando ordens de serviço...");
                    ordemDeServicoDAO.listarOrdem();

                    break;
                case 3:
                    System.out.print("Informe o ID da ordem de serviço: ");
                    int id = input.nextInt();
                    ordemDeServicoDAO.listarPorId(id);
                    break;
                case 4:
                    carroDAO = new CarroDAO();

                    System.out.print("Informe o ID da ordem de serviço a ser editada: ");
                    id = scanner.nextInt();

                    idServico = new ArrayList<>();
                    do{
                        System.out.print("Informe o id do novo servico: ");
                        idServico.add(input.nextInt());

                        System.out.print("\nDeseja continuar? [sim: 1 | não: 0] ");
                        sair = input.nextInt();
                    }while (sair!=0);

                    System.out.print("Informe o id do novo mecanico: ");
                    idMecanico = scanner.nextInt();

                    System.out.print("Informe a placa do novo carro: ");
                    carro = carroDAO.buscarPorPlaca(input.next());
                    idCarro = carro.getId();

                    ordem  = new ordemDeServico();
                    ordem.setId(id);
                    ordem.setIdMecanico(idMecanico);
                    ordem.setIdServicos(idServico);
                    ordem.setIdCarro(idCarro);

                    System.out.println("Editando ordem de serviço...");
                    if (ordemDeServicoDAO.atualizarOrdem(ordem)) {
                        System.out.println("serviço atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar mecânico.");
                    }

                    break;
                case 5:
                    System.out.print("Informe o ID da ordem de serviço: ");
                    int delId = input.nextInt();

                    System.out.println("Removendo ordem de serviço...");
                    if(ordemDeServicoDAO.delOrdem(delId)){
                        System.out.println("Ordem de serviço removido com sucesso!");
                    }else{
                        System.out.println("Erro ao remover ordem de serviço ID "+delId);
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}
