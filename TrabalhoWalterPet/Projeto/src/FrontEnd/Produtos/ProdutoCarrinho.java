package FrontEnd.Produtos;

import FrontEnd.Tema;
import FrontEnd.Componentes.*;
import Backend.Estoque.Produto;
import javax.swing.*;
import java.awt.*;

public class ProdutoCarrinho extends PainelRedondo{
    private Produto produto;

    public ProdutoCarrinho(Produto produto) {
        super(15);
        this.produto = produto;
        setupCard();
    }

    private void setupCard() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Tema.BG_SECONDARY);
        setPreferredSize(new Dimension(180, 180));

        // Nome
        JLabel nomeLabel = new JLabel(produto.getNome());
        nomeLabel.setFont(Tema.FONT_SUBTITLE);
        nomeLabel.setForeground(Tema.TEXT_PRIMARY);
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Preço
        JLabel precoLabel = new JLabel(String.format("R$ %.2f", produto.getPrecoUni()));
        precoLabel.setFont(Tema.FONT_BODY);
        precoLabel.setForeground(Tema.SUCCESS);
        precoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Estoque
        JLabel estoqueLabel = new JLabel("Estoque: " + produto.getQuantd());
        estoqueLabel.setFont(Tema.FONT_SMALL);
        estoqueLabel.setForeground(Tema.TEXT_SECONDARY);
        estoqueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição (se houver)
        String desc = produto.getDescricao();
        JLabel descLabel = new JLabel(desc.isEmpty() ? "" : desc);
        descLabel.setFont(Tema.FONT_SMALL);
        descLabel.setForeground(Tema.TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nomeLabel);
        infoPanel.add(precoLabel);
        infoPanel.add(estoqueLabel);
        if (!desc.isEmpty()) {
            infoPanel.add(descLabel);
        }

        add(infoPanel, BorderLayout.CENTER);
    }
}
