package org.interview.predicate;

import org.interview.model.Apple;

public class AppleGreenColorPredicate implements ApplePredicate{
    @Override
    public Boolean test(Apple apple) {
        if(apple.getColor().equals("GREEN")){
            return true;
        }
        return false;
    }
}
