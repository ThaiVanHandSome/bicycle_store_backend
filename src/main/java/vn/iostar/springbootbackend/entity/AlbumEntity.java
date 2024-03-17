package vn.iostar.springbootbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class AlbumEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_album;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(1000)")
    private String name;

    @Column(name = "day_created")
    private LocalDateTime day_created;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(1000)")
    private String image;

    @OneToMany(mappedBy = "album")
    private List<SongEntity> songs;

    @ManyToOne
    @JoinColumn(name = "id_artist", referencedColumnName = "id_artist")
    private ArtistEntity artist;
}