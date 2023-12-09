package ru.skypro.socks_app.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.socks_app.controller.SockController;
import ru.skypro.socks_app.service.SockService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SockController.class)
@ActiveProfiles("test")

public class SockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SockService socksService;

    @Autowired
    ObjectMapper mapper;
    private String URI="/api/socks";

    @ParameterizedTest
    @CsvSource(value = {
            "white,80,120",
            "green,65,230"
    })
    public void incomeTest1(String color,int cottonPart, int quantity) throws Exception {
         mockMvc.perform(MockMvcRequestBuilders
                        .post(URI+"/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSockJson(color,cottonPart,quantity).toString())
                        .accept(MediaType.APPLICATION_JSON))
                 .andDo(print())
                 .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @ParameterizedTest
    @CsvSource(value = {
            "white,110,120",
            "green,-65,230"
    })
    public void incomeTest2(String color,int cottonPart, int quantity) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI+"/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSockJson(color,cottonPart,quantity).toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @ParameterizedTest
    @CsvSource(value = {
            "blue, 80, 250",
            "orange, 75, 130"
    })
    public void outcomeTest1(String color,int cottonPart, int quantity) throws Exception {
       mockMvc.perform(MockMvcRequestBuilders
                        .post(URI+"/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSockJson(color,cottonPart,quantity).toString())
                        .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @ParameterizedTest
    @CsvSource(value = {
            "yellow, 125, -70",
            "black, -60, 800"
    })
    public void outcomeTest2(String color,int cottonPart, int quantity) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI+"/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSockJson(color,cottonPart,quantity).toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @ParameterizedTest
    @CsvSource(value = {
            "white, equal, 80",
            "red, moreThan, 40",
            "orange, lessThan, 95"
    })

    public void getQuantityTest(String color, String operation, int cottonPart) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .queryParam("color", color)
                        .queryParam("operation", operation)
                        .queryParam("cottonPart", String.valueOf(cottonPart)))
                .andExpect(status().isOk());

    }
    @ParameterizedTest
    @CsvSource({
            "red, equal, -20",
            "blue, moreThan, 110",
            "green, lessThan, 5"
    })

    public void getQuantityNegativeTest(String color, String operation, int cottonPart) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .queryParam("color", color)
                        .queryParam("operation", operation)
                        .queryParam("cottonPart", String.valueOf(cottonPart)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void colorTest() throws Exception {
        JSONObject sockJson = createCountJson(null, "equal", 70);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sockJson.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    @Test
    public void operationTest() throws Exception {
        JSONObject sockJson = createCountJson("white", null, 85);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sockJson.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    private static JSONObject createSockJson(String color, int cottonPart, int quantity) throws JSONException {
        JSONObject sockJson = new JSONObject();
        sockJson.put("color", color);
        sockJson.put("cottonPart", cottonPart);
        sockJson.put("quantity", quantity);
        return sockJson;
    }

    private static JSONObject createCountJson(String color, String operation, int quantity) throws JSONException{
        JSONObject sockJson = new JSONObject();
        sockJson.put("color", color);
        sockJson.put("cottonPart", operation);
        sockJson.put("quantity", quantity);
        return sockJson;
    }

}