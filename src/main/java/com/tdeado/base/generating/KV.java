package com.tdeado.base.generating;

public class KV{
        /**
         * k : 1
         * t : 标题1
         */
        private String k;
        private String t;
        public String getK() {
            return k;
        }
        public void setK(String k) {
            this.k = k;
        }
        public String getT() {
            return t;
        }
        public void setT(String t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return "KV{" +
                    "k='" + k + '\'' +
                    ", t='" + t + '\'' +
                    '}';
        }
    }