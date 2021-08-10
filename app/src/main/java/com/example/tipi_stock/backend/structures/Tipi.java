package com.example.tipi_stock.backend.structures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class for modelling a Tipi structure
 */
public class Tipi {

    private int legQuantity;
    private int mediumGroundStakeQuantity;
    private int largeGroundStakeQuantity;
    private int fairyLightQuantity;
    private int clevisPinsQuantity;
    private String[] components;

    public Tipi() {
        components = new String[]{"Canvas", "8m Legs", "Large Ground Stakes", "Fairy Lights",
                "Chandelier", "Carpet Flooring", "Blanking Panels"};
        legQuantity = 8;
        mediumGroundStakeQuantity = 20;
        largeGroundStakeQuantity= 15;
        fairyLightQuantity = 5;
        clevisPinsQuantity = 20;
    }

    /**
     * Leg quantity accessor
     *
     * @return leg quantity per Tipi
     */
    public int getLegQuantity() {
        return legQuantity;
    }

    /**
     * Medium ground stake quantity accessor
     *
     * @return medium ground stake quantity per Tipi
     */
    public int getMediumGroundStakeQuantity() {
        return mediumGroundStakeQuantity;
    }

    /**
     * Large ground stake quantity accessor
     *
     * @return large ground stake quantity per Tipi
     */
    public int getLargeGroundStakeQuantity() {
        return largeGroundStakeQuantity;
    }

    /**
     * Fairy light quantity accessor
     *
     * @return fairy light quantity per Tipi
     */
    public int getFairyLightQuantity() {
        return fairyLightQuantity;
    }

    /**
     * Clevis pin quantity accessor
     *
     * @return clevis pin quantity per Tipi
     */
    public int getClevisPinsQuantity() {
        return clevisPinsQuantity;
    }

    /**
     * Components array accessor
     *
     * @return components array
     */
    public String[] getComponents() {
        return components;
    }

    /**
     * Overridden toString method, returning a string
     * representation of the class instance fields
     *
     * @return string representation of class
     */
    @NonNull
    @Override
    public String toString() {
        return "Tipi{" +
                "legQuantity=" + legQuantity +
                ", mediumGroundStakeQuantity=" + mediumGroundStakeQuantity +
                ", largeGroundStakeQuantity=" + largeGroundStakeQuantity +
                ", fairyLightQuantity=" + fairyLightQuantity +
                ", clevisPinsQuantity=" + clevisPinsQuantity +
                ", components=" + Arrays.toString(components) +
                '}';
    }

    /**
     * Override the equals method to return a boolean that
     * symbolises whether two objects are identical
     *
     * @param object object for comparison
     * @return boolean value representing whether the two objects are equal
     */
    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object object) {
        // If the object isn't even an instance of Tipi return false immediately
        if (!(object instanceof Tipi)) {
            return false;
        } else {
            Tipi tempTipi =  (Tipi) object;

            // Compare all of object properties
            return Objects.equals(this.clevisPinsQuantity, tempTipi.clevisPinsQuantity)
                    && Arrays.equals(this.components, tempTipi.components)
                    && Objects.equals(this.fairyLightQuantity, tempTipi.fairyLightQuantity)
                    && Objects.equals(this.largeGroundStakeQuantity, tempTipi.largeGroundStakeQuantity)
                    && Objects.equals(this.legQuantity, tempTipi.legQuantity)
                    && Objects.equals(this.mediumGroundStakeQuantity, tempTipi.mediumGroundStakeQuantity);
        }
    }

    /**
     * Override 'hashCode' for consistent storage in Hash date structures
     * @return hash of all object properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                this.components,
                this.clevisPinsQuantity,
                this.fairyLightQuantity,
                this.largeGroundStakeQuantity,
                this.legQuantity,
                this.mediumGroundStakeQuantity
        );
    }
}
