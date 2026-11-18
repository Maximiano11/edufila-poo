package br.senac.pr.visao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import br.senac.pr.jdbc.CmeiDAO;
import br.senac.pr.modelo.Cmei;

public class TelaCmei extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCidade;
    private JTextField txtBairro;
    private JTextField txtEndereco;

    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnBuscar;
    private JButton btnDeletar;
    private JButton btnListar;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCmei() {
        setTitle("Cadastro de CMEIs");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtId = new JTextField(10);
        txtNome = new JTextField(20);
        txtCidade = new JTextField(15);
        txtBairro = new JTextField(15);
        txtEndereco = new JTextField(20);

        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnBuscar = new JButton("Buscar");
        btnDeletar = new JButton("Deletar");
        btnListar = new JButton("Listar");

        JPanel painelCampos = new JPanel(new GridLayout(5, 2));
        painelCampos.add(new JLabel("Id"));
        painelCampos.add(txtId);
        painelCampos.add(new JLabel("Nome"));
        painelCampos.add(txtNome);
        painelCampos.add(new JLabel("Cidade"));
        painelCampos.add(txtCidade);
        painelCampos.add(new JLabel("Bairro"));
        painelCampos.add(txtBairro);
        painelCampos.add(new JLabel("Endereço"));
        painelCampos.add(txtEndereco);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnListar);

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(painelCampos, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        String[] colunas = {"ID", "Nome", "Cidade", "Bairro", "Endereço"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        add(painel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarCmei());
        btnLimpar.addActionListener(e -> limparCampos());
        btnBuscar.addActionListener(e -> buscarCmei());
        btnDeletar.addActionListener(e -> deletarCmei());
        btnListar.addActionListener(e -> listarCmeis());
        listarCmeis();
        setVisible(true);
    }

    private void listarCmeis() {
        CmeiDAO dao = new CmeiDAO();
        modeloTabela.setRowCount(0);
        try {
            List<Cmei> cmeis = dao.listarCmeis();
            for (Cmei cmei : cmeis) {
                Object[] linha = {cmei.getId(), cmei.getNome(), cmei.getCidade(), cmei.getBairro(), cmei.getEndereco()};
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void deletarCmei() {
        CmeiDAO dao = new CmeiDAO();
        try {
            int id = Integer.parseInt(txtId.getText());
            int resp = JOptionPane.showConfirmDialog(this, "Deseja confirmar a exclusão?",
                    "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                dao.deletar(id);
                listarCmeis();
            }
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um id valido");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void buscarCmei() {
        CmeiDAO dao = new CmeiDAO();
        try {
            Cmei cmei = dao.buscarCmeiPorId(Integer.parseInt(txtId.getText()));
            if (cmei == null) {
                JOptionPane.showMessageDialog(this, "CMEI não encontrado");
                return;
            }
            txtNome.setText(cmei.getNome());
            txtCidade.setText(cmei.getCidade());
            txtBairro.setText(cmei.getBairro());
            txtEndereco.setText(cmei.getEndereco());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um id valido");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void salvarCmei() {
        try {
            Cmei cmei = new Cmei();
            cmei.setId(Integer.parseInt(txtId.getText()));
            cmei.setNome(txtNome.getText());
            cmei.setCidade(txtCidade.getText());
            cmei.setBairro(txtBairro.getText());
            cmei.setEndereco(txtEndereco.getText());

            CmeiDAO dao = new CmeiDAO();
            dao.inserir(cmei);
            listarCmeis();
            JOptionPane.showMessageDialog(this, "CMEI Salvo");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um id valido");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtEndereco.setText("");
        txtId.requestFocus();
    }
}
