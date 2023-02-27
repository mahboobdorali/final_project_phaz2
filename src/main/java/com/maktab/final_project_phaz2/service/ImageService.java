package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Image;
import com.maktab.final_project_phaz2.date.repository.ImageRepository;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import lombok.RequiredArgsConstructor;
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
    public void uploadImage(MultipartFile file, Long idExpert) throws IOException {
        Expert expertById = expertService.findExpertById(idExpert);
        if ((!Objects.equals(file.getContentType(), "image/jpeg")) || file.getSize() > 300000)
            throw new RequestIsNotValidException("condition of image not safe");
        Image enterImage = imageRepository.save(Image.builder().type(file.getContentType()).imageData(file.getBytes()).build());
        expertById.setImage(enterImage);
        expertService.updateExpert(expertById);
    }

  /*  public Image findImageById(Long id) {
        return imageRepository.findById(id).
                orElseThrow(() -> new NoResultException("this image dose not exist"));
    }*/

    @Transactional
    public byte[] getImage(String emailAddress) {
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        return expertByEmail.getImage().getImageData();
    }
}
