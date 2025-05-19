package com.talentreef.interviewquestions.takehome.respositories;

import com.talentreef.interviewquestions.takehome.models.Widget;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WidgetRepository {

  private List<Widget> table = new ArrayList<>();

  public List<Widget> deleteById(String name) {
    this.table = table.stream()
                      .filter((Widget widget) -> !name.equals(widget.getName()))
                      .collect(Collectors.toCollection(ArrayList::new));
    return table;
  }

  public List<Widget> findAll() {
    return table;
  }

  public Optional<Widget> findById(String name) {
      return table.stream().filter((Widget widget) -> name.equals(widget.getName())).findAny();
  }

  public Widget save(Widget widget) throws IllegalArgumentException {

    Optional<Widget> existingWidget = findById(widget.getName());
    if (existingWidget.isPresent()) {
     throw new IllegalArgumentException("Widget with name " + widget.getName() + " already exists.");
    }
    table.add(widget);
    return widget;
  }


  public Widget updateWidget(String name,Widget widget) {
    Optional<Widget> existingWidget = findById(name);
    if(existingWidget.isPresent()){
        Widget updatedWidget = existingWidget.get().toBuilder()
                .description(widget.getDescription())
                .price(widget.getPrice())
                .build();
        table.remove(existingWidget.get());
        table.add(updatedWidget);
        return updatedWidget;
    }
    else {
      throw new IllegalArgumentException("Widget with name " + name + " does not exist.");
    }
  }

}
