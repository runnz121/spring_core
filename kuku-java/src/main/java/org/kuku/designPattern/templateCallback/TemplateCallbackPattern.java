package org.kuku.designPattern.templateCallback;

/**
 * 사용처
 * 1. 일정한 패턴을 갖는 작업 흐름이 존재
 * 2. 전체 흐름 중 일부 분만 바꿔서 사용 해야 하는 경우
 *
 */
public class TemplateCallbackPattern {

    public static void main(String[] args) {

        System.out.println("로깅 시작 ==== (공통 메서드)");

        Template template = new Template();
        int result = template.work(a -> a * a);
        System.out.println(result);

        System.out.println("로깅 종료 ==== (공통 메서드)");
    }
}