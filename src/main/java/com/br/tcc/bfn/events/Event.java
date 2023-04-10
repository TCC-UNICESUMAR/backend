package com.br.tcc.bfn.events;

public record Event<T>(EventType type, T payload) {
}
