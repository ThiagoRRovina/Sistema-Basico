package com.battlefield.demo.usuario.controller;

import com.battlefield.demo.usuario.dao.usuarioDAO;
import com.battlefield.demo.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class usuarioListController {

    private final usuarioDAO usuariodao;

    @Autowired
    public usuarioListController(usuarioDAO usuariodao) {
        this.usuariodao = usuariodao;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuariodao.listarTodos("", "ASC");
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }
}