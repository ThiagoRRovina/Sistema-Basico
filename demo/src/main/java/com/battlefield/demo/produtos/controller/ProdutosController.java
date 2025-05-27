package com.battlefield.demo.produtos.controller;


import com.battlefield.demo.produtos.dao.ProdutosDAO;
import com.battlefield.demo.produtos.model.Produtos;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/telaProduto")
public class ProdutosController {

    private final ProdutosDAO produtosdao;

    public ProdutosController(ProdutosDAO produtosdao) {
        this.produtosdao = produtosdao;
    }

    @GetMapping
    public String exibirForm() {
        return "Produto/telaProduto";
    }

    @GetMapping("/lista")
    public String exibirFormLista(Model model) {
        List<Produtos> produtos = produtosdao.listarTodos();
        model.addAttribute("produtos", produtos);
        return "Produto/listaProdutos";
    }


    @PostMapping("/salvarProduto")
    public String salvarProduto(@RequestParam(required = false) Integer idProduto,
                                @RequestParam String nmProduto,
                                @RequestParam String deProduto,
                                @RequestParam int nuPreco,
                                @RequestParam int qtEstoque,
                                RedirectAttributes redirectAttributes) {

        Produtos produto = new Produtos();
        produto.setNmProduto(nmProduto);
        produto.setDeProduto(deProduto);
        produto.setNuPreco(nuPreco);
        produto.setQtEstoque(qtEstoque);

        try {
            if (idProduto == null || idProduto == -1) {
                produtosdao.gravar(produto);
                ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.INSERCAO);
                redirectAttributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
            } else {
                produto.setIdProduto(idProduto);
                boolean atualizou = produtosdao.atualizar(produto);
                if (atualizou) {
                    ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.ALTERACAO);
                    redirectAttributes.addFlashAttribute("message", "Produto atualizado com sucesso!");
                } else {
                    redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar o produto.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro interno no servidor.");
        }

        return "redirect:/telaProduto/lista";
    }

    @PostMapping("/editarProduto")
    public String editarProduto(@RequestParam(required = false) Integer idProduto,
                                @RequestParam String nmProduto,
                                @RequestParam String deProduto,
                                @RequestParam int nuPreco,
                                @RequestParam int qtEstoque,
                                RedirectAttributes redirectAttributes) {

        Produtos produto = new Produtos();
        produto.setNmProduto(nmProduto);
        produto.setDeProduto(deProduto);
        produto.setNuPreco(nuPreco);
        produto.setQtEstoque(qtEstoque);

        try {
            if (idProduto == null || idProduto == -1) {
                produtosdao.gravar(produto);
                ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.INSERCAO);
                redirectAttributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
            } else {
                produto.setIdProduto(idProduto);
                boolean atualizou = produtosdao.atualizar(produto);
                if (atualizou) {
                    ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.ALTERACAO);
                    redirectAttributes.addFlashAttribute("message", "Produto atualizado com sucesso!");
                } else {
                    redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar o produto.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro interno no servidor.");
        }

        return "redirect:/telaProduto/listaProduto";
    }

    @PostMapping("/excluirProduto")
    public String excluirProduto(@RequestParam(required = false) Integer idProduto,
                                @RequestParam String nmProduto,
                                @RequestParam String deProduto,
                                @RequestParam int nuPreco,
                                @RequestParam int qtEstoque,
                                RedirectAttributes redirectAttributes) {

        Produtos produto = new Produtos();
        produto.setNmProduto(nmProduto);
        produto.setDeProduto(deProduto);
        produto.setNuPreco(nuPreco);
        produto.setQtEstoque(qtEstoque);

        try {
            if (idProduto == null || idProduto == -1) {
                produtosdao.gravar(produto);
                ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.INSERCAO);
                redirectAttributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
            } else {
                produto.setIdProduto(idProduto);
                boolean atualizou = produtosdao.atualizar(produto);
                if (atualizou) {
                    ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.ALTERACAO);
                    redirectAttributes.addFlashAttribute("message", "Produto atualizado com sucesso!");
                } else {
                    redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar o produto.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro interno no servidor.");
        }

        return "redirect:/telaProduto/listaProduto";
    }



    @PostMapping
    public String validarProduto(@RequestParam Integer idProduto,
                                 @RequestParam String nmProduto,
                                 RedirectAttributes redirectAttributes) {
        Produtos produtos = produtosdao.buscaProduto(idProduto, nmProduto);

        if (produtos != null) {
            redirectAttributes.addFlashAttribute("message", "Produto validado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciais inválidas.");
        }

        return "redirect:/telaProduto";
    }
}
