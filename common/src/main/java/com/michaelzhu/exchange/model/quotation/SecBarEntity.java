package com.michaelzhu.exchange.model.quotation;

import com.michaelzhu.exchange.model.support.AbstractBarEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sec_bars")
public class SecBarEntity extends AbstractBarEntity {

}
