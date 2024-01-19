package com.example.commandeservice.Controller;


import com.example.commandeservice.Configuration.ApplicationPropertiesConfiguration;
import com.example.commandeservice.Dao.CommandeDao;
import com.example.commandeservice.Model.Commande;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;



@Configuration
@EnableHystrixDashboard
@RestController

public class CommandeController implements HealthIndicator {

            private final ApplicationPropertiesConfiguration appConfig ;
            private final CommandeDao commandeDao ;

    @Autowired
    public CommandeController(ApplicationPropertiesConfiguration appConfig, CommandeDao commandeDao) {
        this.appConfig = appConfig;
        this.commandeDao = commandeDao;
    }





    @GetMapping("/myMessage")

    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage",
            commandProperties ={@HystrixProperty(name =
                    "execution.isolation.thread.timeoutInMilliseconds", value = "1000")},
            threadPoolKey = "messageThreadPool")

    public String getMessage() throws InterruptedException {
        System.out.println("Message from EmployeeController.getMessage():
                Begin To sleep for 3 scondes ");
        Thread.sleep(3000);
        return "Message from EmployeeController.getMessage(): End from sleep for 3 scondes ";
    }
    private String myHistrixbuildFallbackMessage() {
        return "Message from myHistrixbuildFallbackMessage() : Hystrix Fallback message ( after timeout : 1 second )";
    }


    @GetMapping("/Commandes")

 public List<Commande>getLastCommande(){



     List<Commande> dernieresCommandes = commandeDao.findAll().stream()
             .sorted(Comparator.comparing(Commande::getDate).reversed())
             .limit(appConfig.getcommandeslast())
             .collect(Collectors.toList());
     System.out.println(appConfig.getcommandeslast());

     return dernieresCommandes;



 }

    @GetMapping("/Commandeslast")

    public List<Commande>last(){

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(appConfig.getcommandeslast());


        List<Commande> commandesLast20Days = new ArrayList<>();
        List <Commande> allCommandes = commandeDao.findAll();

        for (Commande commande : allCommandes) {
            LocalDate dateCommande = commande.getDate();
            if (dateCommande.isAfter(startDate) || dateCommande.isEqual(startDate) &&
                    (dateCommande.isBefore(currentDate) || dateCommande.isEqual(currentDate))) {
                commandesLast20Days.add(commande);
            }
        }

return commandesLast20Days ;

    }





    @Override
    public Health health() {
        List<Commande> products = commandeDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }


    @PostMapping("/Commandes")
    public Commande addCommande(@RequestBody Commande commande) {
        return commandeDao.save(commande);
    }

    @GetMapping("/Commandes/{id}")
    public Commande getCommandeById(@PathVariable int id) {
        return commandeDao.findById(id).orElse(null);
    }

    @PutMapping("/Commandes/{id}")
    public Commande updateCommande(@PathVariable int id, @RequestBody Commande updatedCommande) {
        Commande existingCommande = commandeDao.findById(id).orElse(null);

        if (existingCommande != null) {
//            existingCommande.setMontant();
            return commandeDao.save(existingCommande);
        }

        return null;
    }

    @DeleteMapping("/Commandes/{id}")
    public void deleteCommande(@PathVariable int id) {
        commandeDao.deleteById(id);
    }

    }

