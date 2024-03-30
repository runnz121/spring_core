package kuku.mainApi.application.service.design.templateCallback;

import kuku.mainApi.domain.test.TestEntity;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class TemplateService {

    @SuppressWarnings("unchecked")
    public <R extends TestEntity, T extends TestEntity> R execute(T t, StrategyCallback<R, T> callback) {

        try {
            loggingStart();

            R result = callback.businessCall(t);

            loggingEnd();

            return result;
        } catch (Exception e) {
            return (R) TestEntity.EMPTY;
        }
    }

    private void loggingStart() {
        log.info("======== 로깅 시작 =======");
    }

    private void loggingEnd() {
        log.info("========= 로그 종료 =======");
    }
}
