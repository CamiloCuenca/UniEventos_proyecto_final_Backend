package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Event.*;

import java.util.List;

public interface EventService {

    String crearEvento(createDTOEvent crearEventoDTO) throws Exception;

    String editarEvento(editDTOEvent editarEventoDTO) throws Exception;

    String eliminarEvento(String id) throws Exception;

    dtoEventInformation obtenerInformacionEvento(String id) throws Exception;

    List<ItemEventDTO> listarEventos();

    List<ItemEventDTO> filtrarEventos(dtoEventFilter filtroEventoDTO);
}
