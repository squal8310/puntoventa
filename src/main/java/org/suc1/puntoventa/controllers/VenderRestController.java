package org.suc1.puntoventa.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suc1.puntoventa.model.Producto;
import org.suc1.puntoventa.model.ProductoParaVender;
import org.suc1.puntoventa.repository.ProductosRepository;
import org.suc1.puntoventa.repository.ProductosVendidosRepository;
import org.suc1.puntoventa.repository.VentasRepository;

@RestController
@RequestMapping(path = "/rest")
public class VenderRestController {
    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private VentasRepository ventasRepository;
    @Autowired
    private ProductosVendidosRepository productosVendidosRepository;

    @GetMapping(value = "/nombre")
    public List<Producto> getByName(@RequestParam(value = "q", required = false) String query, Model model) {
        return productosRepository.findByNombreContains(query);
    }
    
    @GetMapping(value = "/agregar")
    public void agregarAlCarrito(@RequestParam(value = "q", required = false) String q, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        Producto productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(q);
        if (productoBuscadoPorCodigo == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con el código " + q + " no existe")
                    .addFlashAttribute("clase", "warning");
        }
        if (productoBuscadoPorCodigo.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto está agotado")
                    .addFlashAttribute("clase", "warning");
        }
        boolean encontrado = false;
        for (ProductoParaVender productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carrito.add(new ProductoParaVender(productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(), productoBuscadoPorCodigo.getPrecio(), productoBuscadoPorCodigo.getExistencia(), productoBuscadoPorCodigo.getId(), 1f));
        }
        this.guardarCarrito(carrito, request);
    }
    
    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }
    
    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }
}
