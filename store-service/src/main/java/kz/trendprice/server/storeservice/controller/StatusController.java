package kz.trendprice.server.storeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/status")
public class StatusController {
    @GetMapping("/isAlive")
    public ResponseEntity<Boolean> isAlive() {
        return ResponseEntity.ok(true);
    }
}
