package UserInterface;

import Entities.ItemLista;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;


public class ListaDeComprasGUIApp {
	private List<ItemLista> listaDeCompras;
	private JList<ItemLista> lista;
	private JLabel labelTotal;

	public ListaDeComprasGUIApp() {
		listaDeCompras = new ArrayList<>();
		criarGUI();
	}

	private void criarGUI() {
		JFrame frame = new JFrame("Lista de Compras");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		JPanel painelSuperior = new JPanel();
		JTextField campoNome = new JTextField(20);
		JTextField campoQuantidade = new JTextField(5);
		JTextField campoPreco = new JTextField(10);
		JButton botaoAdicionar = new JButton("Adicionar Item");

		botaoAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = campoNome.getText();
				int quantidade = Integer.parseInt(campoQuantidade.getText());
				double preco = Double.parseDouble(campoPreco.getText());

				ItemLista novoItem = new ItemLista(nome, quantidade, preco);
				listaDeCompras.add(novoItem);
				atualizarLista();

				campoNome.setText("");
				campoQuantidade.setText("");
				campoPreco.setText("");
			}
		});

		painelSuperior.add(new JLabel("Nome:"));
		painelSuperior.add(campoNome);
		painelSuperior.add(new JLabel("Quantidade:"));
		painelSuperior.add(campoQuantidade);
		painelSuperior.add(new JLabel("Preço:"));
		painelSuperior.add(campoPreco);
		painelSuperior.add(botaoAdicionar);

		lista = new JList<>();
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer(new ItemListaRenderer());
		lista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = lista.getSelectedIndex();
					if (index >= 0) {
						ItemLista itemSelecionado = listaDeCompras.get(index);
						if (!itemSelecionado.getNome().equals("Valor Total")) {
							abrirDialogoEditar(itemSelecionado);
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(lista);

		JPanel painelInferior = new JPanel();
		JButton botaoRemover = new JButton("Remover Item");
		botaoRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = lista.getSelectedIndex();
				if (index >= 0) {
					ItemLista itemSelecionado = listaDeCompras.get(index);
					if (!itemSelecionado.getNome().equals("Valor Total")) {
						listaDeCompras.remove(index);
						atualizarLista();
					}
				}
			}
		});

		labelTotal = new JLabel("Total: R$ 0.00");

		painelInferior.add(botaoRemover);
		painelInferior.add(labelTotal);

		frame.add(painelSuperior, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(painelInferior, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private void atualizarLista() {
		double total = 0.0;
		ItemLista[] items = new ItemLista[listaDeCompras.size()];
		for (int i = 0; i < listaDeCompras.size(); i++) {
			ItemLista item = listaDeCompras.get(i);
			items[i] = item;

			if (!item.getNome().equals("Valor Total")) {
				double subtotal = item.getQuantidade() * item.getPreco();
				total += subtotal;
			}
		}
		lista.setListData(items);
		labelTotal.setText("Total: R$ " + formatarValor(total));
	}

	private String formatarValor(double valor) {
		DecimalFormat formatador = new DecimalFormat("0.00");
		return formatador.format(valor);
	}

	private void abrirDialogoEditar(ItemLista item) {
		JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setSize(300, 150);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(new BorderLayout());

		JPanel painel = new JPanel();
		JTextField campoQuantidade = new JTextField(String.valueOf(item.getQuantidade()), 5);
		JTextField campoPreco = new JTextField(String.valueOf(item.getPreco()), 10);
		JButton botaoSalvar = new JButton("Salvar");

		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int novaQuantidade = Integer.parseInt(campoQuantidade.getText());
				double novoPreco = Double.parseDouble(campoPreco.getText());

				item.setQuantidade(novaQuantidade);
				item.setPreco(novoPreco);
				atualizarLista();

				dialog.dispose();
			}
		});

		painel.add(new JLabel("Quantidade:"));
		painel.add(campoQuantidade);
		painel.add(new JLabel("Preço:"));
		painel.add(campoPreco);
		painel.add(botaoSalvar);

		dialog.add(painel, BorderLayout.CENTER);
		dialog.setVisible(true);
	}

	private class ItemListaRenderer implements ListCellRenderer<ItemLista> {
		private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(JList<? extends ItemLista> list, ItemLista value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value != null) {
				String nome = value.getNome();
				int quantidade = value.getQuantidade();
				double preco = value.getPreco();
				double subtotal = quantidade * preco;

				String texto = nome + " - " + quantidade + " x " + formatarValor(preco) + " = "
						+ formatarValor(subtotal);
				renderer.setText(texto);
			}
			return renderer;
		}
	}
}
