package com.example.hiennv.basicsampleroomdatabinding.model;

import java.util.Date;

public interface Comment {
    int getId();
    int getProductId();
    String getText();
    Date getPostedAt();
}
