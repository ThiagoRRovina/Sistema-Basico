package com.battlefield.demo.usuario.controller;

import com.battlefield.demo.usuario.dao.usuarioDAO;
import com.battlefield.demo.usuario.dao.usuarioDAO.TipoOcorrenciaLog;
import com.battlefield.demo.usuario.model.Usuario;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import java.text.ParseException;

@Controller
@RequestMapping("/telaLogin")
public class usuarioController {

    private final usuarioDAO usuariodao;

    public usuarioController(usuarioDAO usuariodao) {
        this.usuariodao = usuariodao;
    }


    @GetMapping
    public String exibirForm() {;
        return "telaLogin";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Integer idusuario,
                         @RequestParam String nmNome,
                         @RequestParam String nmEmail,
                         @RequestParam String nmEndereco,
                         @RequestParam String nmSenha,
                         Model model) {

        // Verificar se o email já existe se não existi inseri no banco
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



    @PostMapping("/login")
    public String validarLogin(@RequestParam String nmEmail,
                               @RequestParam String nmSenha,
                               RedirectAttributes redirectAttributes) {

        Usuario usuario = usuariodao.buscaUsuario(nmEmail, nmSenha);

        if (usuario != null) {

            redirectAttributes.addFlashAttribute("message", "Login realizado com sucesso!");
            return "redirect:/telaLogin";
        } else {

            redirectAttributes.addFlashAttribute("error", "Credenciais inválidas. Tente novamente.");
            return "redirect:/telaLogin";
        }
    }

}
