package com.test

import org.concordion.integration.junit4.ConcordionRunner
import org.junit.runner.RunWith

@RunWith(ConcordionRunner.class)
class CreateMethodFromUsage {

    public A field

    def A method() {
        null
    }

    class A {

    }
}
