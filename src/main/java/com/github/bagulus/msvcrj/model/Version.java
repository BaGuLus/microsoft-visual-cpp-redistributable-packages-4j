package com.github.bagulus.msvcrj.model;

    public enum Version {
        V2005,
        V2008,
        V2010,
        V2012,
        V2013,
        V2015PLUS;

        @Override
        public String toString() {
            return name().substring(1).replace("_", "-");
        }
    }
