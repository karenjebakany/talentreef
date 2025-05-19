package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.takehome.exception.ResourceNotFoundException;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
public class WidgetService {

  private final WidgetRepository widgetRepository;

  @Autowired
  private WidgetService(WidgetRepository widgetRepository) {
    Assert.notNull(widgetRepository, "widgetRepository must not be null");
    this.widgetRepository = widgetRepository;
  }

  public List<Widget> getAllWidgets() {
    // Check if the widget list is empty
    if (widgetRepository.findAll().isEmpty()) {
      throw new ResourceNotFoundException("No widgets found");
    }
    return widgetRepository.findAll();
  }
  public Widget getWidgetByName(String name) {
    return widgetRepository.findById(name).orElseThrow(() -> new ResourceNotFoundException("Widget not found with name: " + name));
  }
    public Widget createWidget(Widget widget) {
        return widgetRepository.save(widget);
    }
    public Widget updateWidget(String name, Widget widget) {
    // Check if the widget exists before attempting to update
    if (widgetRepository.findById(name).isEmpty()) {
        throw new ResourceNotFoundException("Widget not found with name: " + name);
    }

        return widgetRepository.updateWidget(name,widget);
    }
    public void deleteWidget(String name) {
        // Check if the widget exists before attempting to delete
        if (widgetRepository.findById(name).isEmpty()) {
            throw new ResourceNotFoundException("Widget not found with name: " + name);
        }
        widgetRepository.deleteById(name);
    }

}
