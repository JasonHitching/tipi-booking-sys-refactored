package com.example.tipi_stock.backend.structures;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class for modelling a marquee object
 */
public class Marquee {

    private final int legQuantity;
    private final int mediumGroundStakeQuantity;
    private final int largeGroundStakeQuantity;
    private final int fairyLightQuantity;
    private final int clevisPinsQuantity;
    private final int centerLegQuantity;
    private final String[] components;

    public Marquee() {
        components = new String[]{"Canvas", "5m Outside Legs", "20m Center Legs", "Ground Stakes",
                "Fairy Lights", "Chandelier", "Carpet Flooring", "Blanking Panels"};
        legQuantity = 12;
        mediumGroundStakeQuantity = 10;
        largeGroundStakeQuantity= 8;
        centerLegQuantity = 4;
        fairyLightQuantity = 15;
        clevisPinsQuantity = 500;
    }

    /**
     * Leg quantity accessor
     * @return leg quantity per Marquee
     */
    public int getLegQuantity() {
        return legQuantity;
    }

    /**
     * Medium ground stake quantity accessor
     * @return medium ground stake quantity per Marquee
     */
    public int getMediumGroundStakeQuantity() {
        return mediumGroundStakeQuantity;
    }

    /**
     * Large ground stake quantity accessor
     * @return large ground stake quantity per Marquee
     */
    public int getLargeGroundStakeQuantity() {
        return largeGroundStakeQuantity;
    }

    /**
     * Fairy light quantity accessor
     * @return fairy light quantity per Marquee
     */
    public int getFairyLightQuantity() {
        return fairyLightQuantity;
    }

    /**
     * Clevis pin quantity accessor
     * @return clevis pin quantity per Marquee
     */
    public int getClevisPinsQuantity() {
        return clevisPinsQuantity;
    }

    /**
     * Components array accessor
     * @return components array
     */
    public String[] getComponents() {
        return components;
    }

    /**
     * Center leg quantity accessor
     * @return center leg quantity per Marquee
     */
    public int getCenterLegQuantity() {
        return centerLegQuantity;
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
        if (!(object instanceof Marquee)) {
            return false;
        } else {
            Marquee tempMarquee =  (Marquee) object;

            // Compare all of object properties
            return Objects.equals(this.clevisPinsQuantity, tempMarquee.clevisPinsQuantity)
                    && Arrays.equals(this.components, tempMarquee.components)
                    && Objects.equals(this.fairyLightQuantity, tempMarquee.fairyLightQuantity)
                    && Objects.equals(this.largeGroundStakeQuantity, tempMarquee.largeGroundStakeQuantity)
                    && Objects.equals(this.legQuantity, tempMarquee.legQuantity)
                    && Objects.equals(this.mediumGroundStakeQuantity, tempMarquee.mediumGroundStakeQuantity)
                    && Objects.equals(this.centerLegQuantity, tempMarquee.centerLegQuantity);
        }
    }

    /**
     * Override 'hashCode' for consistent storage in Hash date structures
     *
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
                this.mediumGroundStakeQuantity,
                this.centerLegQuantity
        );
    }
}
