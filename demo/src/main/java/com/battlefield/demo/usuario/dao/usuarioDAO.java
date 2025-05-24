package com.battlefield.demo.usuario.dao;

import com.battlefield.demo.dao.GenericDAO;
import com.battlefield.demo.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import java.util.List;

@Repository
public class usuarioDAO extends GenericDAO<Usuario, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    public usuarioDAO() {
        super(Usuario.class);
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
        if (usuario.getIdUsuario() == null) {
            entityManager.persist(usuario);
        } else {
            entityManager.merge(usuario);
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
