//package pl.coderslab.service;
//
//import org.hibernate.Hibernate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import pl.coderslab.entity.Customer;
//import pl.coderslab.repository.CustomerRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class DBCustomerService implements CustomerService{
//    private final CustomerRepository customerRepository;
//
//    public DBCustomerService(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    public List<Customer> findAll(){
//        return customerRepository.findAll();
//    }
//
//    public Optional<Customer> findById(Long id){
//        return customerRepository.findById(id);
//    }
//
//    public void save(Customer customer){
//        customerRepository.save(customer);
//    }
//
//    public void deleteById(Long id){
//        customerRepository.deleteById(id);
//    }
//
//    public void update(Customer customer){
//        customerRepository.save(customer);
//    }
//
//    public Customer findByIdWithGroups(Long id){
//        Customer customer = customerRepository.findById(id).get();
//        Hibernate.initialize(customer.getGroups());
//        return customer;
//    }
//}
