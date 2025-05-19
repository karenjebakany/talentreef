package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.talentreef.interviewquestions.takehome.customannotations.TwoDecimalPlaces;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Table
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder(toBuilder=true)
public class Widget {
  @Id
  @NotBlank(message="Name is required and Name must be between 3 to 100 chars")
  @Size(min = 3, max=100)
  private String name;
  @Size(min = 5, max=1000, message = "Description must be between 5 to 1000 chars")
  private String description;
  @NotNull(message = "Amount is required")
  @TwoDecimalPlaces
  private BigDecimal price;

}
