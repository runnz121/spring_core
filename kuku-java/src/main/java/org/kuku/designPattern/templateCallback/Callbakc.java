package org.kuku.designPattern.templateCallback;

public interface Callbakc {

    int execute(int a);
}

class Template {
    int work(Callbakc callbakc) {
        System.out.println("콜백시작 ==== (변하는 메서드)");
        int num = 100;
        return callbakc.execute(num);
    }
}

