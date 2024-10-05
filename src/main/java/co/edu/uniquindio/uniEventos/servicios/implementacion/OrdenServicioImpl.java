package co.edu.uniquindio.uniEventos.servicios.implementacion;

import co.edu.uniquindio.uniEventos.dto.CrearOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.DetalleOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.ObtenerOrdenDTO;
import co.edu.uniquindio.uniEventos.dto.PagoDTO;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoCancelableException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenNoEncontradaException;
import co.edu.uniquindio.uniEventos.excepciones.orden.OrdenYaCanceladaException;
import co.edu.uniquindio.uniEventos.modelo.documentos.*;
import co.edu.uniquindio.uniEventos.modelo.enums.EstadoOrden;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleCarrito;
import co.edu.uniquindio.uniEventos.modelo.vo.DetalleOrden;
import co.edu.uniquindio.uniEventos.modelo.vo.Localidad;
import co.edu.uniquindio.uniEventos.repositorios.*;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EmailServicio;
import co.edu.uniquindio.uniEventos.servicios.interfaces.EventoServicio;
import co.edu.uniquindio.uniEventos.servicios.interfaces.OrdenServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenServicioImpl implements OrdenServicio {

    private final OrdenRepo ordenRepo;
    private final EventoRepo eventoRepo;
    private final CuponRepo cuponRepo;
    private final CuentaRepo cuentaRepo;
    private final CarritoRepo carritoRepo;

    private final EmailServicio emailServicio;
    private final EventoServicio eventoServicio;

    @Override
    public String crearOrden(CrearOrdenDTO crearOrdenDTO) {

        // Verificar la existencia del cliente (cuenta)
        Optional<Cuenta> cuentaOptional = cuentaRepo.findById(crearOrdenDTO.idCliente());

        if (cuentaOptional.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        Cuenta cuenta = cuentaOptional.get();

        // Buscar el carrito del cliente usando su ID
        Optional<Carrito> carritoOptional = carritoRepo.findByIdUsuario(new ObjectId(crearOrdenDTO.idCliente()));

        if (carritoOptional.isEmpty()) {
            throw new RuntimeException("No se encontró un carrito asociado a este cliente.");
        }

        Carrito carrito = carritoOptional.get();

        // Verificar que el carrito no esté vacío
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        // Validar los eventos y localidades dentro del carrito
        List<DetalleOrden> detalles = new ArrayList<>();

        for (DetalleCarrito itemCarrito : carrito.getItems()) {
            Optional<Evento> eventoOptional = eventoRepo.findById(itemCarrito.getIdEvento().toString());

            if (eventoOptional.isEmpty()) {
                throw new RuntimeException("Evento no encontrado");
            }

            Evento evento = eventoOptional.get();

            // Validar la localidad
            Localidad localidadSeleccionada = null;

            for (Localidad localidad : evento.getLocalidades()) {
                if (localidad.getNombre().equals(itemCarrito.getNombreLocalidad())) {
                    localidadSeleccionada = localidad;
                    break;
                }
            }

            if (localidadSeleccionada == null) {
                throw new RuntimeException("Localidad no encontrada");
            }

            // Validar la capacidad de la localidad
            if (localidadSeleccionada.getCapacidadMaxima() < itemCarrito.getCantidad()) {
                throw new RuntimeException("No hay suficientes entradas disponibles en esta localidad");
            }

            // Reducir la capacidad disponible de la localidad
            localidadSeleccionada.setCapacidadMaxima(localidadSeleccionada.getCapacidadMaxima() - itemCarrito.getCantidad());

            // Crear el detalle de la orden
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.setIdEvento(new ObjectId(itemCarrito.getIdEvento().toString()));
            detalleOrden.setNombreLocalidad(itemCarrito.getNombreLocalidad());
            detalleOrden.setCantidad(itemCarrito.getCantidad());
            detalleOrden.setPrecio(localidadSeleccionada.getPrecio());  // Precio tomado de la localidad

            detalles.add(detalleOrden);
        }

        // Aplicar cupones si existen
        float descuento = 0;
        if (crearOrdenDTO.idCupon() != null) {
            Optional<Cupon> cuponOptional = cuponRepo.findById(crearOrdenDTO.idCupon());

            if (cuponOptional.isEmpty()) {
                throw new RuntimeException("Cupón no encontrado");
            }

            Cupon cupon = cuponOptional.get();
            descuento = cupon.getDescuento();
        }

        // Crear la orden
        Orden nuevaOrden = new Orden();
        nuevaOrden.setIdCliente(new ObjectId(crearOrdenDTO.idCliente()));
        nuevaOrden.setFecha(LocalDateTime.now());
        nuevaOrden.setCodigoPasarela(crearOrdenDTO.codigoPasarela());
        nuevaOrden.setItems(detalles);
        nuevaOrden.setTotal(calcularTotalOrden(detalles, descuento));
        nuevaOrden.setEstado(EstadoOrden.ACTIVA); // Estado inicial,Se
        //asigna cuando una orden ha sido creada pero aún no ha sido cancelada o completada.

        // Guardar la orden
        Orden ordenGuardada = ordenRepo.save(nuevaOrden);

        // Retornar el ID de la nueva orden
        return "Orden creada con éxito. ID: " + ordenGuardada.getId();
    }


    // Método auxiliar para calcular el total de la orden con el descuento aplicado
    private float calcularTotalOrden(List<DetalleOrden> detalles, float descuento) {
        float total = 0;
        for (DetalleOrden detalle : detalles) {
            total += detalle.getCantidad() * detalle.getPrecio();
        }
        return total - descuento;
    }



    @Override
    public ObtenerOrdenDTO obtenerOrdenPorId(String idOrden) {
        // Buscar la orden en el repositorio por su ID
        Optional<Orden> ordenOptional = ordenRepo.findById(idOrden);

        // Si la orden no se encuentra, lanzamos una excepción
        if (ordenOptional.isEmpty()) {
            throw new RuntimeException("Orden no encontrada");
        }

        Orden orden = ordenOptional.get();

        // Convertir la lista de DetalleOrden a DetalleOrdenDTO
        List<DetalleOrdenDTO> detallesDTO = new ArrayList<>();

        for (DetalleOrden detalle : orden.getItems()) {

            DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO(
                    detalle.getIdEvento().toString(),
                    detalle.getNombreLocalidad(),
                    detalle.getCantidad(),
                    detalle.getPrecio()
            );
            detallesDTO.add(detalleDTO);
        }

        // Retornar el DTO con la información relevante
        return new ObtenerOrdenDTO(
                orden.getId(),
                orden.getIdCliente().toString(),
                orden.getFecha(),
                orden.getCodigoPasarela(),
                detallesDTO,
                orden.getTotal(),
                new PagoDTO(
                        orden.getPago().getMoneda(),
                        orden.getPago().getCodigoAutorizacion(),
                        orden.getPago().getTipoPago(),
                        orden.getPago().getDetalleEstado(),
                        orden.getPago().getValorTransaccion(),
                        orden.getPago().getEstado()
                )
        );

    }



    @Override
    public String cancelarOrden(String idOrden) throws OrdenNoEncontradaException, OrdenYaCanceladaException,OrdenNoCancelableException {
        // Buscar la orden en el repositorio por su ID
        Optional<Orden> ordenOptional = ordenRepo.findById(idOrden);

        // Si no se encuentra la orden, lanzar una excepción
        if (ordenOptional.isEmpty()) {
            throw new OrdenNoEncontradaException("No se encontró la orden con el ID: " + idOrden);
        }

        Orden orden = ordenOptional.get();

        // Verificar que la orden no esté ya cancelada
        if (orden.getEstado().equals(EstadoOrden.CANCELADA)) {
            throw new OrdenYaCanceladaException("La orden ya está cancelada");
        }

        // Verificar si la fecha del evento asociado es mayor a dos días antes del evento
        for (DetalleOrden detalle : orden.getItems()) {

            Optional<Evento> eventoOptional = eventoRepo.findById(detalle.getIdEvento().toString());

            if (!eventoOptional.isPresent()) {
                throw new RuntimeException("Evento no encontrado");
            }

            Evento evento = eventoOptional.get();
            LocalDateTime fechaActual = LocalDateTime.now();
            LocalDateTime fechaEvento = evento.getFechaEvento();

            // No se puede cancelar la orden si faltan menos de dos días para el evento
            if (fechaEvento.minusDays(2).isBefore(fechaActual)) {
                throw new OrdenNoCancelableException("No se puede cancelar la orden, " +
                        "faltan menos de dos días para el evento");
            }
        }

        // Cambiar el estado de la orden a CANCELADA
        orden.setEstado(EstadoOrden.CANCELADA);

        // Guardar la orden actualizada en la base de datos
        ordenRepo.save(orden);

        // Retornar un mensaje de éxito
        return "Orden cancelada con éxito. ID: " + orden.getId();
    }
}
