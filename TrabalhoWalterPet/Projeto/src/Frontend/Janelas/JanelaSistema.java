package Frontend.Janelas;

import Frontend.Paineis.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaSistema extends JFrame {

    PainelEstoque Estoque = new PainelEstoque();
    PainelProdutos Produtos = new PainelProdutos();
    PainelRelatorios Relatorios = new PainelRelatorios();
    PainelVendas Vendas = new PainelVendas();

    public JanelaSistema() {
        setTitle("PetShop Miaujuda");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);



    }
}
