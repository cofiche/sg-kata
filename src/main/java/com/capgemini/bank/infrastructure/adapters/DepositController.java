package com.capgemini.bank.infrastructure.adapters;


import com.capgemini.bank.domain.DepositCommand;
import com.capgemini.bank.domain.Statement;
import com.capgemini.bank.domain.ports.DepositUseCase;
import com.capgemini.bank.infrastructure.requests.DepositRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    private final DepositUseCase depositUseCase;

    public DepositController(DepositUseCase depositUseCase) {
        this.depositUseCase = depositUseCase;
    }


    @PostMapping
    public ResponseEntity<Statement> deposit(@RequestBody DepositRequest request) {
        return ResponseEntity.ok(depositUseCase.deposit(DepositCommand.toCommand(request)));
    }


}
