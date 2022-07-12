package org.suc1.puntoventa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.suc1.puntoventa.model.Producto;

public interface ProductosRepository extends CrudRepository<Producto, Integer> {

    Producto findFirstByCodigo(String codigo);
    List<Producto> findByNombreContains(String nombre);
}
