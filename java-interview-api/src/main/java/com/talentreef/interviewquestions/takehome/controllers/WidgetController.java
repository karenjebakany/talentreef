package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(WidgetService widgetService) {
    Assert.notNull(widgetService, "Widget Service must not be null");
    this.widgetService = widgetService;
  }

  @GetMapping
  public ResponseEntity<List<Widget>> getAllWidgets() {
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping("/{name}")
    public ResponseEntity<Widget> getWidgetByName(@PathVariable String name) {
        Assert.notNull(name, "name must not be null");
        Widget widget = widgetService.getWidgetByName(name);
      return  ResponseEntity.ok(widget);
    }
    @PutMapping("/{name}")
    public ResponseEntity<Widget> updateWidget(@Valid @RequestBody Widget widget, @PathVariable String name) {
        Widget createdWidget = widgetService.updateWidget(name, widget);
        return ResponseEntity.ok(createdWidget);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteWidget(@PathVariable String name) {

      widgetService.deleteWidget(name);
      return ResponseEntity.noContent().build();

    }


    @PostMapping
    public ResponseEntity<Widget> createWidget(@Valid @RequestBody Widget widget) {
            Assert.notNull(widget, "widget must not be null");
            Widget createdWidget = widgetService.createWidget(widget);
            return ResponseEntity.ok(createdWidget);

    }
}
