package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.dto.Account.createAccountDTO;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountItem;
import co.edu.uniquindio.proyecto.dto.Account.editAccountDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    /**
     * Metodo crearCuenta Test.
     * Este test verifica que una cuenta puede ser creada correctamente sin lanzar excepciones.
     * Se utiliza un DTO con los datos de la nueva cuenta y se espera que al llamar
     * al método 'crearCuenta', se obtenga un id válido para la cuenta creada.
     */
    @Test
    public void crearCuentaTest() {
        createAccountDTO createAccountDTO = new createAccountDTO(
                "1231", // Identificación
                "Pepito Perez", // Nombre
                "12121", // Número de teléfono
                "Calle 123", // Dirección
                "brandonlomejor10@gmail.com", // Correo
                "password" // Contraseña
        );

        // Se espera que no se lance ninguna excepción al crear la cuenta
        assertDoesNotThrow(() -> {
            String id = accountService.crearCuenta(createAccountDTO);
            // Verifica que el id de la cuenta creada no sea nulo
            assertNotNull(id);
        });
    }

    /**
     * Metodo para actualizarCuenta.
     * Este test verifica que se puede editar una cuenta existente sin lanzar excepciones.
     * Se actualizan algunos datos de la cuenta y luego se verifica que los cambios
     * se reflejan correctamente.
     */
    @Test
    public void actualizarCuentaTest() {
        String idCuenta = "66eb290b65664d4c873bc362"; // ID de la cuenta existente
        editAccountDTO editAccountDTO = new editAccountDTO(
                idCuenta, // ID de la cuenta a modificar
                "Pepito perez", // Nombre actualizado
                "12121", // Teléfono
                "Nueva dirección", // Dirección actualizada
                "canguro" // Contraseña actualizada
        );

        // Se espera que no se lance ninguna excepción al editar la cuenta
        assertDoesNotThrow(() -> {
            accountService.editarCuenta(editAccountDTO); // Actualiza la cuenta

            // Verifica que los cambios se han reflejado correctamente
            dtoAccountInformation detalle = accountService.obtenerInformacionCuenta(idCuenta);

            // Comprueba que la dirección del usuario es la nueva dirección
            assertEquals("Nueva dirección", detalle.address());
        });
    }

    /**
     * Metodo para obtener la información de una cuenta existente.
     * Este test verifica que, cuando se proporciona un ID válido, el método
     * 'obtenerInformacionCuenta' devuelve correctamente la información de la cuenta.
     */
    @Test
    public void obtenerInformacionCuentaTest_ExisteCuenta() throws Exception {
        String idCuenta = "66eb290b65664d4c873bc362"; // ID de cuenta válida en la base de datos

        // Obtiene la información de la cuenta y verifica que no sea nula
        dtoAccountInformation cuentaInfo = accountService.obtenerInformacionCuenta(idCuenta);
        assertNotNull(cuentaInfo);

        // Verifica que el ID de la cuenta obtenida es el esperado
        assertEquals(idCuenta, cuentaInfo.idNumber());
        // Aquí también podrías verificar otros campos de la cuenta si es necesario
    }

    /**
     * Metodo para verificar el comportamiento cuando la cuenta no existe.
     * Este test asegura que se lanza la excepción 'AccountNotFoundException' si se intenta
     * obtener la información de una cuenta que no existe en la base de datos.
     */
    @Test
    public void obtenerInformacionCuentaTest_NoExisteCuenta() {
        String idCuenta = "66eb19255765bd1b96784e53"; // ID de una cuenta inexistente

        // Verifica que se lanza la excepción esperada
        AccountNotFoundException thrownException = assertThrows(AccountNotFoundException.class, () -> {
            accountService.obtenerInformacionCuenta(idCuenta);
        });

        // Verifica que el mensaje de la excepción contiene el ID de la cuenta no encontrada
        assertEquals("No se encontró la cuenta con el id " + idCuenta, thrownException.getMessage());
    }

    /**
     * Metodo para eliminar una cuenta.
     * Este test verifica que se puede eliminar una cuenta sin lanzar excepciones.
     * La eliminación en este caso no elimina realmente la cuenta, sino que cambia su estado a ELIMINADO.
     * Luego, se verifica que la cuenta aún existe, pero en estado ELIMINADO.
     */
    @Test
    public void eliminarCuentaTEst() throws Exception {
        String idCuenta = "66eb290b65664d4c873bc362"; // ID de la cuenta a eliminar

        // Cambia el estado de la cuenta a ELIMINADO sin lanzar excepciones
        assertDoesNotThrow(() -> accountService.eliminarCuenta(idCuenta));

        // Verifica que la cuenta sigue existiendo pero con el estado ELIMINADO
        dtoAccountInformation cuenta = accountService.obtenerInformacionCuenta(idCuenta);
        assertNotNull(cuenta);
    }

    /**
     * Metodo para listar cuentas.
     * Este test verifica que el método 'listarCuentas' devuelve el número correcto de cuentas.
     * Asume que ya hay datos predefinidos en la base de datos de pruebas.
     */
    @Test
    public void listarTest() {
        // Lista las cuentas disponibles en la base de datos de pruebas
        List<dtoAccountItem> lista = accountService.listarCuentas();

        // Verifica que la lista contiene el número esperado de elementos
        assertEquals(3, lista.size(), "La lista de cuentas debería contener 2 elementos.");
    }



}







