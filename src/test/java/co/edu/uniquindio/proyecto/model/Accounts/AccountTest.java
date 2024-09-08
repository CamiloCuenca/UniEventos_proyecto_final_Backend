package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTest {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Prueba unitaria para crear una cuenta
     */
    @Test
    public void createAccountTest() {
        //Creamos la cuenta con sus propiedades
        Account account = Account.builder()
                .email("Juanito@gmail.com")
                .password("123456").
                registrationDate(LocalDateTime.now()).
                status(AccountStatus.ACTIVO)
                .user(
                        User.builder()
                                .identityDocument("123")
                                .username("Juanito")
                                .address("Calle 123")
                                .phoneNumber("1231331")
                                .build()
                )
                .rol(Rol.CLIENTE).build();
        //Guardamos la cuenta del usuario en la base de datos.
        Account saved = accountRepository.save(account);
        //Verificamos que se haya guardado validando que no sea null
        assertNotNull(saved);
    }

    /**
     * Metodo para actualizar una cuenta.
     */
    @Test
    public void updateAccountTest() {
        //Obtenemos la cuenta del usuario con el id XXXXXXX
        Account account = accountRepository.findById("66dcf859f36ccd57406c83dd").orElseThrow();
        //Modificamos el Email de la cuenta.
        account.setEmail("nuevocorreo@gmail.com");
        //Guardamos la cuenta del usuario
        accountRepository.save(account);

        //obtenemos la cuenta del usuario con el id XXXXX nuevamente.
        Account accountUpdate = accountRepository.findById("66dcf859f36ccd57406c83dd").orElseThrow();
        //Verificamos que el email se actualizo correctamente.
        assertEquals("nuevocorreo@gmail.com", accountUpdate.getEmail());

    }

    /**
     * Metodo para listar las cuenta.
     */
    @Test
    public void listAccountsTest() {
        //Obtenemos la lista de todos los usuarios
        List<Account> list = accountRepository.findAll();
        //Imprimimos todas las cuentas
        list.forEach(System.out::println);
        //Verificamos que solo exista una
        assertEquals(1, list.size());
    }

    /**
     * metodo para eliminar una cuenta.
     */
    @Test
    public void deleteAccountTest() {
        //Borramos la cuenta del usuario con el id.
        accountRepository.deleteById("66dcf859f36ccd57406c83dd");
        //Obtenemos el usuario con el id XXXXXX
        Account account = accountRepository.findById("66dcf859f36ccd57406c83dd").orElse(null);
        //Verificamos que la cuenta no exista (sea null) ya que fue eliminada.
        assertNull(account);
    }


}