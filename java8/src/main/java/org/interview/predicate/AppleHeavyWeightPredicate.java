package org.interview.predicate;

import org.interview.model.Apple;

public class AppleHeavyWeightPredicate implements ApplePredicate{
    @Override
    public Boolean test(Apple apple) {
        if(apple.getWeight() > 150.0){
            return true;
        }
        return false;
    }
}
