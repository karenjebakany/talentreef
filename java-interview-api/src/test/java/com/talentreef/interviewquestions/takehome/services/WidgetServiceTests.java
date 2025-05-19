package com.talentreef.interviewquestions.takehome.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
public class WidgetServiceTests {

  @Mock
  private WidgetRepository widgetRepository;

  @InjectMocks
  private WidgetService widgetService;

  @Test
  public void when_getAllWidgets_expect_findAllResult() throws Exception {
    Widget widget = Widget.builder().name("Widgette Nielson").build();
    List<Widget> response = List.of(widget);
    when(widgetRepository.findAll()).thenReturn(response);

    List<Widget> result = widgetService.getAllWidgets();

    assertThat(result).isEqualTo(response);
  }

    @Test
    public void when_getWidgetByName_expect_findByIdResult() throws Exception {
        Widget widget = Widget.builder().name("Widgette Nielson").build();
        when(widgetRepository.findById("Widgette Nielson")).thenReturn(java.util.Optional.of(widget));

        Widget result = widgetService.getWidgetByName("Widgette Nielson");

        assertThat(result).isEqualTo(widget);
    }
    @Test
    public void when_createWidget_expect_saveResult() throws Exception {
        Widget widget = Widget.builder().name("Widgette Nielson").build();
        when(widgetRepository.save(widget)).thenReturn(widget);
        Widget result = widgetService.createWidget(widget);
        assertThat(result).isEqualTo(widget);
    }

    @Test

    public void when_createWidget_expect_saveResult_fail() throws Exception {
        Widget widget = Widget.builder().name("Widgette Nielson").description("abc").price(new BigDecimal("45.55")).build();
        when(widgetRepository.save(widget)).thenThrow(new IllegalArgumentException("Widget Description cannot be less than 5 characters"));
    }
    @Test
    public void when_updateWidget_expect_updateResult() throws Exception {
        Widget widget = Widget.builder().name("Widgette Nielson").price(new BigDecimal("45.67")).build();
        // save the widget first
        when(widgetRepository.save(widget)).thenReturn(widget);
        // then update the widget
        Widget w = Widget.builder().name("Widgette Nielson").description("abc").price(new BigDecimal("45.55")).build();
        when(widgetRepository.updateWidget("Widgette Nielson", w)).thenReturn(w);



    }




}
