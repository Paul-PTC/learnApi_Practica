package IntegracionBackFront.backfront.Services.Auth;

import IntegracionBackFront.backfront.Config.Crypto.Argon2Password;
import IntegracionBackFront.backfront.Entities.Users.UserEntity;
import IntegracionBackFront.backfront.Repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    public boolean Login(String correo, String contrasena){
        Argon2Password objHash = new Argon2Password();
        Optional<UserEntity> list = repo.findByCorreo(correo).stream().findFirst();
        if (list.isPresent()){
            UserEntity usuario = list.get();
            String nombreTipoUsuario = usuario.getTipoUsuario().getNombreTipo();
            System.out.println("Ultimo encontrado ID: " + usuario.getId() +
                    ", Email" + usuario.getCorreo() +
                    ", Rol: " + nombreTipoUsuario);
            String HashBD = usuario.getContrasena();
            return objHash.VerifyPassword(HashBD, contrasena);
        }
        return false;
    }

    public Optional<UserEntity> obtenerUsuario(String correo) {
        // Buscar usuario completo en la base de datos
        Optional<UserEntity> userOpt = repo.findByCorreo(correo);
        return (userOpt != null) ? userOpt : null;
    }
}
