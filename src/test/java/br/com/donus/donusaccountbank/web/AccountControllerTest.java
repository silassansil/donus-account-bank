package br.com.donus.donusaccountbank.web;

import br.com.donus.donusaccountbank.MockFactory;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import br.com.donus.donusaccountbank.exception.UserAlreadyExistsException;
import br.com.donus.donusaccountbank.exception.UserNotFoundException;
import br.com.donus.donusaccountbank.exception.enums.ErrorKey;
import br.com.donus.donusaccountbank.service.AccountService;
import br.com.donus.donusaccountbank.web.dto.CustomerDTO;
import br.com.donus.donusaccountbank.web.dto.DepositAmountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    void openAccountWhenIsValidPayload() throws Exception {
        final CustomerDTO dto = MockFactory.buildCustomerDTO();
        final String json = this.objectMapper.writeValueAsString(dto);

        Mockito.when(this.accountService.openAccount(Mockito.any()))
                .thenReturn(MockFactory.buildAccount());

        this.mockMvc.perform(post("/accounts")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.name").value(dto.getName()))
                .andExpect(jsonPath("$.customer.cpf").value(new Cpf(dto.getCpf()).toString()))
                .andExpect(jsonPath("$.createdAt").value(LocalDate.now().toString()));

        Mockito.verify(this.accountService).openAccount(Mockito.any());
    }

    @Test
    void openAccountWhenUserAlreadyExists() throws Exception {
        final CustomerDTO dto = MockFactory.buildCustomerDTO();
        final String json = this.objectMapper.writeValueAsString(dto);

        Mockito.when(this.accountService.openAccount(Mockito.any()))
                .thenThrow(new UserAlreadyExistsException());

        this.mockMvc.perform(post("/accounts")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(ErrorKey.USER_ALREADY_EXISTS.toString()))
                .andExpect(jsonPath("$.message").value("user already exist for this cpf"));

        Mockito.verify(this.accountService).openAccount(Mockito.any());
    }

    @Test
    void openAccountWhenNameIsBlank() throws Exception {
        final CustomerDTO dto = MockFactory.buildCustomerDTO(null);
        final String json = this.objectMapper.writeValueAsString(dto);

        Mockito.when(this.accountService.openAccount(Mockito.any()))
                .thenReturn(MockFactory.buildAccount());

        this.mockMvc.perform(post("/accounts")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorKey.INVALID_DATA.toString()))
                .andExpect(jsonPath("$.message").value("name must not be blank"));
        Mockito.verifyNoInteractions(this.accountService);
    }

    @Test
    void testDepositWhenIsValidAllData() throws Exception {
        final DepositAmountDTO dto = MockFactory.buildDepositAmountDTO();
        final String json = this.objectMapper.writeValueAsString(dto);

        final BalanceProjection projection = MockFactory.buildBalanceProjection();
        Mockito.when(this.accountService.deposit(Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(projection);

        this.mockMvc.perform(put("/accounts/deposit")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(projection.getAmount()));

        Mockito.verify(this.accountService).deposit(Mockito.anyString(), Mockito.anyDouble());
    }

    @Test
    void testDepositWhenIsInvalidCpf() throws Exception {
        final DepositAmountDTO dto = MockFactory.buildDepositAmountDTO("", 100.0);
        final String json = this.objectMapper.writeValueAsString(dto);

        final BalanceProjection projection = MockFactory.buildBalanceProjection();
        Mockito.when(this.accountService.deposit(Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(projection);

        this.mockMvc.perform(put("/accounts/deposit")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorKey.INVALID_DATA.toString()))
                .andExpect(jsonPath("$.message").value("cpfOwner must not be blank"));

        Mockito.verifyNoInteractions(this.accountService);
    }

    @Test
    void testDepositWhenAmountIsMoreThan2000() throws Exception {
        final DepositAmountDTO dto = MockFactory.buildDepositAmountDTO(MockFactory.CPF_2, 10000.0);
        final String json = this.objectMapper.writeValueAsString(dto);

        final BalanceProjection projection = MockFactory.buildBalanceProjection();
        Mockito.when(this.accountService.deposit(Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(projection);

        this.mockMvc.perform(put("/accounts/deposit")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorKey.INVALID_DATA.toString()))
                .andExpect(jsonPath("$.message").value("amount must be less than or equal to 2000"));

        Mockito.verifyNoInteractions(this.accountService);
    }

    @Test
    void testDepositWhenAmountIsLessThan0() throws Exception {
        final DepositAmountDTO dto = MockFactory.buildDepositAmountDTO(MockFactory.CPF_2, -1000.0);
        final String json = this.objectMapper.writeValueAsString(dto);

        final BalanceProjection projection = MockFactory.buildBalanceProjection();
        Mockito.when(this.accountService.deposit(Mockito.anyString(), Mockito.anyDouble()))
                .thenReturn(projection);

        this.mockMvc.perform(put("/accounts/deposit")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorKey.INVALID_DATA.toString()))
                .andExpect(jsonPath("$.message").value("amount must be greater than or equal to 0"));

        Mockito.verifyNoInteractions(this.accountService);
    }

    @Test
    void testDepositWhenUserDoesNotExist() throws Exception {
        final DepositAmountDTO dto = MockFactory.buildDepositAmountDTO(MockFactory.CPF_2, 1000.0);
        final String json = this.objectMapper.writeValueAsString(dto);

        Mockito.when(this.accountService.deposit(Mockito.anyString(), Mockito.anyDouble()))
                .thenThrow(new UserNotFoundException());

        this.mockMvc.perform(put("/accounts/deposit")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ErrorKey.USER_NOT_FOUND.toString()))
                .andExpect(jsonPath("$.message").value("user not found in database"));

        Mockito.verify(this.accountService).deposit(Mockito.anyString(), Mockito.anyDouble());
    }
}
