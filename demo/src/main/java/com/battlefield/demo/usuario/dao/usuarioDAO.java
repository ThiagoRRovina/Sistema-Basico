package com.battlefield.demo.usuario.dao;

import com.battlefield.demo.dao.GenericDAO;
import com.battlefield.demo.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
public class usuarioDAO extends GenericDAO<Usuario, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    public usuarioDAO() {
        super(Usuario.class);
    }
    public Usuario buscarPorEmail(String email) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM Usuario u WHERE u.nmEmail = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se não encontrar usuário
        }
    }

    public Usuario buscaUsuario(String email, String senha) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM Usuario u WHERE u.nmEmail = :email AND u.nmSenha = :senha", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public List<Usuario> listarTodos(String filtro, String ordem) {
//        try {
//            return entityManager.createQuery("FROM Usuario t WHERE " + filtro + " " + ordem, Usuario.class)
//                    .getResultList();
//        } catch (Exception e) {
//            System.out.println("------ERRO usuarioDAO-----\n" + e.getMessage());
//            return null;
//        }
//    }

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
