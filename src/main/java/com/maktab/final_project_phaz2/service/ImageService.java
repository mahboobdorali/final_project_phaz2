package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Image;
import com.maktab.final_project_phaz2.date.model.Person;
import com.maktab.final_project_phaz2.date.repository.ImageRepository;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ExpertService expertService;

    @Transactional
    public void uploadImage(MultipartFile file) throws IOException {
        Expert expert = expertService.findExpertByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        if ((!Objects.equals(file.getContentType(), "image/jpeg")) || file.getSize() > 300000)
            throw new RequestIsNotValidException("condition of image not safe");
        Image enterImage = imageRepository.save(Image.builder().type(file.getContentType()).imageData(file.getBytes()).build());
        expert.setImage(enterImage);
        expertService.updateExpert(expert);
    }

    @Transactional
    public byte[] getImage() {
        Expert expert = expertService.findExpertByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        return expert.getImage().getImageData();

    }
}
