package com.example.pasir_kochanski_cezary.dto;

import com.example.pasir_kochanski_cezary.model.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDTO {

    @NotNull(message = "Kwota nie może być pusta")
    @Min(value = 1, message = "Kwota musi być większa niż 0")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Typ transakcji jest wymagany")
    @Pattern(regexp = "^(INCOME|EXPENSE)$", message = "Typ transakcji powinien mieć wartość Przychód lub Wydatek")
    private String type;

    @Size(max = 50, message = "Tagi nie mogą przekraczać 50 znaków")
    private String tags;

    @Size(max = 255, message = "Notatka może mieć maksymalnie 255 znaków")
    private String notes;

//    private LocalDateTime timestamp;
}
