package org.suc1.puntoventa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suc1.puntoventa.model.Producto;
import org.suc1.puntoventa.repository.ProductosRepository;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/inicio")
public class MasterController {

    @GetMapping(value = "/master")
    public String getMaster(Model model) {
    	StringBuilder sb=new StringBuilder();
        model.addAttribute("producto", new Producto());
        return "master";
    }
}
