package com.michaelzhu.exchange.model.quotation;

import com.michaelzhu.exchange.model.support.EntitySupport;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ticks", uniqueConstraints = @UniqueConstraint(name = "UNI_T_M", columnNames = {
        "takerOrderId", "makerOrderId"}), indexes = @Index(name = "IDX_CAT", columnList = "createdAt"))
public class TickEntity implements EntitySupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    public long id;

    @Column(nullable = false, updatable = false)
    public long sequenceId;

    @Column(nullable = false, updatable = false)
    public Long takerOrderId;

    @Column(nullable = false, updatable = false)
    public Long makerOrderId;

    @Column(nullable = false, updatable = false)
    public boolean takerDirection;

    @Column(nullable = false, updatable = false, precision = PRECISION, scale = SCALE)
    public BigDecimal price;

    public BigDecimal quantity;

    public long createdAt;

    public String toJson() {
        return "[" + createdAt + "," + (takerDirection ? 1 : 0) + "," + price + "," + quantity + "]";
    }
}
