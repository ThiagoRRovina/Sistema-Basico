package com.battlefield.demo.usuario.controller;

import com.battlefield.demo.produtos.dao.ProdutosDAO;
import com.battlefield.demo.produtos.model.Produtos;
import com.battlefield.demo.usuario.dao.usuarioDAO;
import com.battlefield.demo.usuario.dao.usuarioDAO.TipoOcorrenciaLog;
import com.battlefield.demo.usuario.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/telaLogin")
public class usuarioController {

    private final ProdutosDAO produtosdao;
    private final usuarioDAO usuariodao;

    public usuarioController(ProdutosDAO produtosdao, usuarioDAO usuariodao) {
        this.produtosdao = produtosdao;
        this.usuariodao = usuariodao;
    }

    @GetMapping("/Home")
    public String exibirHome() {
        return "Home/telaHome";
    }

//    @GetMapping("/lista")
//    public String exibirFormLista(Model model) {
//        List<Produtos> produtos = produtosdao.listarTodos();
//        model.addAttribute("produtos", produtos);
//        return "Produto/listaProdutos";
//    }

    @GetMapping
    public String exibirForm() {
        return "Usuario/telaLogin";
    }

    @GetMapping("/listaUsuarios")
    public String exibirListaUsuarios(Model model) {
        List<Usuario> usuarios = usuariodao.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "Usuario/listaUsuarios";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Integer idusuario,
                         @RequestParam String nmNome,
                         @RequestParam String nmEmail,
                         @RequestParam String nmEndereco,
                         @RequestParam String nmSenha,
                         @RequestParam String nmTelefone,

                         Model model) {



        Usuario usuarioExistente = usuariodao.buscarPorEmail(nmEmail);
        if (usuarioExistente != null && (idusuario == null || !usuarioExistente.getIdUsuario().equals(idusuario))) {
            model.addAttribute("erro", "O email já está registrado.");
            return "redirect:/telaLogin";
        }

        Usuario usuario = new Usuario();
        usuario.setNmNome(nmNome);
        usuario.setNmEmail(nmEmail);
        usuario.setNmEndereco(nmEndereco);
        usuario.setNmSenha(nmSenha);
        usuario.setNmTelefone(nmTelefone);


        try {
            if (idusuario == null || idusuario == -1) {
                usuariodao.gravar(usuario);
                usuariodao.insereLog("USUARIO", TipoOcorrenciaLog.INSERCAO);
                System.out.println("Usuário inserido com sucesso.");
                return "redirect:/telaLogin";
            } else {
                usuario.setIdUsuario(idusuario);
                boolean atualizou = usuariodao.atualizar(usuario);

                if (atualizou) {
                    usuariodao.insereLog("USUARIO", TipoOcorrenciaLog.ALTERACAO);
                    System.out.println("Usuário atualizado com sucesso.");
                    return "redirect:/telaLogin";
                } else {
                    model.addAttribute("erro", "Erro ao atualizar o usuário.");
                    return "redirect:/telaLogin";
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar ou atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("erro", "Erro interno no servidor.");
            return "redirect:/telaLogin";
        }
    }




    public String limparCampos(Model model) {
        model.addAttribute("produto", new Produtos());
        return "Produto/telaProduto";
    }

    @GetMapping("/excluir/{idUsuario}")
    public String excluirProduto(@PathVariable("idUsuario") Integer idUsuario, RedirectAttributes redirectAttributes) {
        try {
            usuariodao.excluir(idUsuario);
            usuariodao.insereLog("USUARIO", usuarioDAO.TipoOcorrenciaLog.EXCLUSAO);
            redirectAttributes.addFlashAttribute("message", "Produto excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o produto.");
        }

        return "redirect:/telaLogin/listaUsuarios";
    }



    @PostMapping("/login")
    public String validarLogin(@RequestParam String nmEmail,
                               @RequestParam String nmSenha,
                               RedirectAttributes redirectAttributes) {

        Usuario usuario = usuariodao.validaLogin(nmEmail, nmSenha);
        System.out.printf("\nEmail: %s \nSenha: %s\n", nmEmail, nmSenha);

        if (usuario != null) {
            redirectAttributes.addFlashAttribute("message", "Login realizado com sucesso!");
            return "redirect:/telaLogin/Home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciais inválidas. Tente novamente.");
            return "redirect:/telaLogin";
        }
    }



}
