package custom.domain;

import common.domain.LongIdEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * @author Dmitry Tsydzik
 * @since Date: 25.01.14
 */
@Entity
public class CustomEntity extends LongIdEntity {

    @ManyToOne
    private CustomEntity parent;

    @OneToMany(mappedBy = "parent")
    private Collection<CustomEntity> children;

    public CustomEntity getParent() {
        return parent;
    }

    public void setParent(CustomEntity parent) {
        this.parent = parent;
    }

    public Collection<CustomEntity> getChildren() {
        return children;
    }

    public void setChildren(Collection<CustomEntity> children) {
        this.children = children;
    }
}
