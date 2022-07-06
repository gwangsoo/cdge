package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Meta정보
 */
@Entity
@Table(name = "tb_cdge_meta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Whether the station is currently in active use or not
     */
    @Column(name = "active")
    private Boolean active;

    /**
     * Who are available to see it and use it
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability;

    /**
     * State the station is currently
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChargerStatus status;

    @OneToMany(mappedBy = "meta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "meta" }, allowSetters = true)
    private Set<Socket> sockets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Meta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Meta active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Availability getAvailability() {
        return this.availability;
    }

    public Meta availability(Availability availability) {
        this.setAvailability(availability);
        return this;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public ChargerStatus getStatus() {
        return this.status;
    }

    public Meta status(ChargerStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }

    public Set<Socket> getSockets() {
        return this.sockets;
    }

    public void setSockets(Set<Socket> sockets) {
        if (this.sockets != null) {
            this.sockets.forEach(i -> i.setMeta(null));
        }
        if (sockets != null) {
            sockets.forEach(i -> i.setMeta(this));
        }
        this.sockets = sockets;
    }

    public Meta sockets(Set<Socket> sockets) {
        this.setSockets(sockets);
        return this;
    }

    public Meta addSocket(Socket socket) {
        this.sockets.add(socket);
        socket.setMeta(this);
        return this;
    }

    public Meta removeSocket(Socket socket) {
        this.sockets.remove(socket);
        socket.setMeta(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meta)) {
            return false;
        }
        return id != null && id.equals(((Meta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meta{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
