package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    private EventRepository eventRepository;













}
