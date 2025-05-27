package com.battlefield.demo.produtos.dao;

import com.battlefield.demo.dao.GenericDAO;
import com.battlefield.demo.produtos.model.Produtos;
import com.battlefield.demo.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NoResultException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutosDAO  extends GenericDAO<Produtos, Integer> {

    private final DataSource dataSource;
    @PersistenceContext
    private EntityManager entityManager;

    public ProdutosDAO(DataSource dataSource) {
        super(Produtos.class);
        this.dataSource = dataSource;
    }

    public Produtos buscaProduto(Integer id, String nome) {
        try {
            return entityManager
                    .createQuery("SELECT P1.idProduto, P1.nmProduto FROM  Produtos p1", Produtos.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Produtos> listarTodos() {
        List<Produtos> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produtos p = new Produtos();
                p.setIdProduto(rs.getInt("produto_id"));
                p.setNmProduto(rs.getString("nome"));
                p.setDeProduto(rs.getString("descricao"));    //trocar
                p.setNuPreco(rs.getInt("preco"));
                p.setQtEstoque(rs.getInt("estoque"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    @Transactional
    public void gravar(Produtos produtos) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produtos.getNmProduto());
            stmt.setString(2, produtos.getDeProduto());
            stmt.setInt(3, produtos.getNuPreco());
            stmt.setFloat(4, produtos.getQtEstoque());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produtos.setIdProduto(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o produto", e);
        }
    }


    public boolean excluir(Integer idProduto) {
        String sql = "DELETE FROM produtos WHERE idProduto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o produto", e);
        }
    }



    @Transactional
    public void editar(Produtos produtos) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produtos.getNmProduto());
            stmt.setString(2, produtos.getDeProduto());
            stmt.setInt(3, produtos.getNuPreco());
            stmt.setFloat(4, produtos.getQtEstoque());
            stmt.setInt(5, produtos.getIdProduto()); // WHERE id = ?

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o produto", e);
        }
    }
    public enum TipoOcorrenciaLog {
        INSERCAO,
        ALTERACAO,
        EXCLUSAO
    }

    public static void insereLog(String entidade, ProdutosDAO.TipoOcorrenciaLog tipoOcorrencia) {
        System.out.println("Log: " + tipoOcorrencia + " na entidade: " + entidade);
    }
}
