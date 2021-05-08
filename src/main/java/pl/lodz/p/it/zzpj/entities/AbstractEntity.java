package pl.lodz.p.it.zzpj.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
public abstract class AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date")
    private Date modificationDate;

    @Version
    private Long version;

    @PrePersist
    private void prePersist() {
        creationDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    private void preUpdate() {
        modificationDate = new Date(System.currentTimeMillis());
    }

    public abstract Long getId();

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }
}
