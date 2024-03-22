package vn.iostar.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.response.ResponseHandler;
import vn.iostar.springbootbackend.service.ISongLikedService;

@RestController
@RequestMapping("/api/v1/songLiked")
public class SongLikedController {

    @Autowired
    private ISongLikedService songLikedService;

    @GetMapping("/likedCountById/{songId}")
    public ResponseEntity<?> LikeCountById(@PathVariable("songId") Long songId) {
        return ResponseEntity.ok(songLikedService.countLikesBySongId(songId));
    }

    @GetMapping("/isUserLikedSong/songId={songId}&userId={userId}")
    public ResponseEntity<?> isUserLikedSong(@PathVariable("songId") Long songId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(songLikedService.isUserLikedSong(songId, userId));
    }

    @PostMapping("/toggleLike/songId={songId}&userId={userId}")
    public ResponseEntity<?> toggleLike(@PathVariable("songId") Long songId, @PathVariable("userId") Long userId) {
        songLikedService.toggleLike(songId, userId);
        boolean isLiked = songLikedService.isUserLikedSong(songId, userId);
        return ResponseEntity.ok(isLiked);
    }


}