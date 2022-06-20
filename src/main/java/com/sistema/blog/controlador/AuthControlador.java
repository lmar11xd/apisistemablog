package com.sistema.blog.controlador;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegistroDTO;
import com.sistema.blog.entidades.Rol;
import com.sistema.blog.entidades.Usuario;
import com.sistema.blog.repositorio.RolRepositorio;
import com.sistema.blog.repositorio.UsuarioRepositorio;
import com.sistema.blog.seguridad.JWTAuthResponseDTO;
import com.sistema.blog.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        
        //Obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDto) {
        if (usuarioRepositorio.existsByUsername(registroDto.getUsername())) {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepositorio.existsByEmail(registroDto.getEmail())) {
            return new ResponseEntity<>("Email ya existe", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
		usuario.setNombre(registroDto.getNombre());
		usuario.setUsername(registroDto.getUsername());
		usuario.setEmail(registroDto.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDto.getPassword()));
		
		Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").get();
		usuario.setRoles(Collections.singleton(roles));
		
		usuarioRepositorio.save(usuario);
        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);
    }
}
