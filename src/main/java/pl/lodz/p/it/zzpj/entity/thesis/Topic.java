package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Topic.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_topic_id", unique = true),
        @Index(columnList = "name", name = Topic.IX_UQ_NAME, unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}, name = Topic.IX_UQ_NAME)
})
@Entity
@Data
public class Topic {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "topic";
    public static final String IX_UQ_NAME = "ix_uq_topic_name";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(nullable = false, updatable = false)
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "topicList", cascade = CascadeType.ALL)
    Set<Article> articleList = new HashSet<>();
}
