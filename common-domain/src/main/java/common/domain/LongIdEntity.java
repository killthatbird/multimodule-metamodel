package common.domain;

import javax.persistence.*;

/**
 * @author Dmitry Tsydzik
 * @since Date: 25.01.14
 */
@MappedSuperclass
public abstract class LongIdEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
