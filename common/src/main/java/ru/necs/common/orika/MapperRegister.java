package ru.necs.common.orika;

import ma.glasnost.orika.MapperFactory;

public interface MapperRegister {

    void register(MapperFactory mapperFactory);
}
