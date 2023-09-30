package com.analysis.json.parsing.model;

public class SampleResponse {
    private final DependentObject1 obj1;
    private final DependentObject2 obj2;

    public SampleResponse(SampleResponseBuilder builder) {
        this.obj1 = builder.obj1;
        this.obj2 = builder.obj2;
    }

    public static class SampleResponseBuilder {
        private DependentObject1 obj1;
        private DependentObject2 obj2;

        private SampleResponseBuilder() {
        }

        public static SampleResponseBuilder newInstance() {
            return new SampleResponseBuilder();
        }

        public SampleResponseBuilder setObj1(DependentObject1 obj1){
            if(obj1 != null) {
                this.obj1 = obj1;
            }
            return this;
        }

        public SampleResponseBuilder setObj2(DependentObject2 obj2){
            if(obj2 != null) {
                this.obj2 = obj2;
            }
            return this;
        }

        // build method to deal with outer class
        // to return outer instance
        public SampleResponse build()
        {
            return new SampleResponse(this);
        }
    }

    @Override
    public String toString() {
        return "SampleResponse{" +
                "obj1=" + obj1 +
                ", obj2=" + obj2 +
                '}';
    }
}
