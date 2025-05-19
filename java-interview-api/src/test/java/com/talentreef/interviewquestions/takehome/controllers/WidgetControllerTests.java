package com.talentreef.interviewquestions.takehome.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerTests {

  final private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @Mock
  private WidgetService widgetService;

  @InjectMocks
  private WidgetController widgetController;

  @Before
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(widgetController).build();
  }

  @Test
  public void when_getAllWidgets_expect_allWidgets() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    List<Widget> allWidgets = List.of(widget);
    when(widgetService.getAllWidgets()).thenReturn(allWidgets);

    MvcResult result = mockMvc.perform(get("/v1/widgets"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    List<Widget> parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Widget>>(){});
    assertThat(parsedResult).isEqualTo(allWidgets);
  }

  @Test
  public void when_getWidgetByName_expect_widget() throws Exception {
    Widget widget = Widget.builder().name("Widget von Hammersmark").build();
    when(widgetService.getWidgetByName("Widget von Hammersmark")).thenReturn(widget);

    MvcResult result = mockMvc.perform(get("/v1/widgets/Widget von Hammersmark"))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();

    Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<Widget>(){});
    assertThat(parsedResult).isEqualTo(widget);
  }

    @Test
    public void when_createWidget_expect_widget() throws Exception {
      Widget widget = Widget.builder().name("Hammersmark").description("Expression Widget Hammersmark").price(new BigDecimal("44.55")).build();

      when(widgetService.createWidget(any(Widget.class))).thenReturn(widget);
      //post call
      MvcResult result = mockMvc.perform(post("/v1/widgets")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(widget)))
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name").value("Hammersmark"))
              .andExpect(jsonPath("$.description").value("Expression Widget Hammersmark"))
              .andExpect(jsonPath("$.price").value(44.55)).andReturn();


      Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
              new TypeReference<Widget>(){});
      assertThat(parsedResult.getName()).isEqualTo(widget.getName());
    }

    @Test
    public void when_updateWidget_expect_widget() throws Exception {
      Widget widget = Widget.builder().name("Hammersmark").description("Expression Widget Hammersmark").price(new BigDecimal("44.66")).build();
      when(widgetService.updateWidget(any(String.class), any(Widget.class))).thenReturn(widget);

      MvcResult result = mockMvc.perform(put("/v1/widgets/Hammersmark")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(widget)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Expression Widget Hammersmark"))
                .andExpect(jsonPath("$.price").value(44.66)).andReturn();


      Widget parsedResult = objectMapper.readValue(result.getResponse().getContentAsString(),
              new TypeReference<Widget>() {
              });
      assertThat(parsedResult.getPrice()).isEqualTo(new BigDecimal("44.66"));
    }

    @Test
    public void when_deleteWidget_expect_noContent() throws Exception {
        mockMvc.perform(delete("/v1/widgets/Hammersmark"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }



}
