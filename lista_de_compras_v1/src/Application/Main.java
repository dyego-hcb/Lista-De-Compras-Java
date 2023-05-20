package Application;

import Entities.*;

import java.util.Scanner;

public class Main {

    public static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        ListaDeComprasApp app = new ListaDeComprasApp();
        boolean executando = true;

        while (executando) {
            mostrarMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    adicionarItem(app);
                    break;
                case 2:
                    removerItem(app);
                    break;
                case 3:
                    app.mostrarLista();
                    break;
                case 4:
                    double total = app.calcularTotal();
                    System.out.println("Total de gastos: R$" + total);
                    break;
                case 5:
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }

        scanner.close();
        System.out.println("Aplicativo encerrado.");
    }

    private static void mostrarMenu() {
        System.out.println("===== Lista de Compras App =====");
        System.out.println("1. Adicionar item");
        System.out.println("2. Remover item");
        System.out.println("3. Mostrar lista");
        System.out.println("4. Calcular total");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        return scanner.nextInt();
    }

    private static void adicionarItem(ListaDeComprasApp app) {
        System.out.print("Digite o item a ser adicionado: ");
        scanner.nextLine(); // Limpar o buffer do scanner
        String item = scanner.nextLine();
        app.adicionarItem(item);
    }

    private static void removerItem(ListaDeComprasApp app) {
        System.out.print("Digite o índice do item a ser removido: ");
        int indice = scanner.nextInt();
        app.removerItem(indice);
    }
}