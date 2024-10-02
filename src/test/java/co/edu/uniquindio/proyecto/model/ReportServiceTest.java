package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.service.Implementation.ReportService;
import co.edu.uniquindio.proyecto.dto.OrderReportDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Esta anotación carga el contexto completo de Spring
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void testGenerateSalesReportByLocation() {
        // Act - Llamar al método de generación de reportes
        List<OrderReportDTO> report = reportService.generateSalesReportByLocation();

        // Assert - Verificar que el reporte no sea nulo ni vacío
        assertNotNull(report, "El reporte no debe ser nulo.");
        assertFalse(report.isEmpty(), "El reporte no debe estar vacío.");

        // Otras validaciones opcionales
        // Verificar que contiene datos esperados
        for (OrderReportDTO entry : report) {
            System.out.println("Localidad: " + entry.location() + ", Total de ventas: " + entry.totalSales());
            assertTrue(entry.totalSales() > 0, "El total de ventas debe ser mayor que 0 para la localidad " + entry.location());
        }
    }

    @Test
    public void testGenerateSalesReportPDF() {
        // Generar el PDF
        reportService.generateSalesReportPDF();

        // Definir la ruta donde se guardará el PDF
        String homeDir = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        String pdfFilePath;

        // Verificar el sistema operativo y definir la ruta del PDF
        if (os.contains("win")) {
            // Windows
            pdfFilePath = Paths.get(homeDir, "Desktop", "Reporte_Ventas.pdf").toString();
        } else if (os.contains("mac")) {
            // macOS
            pdfFilePath = Paths.get(homeDir, "Desktop", "Reporte_Ventas.pdf").toString();
        } else {
            // Linux y otros
            pdfFilePath = Paths.get(homeDir, "Escritorio", "Reporte_Ventas.pdf").toString();
        }

        // Comprobar que el archivo PDF fue creado y su ruta no es nula
        assertNotNull(pdfFilePath, "La ruta del PDF generado no debería ser nula");

        // Verificar que el archivo realmente existe
        File pdfFile = new File(pdfFilePath);
        assertTrue(pdfFile.exists(), "El archivo PDF debería existir en la ruta especificada");

        // Opcional: Comprobar que el archivo no está vacío
        assertTrue(pdfFile.length() > 0, "El archivo PDF debería tener contenido");

        // Opcional: Limpiar (borrar) el archivo después de la prueba
        // pdfFile.delete();
    }
}
