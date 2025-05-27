package com.battlefield.demo.usuario.dao;

import com.battlefield.demo.dao.GenericDAO;
import com.battlefield.demo.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.sql.DataSource;

import java.sql.*;
import java.util.List;

@Repository
public class usuarioDAO extends GenericDAO<Usuario, Integer> {

    private final DataSource dataSource;
    @PersistenceContext
    private EntityManager entityManager;

    public usuarioDAO(DataSource dataSource) {
        super(Usuario.class);
        this.dataSource = dataSource;
    }
    public Usuario buscarPorEmail(String email) {
        List<Usuario> resultado = entityManager
                .createQuery("SELECT u FROM Usuario u WHERE u.nmEmail = :email", Usuario.class)
                .setParameter("email", email)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public Usuario buscaUsuario(String email, String senha) {
        try {
            return entityManager
                    .createQuery("SELECT u.nmEmail, u.nmSenha FROM Usuario u WHERE u.nmEmail = :email AND u.nmSenha= :senha", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario validaLogin(String email, String senha) {
        List<Usuario> resultado = entityManager
                .createQuery("SELECT u FROM Usuario u WHERE u.nmEmail = :email AND u.nmSenha = :senha", Usuario.class)
                .setParameter("email", email)
                .setParameter("senha", senha)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }


    @Transactional
    public void gravar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, endereco, senha, telefone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNmNome());
            stmt.setString(2, usuario.getNmEmail());
            stmt.setString(3, usuario.getNmEndereco());
            stmt.setString(4, usuario.getNmSenha());
            stmt.setString(5, usuario.getNmTelefone());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        }
    }



    public enum TipoOcorrenciaLog {
        INSERCAO,
        ALTERACAO,
        EXCLUSAO
    }

    public static void insereLog(String entidade, TipoOcorrenciaLog tipoOcorrencia) {
        System.out.println("Log: " + tipoOcorrencia + " na entidade: " + entidade);
    }
}
