package com.example.demoVaadin;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Michal on 2017-06-07.
 */
@SpringUI
public class MyVaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(initContent());
    }

    private VerticalLayout initContent() {
        VerticalLayout components = new VerticalLayout();
        MyComboBox<BalanceCode> c = new MyComboBox<>(BalanceCode.class, filter -> IntStream.range(1, 100000).boxed().map(i -> new BalanceCode(String.valueOf(i))).filter(bc -> bc.getCode().contains(filter)).collect(Collectors.toList()), s -> new BalanceCode(s));
        components.addComponent(c);
        return components;
    }

    public static class BalanceCode {
        private String code;

        public BalanceCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
