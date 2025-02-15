package com.capgemini.bank.infrastructure.adapters;


import com.capgemini.bank.domain.Statement;
import com.capgemini.bank.domain.WithdrawCommand;
import com.capgemini.bank.domain.ports.WithdrawalUseCase;
import com.capgemini.bank.infrastructure.requests.WithdrawRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/withdraw")
public class WithdrawalController {

    private final WithdrawalUseCase withdrawalUseCase;


    public WithdrawalController(WithdrawalUseCase withdrawalUseCase) {
        this.withdrawalUseCase = withdrawalUseCase;
    }

    @PostMapping
    public ResponseEntity<Statement> withdraw(@RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(withdrawalUseCase.withdraw(WithdrawCommand.toCommand(request)));
    }


}
