package com.boatfly.designpattern.proxy.statics;

public class TeatherProxy implements ITeather {

    private ITeather teather;

    public TeatherProxy(ITeather _teather){
        this.teather = _teather;
    }

    @Override
    public void teach() {
        System.out.println("teach before");
        teather.teach();
        System.out.println("teach after");
    }
}
