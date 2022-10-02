package com.viste_un_futuro.controller;

import com.viste_un_futuro.domain.Usuario;
import com.viste_un_futuro.model.ClasificadoDTO;
import com.viste_un_futuro.repos.UsuarioRepository;
import com.viste_un_futuro.service.ClasificadoService;
import com.viste_un_futuro.util.WebUtils;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/clasificados")
public class ClasificadoController {

    private final ClasificadoService clasificadoService;
    private final UsuarioRepository usuarioRepository;

    public ClasificadoController(final ClasificadoService clasificadoService,
            final UsuarioRepository usuarioRepository) {
        this.clasificadoService = clasificadoService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioValues", usuarioRepository.findAll().stream().collect(
                Collectors.toMap(Usuario::getId, Usuario::getNombres)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("clasificados", clasificadoService.findAll());
        return "clasificado/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("clasificado") final ClasificadoDTO clasificadoDTO) {
        return "clasificado/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("clasificado") @Valid final ClasificadoDTO clasificadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clasificado/add";
        }
        clasificadoService.create(clasificadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("clasificado.create.success"));
        return "redirect:/clasificados";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("clasificado", clasificadoService.get(id));
        return "clasificado/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("clasificado") @Valid final ClasificadoDTO clasificadoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clasificado/edit";
        }
        clasificadoService.update(id, clasificadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("clasificado.update.success"));
        return "redirect:/clasificados";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        clasificadoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("clasificado.delete.success"));
        return "redirect:/clasificados";
    }

}
