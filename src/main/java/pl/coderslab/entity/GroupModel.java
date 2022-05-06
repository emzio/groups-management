package pl.coderslab.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.MERGE)
    private List<User> users = new ArrayList<>();

    @NotBlank
    private String name;

    private DayOfWeek dayOfWeek;


    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime localTime;

    @Digits(integer = 2, fraction = 0)
    @Min(value = 1)

    private Integer size;

    @Digits(integer = 8, fraction = 2)
    @Min(value = 0)
    private BigDecimal paymentRate;

    @Override
    public String toString() {
        return "GroupModel{" +
                "size=" + size +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", localTime=" + localTime +
                ", paymentRate=" + paymentRate +
                '}';
    }

    public BigDecimal getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(BigDecimal paymentRate) {
        this.paymentRate = paymentRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Long> getUserListId() {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
