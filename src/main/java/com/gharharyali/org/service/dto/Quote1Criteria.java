package com.gharharyali.org.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.gharharyali.org.domain.Quote1} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.Quote1Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /quote-1-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Quote1Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter symbol;

    private BigDecimalFilter price;

    private ZonedDateTimeFilter lastTrade;

    public Quote1Criteria() {
    }

    public Quote1Criteria(Quote1Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.symbol = other.symbol == null ? null : other.symbol.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.lastTrade = other.lastTrade == null ? null : other.lastTrade.copy();
    }

    @Override
    public Quote1Criteria copy() {
        return new Quote1Criteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public ZonedDateTimeFilter getLastTrade() {
        return lastTrade;
    }

    public void setLastTrade(ZonedDateTimeFilter lastTrade) {
        this.lastTrade = lastTrade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Quote1Criteria that = (Quote1Criteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(price, that.price) &&
            Objects.equals(lastTrade, that.lastTrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        symbol,
        price,
        lastTrade
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote1Criteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (symbol != null ? "symbol=" + symbol + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (lastTrade != null ? "lastTrade=" + lastTrade + ", " : "") +
            "}";
    }

}
