package vn.iostar.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.entity.Bicycle;
import vn.iostar.springbootbackend.entity.BicycleComment;
import vn.iostar.springbootbackend.entity.User;
import vn.iostar.springbootbackend.model.BicycleCommentRequestModel;
import vn.iostar.springbootbackend.model.BicycleCommentResponseModel;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.BicycleCommentService;
import vn.iostar.springbootbackend.service.impl.BicycleService;
import vn.iostar.springbootbackend.service.impl.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BicycleCommentController {
    @Autowired
    private BicycleCommentService bicycleCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private BicycleService bicycleService;

    @PostMapping("/comment/add")
    public ResponseEntity<?> addComment(@RequestBody BicycleCommentRequestModel req) {
        BaseResponse response = new BaseResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email).get();
            Optional<Bicycle> optBicycle = bicycleService.getBicycleById(req.getIdBicycle());
            if(optBicycle.isPresent()) {
                BicycleComment bicycleComment = BicycleComment.builder()
                        .bicycle(optBicycle.get())
                        .content(req.getContent())
                        .dayCommented(LocalDateTime.now())
                        .user(user)
                        .build();
                bicycleCommentService.saveComment(bicycleComment);
                response = BaseResponse.builder().code(200).status("success").message("Comment added successfully!").build();
                return ResponseEntity.ok(response);
            }
            response = BaseResponse.builder().code(400).status("error").message("Bicycle not found!").build();
            return ResponseEntity.ok(response);
        }
        response = BaseResponse.builder().code(400).status("error").message("User not found!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycle/{idBicycle}/comments")
    public ResponseEntity<?> getAllCommentsOfBicycle(@PathVariable Long idBicycle, @RequestParam("page") int page, @RequestParam("size") int size) {
        Page<BicycleCommentResponseModel> bicycleCommentResponseModels = bicycleCommentService.getAllComments(idBicycle, page, size);
        BaseResponse response = BaseResponse.builder().code(200).status("success").message("Get all comments successfully!").data(bicycleCommentResponseModels).build();
        return ResponseEntity.ok(response);
    }
}
