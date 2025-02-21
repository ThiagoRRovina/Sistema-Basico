package com.battlefield.demo.produtos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "PRODUTOS_1")
public class Produtos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "NM_PRODUTO")
    private String nmProduto;

    @Column(name = "DE_PRODUTO")
    private String deProduto;

    @Column(name = "NU_PRECO")
    private int nuPreco;

    @Column(name = "QT_ESTOQUE")
    private int qtEstoque;

    public Produtos() {
        this.idProduto = idProduto;
        this.nmProduto = nmProduto;
        this.deProduto = deProduto;
        this.nuPreco = nuPreco;
        this.qtEstoque = qtEstoque;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "idProduto=" + idProduto +
                ", nmProduto='" + nmProduto + '\'' +
                ", deProduto='" + deProduto + '\'' +
                ", nuPreco=" + nuPreco +
                ", qtEstoque=" + qtEstoque +
                '}';
    }
}
