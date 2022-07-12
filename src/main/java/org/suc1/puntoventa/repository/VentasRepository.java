package org.suc1.puntoventa.repository;

import org.springframework.data.repository.CrudRepository;
import org.suc1.puntoventa.model.Venta;

public interface VentasRepository extends CrudRepository<Venta, Integer> {
}
