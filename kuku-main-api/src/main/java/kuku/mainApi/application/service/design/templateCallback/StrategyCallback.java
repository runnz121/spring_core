package kuku.mainApi.application.service.design.templateCallback;

import kuku.mainApi.domain.test.TestEntity;

public interface StrategyCallback<R extends TestEntity, T extends TestEntity> {

    R businessCall(T t);
}
