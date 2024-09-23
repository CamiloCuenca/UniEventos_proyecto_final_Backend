package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.config.JWTUtils;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.exception.account.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Accounts.User;
import co.edu.uniquindio.proyecto.model.Accounts.ValidationCode;
import co.edu.uniquindio.proyecto.model.Accounts.ValidationCodePassword;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceimp implements AccountService {

    private final AccountRepository cuentaRepo;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    @Autowired
    EmailService emailService;

    /**
     * Metodo encargado de la estructura del token.
     *
     * @param account
     * @return
     */
    private Map<String, Object> construirClaims(Account account) {
        return Map.of(
                "rol", account.getRol(),
                "nombre", account.getUser().getName(),
                "id", account.getAccountId()
        );
    }

    private boolean existsEmail(String email) {
        return cuentaRepo.findByEmail(email).isPresent();
    }

    private boolean idExists(String idNumber) {
        return cuentaRepo.findByIdnumber(idNumber).isPresent();
    }

    /**
     * Metodo encargado del inicio de sesion de la cuenta Encriptado la clave del usuario y a su vez generando un token segun su rol.
     *
     * @param loginDTO
     * @return
     * @throws EmailNotFoundException
     * @throws InvalidPasswordException
     */
    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws EmailNotFoundException, InvalidPasswordException {
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(loginDTO.email());

        // Corregido: lanzar excepción si no se encuentra el email
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(loginDTO.email());
        }

        Account account = optionalAccount.get();

        // Verificar si la contraseña es válida
        if (!passwordEncoder.matches(loginDTO.password(), account.getPassword())) {
            throw new InvalidPasswordException();
        }
        Map<String, Object> map = construirClaims(account);
        return new TokenDTO(jwtUtils.generateToken(account.getEmail(), map));
    }

    /**
     * Metodo encargado de Activar la cuenta con el codigo mandado por correo.
     *
     * @param correo
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public String activateAccount(String correo, String code) throws Exception {
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(correo);
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(correo);
        }

        Account account = optionalAccount.get();
        ValidationCode validationCode = account.getRegistrationValidationCode();

        if (validationCode == null || validationCode.isExpired()) {
            throw new ValidationCodeExpiredException();
        }

        if (!validationCode.getCode().equals(code)) {
            throw new InvalidValidationCodeException();
        }

        account.setStatus(AccountStatus.ACTIVO);
        cuentaRepo.save(account);

        return "Cuenta activada exitosamente";
    }

    /**
     * Metodo encargado de crear una cuenta.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String createAccount(createAccountDTO cuenta) throws Exception {

        if (existsEmail(cuenta.email())) {
            throw new EmailAlreadyExistsException(cuenta.email());
        }

        if (idExists(cuenta.idNumber())) {
            throw new CedulaAlreadyExistsException(cuenta.idNumber());
        }
        //Segmento del codigo que se encarga de encriptar el codigo.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(cuenta.password());

        //Mapeamos (pasamos) los datos del DTO a un objeto de tipo Cuenta
        Account newAccount = new Account();
        newAccount.setEmail(cuenta.email());
        //Clave encriptada.
        newAccount.setPassword(hashedPassword);
        newAccount.setRol(Rol.CLIENTE);
        newAccount.setRegistrationDate(LocalDateTime.now());
        newAccount.setUser(new User(
                cuenta.idNumber(),
                cuenta.name(),
                cuenta.phoneNumber(),
                cuenta.address()
        ));
        newAccount.setStatus(AccountStatus.INACTIVO);

        String validationCode = generateValidationCode();
        ValidationCode validationCodeObj = new ValidationCode(validationCode);
        newAccount.setRegistrationValidationCode(validationCodeObj);

        Account createdAccount = cuentaRepo.save(newAccount);

        String plainTextMessage = "Estimado usuario,\n\n" +
                "Gracias por registrarse en nuestra plataforma. Para activar su cuenta, por favor utilice el siguiente código de activación:\n\n" +
                "Código de activación: " + validationCode + "\n\n" +
                "Este código es válido por 15 minutos.\n\n" +
                "Si usted no solicitó este registro, por favor ignore este correo.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        emailService.sendMail(new EmailDTO(newAccount.getEmail(), "\"Activación de cuenta\"", plainTextMessage));


        return createdAccount.getAccountId();
    }

    // Método auxiliar para generar un código de validación aleatorio
    private String generateValidationCode() {
        return UUID.randomUUID().toString().substring(0, 8); // Código de 8 caracteres
    }




    /**
     * Metodo para actualizarCuenta.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String editAccount(editAccountDTO cuenta) throws AccountNtFoundException {
        Optional<Account> optionalAccount = cuentaRepo.findById(cuenta.id());

        if (optionalAccount.isEmpty()) {
            throw new AccountNtFoundException(cuenta.id());
        }

        Account cuentaModificada = optionalAccount.get();
        cuentaModificada.getUser().setName(cuenta.username());
        cuentaModificada.getUser().setPhoneNumber(cuenta.phoneNumber());
        cuentaModificada.getUser().setAddress(cuenta.address());
        cuentaModificada.setPassword(cuenta.password());
        //Segmento de codigo que se encarga de encriptar la clave.
        if (cuenta.password() != null && !cuenta.password().isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = bCryptPasswordEncoder.encode(cuenta.password());
            cuentaModificada.setPassword(hashedPassword);
        }
        cuentaRepo.save(cuentaModificada);
        return cuentaModificada.getAccountId();
    }

    /**
     * Metodo encargado de obtener la informacion de la cuenta.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public dtoAccountInformation obtainAccountInformation(String id) throws AccountNotFoundException {
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);
        if (optionalCuenta.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        Account account = optionalCuenta.get();

        return new dtoAccountInformation(
                account.getAccountId(),
                account.getUser().getIdNumber(),
                account.getUser().getPhoneNumber(),
                account.getUser().getAddress(),
                account.getEmail()
        );
    }

    /**
     * Metodo para listar cuentas.
     *
     * @return
     */
    @Override
    public List<dtoAccountItem> listAccounts() {
        try {
            List<Account> cuentas = cuentaRepo.findAll();
            if (cuentas.isEmpty()) {
                throw new NoAccountsFoundException();
            }
            List<dtoAccountItem> items = new ArrayList<>();

            for (Account account : cuentas) {
                items.add(new dtoAccountItem(
                        account.getAccountId(),
                        account.getUser().getName(),
                        account.getEmail(),
                        account.getUser().getPhoneNumber()
                ));
            }
            return items;
        } catch (Exception e) {
            throw new AccountRetrievalException(e.getMessage());
        }
    }

    /**
     * Metodo para eliminarCuenta.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteAccount(String id) throws AccountNotFoundException {
        //Buscamos la cuenta del usuario que se quiere eliminar
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);
        if (optionalCuenta.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        Account cuenta = optionalCuenta.get();
        cuenta.setStatus(AccountStatus.ELIMINADO);
        cuentaRepo.save(cuenta);
        return cuenta.getAccountId();
    }

    /**
     * Metodo para enviar el codigo de cambio de contraseña
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public String sendPasswordRecoveryCode(String email) throws Exception {
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(email);
        }
        Account account = optionalAccount.get();

        String passwordValidationCOde = generateValidationCode();
        ValidationCodePassword validationCodePassword = new ValidationCodePassword(generateValidationCode());
        account.setPasswordValidationCode(validationCodePassword);
        cuentaRepo.save(account);
        // Crear el mensaje a enviar por correo
        String plainTextMessage = "Estimado usuario,\n\n" +
                "Ha solicitado recuperar su contraseña. Utilice el siguiente código de recuperación para restablecer su contraseña:\n\n" +
                "Código de recuperación: " + passwordValidationCOde + "\n\n" +
                "Este código es válido por 15 minutos.\n\n" +
                "Si usted no solicitó esta recuperación, por favor ignore este correo.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        // Enviar el correo electrónico con el código de recuperación
        emailService.sendMail(new EmailDTO(account.getEmail(), "Recuperación de contraseña", plainTextMessage));

        return "Código de recuperación enviado al correo " + account.getEmail();
    }

    /**
     * El metodo que estaba implementado para el cambio de contraseña
     * @param changePasswordDTO
     * @return
     * @throws Exception
     */
    @Override
    public String cambiarPassword(changePasswordDTO changePasswordDTO) throws Exception {
        // Buscar la cuenta por el código de recuperación
        Optional<Account> optionalAccount = cuentaRepo.findByPasswordValidationCode(changePasswordDTO.verificationCode());
        if (optionalAccount.isEmpty()) {
            throw new InvalidValidationCodeException("El código de recuperación es incorrecto.");
        }

        Account account = optionalAccount.get();
        ValidationCode validationCodeObj = account.getRegistrationValidationCode();

        // Validar si el código ha expirado
        if (validationCodeObj.isExpired()) {
            throw new ValidationCodeExpiredException("El código de recuperación ha expirado.");
        }

        // Verificar que las contraseñas ingresadas coinciden
        if (!changePasswordDTO.newPassword().equals(changePasswordDTO.confirmationPassword())) {
            throw new PasswordsDoNotMatchException("Las contraseñas no coinciden.");
        }

        // Encriptar la nueva contraseña
        String encryptedPassword = passwordEncoder.encode(changePasswordDTO.newPassword());
        account.setPassword(encryptedPassword);

        // Limpiar el código de recuperación para evitar su reutilización
        account.setRegistrationValidationCode(null);

        // Guardar la cuenta actualizada
        cuentaRepo.save(account);

        return "La contraseña ha sido cambiada exitosamente.";
    }





}
