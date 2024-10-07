package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.dto.OrderReportDTO;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;
    private final AccountRepository accountRepository;

    public List<OrderReportDTO> generateSalesReportByLocation() {
        // Obtener todas las órdenes
        List<Order> orders = orderRepository.findAll();

        // Agrupar ventas por localidad y sumar las ganancias
        Map<String, Double> salesByLocation = new HashMap<>();
        for (Order order : orders) {
            for (OrderDetail detail : order.getItems()) {
                String location = String.valueOf(detail.getLocalityName());
                double totalSales = detail.getPrice() * detail.getAmount();
                salesByLocation.put(location, salesByLocation.getOrDefault(location, 0.0) + totalSales);
            }
        }

        // Convertir la información en un DTO para retornar
        List<OrderReportDTO> reportData = new ArrayList<>();
        salesByLocation.forEach((location, total) -> {
            reportData.add(new OrderReportDTO(location, total));
        });

        return reportData;
    }

    // Método para generar el PDF
    public byte[] generateSalesReportPDF() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Crear el escritor PDF
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar título grande centrado
            Paragraph title = new Paragraph("Reporte de Ventas").setBold().setFontSize(18).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            document.add(title);

            // Agregar fecha en la parte superior derecha
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Paragraph date = new Paragraph("Fecha: " + formattedDate).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT);
            document.add(date);

            // Tabla de ventas por localidad
            List<OrderReportDTO> salesReport = generateSalesReportByLocation();
            document.add(new Paragraph("Ventas por Localidad"));
            Table salesTable = new Table(2);
            salesTable.addHeaderCell("Localidad");
            salesTable.addHeaderCell("Total Vendido");
            for (OrderReportDTO report : salesReport) {
                salesTable.addCell(report.location());
                salesTable.addCell(String.valueOf(report.totalSales()));
            }
            document.add(salesTable);

            // Tabla de eventos más vendidos
            Map<String, Integer> topEvents = generateTopSellingEvents();
            document.add(new Paragraph("Eventos más Vendidos"));
            Table eventsTable = new Table(2);
            eventsTable.addHeaderCell("Evento");
            eventsTable.addHeaderCell("Cantidad Vendida");
           /* topEvents.forEach((event, sales) -> {
                eventsTable.addCell(event);
                eventsTable.addCell(String.valueOf(sales));
            }); */
            document.add(eventsTable);

            // Tabla de usuarios activos
            long activeUsers = countActiveUsers();
            document.add(new Paragraph("Usuarios Activos: " + activeUsers));

            // Tabla de eventos activos
            long activeEvents = countActiveEvents();
            document.add(new Paragraph("Eventos Activos: " + activeEvents));

            // Cerrar el documento
            document.close();

            // Guardar el PDF en el escritorio
            String homeDir = System.getProperty("user.home");
            String os = System.getProperty("os.name").toLowerCase();
            Path desktopPath;

            if (os.contains("win") || os.contains("mac")) {
                desktopPath = Paths.get(homeDir, "Desktop", "Reporte_Ventas.pdf");
            } else {
                desktopPath = Paths.get(homeDir, "Escritorio", "Reporte_Ventas.pdf");
            }

            Files.write(desktopPath, byteArrayOutputStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return byteArrayOutputStream.toByteArray();
    }

    // Métodos para obtener datos adicionales
    private Map<String, Integer> generateTopSellingEvents() {
        List<Order> orders = orderRepository.findAll();
        Map<String, Integer> eventSales = new HashMap<>();

        for (Order order : orders) {
            for (OrderDetail detail : order.getItems()) {
                String eventName = detail.getEventName();
                eventSales.put(eventName, eventSales.getOrDefault(eventName, 0) + detail.getAmount());
            }
        }
        return eventSales;
    }

    private long countActiveUsers() {
        return accountRepository.countByStatus(AccountStatus.ACTIVE);  // Cuenta los usuarios activos
    }

    private long countActiveEvents() {
        return eventRepository.countByStatus(EventStatus.ACTIVE);  // Cuenta los eventos activos
    }
}
