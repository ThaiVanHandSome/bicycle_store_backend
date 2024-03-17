package vn.iostar.springbootbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class SongEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_song;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(1000)")
    private String name;

    private Long id_artist;

    @Column(name = "views")
    private int views;

    @Column(name = "resource", nullable = false, columnDefinition = "varchar(1000)")
    private String resource;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(1000)")
    private String image;

    @ManyToOne
    @JoinColumn(name = "id_album", referencedColumnName = "id_album")
    private AlbumEntity album;

    @OneToMany(mappedBy = "song")
    private List<PlaylistSongEntity> playlistSongs;

    @OneToMany(mappedBy = "song")
    private List<SongLikedEntity> songLikeds;

    @OneToMany(mappedBy = "song")
    private List<ArtistSongEntity> artistSongs;
}