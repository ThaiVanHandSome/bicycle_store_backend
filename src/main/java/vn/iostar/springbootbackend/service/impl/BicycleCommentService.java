package vn.iostar.springbootbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleComment;
import vn.iostar.springbootbackend.model.BicycleCommentResponseModel;
import vn.iostar.springbootbackend.model.OrderDetailModel;
import vn.iostar.springbootbackend.repository.BicycleCommentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BicycleCommentService {
    @Autowired
    private BicycleCommentRepository bicycleCommentRepository;

    @Autowired
    private BicycleService bicycleService;

    public void saveComment(BicycleComment bicycleComment) {
        bicycleCommentRepository.save(bicycleComment);
    }

    public Page<BicycleCommentResponseModel> getAllComments(Long idBicycle, int page, int size) {
        Bicycle bicycle = bicycleService.getBicycleById(idBicycle).get();
        List<BicycleComment> bicycleComments = bicycleCommentRepository.findByBicycle(bicycle);
        List<BicycleCommentResponseModel> bicycleCommentResponseModels = new ArrayList<>();
        for (BicycleComment bicycleComment : bicycleComments) {
            BicycleCommentResponseModel bicycleCommentResponseModel = BicycleCommentResponseModel.builder()
                    .avatar(bicycleComment.getUser().getAvatar())
                    .fullName(bicycleComment.getUser().getFirstName() + " " + bicycleComment.getUser().getLastName())
                    .createAt(bicycleComment.getDayCommented())
                    .content(bicycleComment.getContent())
                    .build();
            bicycleCommentResponseModels.add(bicycleCommentResponseModel);
        }
        Collections.reverse(bicycleCommentResponseModels);
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = pageable.getOffset() + pageable.getPageSize() > bicycleCommentResponseModels.size() ? bicycleCommentResponseModels.size() : (int) pageable.getOffset() + pageable.getPageSize();
        List<BicycleCommentResponseModel> newBicycleCommentResponseModels = bicycleCommentResponseModels.subList(start, end);
        return new PageImpl<BicycleCommentResponseModel>(newBicycleCommentResponseModels, pageable, bicycleCommentResponseModels.size());
    }
}
