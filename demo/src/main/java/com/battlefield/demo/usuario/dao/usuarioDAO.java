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

    public Usuario buscaPorEmail(String nome) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM Usuario u WHERE u.nmNome = :nome", Usuario.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Usuario> listarTodos(String filtro, String ordem) {
        try {
            return entityManager.createQuery("FROM Usuario t WHERE " + filtro + " " + ordem, Usuario.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("------ERRO usuarioDAO-----\n" + e.getMessage());
            return null;
        }
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
