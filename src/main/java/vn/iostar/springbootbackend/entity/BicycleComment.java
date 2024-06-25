package vn.iostar.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bicycle_comments")
public class BicycleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicycle_comment")
    private Long idBicycleComment;

    @Column(name = "content", columnDefinition = "nvarchar(10000)")
    private String content;

    @Column(name = "day_commented")
    private LocalDateTime dayCommented;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bicycle", referencedColumnName = "id_bicycle")
    private Bicycle bicycle;
}
