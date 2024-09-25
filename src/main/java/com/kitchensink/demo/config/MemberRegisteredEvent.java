package com.kitchensink.demo.config;
import org.springframework.context.ApplicationEvent;

import com.kitchensink.demo.model.Member;

public class MemberRegisteredEvent extends ApplicationEvent {

    private Member member;

    public MemberRegisteredEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
