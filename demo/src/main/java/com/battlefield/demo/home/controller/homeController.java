package com.battlefield.demo.home.controller;

import com.battlefield.demo.produtos.dao.ProdutosDAO;
import com.battlefield.demo.produtos.model.Produtos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Home")
public class homeController {

    @GetMapping
    public String exibirHome() {
        return "Home/telaHome";
    }

    @GetMapping("/Produto")
    public String exibirProduto() {return "redirect:/telaProduto/lista";}

    @GetMapping("/Usuario")
    public String exibirUsuario() {
        return "redirect:/telaLogin/listaUsuarios";
    }
}
