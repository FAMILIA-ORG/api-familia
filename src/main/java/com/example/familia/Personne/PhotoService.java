package com.example.familia.Personne;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;

    private final PersonneRepository personneRepository;

    public Personne uploadPhoto(Long id, MultipartFile file) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable"));

        validateImage(file);

        try {
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);

            String ext = getExtension(file.getOriginalFilename());
            String filename = "personne_" + id + "_" + UUID.randomUUID() + "." + ext;
            Path dest = dir.resolve(filename);

            deleteOldFile(personne.getPhoto());

            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            personne.setPhoto(baseUrl + "/uploads/personnes/" + filename);
            return personneRepository.save(personne);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'enregistrement du fichier");
        }
    }

    public Personne deletePhoto(Long id) {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable"));

        deleteOldFile(personne.getPhoto());
        personne.setPhoto(null);
        return personneRepository.save(personne);
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fichier vide ou absent");
        }
        String ct = file.getContentType();
        if (ct == null || !ct.startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le fichier doit être une image");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La taille maximale est de 5 Mo");
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "jpg";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    private void deleteOldFile(String photoUrl) {
        if (photoUrl == null || photoUrl.isBlank()) return;
        if (photoUrl.startsWith("/avatars/")) return;
        try {
            String prefix = baseUrl + "/uploads/personnes/";
            if (photoUrl.startsWith(prefix)) {
                String filename = photoUrl.substring(prefix.length());
                Path old = Paths.get(uploadDir, filename);
                Files.deleteIfExists(old);
            }
        } catch (IOException ignored) {
        }
    }
}
