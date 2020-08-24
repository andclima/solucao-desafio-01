import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AppBanco {
    // Variáveis da classe
    private static List<Cliente> listaCliente = new ArrayList<>();
    private static List<Conta> listaConta = new ArrayList<>();
    private static Scanner reader = new Scanner(System.in);
    private static DecimalFormat df = new DecimalFormat("###,##0.00");

    public static void main(String[] args) {
        int menu;
        do {
            menu = exibeMenu();
            switch (menu) {
                case 1: // Cadastrar cliente
                    cadastrarCliente();
                    break;
                case 2: // Listar clientes
                    listarClientes();
                    break;
                case 3: // Abrir conta
                    abrirConta();
                    break;
                case 4: // Efetuar depósito
                    efetuarDeposito();
                    break;
                case 5: // Efetuar saque
                    efetuarSaque();
                    break;
                case 6: // Relatório de contas
                    relatorioContas();
                    break;
                case 7: // Finalizar programa
                    System.out.println("\nAté a próxima...");
                    break;
                default:
                    System.out.println("\nOpção inválida!");
                    pausa(1);
            }
        } while (menu != 7);
    }

    public static int exibeMenu() {
        clrscr();
        System.out.println("Menu Principal\n");
        System.out.println("1. Cadastrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Abrir conta");
        System.out.println("4. Efetuar depósito");
        System.out.println("5. Efetuar saque");
        System.out.println("6. Relatório de contas");
        System.out.println("7. Finalizar programa");
        System.out.print("\nInforme uma opção: ");

        int opcao = -1;
        try {
            String texto = reader.nextLine();
            opcao = Integer.parseInt(texto);
        } catch (Exception imex) {
            // Entrada de dados inválida!
        }
        return opcao;
    }

    public static void cadastrarCliente() {
        String codigo = "", nome = "", email = "", telefone = "";
        clrscr();
        System.out.println("Novo Cliente\n");
        System.out.println("Informe os dados do novo cliente...");
        System.out.print("Código..: ");
        codigo = reader.nextLine();
        
        System.out.print("Nome....: ");
        nome = reader.nextLine();
        
        System.out.print("E-mail..: ");
        email = reader.nextLine();
        
        System.out.print("Telefone: ");
        telefone = reader.nextLine();
        
        Cliente novoCliente = new Cliente();
        novoCliente.setCodigo(codigo);
        novoCliente.setNome(nome);
        novoCliente.setEmail(email);
        novoCliente.setTelefone(telefone);

        if (listaCliente.contains(novoCliente)) {
            System.out.println("\nJá existe um cliente com este código!");
        } else {
            listaCliente.add(novoCliente);
            System.out.println("\nCliente adicionado com sucesso!");
        }
        pausa(1);
    }

    public static void listarClientes() {
        clrscr();
        System.out.println("Listar clientes\n");
        System.out.println(
            String.format("%6s %-30s %-30s %-20s", 
                "Código", 
                "Nome",
                "E-mail", 
                "Telefone"));
        for (Cliente cliente:listaCliente) {
            System.out.println(
                String.format("%6s %-30s %-30s %-20s", 
                cliente.getCodigo(), 
                cliente.getNome(),
                cliente.getEmail(), 
                cliente.getTelefone()));
        }
        System.out.print("\nPressione ENTER para continuar...");
        reader.nextLine();
    }

    public static void abrirConta() {
        clrscr();
        System.out.println("Abrir conta\n");
        System.out.print("Código do cliente: ");
        String codigo = reader.nextLine();
        Cliente cliente = new Cliente();
        cliente.setCodigo(codigo);
        if (listaCliente.contains(cliente)) {
            cliente = listaCliente.get(listaCliente.indexOf(cliente));
            System.out.println(String.format("Nome do cliente..: %s", cliente.getNome()));
        } else {
            System.out.println("\nCliente não cadastrado");
            pausa(1);
            return;
        }
        System.out.print("Número da conta..: ");
        String numero = reader.nextLine();
        System.out.print("Saldo inicial....: ");
        String saldo = reader.nextLine();
        BigDecimal valorInicial = BigDecimal.ZERO;
        try {
            valorInicial = new BigDecimal(saldo);
        } catch (Exception ex) {
            System.out.println("\nValor inválido! Será considerado o valor zero.");
        }
        
        Conta novaConta = new Conta();
        novaConta.setCliente(cliente);
        novaConta.setNumero(numero);
        novaConta.setSaldo(valorInicial);

        if (listaConta.contains(novaConta)) {
            System.out.println("\nJá existe uma conta com este número!");
        } else {
            listaConta.add(novaConta);
            System.out.println("\nConta cadastrada com sucesso!");
        }
        pausa(1);
    }

    public static void efetuarDeposito() {
        clrscr();
        System.out.println("Efetuar depósito\n");
        System.out.print("Número da conta...: ");
        String numero = reader.nextLine();

        Conta conta = new Conta();
        conta.setNumero(numero);

        if (listaConta.contains(conta)) {
            conta = listaConta.get(listaConta.indexOf(conta));
            System.out.println(String.format("Nome do cliente...: %s", conta.getCliente().getNome()));
            System.out.println(String.format("Saldo atual.......: %s", df.format(conta.getSaldo())));
        } else {
            System.out.println("\nConta não cadastrada");
            pausa(1);
            return;
        }
        System.out.print("Valor do depósito.: ");
        String valor = reader.nextLine();
        BigDecimal valorDeposito = BigDecimal.ZERO;
        try {
            valorDeposito = new BigDecimal(valor);
        } catch (Exception ex) {
            System.out.println("\nValor inválido!");
            pausa(1);
            return;
        }

        conta.setSaldo(conta.getSaldo().add(valorDeposito));
        System.out.println("Depósito efetuado com sucesso!");
        System.out.println(String.format("Novo saldo........: %s",df.format(conta.getSaldo())));
        pausa(2);
    }

    public static void efetuarSaque() {
        clrscr();
        System.out.println("Efetuar saque\n");
        System.out.print("Número da conta...: ");
        String numero = reader.nextLine();
        Conta conta = new Conta();
        conta.setNumero(numero);
        if (listaConta.contains(conta)) {
            conta = listaConta.get(listaConta.indexOf(conta));
            System.out.println(String.format("Nome do cliente...: %s", conta.getCliente().getNome()));
            System.out.println(String.format("Saldo atual.......: %s", df.format(conta.getSaldo())));
        } else {
            System.out.println("\nConta não cadastrada");
            pausa(1);
            return;
        }
        System.out.print("Valor do saque....: ");
        String valor = reader.nextLine();
        BigDecimal valorSaque = BigDecimal.ZERO;
        try {
            valorSaque = new BigDecimal(valor);
        } catch (Exception ex) {
            System.out.println("\nValor inválido!");
            pausa(1);
            return;
        }
        
        // Se o saldo for menor que o valor do saque
        if (conta.getSaldo().compareTo(valorSaque) < 0) {
            System.out.println("\nSaldo insuficiente!");
            pausa(1);
            return;
        }

        conta.setSaldo(conta.getSaldo().subtract(valorSaque));
        System.out.println("Saque efetuado com sucesso!");
        System.out.println(String.format("Novo saldo........: %s",df.format(conta.getSaldo())));
        pausa(2);
    }

    public static void relatorioContas() {
        clrscr();
        System.out.println("Relatório de contas\n");
        System.out.println(
            String.format("%-8s %-30s %-30s %10s", 
                "Número", 
                "Cliente",
                "E-mail", 
                "Saldo"));
        for (Conta conta:listaConta) {
            System.out.println(
                String.format("%-8s %-30s %-30s %10s", 
                conta.getNumero(), 
                conta.getCliente().getNome(),
                conta.getCliente().getEmail(), 
                df.format(conta.getSaldo())));
        }
        System.out.print("\nPressione ENTER para continuar...");
        reader.nextLine();
    }
    
    public static void clrscr() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void pausa(int tempoEmSegundos) {
        try {
            TimeUnit.SECONDS.sleep(tempoEmSegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}