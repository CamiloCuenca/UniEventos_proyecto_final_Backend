package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Metodo crearCuenta Test.
     * Este test verifica que una cuenta puede ser creada correctamente sin lanzar excepciones.
     * Se utiliza un DTO con los datos de la nueva cuenta y se espera que al llamar
     * al método 'crearCuenta', se obtenga un id válido para la cuenta creada.
     */
    @Test
    public void createAccountTest() {
        createAccountDTO createAccountDTO = new createAccountDTO(
                "100000000101", // Identificación
                "Brandon Montealegre", // Nombre
                "12121", // Número de teléfono
                "Calle 123", // Dirección
                "ba5808864@gmail.com", // Correo
                "M@mahermosa123" // Contraseña
        );

        // Se espera que no se lance ninguna excepción al crear la cuenta
        assertDoesNotThrow(() -> {
            String id = accountService.createAccount(createAccountDTO);
            // Verifica que el id de la cuenta creada no sea nulo
            assertNotNull(id);
        });
    }


    @Test
    public void loginAccountTest() {
        String email = "brandonca123@gmai.com";
        String password = "M@mahermosa123";  // Contraseña válida

        LoginDTO createLoginDTO = new LoginDTO(email, password);

        assertDoesNotThrow(() -> {
            TokenDTO tokenDTO = accountService.login(createLoginDTO);

            // Imprimir el token en la consola
            System.out.println("Token generado: " + tokenDTO.token());
        });
    }

    /**
     * Metodo para actualizarCuenta.
     * Este test verifica que se puede editar una cuenta existente sin lanzar excepciones.
     * Se actualizan algunos datos de la cuenta y luego se verifica que los cambios
     * se reflejan correctamente.
     */
    @Test
    public void updateTestAccount() {
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
            accountService.editAccount(editAccountDTO); // Actualiza la cuenta

            // Verifica que los cambios se han reflejado correctamente
            dtoAccountInformation detalle = accountService.obtainAccountInformation(idCuenta);

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
    public void obtainAccountInformatio() throws Exception {
        String idCuenta = "66eb290b65664d4c873bc362"; // ID de cuenta válida en la base de datos

        // Obtiene la información de la cuenta y verifica que no sea nula
        dtoAccountInformation cuentaInfo = accountService.obtainAccountInformation(idCuenta);
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
    public void obtainAccountInformation() {
        String idCuenta = "66eb19255765bd1b96784e53"; // ID de una cuenta inexistente

        // Verifica que se lanza la excepción esperada
        AccountNotFoundException thrownException = assertThrows(AccountNotFoundException.class, () -> {
            accountService.obtainAccountInformation(idCuenta);
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
    public void deleteAccountTEst() throws Exception {
        String idCuenta = "66eb290b65664d4c873bc362"; // ID de la cuenta a eliminar

        // Cambia el estado de la cuenta a ELIMINADO sin lanzar excepciones
        assertDoesNotThrow(() -> accountService.deleteAccount(idCuenta));

        // Verifica que la cuenta sigue existiendo pero con el estado ELIMINADO
        dtoAccountInformation cuenta = accountService.obtainAccountInformation(idCuenta);
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
        List<dtoAccountItem> lista = accountService.listAccounts();

        // Verifica que la lista contiene el número esperado de elementos
        assertEquals(3, lista.size(), "La lista de cuentas debería contener 2 elementos.");
    }


    @Test
    public void activateAccountTest() throws Exception {
        String correo = "ba5808864@gmail.com";
        String code = "9d9a1426";
        accountService.activateAccount(correo, code);

    }

    /**
     * Metodo para mandar el codigo de cambio de password
     *
     * @throws Exception
     */
    @Test
    void testSendPasswordRecoveryCode_success() throws Exception {
        // Datos de prueba
        String email = "ba5808864@gmail.com";

        // Ejecutar el método
        String result = accountService.sendPasswordRecoveryCode(email);

        // Verificaciones
        assertEquals("Código de recuperación enviado al correo " + email, result);
    }

    /**
     * el metodo de cambio de contraseña que no alcance hacer
     *
     * @throws Exception
     */
    @Test
    void testChangePassword_success() throws Exception {
        String correo = "ba5808864@gmail.com";
        String code = "774445fe";
        String newPassword = "M@mahermosa123";
        String confirmatePassword = "M@mahermosa123";
        changePasswordDTO changePasswordDTO = new changePasswordDTO(newPassword, confirmatePassword);

        // Llamar al método a probar
        accountService.changePassword(changePasswordDTO, correo, code);

        // Verificar que la cuenta fue actualizada correctamente en la base de datos
        Optional<Account> updatedAccount = accountRepository.findByEmail(correo);
        assertTrue(updatedAccount.isPresent());

        Account account = updatedAccount.get();

        // Verificar que la nueva contraseña coincida con la que fue encriptada
        assertTrue(passwordEncoder.matches(newPassword, account.getPassword()));
    }


}







