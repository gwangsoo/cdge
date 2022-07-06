package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.Provider;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전소
 */
@Entity
@Table(name = "tb_cdge_station")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 판매자ID
     */
    @Column(name = "seller_id")
    private Integer sellerId;

    /**
     * 충전소명
     */
    @Size(max = 256)
    @Column(name = "name", length = 256)
    private String name;

    /**
     * 위도
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * 경도
     */
    @Column(name = "longitide")
    private Double longitide;

    /**
     * icon ID
     */
    @Column(name = "icon")
    private Integer icon;

    /**
     * 주소
     */
    @Size(max = 256)
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 도시
     */
    @Size(max = 128)
    @Column(name = "city", length = 128)
    private String city;

    /**
     * 운영시간
     */
    @Size(max = 64)
    @Column(name = "open_hours", length = 64)
    private String openHours;

    /**
     * 제공자
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    /**
     * 알림메세지
     */
    @Size(max = 256)
    @Column(name = "alert_message", length = 256)
    private String alertMessage;

    /**
     * 이동여부
     */
    @Column(name = "is_removed")
    private Boolean isRemoved;

    /**
     * 사적이용여부
     */
    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToMany(mappedBy = "station")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "connectors", "pricings", "station" }, allowSetters = true)
    private Set<Evse> evses = new HashSet<>();

    @OneToMany(mappedBy = "station")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "meta", "station" }, allowSetters = true)
    private Set<Charger> chargers = new HashSet<>();

    @OneToMany(mappedBy = "station")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "station" }, allowSetters = true)
    private Set<Picture> pictures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Station id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return this.sellerId;
    }

    public Station sellerId(Integer sellerId) {
        this.setSellerId(sellerId);
        return this;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return this.name;
    }

    public Station name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Station latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitide() {
        return this.longitide;
    }

    public Station longitide(Double longitide) {
        this.setLongitide(longitide);
        return this;
    }

    public void setLongitide(Double longitide) {
        this.longitide = longitide;
    }

    public Integer getIcon() {
        return this.icon;
    }

    public Station icon(Integer icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return this.address;
    }

    public Station address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Station city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenHours() {
        return this.openHours;
    }

    public Station openHours(String openHours) {
        this.setOpenHours(openHours);
        return this;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public Station provider(Provider provider) {
        this.setProvider(provider);
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getAlertMessage() {
        return this.alertMessage;
    }

    public Station alertMessage(String alertMessage) {
        this.setAlertMessage(alertMessage);
        return this;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public Boolean getIsRemoved() {
        return this.isRemoved;
    }

    public Station isRemoved(Boolean isRemoved) {
        this.setIsRemoved(isRemoved);
        return this;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Boolean getIsPrivate() {
        return this.isPrivate;
    }

    public Station isPrivate(Boolean isPrivate) {
        this.setIsPrivate(isPrivate);
        return this;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<Evse> getEvses() {
        return this.evses;
    }

    public void setEvses(Set<Evse> evses) {
        if (this.evses != null) {
            this.evses.forEach(i -> i.setStation(null));
        }
        if (evses != null) {
            evses.forEach(i -> i.setStation(this));
        }
        this.evses = evses;
    }

    public Station evses(Set<Evse> evses) {
        this.setEvses(evses);
        return this;
    }

    public Station addEvse(Evse evse) {
        this.evses.add(evse);
        evse.setStation(this);
        return this;
    }

    public Station removeEvse(Evse evse) {
        this.evses.remove(evse);
        evse.setStation(null);
        return this;
    }

    public Set<Charger> getChargers() {
        return this.chargers;
    }

    public void setChargers(Set<Charger> chargers) {
        if (this.chargers != null) {
            this.chargers.forEach(i -> i.setStation(null));
        }
        if (chargers != null) {
            chargers.forEach(i -> i.setStation(this));
        }
        this.chargers = chargers;
    }

    public Station chargers(Set<Charger> chargers) {
        this.setChargers(chargers);
        return this;
    }

    public Station addCharger(Charger charger) {
        this.chargers.add(charger);
        charger.setStation(this);
        return this;
    }

    public Station removeCharger(Charger charger) {
        this.chargers.remove(charger);
        charger.setStation(null);
        return this;
    }

    public Set<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        if (this.pictures != null) {
            this.pictures.forEach(i -> i.setStation(null));
        }
        if (pictures != null) {
            pictures.forEach(i -> i.setStation(this));
        }
        this.pictures = pictures;
    }

    public Station pictures(Set<Picture> pictures) {
        this.setPictures(pictures);
        return this;
    }

    public Station addPicture(Picture picture) {
        this.pictures.add(picture);
        picture.setStation(this);
        return this;
    }

    public Station removePicture(Picture picture) {
        this.pictures.remove(picture);
        picture.setStation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        return id != null && id.equals(((Station) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", sellerId=" + getSellerId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitide=" + getLongitide() +
            ", icon=" + getIcon() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", openHours='" + getOpenHours() + "'" +
            ", provider='" + getProvider() + "'" +
            ", alertMessage='" + getAlertMessage() + "'" +
            ", isRemoved='" + getIsRemoved() + "'" +
            ", isPrivate='" + getIsPrivate() + "'" +
            "}";
    }
}
