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
                         RedirectAttributes redirectAttributes) {
        System.out.println("----funcao salvar----");
        System.out.println("Dados recebidos: Nome=" + nmNome + ", Email=" + nmEmail);

        Usuario usuario = new Usuario();
        usuario.setNmNome(nmNome);
        usuario.setNmEmail(nmEmail);
        usuario.setNmEndereco(nmEndereco);
        usuario.setNmSenha(nmSenha);

        if (idusuario == null || idusuario == -1) {
            try {
                usuariodao.gravar(usuario);
                redirectAttributes.addFlashAttribute("message", "Usuário cadastrado com sucesso!");
                usuarioDAO.insereLog("USUARIO", TipoOcorrenciaLog.INSERCAO);
                return "redirect:/usuarios";
            } catch (Exception e) {
                System.out.println("Erro ao salvar usuário: " + e.getMessage());
                redirectAttributes.addFlashAttribute("error", "Erro: não foi possível incluir o usuário.");
                return "redirect:/telaLogin";
            }
        } else {
            usuario.setIdUsuario(idusuario);
            boolean atualizou = usuariodao.atualizar(usuario);
            if (atualizou) {
                redirectAttributes.addFlashAttribute("message", "Usuário atualizado com sucesso!");
                usuarioDAO.insereLog("USUARIO", TipoOcorrenciaLog.ALTERACAO);
                return "redirect:/usuarios";
            } else {
                redirectAttributes.addFlashAttribute("error", "Erro: não foi possível atualizar o usuário.");
                return "redirect:/telaLogin";
            }
        }
    }



}
