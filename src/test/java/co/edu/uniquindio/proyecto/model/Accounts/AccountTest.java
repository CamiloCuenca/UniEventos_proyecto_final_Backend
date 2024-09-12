package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.dto.Account.createAccountDTO;
import co.edu.uniquindio.proyecto.dto.Account.editAccountDTO;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountItem;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    /**
     * Metodo crearCuenta Test.
     */
    @Test
    public void crearCuentaTest() {
        // Crear un DTO con los datos para crear una nueva cuenta
        createAccountDTO createAccountDTO = new createAccountDTO(
                "1234",
                "Pepito Perez",
                "12121",
                "Calle 123",
                "brandonlomejor12@gmail.com",
                "password"
        );

        // Se espera que no se lance ninguna excepción
        assertDoesNotThrow(() -> {
            // Se crea la cuenta y se imprime el id
            String id = accountService.crearCuenta(createAccountDTO);
            // Se espera que el id no sea nulo
            assertNotNull(id);
        });
    }

    /**
     * Metodo para actualizarCUenta
     */
    @Test
    public void actualizarCuentaTest() {
        String idCuenta = "66e1dde556152b147784aab2";
        editAccountDTO editAccountDTO = new editAccountDTO(
                idCuenta,
                "Pepito perez",
                "12121",
                "Nueva dirección",
                "canguro"
        );

        //Se espera que no se lance ninguna excepción
        assertDoesNotThrow(() -> {
            //Se actualiza la cuenta del usuario con el id definido
            accountService.editarCuenta(editAccountDTO);


            //Obtenemos el detalle de la cuenta del usuario con el id definido
            dtoAccountInformation detalle = accountService.obtenerInformacionCuenta(idCuenta);


            //Se verifica que la dirección del usuario sea la actualizada
            assertEquals("Nueva dirección", detalle.address());
        });
    }

    /**
     * Metodo para eliminar cuenta
     *
     * @throws Exception
     */
    @Test
    public void eliminarCuentaTEst() throws Exception {
        String idCuenta = "66a2a9aaa8620e3c1c5437be";

        // Cambia el estado de la cuenta a ELIMINADO
        assertDoesNotThrow(() -> accountService.eliminarCuenta(idCuenta));

        // Verifica que la cuenta sigue existiendo y su estado es ELIMINADO
        dtoAccountInformation cuenta = accountService.obtenerInformacionCuenta(idCuenta);
        assertNotNull(cuenta);

    }

    /**
     * Metodo para listar cuentas.
     */
    @Test
    public void listarTest() {
        // Asume que ya has configurado datos iniciales en tu base de datos de prueba
        List<dtoAccountItem> lista = accountService.listarCuentas();
        // Verifica que la lista contiene el número esperado de elementos
        assertEquals(4, lista.size(), "La lista de cuentas debería contener 4 elementos.");
    }

}







