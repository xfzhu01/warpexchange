package com.michaelzhu.exchange.message;

import com.michaelzhu.exchange.model.quotation.TickEntity;

import java.util.List;

public class TickMessage extends AbstractMessage {

    public long sequenceId;

    public List<TickEntity> ticks;

}