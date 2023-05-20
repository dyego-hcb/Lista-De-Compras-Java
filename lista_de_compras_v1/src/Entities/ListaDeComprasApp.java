package Entities;

import java.util.ArrayList;
import java.util.List;

import Application.Main;

public class ListaDeComprasApp {

    private List<String> listaDeCompras;

    public ListaDeComprasApp() {
        listaDeCompras = new ArrayList<>();
    }

    public void adicionarItem(String item) {
        listaDeCompras.add(item);
        System.out.println("Item adicionado: " + item);
    }

    public void removerItem(int indice) {
        if (indice >= 0 && indice < listaDeCompras.size()) {
            String itemRemovido = listaDeCompras.remove(indice);
            System.out.println("Item removido: " + itemRemovido);
        } else {
            System.out.println("Índice inválido. Item não removido.");
        }
    }

    public void mostrarLista() {
        System.out.println("===== Lista de Compras =====");
        if (listaDeCompras.isEmpty()) {
            System.out.println("A lista está vazia.");
        } else {
            for (int i = 0; i < listaDeCompras.size(); i++) {
                System.out.println(i + ". " + listaDeCompras.get(i));
            }
        }
    }

    public double calcularTotal() {
        double total = 0.0;

        if (!listaDeCompras.isEmpty()) {
            for (String item : listaDeCompras) {
                total += lerValorItem(item);
            }
        }

        return total;
    }

    private double lerValorItem(String item) {
        System.out.print("Digite o valor do item \"" + item + "\": ");
        return Main.scanner.nextDouble();
    }
}
