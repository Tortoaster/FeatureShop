package com.toinerick.spl.plugins.message;

import java.io.Serializable;

public interface MessageFlag extends Serializable {

    String preprocess(String message);

    String postprocess(String message);
}
