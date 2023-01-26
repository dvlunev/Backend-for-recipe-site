package me.lunev.homework38.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Ingredient {

    private String name;
    private int amount;
    private String unitMeasurement;

    private static int number = 1;

    public Ingredient(String name, int amount, String unitMeasurement) {
        setName(name);
        setAmount(amount);
        setUnitMeasurement(unitMeasurement);
    }

    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            name = "Ингредиент " + number++;
        }
        this.name = name;
    }

    public void setAmount(int amount) {

        if (amount <= 0) {
            amount = 1;
        }
        this.amount = amount;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        if (StringUtils.isBlank(unitMeasurement)) {
            unitMeasurement = "По вкусу";
        }
        this.unitMeasurement = unitMeasurement;
    }

    @Override
    public String toString() {
        return "\n• " + name + " - " + amount + " " + unitMeasurement;
    }
}
