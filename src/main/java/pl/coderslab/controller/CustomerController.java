//package pl.coderslab.controller;
//
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import pl.coderslab.entity.Customer;
//import pl.coderslab.entity.GroupModel;
//import pl.coderslab.service.CustomerService;
//import pl.coderslab.service.DBGroupService;
//import pl.coderslab.service.GroupService;
//
//@Controller
////@RequestMapping("/data/customers")
//@RequestMapping("/admin/customers")
//public class CustomerController {
//    private final CustomerService customerService;
//    private final DBGroupService dbGroupService;
//
//    public CustomerController(CustomerService customerService, DBGroupService dbGroupService) {
//        this.customerService = customerService;
//        this.dbGroupService = dbGroupService;
//    }
//
//    @GetMapping("")
//    @ResponseBody
//    private String findAll(){
//        return customerService.findAll().toString();
//    }
//
//    @GetMapping("/add")
//    @ResponseBody
//    private String add(){
//        Customer customer = new Customer();
//        customer.setEmail("email@domain.pl");
//        customer.setName("Name");
//        customer.setLastName("Lastname");
////        GroupModel groupModel = dbGroupService.findById(2l).get();
////        customer.getGroups().add(groupModel);
//        customerService.save(customer);
//        return customer.toString();
//    }
//
//}
