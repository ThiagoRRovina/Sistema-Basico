package com.battlefield.demo.produtos.controller;

import com.battlefield.demo.produtos.dao.ProdutosDAO;
import com.battlefield.demo.produtos.model.Produtos;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/telaProduto")
public class ProdutosController {

    private final ProdutosDAO produtosdao;

    public ProdutosController(ProdutosDAO produtosdao) {
        this.produtosdao = produtosdao;
    }

    // Tela de cadastro de produto
    @GetMapping
    public String exibirForm(Model model) {
        model.addAttribute("produto", new Produtos());
        return "Produto/telaProduto";
    }

//    @GetMapping("/editar")
//    public String editProduto(Model model) {
//        model.addAttribute("produto", new Produtos());
//        return "Produto/telaProduto";
//    }


    // Tela de listagem
    @GetMapping("/lista")
    public String exibirFormLista(Model model) {
        List<Produtos> produtos = produtosdao.listarTodos();
        model.addAttribute("produtos", produtos);
        return "Produto/listaProdutos";
    }

    // Salvar ou atualizar produto
    @PostMapping("/salvarProduto")
    public String salvarProduto(@RequestParam(required = false) Integer idProduto,
                                @RequestParam String nmProduto,
                                @RequestParam String deProduto,
                                @RequestParam String nuPreco,
                                @RequestParam int qtEstoque,
<<<<<<< Updated upstream
                                @RequestParam("imagemProduto") MultipartFile imagemProduto,
=======
                                @RequestParam(required = false) MultipartFile imagemProduto,
>>>>>>> Stashed changes
                                RedirectAttributes redirectAttributes) {
        try {
            int precoCentavos = (int) (Double.parseDouble(nuPreco.replace(".", "").replace(",", ".")) * 100);

            Produtos produto = new Produtos();
            produto.setNmProduto(nmProduto);
            produto.setDeProduto(deProduto);
            produto.setNuPreco(precoCentavos);
            produto.setQtEstoque(qtEstoque);

            if (imagemProduto != null && !imagemProduto.isEmpty()) {
<<<<<<< Updated upstream
                try {

                    String uploadDir = "src/main/resources/";

                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // Create unique filename to avoid conflicts
                    String originalFilename = imagemProduto.getOriginalFilename();
                    String fileExtension = "";
                    if (originalFilename != null && originalFilename.contains(".")) {
                        fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }
                    String uniqueFilename = System.currentTimeMillis() + "_" + nmProduto.replaceAll("[^a-zA-Z0-9]", "_") + fileExtension;

                    Path filePath = uploadPath.resolve(uniqueFilename);
                    imagemProduto.transferTo(filePath.toFile());
                    produto.setImagemProduto(uniqueFilename);

                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("erro", "Erro ao salvar a imagem do produto.");
                    return "redirect:/telaProduto/lista";
                }
            } else if (idProduto != null && idProduto != -1) {
                // If editing and no new image provided, keep the existing image
                Produtos produtoExistente = produtosdao.buscarPorId(idProduto);
                if (produtoExistente != null) {
                    produto.setImagemProduto(produtoExistente.getImagemProduto());
                }
=======
                byte[] bytes = imagemProduto.getBytes();
                produto.setImagemProduto(bytes);
>>>>>>> Stashed changes
            }

            if (idProduto == null || idProduto == -1) {
                produtosdao.gravar(produto);
                ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.INSERCAO);
                redirectAttributes.addFlashAttribute("message", "Produto cadastrado com sucesso!");
            } else {
                produto.setIdProduto(idProduto);
                produtosdao.editar(produto);
                ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.ALTERACAO);
                redirectAttributes.addFlashAttribute("message", "Produto atualizado com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro interno no servidor.");
        }

        return "redirect:/telaProduto/lista";
    }

<<<<<<< Updated upstream

=======
    // Editar produto
>>>>>>> Stashed changes
    @GetMapping("/editar/{idProduto}")
    public String editarProduto(@PathVariable Integer idProduto, Model model, RedirectAttributes redirectAttributes) {
        try {
            Produtos produto = produtosdao.buscarPorId(idProduto);

            if (produto == null) {
                redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
                return "redirect:/telaProduto/lista";
            }
            model.addAttribute("produto", produto);
            return "Produto/telaProduto";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao carregar produto.");
            return "redirect:/telaProduto/lista";
        }
    }

    // Excluir produto
    @GetMapping("/excluir/{idProduto}")
    public String excluirProduto(@PathVariable("idProduto") Integer idProduto, RedirectAttributes redirectAttributes) {
        try {
            produtosdao.excluir(idProduto);
            ProdutosDAO.insereLog("PRODUTOS", ProdutosDAO.TipoOcorrenciaLog.EXCLUSAO);
            redirectAttributes.addFlashAttribute("message", "Produto excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o produto.");
        }

        return "redirect:/telaProduto/lista";
    }

    // Validar produto (se precisar em outra lógica sua)
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

    // Exibir imagem do produto
    @GetMapping("/imagem/{idProduto}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Integer idProduto) {
        Produtos produto = produtosdao.buscarPorId(idProduto);

        if (produto == null || produto.getImagemProduto() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(produto.getImagemProduto());
    }
}
