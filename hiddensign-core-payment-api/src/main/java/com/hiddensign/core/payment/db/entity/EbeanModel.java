package com.hiddensign.core.payment.db.entity;

import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import com.hiddensign.core.common.data.BaseModel;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Objects;

/**
 * Created by Nikolay on 27-Jul-15.
 */
@MappedSuperclass
public class EbeanModel implements BaseModel<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @WhenCreated
    @Column(name = "creation_date", nullable = false, updatable = false, insertable = true)
    protected DateTime creationDate;

    @WhenModified
    @Column(name = "last_update", nullable = false)
    protected DateTime lastUpdate;

    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(DateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EbeanModel)) {
            return false;
        }
        EbeanModel ebeanModel = (EbeanModel) o;
        return Objects.equals(id, ebeanModel.id) &&
                Objects.equals(creationDate, ebeanModel.creationDate) &&
                Objects.equals(lastUpdate, ebeanModel.lastUpdate) &&
                Objects.equals(version, ebeanModel.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, lastUpdate, version);
    }

    @Override
    public String toString() {
        return "EbeanModel{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", lastUpdate=" + lastUpdate +
                ", version=" + version +
                "} " + super.toString();
    }
}
