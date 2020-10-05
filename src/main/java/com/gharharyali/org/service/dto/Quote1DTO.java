package com.gharharyali.org.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.gharharyali.org.domain.Quote1} entity.
 */
public class Quote1DTO implements Serializable {
    
    private Long id;

    @NotNull
    private String symbol;

    @NotNull
    private BigDecimal price;

    @NotNull
    private ZonedDateTime lastTrade;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getLastTrade() {
        return lastTrade;
    }

    public void setLastTrade(ZonedDateTime lastTrade) {
        this.lastTrade = lastTrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote1DTO)) {
            return false;
        }

        return id != null && id.equals(((Quote1DTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote1DTO{" +
            "id=" + getId() +
            ", symbol='" + getSymbol() + "'" +
            ", price=" + getPrice() +
            ", lastTrade='" + getLastTrade() + "'" +
            "}";
    }
}
