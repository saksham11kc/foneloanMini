//package com.foneloanLite;
//
//import com.foneloanLite.entity.Manager;
//import com.foneloanLite.entity.SupportAgent;
//import com.foneloanLite.repository.ManagerRepository;
//import com.foneloanLite.repository.SupportAgentRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final ManagerRepository managerRepository;
//    private final SupportAgentRepository supportAgentRepository;
//
//    public DataInitializer(ManagerRepository managerRepository,
//                           SupportAgentRepository supportAgentRepository) {
//        this.managerRepository = managerRepository;
//        this.supportAgentRepository = supportAgentRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        //For manager
//        String managerEmail = "Manager123@gmail.com";
//        if (!managerRepository.existsById(managerEmail)) {
//            Manager manager = new Manager(
//                    managerEmail,
//                    "ManagerFname",
//                    "ManagerLname",
//                    LocalDate.of(1995, 5, 5),
//                    "Manager123"
//            );
//            managerRepository.save(manager);
//            System.out.println("Manager account created.");
//        }
//
 //         //For support agent
//        String agentEmail = "Hridesh@gmail.com";
//        if (!supportAgentRepository.existsById(agentEmail)) {
//            SupportAgent agent = new SupportAgent(
//                    agentEmail,
//                    "Hridesh",
//                    "Sapkota",
//                    LocalDate.of(1990, 1, 1),
//                    "Hridesh123"
//            );
//            supportAgentRepository.save(agent);
//            System.out.println("Support agent account created.");
//        }
//    }
//}
