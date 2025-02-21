//package com.battlefield.demo.produtos.dao;
//
//import com.battlefield.demo.dao.GenericDAO;
//import com.battlefield.demo.produtos.model.Produtos;
//import com.battlefield.demo.usuario.model.Usuario;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.NoResultException;
//import java.util.List;
//
//public class ProdutosDAO  extends GenericDAO<Produtos, Integer> {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public ProdutosDAO() {
//        super(Produtos.class);
//    }
//
//    public Produtos buscaProduto(Integer id, String nome) {
//        try {
//            return entityManager
//                    .createQuery("SELECT P1.idProduto, P1.nmProduto FROM  Produtos p1", Produtos.class)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//
//    public List<Usuario> listarTodos(String filtro, String ordem) {
//        try {
//            return entityManager.createQuery("FROM Produtos  WHERE " + filtro + " " + ordem, Usuario.class)
//                    .getResultList();
//        } catch (Exception e) {
//            System.out.println("------ERRO produtosDAO-----\n" + e.getMessage());
//            return null;
//        }
//    }
//
//    @Transactional
//    public void gravar(Produtos produtos) {
//        if (produtos.getIdProduto() == null) {
//            entityManager.persist(produtos);
//        } else {
//            entityManager.merge(produtos);
//        }
//    }
//
//    public enum TipoOcorrenciaLog {
//        INSERCAO,
//        ALTERACAO,
//        EXCLUSAO
//    }
//
//    public static void insereLog(String entidade, ProdutosDAO.TipoOcorrenciaLog tipoOcorrencia) {
//        System.out.println("Log: " + tipoOcorrencia + " na entidade: " + entidade);
//    }
//}
