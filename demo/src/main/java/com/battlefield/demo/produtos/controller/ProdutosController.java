//package com.battlefield.demo.produtos.controller;
//
//import com.battlefield.demo.produtos.dao.ProdutosDAO;
//import com.battlefield.demo.usuario.dao.usuarioDAO;
//import com.battlefield.demo.produtos.model.Produtos;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/telaLogin")
//public class ProdutosController {
//    private final ProdutosDAO produtosDAO;
//
//    public ProdutosController() {
//        produtosDAO = null;
//    }
//
//
//    @GetMapping
//    public String exibirForm() {;
//        return "telaProduto";
//    }
//
//    @PostMapping("/salvarProduto")
//    public String salvarProduto(@RequestParam(required = false) Integer idProduto,
//                         @RequestParam String nmProduto,
//                         @RequestParam String deProduto,
//                         @RequestParam int nuPreco,
//                         @RequestParam int qtEstoque,
//                         RedirectAttributes redirectAttributes) {
//        System.out.println("----funcao salvar----");
//        System.out.println("Dados recebidos\n------------------\n Nome=" + nmProduto + "| Email=" + nuPreco + " | "+ ", Quantidade=" + qtEstoque);
//
//        Produtos produtos = new Produtos();
//        produtos.setNmProduto(nmProduto);
//        produtos.setDeProduto(deProduto);
//        produtos.setNuPreco(nuPreco);
//        produtos.setQtEstoque(qtEstoque);
//
//        if (idProduto == null || idProduto == -1) {
//            try {
//                produtosDAO.gravar(produtos);
//                redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
//                ProdutosDAO.insereLog("USUARIO", ProdutosDAO.TipoOcorrenciaLog.INSERCAO);
//                return "redirect:/telaProdutos";
//            } catch (Exception e) {
//                System.out.println("Erro ao salvar usuário: " + e.getMessage());
//                redirectAttributes.addFlashAttribute("error", "Erro: não foi possível incluir o usuário.");
//                return "redirect:/telaLogin";
//            }
//        } else {
//            produtos.setIdProduto(idProduto);
//            boolean atualizou = produtosDAO.atualizar(produtos);
//            if (atualizou) {
//                redirectAttributes.addFlashAttribute("message", "Usuário atualizado com sucesso!");
//                ProdutosDAO.insereLog("PRODUTOS_1", ProdutosDAO.TipoOcorrenciaLog.ALTERACAO);
//                return "redirect:/telaProdutos";
//            } else {
//                redirectAttributes.addFlashAttribute("error", "Erro: não foi possível atualizar o usuário.");
//                return "redirect:/telaLogin";
//            }
//        }
//    }
//}
