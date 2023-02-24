package com.example.qr_quest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AvatarTest {
    private Avatar mockAvatar() {
        return new Avatar("123456");
    }

    @Test
    public void getAvatarName() {
        Avatar avatar = mockAvatar();
        assertEquals("FatOneHeadedTrickyLandKong", avatar.getAvatarName());
    }

    @Test
    public void getAvatarFigure() {
        Avatar avatar = mockAvatar();
        assertEquals(":####:\n" + "e: - - :e\n" + ":  V  :\n" + ":  n  :\n" + ":####:", avatar.getAvatarFigure());
    }

}