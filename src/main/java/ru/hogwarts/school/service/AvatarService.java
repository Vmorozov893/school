package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;

    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            logger.error("Student with ID: {} not found.", studentId);
            return new EntityNotFoundException("Student not found");
        });;
        try {
        Path filePath = saveToDisc(studentId,avatarFile);
        saveToDB(student,filePath,avatarFile);
            logger.info("Avatar uploaded successfully for student with ID: {}", studentId);
        } catch (IOException e) {
            logger.error("Error occurred while uploading avatar for student with ID: {}. Error: {}", studentId, e.getMessage());
            throw e;
        }
    }

    private Path saveToDisc(Long studentId,MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for save avatar to disc");
        Path filePath = Path.of(avatarsDir, "avatar" + studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            logger.error("Error saving avatar: " + e.getMessage(), e);
            throw e;
        }
        logger.info("Avatar successfully saved: " + filePath);
        return filePath;
    }

    private void saveToDB(Student student, Path filePath, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for save avatar to DataBase");
        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        try {
            avatarRepository.save(avatar);
            logger.info("Аватар успешно сохранен для студента с ID: " + student.getId());
        } catch (Exception e) {
            logger.error("Ошибка при сохранении аватара в базу данных: " + e.getMessage(), e);
            throw e;
        }
    }

    public Avatar findAvatar(Long studentId){
        Optional<Avatar> avatarOptional = avatarRepository.findByStudent_id(studentId);

        if (avatarOptional.isPresent()) {
            logger.info("Avatar found for student ID: {}", studentId);
            return avatarOptional.get();
        } else {
            logger.warn("Avatar not found for student ID: {}", studentId);
            return new Avatar();
        }
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for get extensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getPaginatedAvatars(int pageNumber,int pageSize){
        logger.info("Was invoked method for get paginated avatars");
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        Page<Avatar> avatarPage = avatarRepository.findAll(pageable);
        return avatarPage.getContent();
    }
}
