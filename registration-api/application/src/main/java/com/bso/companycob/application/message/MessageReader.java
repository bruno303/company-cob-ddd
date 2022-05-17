package com.bso.companycob.application.message;

import java.util.List;

public interface MessageReader<T> {
    List<T> read(String queue);
}
