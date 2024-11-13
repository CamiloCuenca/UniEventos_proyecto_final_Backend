package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.config.JWTUtils;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.exception.Coupons.CouponCreationException;
import co.edu.uniquindio.proyecto.exception.account.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Accounts.User;
import co.edu.uniquindio.proyecto.model.Accounts.ValidationCode;
import co.edu.uniquindio.proyecto.model.Accounts.ValidationCodePassword;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
import lombok.RequiredArgsConstructor;
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

    private final AccountRepository accountRepository;

    private final CouponService couponService;

    private final EmailService emailService;

    /**
     * Metodo encargado validar que el Email no este en uso
     *
     * @param email Email de de la cuenta a buscar
     * @return
     */
    private boolean existsEmail(String email) {
        return cuentaRepo.findByEmail(email).isPresent();
    }

    /**
     * Metodo para encriptar la contrasena.
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Metodo encargado de validar que la cedula del usuario no este en uso.
     *
     * @param idNumber el id del usuario a buscar
     * @return
     */
    private boolean idExists(String idNumber) {
        return cuentaRepo.findByIdUNumber(idNumber).isPresent();
    }

    /**
     * Metodo encargado de crear el cuerpo del token.
     *
     * @param account Estructura de la cuenta para el token
     * @return
     */
    private Map<String, Object> construirClaims(Account account) {
        return Map.of(
                "rol", account.getRol(),
                "nombre", account.getUser().getName(),
                "id", account.getAccountId(),
                "email", account.getEmail()
        );
    }

    /**
     * Método login(): Permite iniciar sesión utilizando las credenciales de email y contraseña del usuario. Si la cuenta no está activa o la contraseña es incorrecta, lanza las excepciones correspondientes.
     * Utiliza JWT para generar un token basado en los detalles de la cuenta (rol, nombre, id).
     *
     * @param loginDTO DTO destinado para el login.
     * @return
     * @throws EmailNotFoundException
     * @throws InvalidPasswordException
     */
    @Override
    public TokenDTO login(LoginDTO loginDTO) throws EmailNotFoundException, InvalidPasswordException, ActiveAccountException {
        // Buscar la cuenta en la base de datos utilizando el email proporcionado en el LoginDTO.
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(loginDTO.email());

        // Si no se encuentra una cuenta asociada al email, lanzar una excepción personalizada.
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(loginDTO.email());
        }

        // Obtener la cuenta de la Optional. Aquí se asume que la cuenta está presente, ya que se verificó antes.
        Account account = optionalAccount.get();

        // Verificar el estado de la cuenta. Si no está activa, lanzar una excepción personalizada.
        if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new ActiveAccountException("La cuenta no se encuentra Activada. Por favor, actívala para ingresar!");
        }

        if (account.getFailedLoginAttempts() >= 3) {
            account.setStatus(AccountStatus.INACTIVE); // Cambiar el estado de la cuenta a INACTIVA.
            cuentaRepo.save(account); // Guardar el estado actualizado en la base de datos.
            throw new ActiveAccountException("La cuenta ha sido bloqueada por múltiples intentos fallidos de inicio de sesión.");
        }


        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada utilizando el passwordEncoder.
        if (!passwordEncoder.matches(loginDTO.password(), account.getPassword())) {
            // Incrementar el contador de intentos fallidos si la contraseña es incorrecta.
            account.setFailedLoginAttempts(account.getFailedLoginAttempts() + 1);
            cuentaRepo.save(account); // Guardar el número de intentos fallidos en la base de datos.
            throw new InvalidPasswordException(); // Lanzar excepción si la contraseña es incorrecta.
        }

        account.setFailedLoginAttempts(0);

        // Cambiar el estado de la cuenta a ACTIVA si está bloqueada y la contraseña es correcta.
        if (account.getStatus() == AccountStatus.INACTIVE && account.getFailedLoginAttempts() < 3) {
            account.setStatus(AccountStatus.ACTIVE);
        }

        cuentaRepo.save(account); // Guardar el estado actualizado en la base de datos.

        // Construir los claims necesarios para generar el token JWT, pasando la cuenta encontrada.
        Map<String, Object> map = construirClaims(account);

        // Generar y devolver un nuevo TokenDTO que contiene el token JWT generado.
        return new TokenDTO(jwtUtils.generateToken(account.getEmail(), map));
    }

    /**
     * Método createAccount(): Crea una nueva cuenta, encripta la contraseña con BCryptPasswordEncoder, genera un código de validación, y envía un email electrónico al usuario para activar la cuenta.
     * Valida que el email y el número de identificación no existan previamente en la base de datos.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String createAccount(createAccountDTO cuenta) throws Exception {
        // Verificar si ya existe una cuenta con el mismo email.
        if (existsEmail(cuenta.email())) {
            throw new EmailAlreadyExistsException(cuenta.email());
        }

        // Verificar si ya existe una cuenta con el mismo número de identificación.
        if (idExists(cuenta.idNumber())) {
            throw new CedulaAlreadyExistsException(cuenta.idNumber());
        }

        // Segmento del código que se encarga de encriptar la contraseña.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(cuenta.password());

        // Mapeamos (pasamos) los datos del DTO a un objeto de tipo Account.
        Account newAccount = new Account();
        newAccount.setEmail(cuenta.email());
        // Asignar la clave encriptada a la cuenta.
        newAccount.setPassword(hashedPassword);
        newAccount.setRol(Rol.CUSTOMER); // Asignar rol de cliente.
        newAccount.setRegistrationDate(LocalDateTime.now()); // Establecer la fecha de registro.

        // Crear un nuevo objeto User con los datos proporcionados.
        newAccount.setUser(new User(
                cuenta.idNumber(),
                cuenta.name(),
                cuenta.phoneNumber(),
                cuenta.address()
        ));

        // Establecer el estado inicial de la cuenta como inactiva.
        newAccount.setStatus(AccountStatus.INACTIVE);

        // Generar un código de validación y asociarlo a la cuenta.
        String validationCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        ValidationCode validationCodeObj = new ValidationCode(validationCode);
        newAccount.setRegistrationValidationCode(validationCodeObj);

        // Guardar la nueva cuenta en el repositorio.
        Account createdAccount = cuentaRepo.save(newAccount);

        // Enviar el código de validación por email electrónico.
        emailService.sendCodevalidation(createdAccount.getEmail(), validationCode);

        // Retornar el ID de la cuenta creada.
        return createdAccount.getAccountId();
    }

    /**
     * Metodo encargado de actualizar o editar los datos de la cuenta.
     *
     * @param cuenta
     * @return
     * @throws Exception
     */
    @Override
    public String editAccount(editAccountDTO cuenta, String id) throws AccountNotFoundException {
        // Buscar la cuenta en la base de datos utilizando el ID proporcionado
        Optional<Account> optionalAccount = cuentaRepo.findById(id);

        // Verificar si la cuenta existe; si no, lanzar una excepción personalizada
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("No se encontró la cuenta con ID: " + id);
        }

        // Obtener la cuenta encontrada
        Account cuentaActualizada = optionalAccount.get();

        // Actualizar los datos básicos utilizando el DTO
        cuentaActualizada.getUser().setName(cuenta.name()); // Usar el método name() del DTO
        cuentaActualizada.getUser().setPhoneNumber(cuenta.phoneNumber()); // Usar el método phoneNumber() del DTO
        cuentaActualizada.getUser().setAddress(cuenta.address()); // Usar el método address() del DTO

        // Guardar los cambios en la base de datos sin tocar la contraseña
        cuentaRepo.save(cuentaActualizada);

        return cuentaActualizada.getAccountId();
    }

    /**
     * Método obtainAccountInformation(): Recupera la información básica de la cuenta basada en su ID.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public MessageDTOC<dtoAccountInformation> obtainAccountInformation(String id) {
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);

        // Manejo del caso en que la cuenta no existe
        if (optionalCuenta.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("Cuenta no encontrada", 404);
            return new MessageDTOC<>(true, null, errorResponse); // Asegúrate de que respuesta es null
        }

        // Si la cuenta existe, obtenemos la información
        Account account = optionalCuenta.get();
        dtoAccountInformation accountInfo = new dtoAccountInformation(
                account.getUser().getName(),
                account.getUser().getPhoneNumber(),
                account.getUser().getAddress()
        );

        // Retornamos la respuesta exitosa
        return new MessageDTOC<>(false, accountInfo, null); // No hay error
    }


    @Override
    public String updatePassword(updatePasswordDTO updatePasswordDTO, String id)
            throws AccountNotFoundException, InvalidCurrentPasswordException, PasswordMismatchException {
        // Buscar la cuenta en la base de datos utilizando el id proporcionado
        Optional<Account> optionalAccount = cuentaRepo.findById(id);

        // Verificar si la cuenta existe
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("No se encontró la cuenta");
        }

        Account account = optionalAccount.get();

        // Verificar si la contraseña actual coincide con la almacenada
        if (!passwordEncoder.matches(updatePasswordDTO.currentPassword(), account.getPassword())) {
            throw new InvalidCurrentPasswordException("La contraseña actual es incorrecta.");
        }

        // Validar la nueva contraseña (longitud mínima, etc.)
        String newPassword = updatePasswordDTO.newPassword();
        String confirmNewPassword = updatePasswordDTO.confirmationPassword(); // Obtener la confirmación

        // Verificar que la nueva contraseña no esté vacía y tenga al menos 8 caracteres
        if (newPassword.isEmpty() || newPassword.length() < 8) {
            throw new InvalidCurrentPasswordException("La nueva contraseña debe tener al menos 8 caracteres.");
        }

        // Verificar que la nueva contraseña coincida con la confirmación
        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordMismatchException("La nueva contraseña y la confirmación no coinciden.");
        }

        // Encriptar y actualizar la nueva contraseña
        String encryptedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encryptedPassword);
        cuentaRepo.save(account);

        return "La contraseña ha sido cambiada exitosamente.";
    }

    /**
     * Método listAccounts(): Devuelve una lista de todas las cuentas registradas en la base de datos, lanzando una excepción si no se encuentran cuentas.
     *
     * @return
     */
    @Override
    public List<dtoAccountItem> listAccounts() {
        try {
            // Obtener todas las cuentas de la base de datos.
            List<Account> cuentas = cuentaRepo.findAll();

            // Verificar si la lista de cuentas está vacía; si es así, lanzar una excepción personalizada.
            if (cuentas.isEmpty()) {
                throw new NoAccountsFoundException();
            }


            List<dtoAccountItem> items = new ArrayList<>();

            // Iterar sobre cada cuenta y mapear los datos a objetos dtoAccountItem.
            for (Account account : cuentas) {
                items.add(new dtoAccountItem(
                        account.getAccountId(), // ID de la cuenta
                        account.getUser().getName(), // Nombre del usuario
                        account.getUser().getPhoneNumber(), // Número de teléfono del usuario
                        account.getEmail() // Email de la cuenta

                ));
            }

            // Retornar la lista de DTOs de cuentas.
            return items;
        } catch (Exception e) {
            // Manejo de excepciones: si ocurre un error, lanzar una excepción personalizada con el mensaje de error.
            throw new AccountRetrievalException(e.getMessage());
        }
    }

    /**
     * Método deleteAccount(): Cambia el estado de una cuenta a "ELIMINADA" en lugar de borrarla físicamente.
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteAccount(String id, PasswordDTO password) throws AccountNotFoundException {
        // Buscar la cuenta del usuario que se quiere eliminar en la base de datos.
        Optional<Account> optionalCuenta = cuentaRepo.findById(id);

        // Verificar si la cuenta existe; si no, lanzar una excepción personalizada.
        if (optionalCuenta.isEmpty()) {
            throw new AccountNotFoundException(id);
        }

        // Obtener la cuenta encontrada.
        Account account = optionalCuenta.get();

        // Verificar si la contraseña proporcionada coincide con la contraseña almacenada utilizando el passwordEncoder.
        if (!passwordEncoder.matches(password.password(), account.getPassword())) {
            throw new InvalidPasswordException(); // Lanzar excepción si la contraseña es incorrecta.
        }

        // Cambiar el estado de la cuenta a ELIMINATED en lugar de borrarla físicamente.
        account.setStatus(AccountStatus.ELIMINATED);

        // Guardar la cuenta actualizada en la base de datos.
        cuentaRepo.save(account);

        // Retornar el ID de la cuenta eliminada.
        return account.getAccountId();
    }


    /**
     * Método sendPasswordRecoveryCode(): Genera un código de validación para recuperar la contraseña y lo envía al email electrónico del usuario.
     *
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public String sendPasswordRecoveryCode(String email) throws Exception {
        // Buscar la cuenta asociada al email proporcionado.
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(email);

        // Verificar si se encontró la cuenta; si no, lanzar una excepción personalizada.
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(email);
        }

        // Obtener la cuenta encontrada.
        Account account = optionalAccount.get();

        // Generar un código de validación para la recuperación de contraseña.
        String passwordValidationCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        ValidationCodePassword validationCodePassword = new ValidationCodePassword(passwordValidationCode);

        // Asignar el código de validación a la cuenta.
        account.setPasswordValidationCode(validationCodePassword);

        // Guardar la cuenta actualizada en la base de datos.
        cuentaRepo.save(account);

        // Enviar por email el código de recuperación al usuario.
        emailService.sendRecoveryCode(account.getEmail(), passwordValidationCode);

        // Retornar un mensaje indicando que el código fue enviado.
        return "Código de recuperación enviado al email " + account.getEmail();
    }


    @Override
    public String sendActiveCode(String email) throws Exception {
        Optional<Account> optionalAccount = cuentaRepo.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            throw new EmailNotFoundException(email);
        }

        Account account = optionalAccount.get();
        String activeCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        ValidationCode validationCode = new ValidationCode(activeCode);
        account.setRegistrationValidationCode(validationCode);
        cuentaRepo.save(account);
        emailService.sendCodevalidation(account.getEmail(), activeCode);
        return "Código de validacion de cuenta enviado al correo: " + account.getEmail();
    }


    /**
     * Método changePassword(): Permite al usuario cambiar su contraseña tras verificar que el código de recuperación es válido y no ha expirado.
     *
     * @param changePasswordDTO
     * @return
     * @throws Exception
     */


    @Override
    public String changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        // Buscar la cuenta del usuario por el código de validación
        Optional<Account> optionalAccount = cuentaRepo.findByValidationCode(changePasswordDTO.code());

        if (optionalAccount.isEmpty()) {
            throw new InvalidValidationCodeException("No se encontró la cuenta.");
        }

        Account account = optionalAccount.get();
        ValidationCodePassword validationCodePassword = account.getPasswordValidationCode();

        // Verificar si el código de validación es nulo o ha expirado
        if (validationCodePassword == null || validationCodePassword.isExpired()) {
            throw new ValidationCodeExpiredException("El código de recuperación ha expirado o no es válido.");
        }

        // Verificar que las contraseñas coincidan
        if (!changePasswordDTO.newPassword().equals(changePasswordDTO.confirmationPassword())) {
            throw new PasswordsDoNotMatchException("Las contraseñas no coinciden.");
        }

        // Encriptar la nueva contraseña y actualizar la cuenta
        account.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));

        // Limpiar el código de validación después de cambiar la contraseña exitosamente
        account.setPasswordValidationCode(null);
        account.setFailedLoginAttempts(0);
        account.setStatus(AccountStatus.ACTIVE);

        // Guardar la cuenta actualizada en el repositorio
        cuentaRepo.save(account);

        return "La contraseña ha sido cambiada exitosamente.";
    }


    /**
     * Método activateAccount(): Activa la cuenta de un usuario utilizando el código de validación que se envía por email al momento de crear la cuenta.
     *
     * @return
     * @throws Exception
     */
    @Override
    public String activateAccount(ActivateAccountDTO activateAccountDTO) throws Exception {
        // Buscar la cuenta por código de activación y lanzar excepción si no se encuentra
        Account account = cuentaRepo.findByActiveCode(activateAccountDTO.code())
                .orElseThrow(() -> new AccountNotFoundException(activateAccountDTO.code()));

        // Verificar si la cuenta ya está activa
        if (account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new AccountAlreadyActiveException("La cuenta ya está activada.");
        }

        // Obtener y verificar el código de validación asociado a la cuenta
        ValidationCode validationCode = Optional.ofNullable(account.getRegistrationValidationCode())
                .orElseThrow(() -> new ValidationCodeExpiredException("El código de validación no existe."));

        // Validar código y fecha de expiración
        if (validationCode.isExpired()) {
            throw new ValidationCodeExpiredException("El código de validación ha expirado.");
        }
        if (!validationCode.getCode().equals(activateAccountDTO.code())) {
            throw new InvalidValidationCodeException("El código de validación es inválido.");
        }

        // Crear y enviar el cupón de bienvenida
        CouponDTO couponDTO = generateWelcomeCoupon();
        couponService.createCoupon(couponDTO);
        emailService.sendWelcomeCoupon(activateAccountDTO.email(), couponDTO.code());

        // Activar la cuenta
        account.setRegistrationValidationCode(null);
        account.setStatus(AccountStatus.ACTIVE);
        account.setFailedLoginAttempts(0);
        cuentaRepo.save(account);

        // Retornar mensaje de éxito
        return "Cuenta activada exitosamente y cupón enviado por email";
    }


    /**
     * Mètodo para generar el cupon de bienvenida de un 15% de descuento
     *
     * @return cupon de bienvenida
     */

    public CouponDTO generateWelcomeCoupon() throws CouponCreationException {
        try {
            // Creamos el cupón de 15% de descuento
            CouponDTO couponDTO = new CouponDTO(
                    "Cupón de Bienvenida",              // Nombre del cupón
                    UUID.randomUUID().toString().replace("-", "").substring(0, 8), // Código único de cupón
                    "15",                               // Descuento del 15%
                    LocalDateTime.now().plusDays(30),   // Fecha de expiración (30 días a partir de ahora)
                    CouponStatus.AVAILABLE,             // Estado disponible
                    TypeCoupon.ONLY,                    // Tipo de cupón: uso único
                    null,                               // ID del evento: (no es requerido para bienvenida)
                    LocalDateTime.now()                 // Fecha de inicio: hoy
            );
            return couponDTO;
        } catch (Exception e) {
            // Manejar la excepción y lanzar una excepción más específica si es necesario
            throw new CouponCreationException("Error al generar el cupón de bienvenida");
        }
    }


}
