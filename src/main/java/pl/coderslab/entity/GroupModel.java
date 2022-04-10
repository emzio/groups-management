package pl.coderslab.entity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "other")
public class GroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "groups")
    private List<Customer> customers = new ArrayList<>();

    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime localTime;

    @ManyToMany
    @JoinTable(name = "GroupModel_canceledClasses",
            joinColumns = @JoinColumn(name = "GroupModel_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "canceledClasses_id", referencedColumnName = "id"))
    private List<CanceledClasses> canceledClasses = new ArrayList<>();

    public List<CanceledClasses> getCanceledClasses() {
        return canceledClasses;
    }


    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", localTime=" + localTime +
                '}';
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
